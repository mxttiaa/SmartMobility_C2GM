package backend;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Controller per la gestione dell'endpoint GET /api/mezzo e /api/mezzi/vicini.
 */
public class MezzoController implements HttpHandler {

    private MezzoManager mezzoManager;

    public MezzoController() {
        this.mezzoManager = new MezzoManager();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Imposta le intestazioni CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Validazione token di sessione
            String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                sendResponse(exchange, 401, "{\"errore\": \"Non autorizzato: token mancante\"}");
                return;
            }

            String token = authHeader.substring(7);
            Integer idUtente = SessionManager.getInstance().getUserIdByToken(token);
            if (idUtente == null) {
                sendResponse(exchange, 401, "{\"errore\": \"Non autorizzato: token non valido o scaduto\"}");
                return;
            }

            String path = exchange.getRequestURI().getPath();

            if ("/api/mezzi/vicini".equals(path)) {
                handleVicini(exchange);
            } else if ("/api/mezzo".equals(path)) {
                handleSingoloMezzo(exchange);
            } else {
                sendResponse(exchange, 404, "{\"errore\": \"Endpoint non trovato\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"errore\": \"Metodo non consentito\"}");
        }
    }

    private void handleVicini(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            sendResponse(exchange, 400, "{\"errore\": \"Parametri mancanti\"}");
            return;
        }

        double lat = 0, lon = 0;
        int raggio = 1500;
        String categoria = "";

        try {
            String[] params = query.split("&");
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    String key = pair[0];
                    String value = pair[1];
                    if ("lat".equals(key))
                        lat = Double.parseDouble(value);
                    else if ("lon".equals(key))
                        lon = Double.parseDouble(value);
                    else if ("raggio".equals(key))
                        raggio = Integer.parseInt(value);
                    else if ("categoria".equals(key))
                        categoria = java.net.URLDecoder.decode(value, "UTF-8");
                }
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"errore\": \"Formato parametri non valido\"}");
            return;
        }

        List<Mezzo> vicini = mezzoManager.trovaMezziInVicinanza(lat, lon, raggio, categoria);

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < vicini.size(); i++) {
            Mezzo m = vicini.get(i);
            double distanzaStimata = mezzoManager.calcolaDistanzaStimata(m);
            jsonBuilder.append("{")
                    .append("\"idMezzo\":").append(m.getIdMezzo()).append(",")
                    .append("\"tipologia\":\"").append(m.getTipologia()).append("\",")
                    .append("\"portataMassima\":").append(m.getPortataMassima()).append(",")
                    .append("\"livelloBatteria\":").append(m.getLivelloBatteria()).append(",")
                    .append("\"distanzaStimata\":").append(distanzaStimata).append(",")
                    .append("\"latitudine\":").append(m.getLatitudine()).append(",")
                    .append("\"longitudine\":").append(m.getLongitudine())
                    .append("}");
            if (i < vicini.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        sendResponse(exchange, 200, jsonBuilder.toString());
    }

    private void handleSingoloMezzo(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.contains("idMezzo=")) {
            sendResponse(exchange, 400, "{\"errore\": \"Richiesta non valida: idMezzo mancante\"}");
            return;
        }

        int idMezzo = -1;
        try {
            String idStr = query.split("idMezzo=")[1].split("&")[0];
            idMezzo = Integer.parseInt(idStr);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"errore\": \"Richiesta non valida: idMezzo formato errato\"}");
            return;
        }

        Mezzo mezzo = mezzoManager.getMezzo(idMezzo);
        if (mezzo == null) {
            sendResponse(exchange, 404, "{\"errore\": \"Mezzo non trovato\"}");
            return;
        }

        double distanzaStimata = mezzoManager.calcolaDistanzaStimata(mezzo);

        String jsonRes = "{" +
                "\"idMezzo\":" + mezzo.getIdMezzo() + "," +
                "\"tipologia\":\"" + mezzo.getTipologia() + "\"," +
                "\"portataMassima\":" + mezzo.getPortataMassima() + "," +
                "\"livelloBatteria\":" + mezzo.getLivelloBatteria() + "," +
                "\"distanzaStimata\":" + distanzaStimata +
                "}";

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        sendResponse(exchange, 200, jsonRes);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
