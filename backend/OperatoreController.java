package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Controller per la gestione delle richieste REST relative all'Operatore.
 */
public class OperatoreController implements HttpHandler {

    private MezzoManager mezzoManager;

    public OperatoreController() {
        this.mezzoManager = new MezzoManager();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Aggiunta degli header CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Gestione Preflight
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            // Verificare auth token/ruolo in uno scenario reale (qui tralasciato per semplicità di mock)
            List<Mezzo> mezzi = mezzoManager.getAllMezzi();

            // Costruiamo la risposta JSON manualmente per evitare dipendenze esterne
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < mezzi.size(); i++) {
                Mezzo m = mezzi.get(i);
                json.append("{")
                    .append("\"idMezzo\":").append(m.getIdMezzo()).append(",")
                    .append("\"tipologia\":\"").append(m.getTipologia() != null ? m.getTipologia() : "").append("\",")
                    .append("\"latitudine\":").append(m.getLatitudine()).append(",")
                    .append("\"longitudine\":").append(m.getLongitudine()).append(",")
                    .append("\"statoOperativo\":\"").append(m.getStatoOperativo() != null ? m.getStatoOperativo() : "disponibile").append("\"")
                    .append("}");
                
                if (i < mezzi.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            sendResponse(exchange, 200, json.toString());
        } else {
            sendResponse(exchange, 405, "{\"errore\":\"Metodo non consentito, atteso GET\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] bytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
