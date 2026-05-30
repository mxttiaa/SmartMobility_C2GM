package backend;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestisce le sessioni degli utenti autenticati mantenendole in memoria RAM.
 * (In un ambiente di produzione reale si userebbe Redis o JWT stateless).
 */
public class SessionManager {

    private static SessionManager instance;
    private Map<String, Integer> activeSessions;

    private SessionManager() {
        activeSessions = new ConcurrentHashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Crea una nuova sessione per l'utente, genera un token e lo associa all'ID.
     * @param idUtente L'ID dell'utente autenticato.
     * @return Il token di sessione (Bearer) generato.
     */
    public String createSession(int idUtente) {
        String token = UUID.randomUUID().toString();
        activeSessions.put(token, idUtente);
        return token;
    }

    /**
     * Recupera l'ID dell'utente a partire dal token inviato.
     * @param token Il token Bearer.
     * @return L'ID utente (Integer) se il token è valido, altrimenti null.
     */
    public Integer getUserIdByToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        return activeSessions.get(token);
    }

    /**
     * Invalida (distrugge) una sessione in memoria.
     * @param token Il token da rimuovere.
     */
    public void invalidateSession(String token) {
        if (token != null) {
            activeSessions.remove(token);
        }
    }
}
