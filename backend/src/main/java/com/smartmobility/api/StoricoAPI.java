package com.smartmobility.api;

import com.smartmobility.dao.NoleggioDAO;
import com.smartmobility.dao.NoleggioDAO.StoricoItem;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class StoricoAPI implements HttpHandler {

    private NoleggioDAO noleggioDAO;

    public StoricoAPI() {
        this.noleggioDAO = new NoleggioDAO();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                // Estrarre il parametro email dall'URL query string
                String query = exchange.getRequestURI().getQuery();
                String email = null;
                
                if (query != null && query.contains("email=")) {
                    for (String param : query.split("&")) {
                        String[] pair = param.split("=");
                        if (pair.length > 1 && "email".equals(pair[0])) {
                            email = java.net.URLDecoder.decode(pair[1], StandardCharsets.UTF_8.name());
                            break;
                        }
                    }
                }
                
                if (email == null || email.isEmpty()) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametro 'email' mancante\"}");
                    return;
                }

                // Recupero storico dal DAO
                List<StoricoItem> storico = noleggioDAO.readByEmail(email);
                
                // Creazione array JSON manuale
                String jsonElements = storico.stream().map(item -> 
                    "{" +
                    "\"codiceVeicolo\": \"" + item.codiceVeicolo + "\", " +
                    "\"dataInizio\": \"" + item.dataInizio + "\", " +
                    "\"stato\": \"" + item.stato + "\"" +
                    "}"
                ).collect(Collectors.joining(", "));
                
                String jsonResponse = "[" + jsonElements + "]";
                
                sendResponse(exchange, 200, jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
