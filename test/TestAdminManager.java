package test;

import com.smartmobility.manager.AdminManager;
import com.smartmobility.manager.UserManager;
import com.smartmobility.model.*;
import com.smartmobility.dao.VeicoloDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestAdminManager {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        AdminManager adminManager = new AdminManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO();

        System.out.println("--- INIZIO TEST ADMIN MANAGER ---");

        try {
            // 1. Creazione dell'account Admin (se non esiste)
            String emailAdmin = "admin@zootropolis.it";
            Account admin = null;
            try {
                admin = userManager.registraAccount("Admin", "Super", emailAdmin);
                System.out.println("1. Account Admin creato: " + emailAdmin);
            } catch (IllegalArgumentException e) {
                System.out.println("1. Account Admin già esistente, procedo.");
            }

            // 2. Creazione Utente Standard
            String emailUtente = "segnalatore_" + System.currentTimeMillis() + "@email.it";
            Account utente = userManager.registraAccount("Luigi", "Verdi", emailUtente);

            // 3. Test Segnalazione Supporto
            System.out.println("\n3. Creazione Segnalazione...");
            SegnalazioneSupporto ticket = new SegnalazioneSupporto("TICKET-" + System.currentTimeMillis(),
                    "Problema con l'app", LocalDateTime.now());
            // Nota: per semplicità usiamo direttamente il DAO qui per simulare l'apertura
            com.smartmobility.dao.SegnalazioneDAO segDAO = new com.smartmobility.dao.SegnalazioneDAO();
            segDAO.createSupporto(ticket, utente.getEmail());
            System.out.println("Ticket creato. Stato iniziale: " + ticket.getStato());

            // 4. Test Presa in Carico (Manager)
            System.out.println("\n4. Presa in carico del Ticket...");
            Account operatore = new Account("Operatore", "Test", emailAdmin); // Simuliamo l'oggetto admin
            adminManager.assegnaSegnalazione(operatore, ticket);
            System.out.println("Stato dopo assegnazione: " + ticket.getStato());

            // 5. Test Regola Urbana
            System.out.println("\n5. Creazione Regola Urbana (ZTL)...");
            RegolaUrbana ztl = new RegolaUrbana("ZTL-CENTRO", TipoRestrizione.ZTL, new ArrayList<>(), 30,
                    LocalDateTime.now(), LocalDateTime.now().plusDays(30));

            // Passiamo l'oggetto "admin" che abbiamo creato al punto 1 del test
            adminManager.aggiungiRegolaUrbana(admin, ztl);

            System.out.println("ZTL inserita con successo.");

            // 6. Test Blocco Remoto Veicolo (recuperiamo l'AUTO-001 dello step precedente)
            System.out.println("\n6. Test Sicurezza - Blocco Remoto...");
            Veicolo auto = veicoloDAO.readByCodice("AUTO-001");
            if (auto != null) {
                adminManager.forzaBloccoRemoto(auto);
                auto = veicoloDAO.readByCodice("AUTO-001"); // Rileggiamo per conferma
                System.out.println("Stato dell'auto AUTO-001 forzato a: " + auto.getStatoOperativo());
            } else {
                System.out.println("Nessun veicolo 'AUTO-001' trovato per testare il blocco.");
            }

            // 7. Test Statistiche
            System.out.println("\n7. Generazione Report...");
            ReportStatistico report = adminManager.generaReportStatistico("Noleggi_Mese");
            System.out.println("Report generato con ID: " + report.getIdReport());

        } catch (Exception e) {
            System.out.println("❌ Errore durante l'esecuzione del test: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- FINE TEST ---");
    }
}