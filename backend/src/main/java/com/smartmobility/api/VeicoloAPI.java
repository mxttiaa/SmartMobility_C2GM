package com.smartmobility.api;

import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.model.Automobile;
import com.smartmobility.model.Bicicletta;
import com.smartmobility.model.Monopattino;
import com.smartmobility.model.Veicolo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class VeicoloAPI implements HttpHandler {

    private VeicoloDAO veicoloDAO;

    public VeicoloAPI() {
        this.veicoloDAO = new VeicoloDAO();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Headers CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                // Recupero lista veicoli dal DB
                List<Veicolo> veicoli = veicoloDAO.readAll();
                
                // Creazione manuale dell'array JSON
                String jsonElements = veicoli.stream().map(v -> {
                    String tipo = "Generico";
                    if (v instanceof Automobile) {
                        tipo = "Automobile";
                    } else if (v instanceof Monopattino) {
                        tipo = "Monopattino";
                    } else if (v instanceof Bicicletta) {
                        tipo = "Bicicletta";
                    }
                    
                    return "{" +
                        "\"codice\": \"" + v.getCodiceIdentificativo() + "\", " +
                        "\"modello\": \"" + tipo + "\", " +
                        "\"stato\": \"" + v.getStatoOperativo().name() + "\"" +
                        "}";
                }).collect(Collectors.joining(", "));
                
                String jsonResponse = "[" + jsonElements + "]";
                
                // Invio risposta HTTP 200
                sendResponse(exchange, 200, jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                // Gestione eccezioni HTTP 500
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
            }
        } else {
            // Metodo non consentito
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
