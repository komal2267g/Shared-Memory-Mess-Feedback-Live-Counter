package src;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SharedMemoryEngine {
    private static final String FILE_NAME = "data/campus_governance.bin";
    private static final int DAY_BLOCK = 128; 
    private static final int LOG_BLOCK = 256; 
    private static final int MAX_LOGS = 1000;
    private static final int HEADER_OFFSET = 366 * DAY_BLOCK;
    private static final int TOTAL_SIZE = HEADER_OFFSET + (MAX_LOGS * LOG_BLOCK) + 128;

    private static MappedByteBuffer buffer;

    static {
        try {
            File f = new File(FILE_NAME);
            boolean isNew = !f.exists();
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.setLength(TOTAL_SIZE);
            buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, TOTAL_SIZE);
            
            if (isNew) {
                buffer.putInt(TOTAL_SIZE - 40, 0); 
                buffer.putInt(TOTAL_SIZE - 30, 0); 
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static synchronized void saveEntry(String user, String meal, String rate, String msg) {
        try {
            int dayOffset = LocalDate.now().getDayOfYear() * DAY_BLOCK;
            int rateIdx = rate.equalsIgnoreCase("Good") ? 0 : (rate.equalsIgnoreCase("Average") ? 4 : 8);
            buffer.putInt(dayOffset + rateIdx, buffer.getInt(dayOffset + rateIdx) + 1);

            int count = buffer.getInt(TOTAL_SIZE - 40);
            int pointer = buffer.getInt(TOTAL_SIZE - 30);
            int off = HEADER_OFFSET + (pointer * LOG_BLOCK);

            // Important: Use YYYY-MM-DD format for easy JS filtering
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            writeStr(off, time, 20);
            writeStr(off + 25, user, 20);
            writeStr(off + 50, meal, 15);
            writeStr(off + 70, rate, 15);
            writeStr(off + 90, msg, 150);

            buffer.putInt(TOTAL_SIZE - 40, count + 1);
            buffer.putInt(TOTAL_SIZE - 30, (pointer + 1) % MAX_LOGS);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static String getYearlyStats() {
        int g=0, a=0, p=0;
        for(int i=1; i<=365; i++) {
            int off = i * DAY_BLOCK;
            g += buffer.getInt(off); a += buffer.getInt(off + 4); p += buffer.getInt(off + 8);
        }
        return String.format("[%d, %d, %d]", g, a, p);
    }

    public static String getLogsJSON() {
        StringBuilder json = new StringBuilder("[");
        int count = buffer.getInt(TOTAL_SIZE - 40);
        int toRead = Math.min(count, MAX_LOGS);
        for (int i = 0; i < toRead; i++) {
            int off = HEADER_OFFSET + (i * LOG_BLOCK);
            json.append(String.format("{\"t\":\"%s\",\"u\":\"%s\",\"m\":\"%s\",\"r\":\"%s\",\"msg\":\"%s\"},",
                readStr(off), readStr(off + 25), readStr(off + 50), readStr(off + 70), readStr(off + 90)));
        }
        if (json.length() > 1) json.setLength(json.length() - 1);
        return json.append("]").toString();
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