package src;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/submit", e -> {
            handleCors(e);
            if (e.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                e.sendResponseHeaders(204, -1);
                return;
            }
            if (e.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    String body = new String(e.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Map<String, String> p = parse(body);
                    SharedMemoryEngine.saveEntry(p.get("u"), p.get("m"), p.get("r"), p.get("msg"));
                    send(e, "SUCCESS", 200);
                } catch (Exception ex) {
                    send(e, "ERROR", 500);
                }
            }
        });

        server.createContext("/api/data", e -> {
            handleCors(e);
            if (e.getRequestMethod().equalsIgnoreCase("GET")) {
                e.getResponseHeaders().add("Content-Type", "application/json");
                String json = "{\"logs\":" + SharedMemoryEngine.getLogsJSON() + ", \"stats\":" + SharedMemoryEngine.getYearlyStats() + "}";
                send(e, json, 200);
            }
        });

        System.out.println("✅ Campus Portal Engine LIVE on Port 8080");
        server.start();
    }

    private static void handleCors(HttpExchange e) {
        e.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        e.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        e.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static Map<String, String> parse(String b) {
        Map<String, String> m = new HashMap<>();
        for(String kv : b.split("&")) {
            String[] s = kv.split("=");
            if(s.length > 1) {
                try { m.put(s[0], URLDecoder.decode(s[1], "UTF-8")); } catch(Exception ex) {}
            }
        }
        return m;
    }

    private static void send(HttpExchange e, String r, int c) throws IOException {
        byte[] b = r.getBytes(StandardCharsets.UTF_8);
        e.sendResponseHeaders(c, b.length);
        e.getResponseBody().write(b);
        e.getResponseBody().close();
    }
}