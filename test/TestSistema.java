package test;

import com.smartmobility.manager.*;
import com.smartmobility.dao.*;
import com.smartmobility.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestSistema {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        BookingManager bookingManager = new BookingManager();
        AdminManager adminManager = new AdminManager();
        CommunicationManager commManager = new CommunicationManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO();

        System.out.println("--- INIZIO TEST DI INTEGRAZIONE COMPLETO ---");

        try {
            // 1. Setup: Account e Veicolo
            String email = "utente_sistema_" + System.currentTimeMillis() + "@test.it";
            Account user = userManager.registraAccount("Mario", "Sistema", email);

            // --- AGGIUNTA PER IL RESET ---
            Veicolo auto = veicoloDAO.readByCodice("AUTO-001");
            auto.setStatoOperativo(StatoVeicolo.DISPONIBILE); // Forza lo stato a disponibile
            veicoloDAO.updateStatoEPosizione(auto); // Salva su DB
            System.out.println("1. Veicolo AUTO-001 resettato su DISPONIBILE.");
            // -----------------------------

            // 2. Flusso Booking
            System.out.println("-> Test Prenotazione e Noleggio...");
            Prenotazione p = bookingManager.prenotaVeicolo(user, auto, "Stazione Centrale");
            Noleggio n = bookingManager.avviaNoleggio(user, auto);
            bookingManager.concludiNoleggio(n);
            System.out.println("   [OK] Ciclo Noleggio completato.");

            // 3. Test Amministrazione (Guasto)
            System.out.println("-> Test Segnalazione Guasto...");
            SegnalazioneGuasto guasto = new SegnalazioneGuasto("G-" + System.currentTimeMillis(), "Motore",
                    "Rumore strano", LocalDateTime.now());
            SegnalazioneDAO sDAO = new SegnalazioneDAO();
            sDAO.createGuasto(guasto, user.getEmail(), auto.getCodiceIdentificativo());
            System.out.println("   [OK] Guasto registrato.");

            // 4. Test Servizi Aggiuntivi (Assistenza)
            System.out.println("-> Test Assistenza...");
            SessioneAssistenza sess = commManager.richiediAssistenza(user, "Tecnico", "Non riesco a sbloccare");
            boolean opDisponibile = commManager.verificaDisponibilitaOperatori();
            if (opDisponibile) {
                Account operatore = new Account("Op", "Test", "op@test.it"); // Stub
                commManager.inoltraRichiesta(sess, operatore);
            }
            System.out.println("   [OK] Flusso assistenza terminato.");

        } catch (Exception e) {
            System.err.println("❌ TEST DI SISTEMA FALLITO: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("\n--- TEST DI INTEGRAZIONE SUPERATO CON SUCCESSO ---");
    }
}