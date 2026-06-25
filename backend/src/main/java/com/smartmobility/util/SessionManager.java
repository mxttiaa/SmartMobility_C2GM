package com.smartmobility.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

/**
 * Gestione sessioni server-side.
 * Mantiene una mappa in-memory dei token di sessione attivi.
 * Ogni sessione contiene email, ruolo e timestamp di creazione.
 */
public class SessionManager {

    private static final ConcurrentHashMap<String, SessionData> sessions = new ConcurrentHashMap<>();
    private static final long SESSION_DURATION_MS = 8 * 60 * 60 * 1000; // 8 ore

    public static class SessionData {
        private final String email;
        private final String ruolo;
        private final long createdAt;

        public SessionData(String email, String ruolo) {
            this.email = email;
            this.ruolo = ruolo;
            this.createdAt = System.currentTimeMillis();
        }

        public String getEmail() { return email; }
        public String getRuolo() { return ruolo; }
        public long getCreatedAt() { return createdAt; }

        public boolean isExpired() {
            return (System.currentTimeMillis() - createdAt) > SESSION_DURATION_MS;
        }
    }

    /**
     * Crea una nuova sessione per l'utente autenticato.
     * @return il token UUID generato
     */
    public static String createSession(String email, String ruolo) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, new SessionData(email, ruolo));
        return token;
    }

    /**
     * Recupera i dati di sessione dal token.
     * @return SessionData oppure null se il token non esiste o è scaduto
     */
    public static SessionData getSession(String token) {
        if (token == null) return null;
        SessionData data = sessions.get(token);
        if (data == null) return null;
        if (data.isExpired()) {
            sessions.remove(token);
            return null;
        }
        return data;
    }

    /**
     * Distrugge una sessione (logout).
     */
    public static void destroySession(String token) {
        if (token != null) {
            sessions.remove(token);
        }
    }

    /**
     * Verifica se un token è valido e non scaduto.
     */
    public static boolean isValid(String token) {
        return getSession(token) != null;
    }

    /**
     * Estrae il token dall'header Authorization.
     * Formato atteso: "Bearer <token>"
     */
    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
