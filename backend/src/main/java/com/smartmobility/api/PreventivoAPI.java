package com.smartmobility.api;

import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.manager.PricingManager;
import com.smartmobility.model.Veicolo;
import com.smartmobility.model.Tariffa;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PreventivoAPI implements HttpHandler {

    private VeicoloDAO veicoloDAO;
    private PricingManager pricingManager;

    public PreventivoAPI() {
        this.veicoloDAO = new VeicoloDAO();
        this.pricingManager = new PricingManager();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String codiceVeicolo = getQueryParam(query, "codiceVeicolo");
                String latStr = getQueryParam(query, "lat");
                String lonStr = getQueryParam(query, "lon");

                if (codiceVeicolo == null || latStr == null || lonStr == null) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametri codiceVeicolo, lat e lon obbligatori\"}");
                    return;
                }

                double destLat, destLon;
                try {
                    destLat = Double.parseDouble(latStr);
                    destLon = Double.parseDouble(lonStr);
                } catch (NumberFormatException e) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Coordinate non valide\"}");
                    return;
                }

                Veicolo veicolo = veicoloDAO.readByCodice(codiceVeicolo);
                if (veicolo == null) {
                    sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Veicolo non trovato\"}");
                    return;
                }

                // Calcolo distanza (Haversine formula in km)
                double vLat = veicolo.getCoordinateAttuali().getLatitudine();
                double vLon = veicolo.getCoordinateAttuali().getLongitudine();
                
                double earthRadius = 6371.0; // km
                double dLat = Math.toRadians(destLat - vLat);
                double dLon = Math.toRadians(destLon - vLon);
                double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                           Math.cos(Math.toRadians(vLat)) * Math.cos(Math.toRadians(destLat)) *
                           Math.sin(dLon/2) * Math.sin(dLon/2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                double distanza = earthRadius * c;

                if (distanza < 0.1) distanza = 0.1; // Minimo 100 metri per evitare costi 0

                // Dati veicolo (Velocità media in km/h, Costo al minuto in euro)
                double V = 20.0;
                double P_min = 0.20;
                
                if (veicolo instanceof com.smartmobility.model.Bicicletta) {
                    V = 15.0;
                    P_min = 0.12;
                } else if (veicolo instanceof com.smartmobility.model.Monopattino) {
                    V = 20.0;
                    P_min = 0.20;
                } else if (veicolo instanceof com.smartmobility.model.Automobile) {
                    V = 25.0;
                    P_min = 0.30;
                }

                // S = Costo di sblocco ($1.00 €)
                double S = 1.00;
                
                // formula: C = 1.00 + (D / V * 60) * P_min
                double minutiStimati = (distanza / V) * 60.0;
                double preventivoCalcolato = S + (minutiStimati * P_min);

                // Formatta json response
                String jsonResponse = String.format(java.util.Locale.US,
                    "{\"status\": \"success\", \"preventivo\": %.2f, \"distanza\": %.2f, \"minuti\": %d}",
                    preventivoCalcolato, distanza, Math.round(minutiStimati));

                sendResponse(exchange, 200, jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private String getQueryParam(String query, String param) {
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2 && kv[0].equals(param)) {
                return kv[1];
            }
        }
        return null;
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
