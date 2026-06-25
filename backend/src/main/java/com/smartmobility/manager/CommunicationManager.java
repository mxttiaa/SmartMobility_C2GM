package com.smartmobility.manager;

import com.smartmobility.dao.SessioneAssistenzaDAO;
import com.smartmobility.model.Account;
import com.smartmobility.model.SessioneAssistenza;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommunicationManager {

    private SessioneAssistenzaDAO sessioneDAO;

    public CommunicationManager() {
        this.sessioneDAO = new SessioneAssistenzaDAO();
    }

    public SessioneAssistenza richiediAssistenza(String email, String categoria, String dettagli) {
        if (email == null || email.isEmpty()) return null;
        
        SessioneAssistenza sessione = new SessioneAssistenza(
            "SESS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
            categoria,
            dettagli,
            LocalDateTime.now()
        );
        
        sessioneDAO.create(sessione, email);
        System.out.println("Nuova sessione di assistenza creata con ID: " + sessione.getIdSessione());
        
        return sessione;
    }

    public boolean verificaDisponibilitaOperatori() {
        // Simulazione di verifica della disponibilità. In un caso reale si interrogherebbe 
        // il database per vedere se ci sono account con ruolo 'OPERATORE' non occupati.
        boolean disponibile = Math.random() > 0.2; // 80% di probabilità che ci sia un operatore
        System.out.println("Verifica disponibilità operatori: " + (disponibile ? "Operatore trovato" : "Nessun operatore libero"));
        return disponibile;
    }

    public void inoltraRichiesta(SessioneAssistenza sessione, Account operatore) {
        if (sessione == null || operatore == null) return;
        
        sessione.avvia(); // Imposta lo stato a IN_CORSO
        sessioneDAO.updateStato(sessione);
        sessioneDAO.assegnaOperatore(sessione.getIdSessione(), operatore.getEmail());
        
        System.out.println("Sessione " + sessione.getIdSessione() + " inoltrata con successo all'operatore " + operatore.getEmail());
    }
}
