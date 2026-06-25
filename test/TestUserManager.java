package test;

import com.smartmobility.manager.UserManager;
import com.smartmobility.model.Account;

public class TestUserManager {
    public static void main(String[] args) {
        UserManager manager = new UserManager();

        // Generiamo un'email unica per poter lanciare il test infinite volte
        String emailTest = "mario.rossi_" + System.currentTimeMillis() + "@email.it";

        System.out.println("--- INIZIO TEST USER MANAGER ---");

        // 1. Test Registrazione
        System.out.println("\n1. Test Registrazione...");
        Account nuovo = null;
        try {
            nuovo = manager.registraAccount("Mario", "Rossi", emailTest);
            System.out.println("✅ Registrato con stato: " + nuovo.getStato() + " (Email: " + emailTest + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore imprevisto: " + e.getMessage());
            return; // Se fallisce qui, fermiamo il test
        }

        // 2. Test Registrazione Duplicata
        System.out.println("\n2. Test Registrazione Duplicata (Deve fallire)...");
        try {
            manager.registraAccount("Mario", "Rossi", emailTest);
            System.out.println("❌ Errore: Il sistema ha permesso un duplicato!");
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Ottimo! Il sistema ha bloccato il duplicato: " + e.getMessage());
        }

        // 3. Test Convalida
        System.out.println("\n3. Test Convalida...");
        boolean esito = manager.convalidaCodice(nuovo, "123456");
        System.out.println("✅ Convalida codice '123456': " + esito);
        System.out.println("✅ Nuovo stato: " + nuovo.getStato());

        // 4. Test Metodo Pagamento
        System.out.println("\n4. Test Metodo Pagamento...");
        try {
            manager.associaPagamento(nuovo, "TOKEN_CARTA_CREDITO_123");
            System.out.println("✅ Pagamento associato con successo!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore carta: " + e.getMessage());
        }

        // 5. Test Blocco
        System.out.println("\n5. Test Blocco Profilo...");
        manager.bloccaProfilo(nuovo, "Comportamento scorretto");
        System.out.println("✅ Stato finale: " + nuovo.getStato());

        System.out.println("\n--- FINE TEST ---");
    }
}