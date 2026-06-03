package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RichiestaAssistenzaController implements HttpHandler {

    private RichiestaAssistenzaManager manager;

    public RichiestaAssistenzaController() {
        this.manager = new RichiestaAssistenzaManager();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);
        String method = exchange.getRequestMethod();

        if ("OPTIONS".equals(method)) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        // Auth extraction
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        Integer idUtente = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            idUtente = SessionManager.getInstance().getUserIdByToken(token);
        }

        // Fallback locale per permettere l'utilizzo senza token reale qualora non
        // disponibile
        if (idUtente == null) {
            idUtente = 1;
        }

        if ("POST".equals(method)) {
            String requestBody = readRequestBody(exchange);
            String descrizione = extractJsonField(requestBody, "descrizioneProblema");

            if (descrizione == null || descrizione.trim().isEmpty()) {
                sendResponse(exchange, 400, "{\"errore\":\"Descrizione obbligatoria mancante\"}");
                return;
            }

            RichiestaAssistenza salvata = manager.creaRichiesta(idUtente, descrizione);

            if (salvata != null) {
                String descClean = salvata.getDescrizioneProblema().replace("\"", "\\\"");
                String jsonResponse = String.format(
                        "{\"id\":%d, \"stato\":\"%s\", \"descrizioneProblema\":\"%s\"}",
                        salvata.getId(), salvata.getStato(), descClean);
                sendResponse(exchange, 201, jsonResponse);
            } else {
                sendResponse(exchange, 500, "{\"errore\":\"Errore durante il salvataggio nel database\"}");
            }

        } else if ("GET".equals(method)) {
            List<RichiestaAssistenza> lista = manager.getRichiesteUtente(idUtente);
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < lista.size(); i++) {
                RichiestaAssistenza r = lista.get(i);
                String descClean = r.getDescrizioneProblema() != null ? r.getDescrizioneProblema().replace("\"", "\\\"")
                        : "";
                sb.append(String.format("{\"id\":%d, \"stato\":\"%s\", \"descrizioneProblema\":\"%s\"}",
                        r.getId(), r.getStato(), descClean));
                if (i < lista.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
            sendResponse(exchange, 200, sb.toString());

        } else {
            sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso POST o GET\"}");
        }
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private String extractJsonField(String json, String field) {
        String regex = "\"" + field + "\"\\s*:\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
