package src;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SharedMemoryEngine {
    private static final String FILE_NAME = "data/hostel_v4.bin"; 
    private static final int MAX_LOGS = 100;
    private static final int LOG_BLOCK = 256;
    private static final int STATS_START = 1024;   // 1KB
    private static final int LOGS_START = 20480;   // 20KB
    private static final int TOTAL_SIZE = 1024 * 1024; // 1MB

    private static MappedByteBuffer buffer;

    static {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw");
            raf.setLength(TOTAL_SIZE);
            buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, TOTAL_SIZE);
            
            // Initialize pointers if file is new
            if (buffer.getInt(0) < 0) { // Safety check
                buffer.putInt(0, 0); 
                buffer.putInt(4, 0); 
                buffer.force();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static synchronized void saveEntry(String user, String meal, String rate, String msg) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw");
             FileChannel channel = raf.getChannel()) {
            
            // 1. DISTRIBUTED MUTEX: Prevents Race Conditions
            FileLock lock = channel.lock(); 
            try {
                // 2. Update Daily Stats
                int dayOffset = STATS_START + (LocalDate.now().getDayOfYear() * 16);
                int rateIdx = rate.equalsIgnoreCase("Good") ? 0 : (rate.equalsIgnoreCase("Average") ? 4 : 8);
                buffer.putInt(dayOffset + rateIdx, buffer.getInt(dayOffset + rateIdx) + 1);

                // 3. Save Audit Log Record
                int count = buffer.getInt(0);
                int pointer = buffer.getInt(4);
                int off = LOGS_START + (pointer * LOG_BLOCK);

                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                writeStr(off, time, 20);
                writeStr(off + 25, user, 20);
                writeStr(off + 50, meal, 15);
                writeStr(off + 70, rate, 15);
                writeStr(off + 90, (msg == null || msg.isEmpty()) ? "N/A" : msg, 150);

                // 4. Update Global Pointers
                buffer.putInt(0, count + 1);
                buffer.putInt(4, (pointer + 1) % MAX_LOGS);
                buffer.force(); // Ensure persistence in memory segment

            } finally {
                if (lock != null) lock.release();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static String getLogsJSON() {
        StringBuilder json = new StringBuilder("[");
        int count = buffer.getInt(0);
        int toRead = Math.min(count, MAX_LOGS);
        for (int i = 0; i < toRead; i++) {
            int off = LOGS_START + (i * LOG_BLOCK);
            json.append(String.format("{\"t\":\"%s\",\"u\":\"%s\",\"m\":\"%s\",\"r\":\"%s\",\"msg\":\"%s\"},",
                readStr(off), readStr(off + 25), readStr(off + 50), readStr(off + 70), readStr(off + 90)));
        }
        if (json.length() > 1) json.setLength(json.length() - 1);
        return json.append("]").toString();
    }

    public static String getYearlyStats() {
        int dayOffset = STATS_START + (LocalDate.now().getDayOfYear() * 16);
        return String.format("[%d, %d, %d]", 
            buffer.getInt(dayOffset), buffer.getInt(dayOffset + 4), buffer.getInt(dayOffset + 8));
    }

    private static void writeStr(int o, String s, int m) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        int l = Math.min(b.length, m);
        buffer.putInt(o, l);
        for(int i=0; i<l; i++) buffer.put(o + 4 + i, b[i]);
    }

    private static String readStr(int o) {
        int l = buffer.getInt(o);
        if(l<=0 || l>250) return "";
        byte[] b = new byte[l];
        for(int i=0; i<l; i++) b[i] = buffer.get(o + 4 + i);
        return new String(b, StandardCharsets.UTF_8);
    }
}