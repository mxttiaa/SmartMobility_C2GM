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
                String durataStr = getQueryParam(query, "durata");

                if (codiceVeicolo == null || durataStr == null) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Parametri codiceVeicolo e durata obbligatori\"}");
                    return;
                }

                int minutiStimati;
                try {
                    minutiStimati = Integer.parseInt(durataStr);
                    if (minutiStimati <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    sendResponse(exchange, 400, "{\"status\": \"error\", \"message\": \"Durata stimata non valida\"}");
                    return;
                }

                Veicolo veicolo = veicoloDAO.readByCodice(codiceVeicolo);
                if (veicolo == null) {
                    sendResponse(exchange, 404, "{\"status\": \"error\", \"message\": \"Veicolo non trovato\"}");
                    return;
                }

                // Calcolo preventivo utilizzando PricingManager
                Tariffa tariffa = pricingManager.getTariffaPerVeicolo(veicolo);
                double preventivo = pricingManager.calcolaPreventivo(tariffa, minutiStimati * 0.33f); // Simuliamo distanza basata sul tempo per usare l'API esistente o usiamo minuti
                // Wait, PricingManager's calcolaPreventivo takes (Tariffa tariffa, float distanza).
                // Let's assume 1 km = 3 minuti in media, so distanza = minutiStimati / 3.0f
                float distanzaStimata = minutiStimati / 3.0f;
                double preventivoCalcolato = pricingManager.calcolaPreventivo(tariffa, distanzaStimata);

                sendResponse(exchange, 200, "{\"status\": \"success\", \"preventivo\": " + preventivoCalcolato + "}");

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
