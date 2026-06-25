package test;

import com.smartmobility.manager.BookingManager;
import com.smartmobility.manager.UserManager;
import com.smartmobility.model.Account;
import com.smartmobility.model.Noleggio;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.Veicolo;
import com.smartmobility.dao.VeicoloDAO;

public class TestBookingManager {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        BookingManager bookingManager = new BookingManager();
        VeicoloDAO veicoloDAO = new VeicoloDAO();

        System.out.println("--- INIZIO TEST BOOKING MANAGER ---");

        try {
            // 1. Creiamo un utente al volo per il test
            String emailTest = "turista_" + System.currentTimeMillis() + "@email.it";
            Account utente = userManager.registraAccount("Chiara", "Bianchi", emailTest);
            System.out.println("1. Utente di test creato: " + utente.getEmail());

            // 2. Recuperiamo l'auto inserita tramite SQL
            Veicolo auto = veicoloDAO.readByCodice("AUTO-001");
            if (auto == null) {
                System.out.println("ERRORE: Veicolo AUTO-001 non trovato. Assicurati di aver eseguito la query SQL!");
                return;
            }
            System.out.println("2. Veicolo recuperato: " + auto.getCodiceIdentificativo() + " (Stato: "
                    + auto.getStatoOperativo() + ")");

            // 3. Test Prenotazione
            System.out.println("\n3. Effettuo la prenotazione...");
            Prenotazione p = bookingManager.prenotaVeicolo(utente, auto, "Piazza Centrale");
            System.out.println("Prenotazione registrata! Destinazione: " + p.getDestinazione());

            // Rileggiamo il veicolo dal DB per vedere se lo stato è cambiato
            auto = veicoloDAO.readByCodice("AUTO-001");
            System.out.println("Nuovo stato del veicolo nel DB: " + auto.getStatoOperativo());

            // 4. Test Noleggio
            System.out.println("\n4. Avvio il noleggio...");
            Noleggio n = bookingManager.avviaNoleggio(utente, auto);
            System.out.println("Noleggio iniziato alle: " + n.getInizioNoleggio());

            // 5. Test Pausa
            System.out.println("\n5. Metto in pausa il noleggio...");
            bookingManager.mettiInPausa(n);
            System.out.println("Stato noleggio attuale: " + n.getStato());

            // 6. Test Chiusura
            System.out.println("\n6. Concludo il noleggio...");
            bookingManager.concludiNoleggio(n);
            System.out.println(
                    "Noleggio terminato alle: " + n.getFineNoleggio() + " (Stato finale: " + n.getStato() + ")");

        } catch (Exception e) {
            System.out.println("Errore durante l'esecuzione del test: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- FINE TEST ---");
    }
}