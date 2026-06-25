package test;

import com.smartmobility.manager.UserManager;
import com.smartmobility.model.Account;

public class TestUserManager {
    public static void main(String[] args) {
        UserManager manager = new UserManager();

        System.out.println("Test Registrazione...");
        Account nuovo = manager.registraAccount("Mario", "Rossi", "mario.rossi@email.it");
        System.out.println("Registrato con stato: " + nuovo.getStato());

        System.out.println("\nTest Convalida...");
        boolean esito = manager.convalidaCodice(nuovo, "123456");
        System.out.println("Convalida codice '123456': " + esito);
        System.out.println("Nuovo stato: " + nuovo.getStato());

        System.out.println("\nTest Metodo Pagamento...");
        manager.associaPagamento(nuovo, "TOKEN_CARTA_CREDITO_123");
        System.out.println("Pagamento associato con successo!");

        System.out.println("\nTest Blocco Profilo...");
        manager.bloccaProfilo(nuovo, "Comportamento scorretto");
    }
}