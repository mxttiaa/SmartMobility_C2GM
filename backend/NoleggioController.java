package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller per la gestione delle richieste REST relative ai noleggi.
 */
public class NoleggioController implements HttpHandler {

    private NoleggioManager noleggioManager;
    private MezzoManager mezzoManager;
    private TariffaManager tariffaManager;

    public NoleggioController() {
        this.noleggioManager = new NoleggioManager();
        this.mezzoManager = new MezzoManager();
        this.tariffaManager = new TariffaManager();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                // Verifica autenticazione
                String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    sendResponse(exchange, 401, "{\"errore\":\"Non autenticato\"}");
                    return;
                }

                String token = authHeader.substring(7);
                Integer userId = SessionManager.getInstance().getUserIdByToken(token);
                if (userId == null) {
                    sendResponse(exchange, 401, "{\"errore\":\"Token non valido o scaduto\"}");
                    return;
                }

                // Legge JSON, estrae idMezzo, durataMinuti, distanzaKm
                String requestBody = readRequestBody(exchange);
                
                Integer idMezzo = extractJsonInt(requestBody, "idMezzo");
                Integer durataMinuti = extractJsonInt(requestBody, "durataMinuti");
                Double distanzaKm = extractJsonDouble(requestBody, "distanzaKm");

                if (idMezzo == null || durataMinuti == null) {
                    sendResponse(exchange, 400, "{\"errore\":\"Parametri mancanti\"}");
                    return;
                }

                if (distanzaKm == null) {
                    distanzaKm = 0.0;
                }
                
                // Ottiene il Mezzo tramite MezzoManager
                Mezzo mezzo = mezzoManager.getMezzo(idMezzo);
                if (mezzo == null) {
                    sendResponse(exchange, 404, "{\"errore\":\"Mezzo non trovato\"}");
                    return;
                }
                
                // Ottiene la tipologia
                String tipo = mezzo.getTipologia();
                
                // Chiama TariffaManager.ottieniTariffaVigente(idMezzo, tipo)
                Tariffa tariffa = tariffaManager.ottieniTariffaVigente(idMezzo, tipo);
                if (tariffa == null) {
                    sendResponse(exchange, 404, "{\"errore\":\"Tariffa non trovata per il mezzo\"}");
                    return;
                }

                // Chiama NoleggioManager.stimareCostoNoleggio
                StimaCostoNoleggio stima = noleggioManager.stimareCostoNoleggio(userId, idMezzo, durataMinuti, distanzaKm);
                
                // Restituisce JSON
                String jsonResponse = String.format(java.util.Locale.US, "{\"importo\": %.2f, \"descrizione\": \"%s\"}", 
                                                    stima.getImporto(), 
                                                    stima.getDescrizione().replace("\"", "\\\""));
                sendResponse(exchange, 200, jsonResponse);
                
            } catch (IllegalArgumentException e) {
                // Errore 400 per parametri non validi
                sendResponse(exchange, 400, "{\"errore\":\"Parametri non validi\"}");
            } catch (Exception e) {
                // Gestisce eccezioni 500
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"errore\":\"Errore interno del server\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito\"}");
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
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private Integer extractJsonInt(String json, String field) {
        String regex = "\"" + field + "\"\\s*:\\s*(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private Double extractJsonDouble(String json, String field) {
        String regex = "\"" + field + "\"\\s*:\\s*(-?[\\d\\.]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return null;
    }
}
