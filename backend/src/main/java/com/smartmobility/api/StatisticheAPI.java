package com.smartmobility.api;

import com.smartmobility.util.DatabaseConnection;
import com.smartmobility.util.SessionManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticheAPI implements HttpHandler {

    private String[] allowedRoles;

    public StatisticheAPI(String... allowedRoles) {
        this.allowedRoles = allowedRoles;
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

        // Verifica Autenticazione server-side
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        String token = SessionManager.extractToken(authHeader);
        SessionManager.SessionData session = SessionManager.getSession(token);
        
        if (session == null) {
            sendResponse(exchange, 401, "{\"status\": \"error\", \"message\": \"Non autenticato: effettua il login\"}");
            return;
        }

        boolean authorized = false;
        if (allowedRoles != null) {
            for (String role : allowedRoles) {
                if (role.equals(session.getRuolo())) {
                    authorized = true;
                    break;
                }
            }
        }
        
        if (!authorized) {
            sendResponse(exchange, 403, "{\"status\": \"error\", \"message\": \"Accesso negato: ruolo non autorizzato\"}");
            return;
        }

        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            try {
                String jsonResponse = generaStatistiche();
                sendResponse(exchange, 200, jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"status\": \"error\", \"message\": \"Errore interno del server durante il calcolo delle statistiche\"}");
            }
        } else {
            sendResponse(exchange, 405, "{\"status\": \"error\", \"message\": \"Metodo non consentito\"}");
        }
    }

    private String generaStatistiche() throws SQLException {
        StringBuilder json = new StringBuilder("{");

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // 1. Tendenze di utilizzo (Conteggio noleggi per giorno degli ultimi 7 giorni)
            String sqlTendenze = "SELECT DATE(inizio_noleggio) as data_noleggio, COUNT(*) as totale " +
                                 "FROM noleggio GROUP BY DATE(inizio_noleggio) ORDER BY data_noleggio DESC LIMIT 7";
            List<String> tendenzeList = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(sqlTendenze);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String data = rs.getString("data_noleggio");
                    int totale = rs.getInt("totale");
                    tendenzeList.add(String.format("{\"data\": \"%s\", \"totale\": %d}", data, totale));
                }
            }
            json.append("\"tendenze\": [").append(String.join(",", tendenzeList)).append("], ");

            // 2. Efficienza della flotta (Conteggio veicoli per stato operativo)
            String sqlEfficienza = "SELECT stato_operativo, COUNT(*) as totale FROM veicolo GROUP BY stato_operativo";
            List<String> efficienzaList = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEfficienza);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String stato = rs.getString("stato_operativo");
                    int totale = rs.getInt("totale");
                    efficienzaList.add(String.format("{\"stato\": \"%s\", \"totale\": %d}", stato, totale));
                }
            }
            json.append("\"efficienza\": [").append(String.join(",", efficienzaList)).append("], ");

            // 3. Abbattimento emissioni (Simulato: km teorici percorsi in bici/monopattino x 120g CO2 risparmiati)
            // Velocità media stimata: Bici 15 km/h (0.25 km/min), Monopattino 20 km/h (0.33 km/min)
            // Emissione media auto evitata: 120 g CO2 / km
            String sqlEmissioni = "SELECT v.tipo_veicolo, SUM(TIMESTAMPDIFF(MINUTE, n.inizio_noleggio, n.fine_noleggio)) as minuti_totali " +
                                  "FROM noleggio n JOIN veicolo v ON n.veicolo_id = v.id " +
                                  "WHERE n.stato = 'CONCLUSO' AND v.tipo_veicolo IN ('BICICLETTA', 'MONOPATTINO') " +
                                  "GROUP BY v.tipo_veicolo";
            double co2RisparmiataKg = 0.0;
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEmissioni);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String tipo = rs.getString("tipo_veicolo");
                    int minuti = rs.getInt("minuti_totali");
                    if ("BICICLETTA".equals(tipo)) {
                        co2RisparmiataKg += (minuti * 0.25) * 120 / 1000.0;
                    } else if ("MONOPATTINO".equals(tipo)) {
                        co2RisparmiataKg += (minuti * 0.33) * 120 / 1000.0;
                    }
                }
            }
            json.append(String.format(java.util.Locale.US, "\"co2RisparmiataKg\": %.2f, ", co2RisparmiataKg));

            // 4. Tratte critiche / Veicoli più utilizzati
            String sqlTratte = "SELECT v.codice_identificativo, COUNT(n.id) as num_noleggi " +
                               "FROM noleggio n JOIN veicolo v ON n.veicolo_id = v.id " +
                               "GROUP BY v.codice_identificativo ORDER BY num_noleggi DESC LIMIT 5";
            List<String> tratteList = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(sqlTratte);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String codice = rs.getString("codice_identificativo");
                    int numNoleggi = rs.getInt("num_noleggi");
                    tratteList.add(String.format("{\"codice\": \"%s\", \"noleggi\": %d}", codice, numNoleggi));
                }
            }
            json.append("\"veicoliPiuUsati\": [").append(String.join(",", tratteList)).append("]");

        }
        
        json.append("}");
        return json.toString();
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
