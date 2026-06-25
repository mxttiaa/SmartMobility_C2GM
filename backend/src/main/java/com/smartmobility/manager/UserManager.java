package com.smartmobility.manager;

import com.smartmobility.dao.AccountDAO;
import com.smartmobility.model.Account;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.StatoAccount;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserManager {
    
    private AccountDAO accountDAO;

    public UserManager() {
        this.accountDAO = new AccountDAO();
    }

    /**
     * Registra un nuovo account con password e ruolo.
     * Come da SequenzaUC-01: crea Account con stato DA_VERIFICARE.
     */
    public Account registraAccount(String nome, String cognome, String email, String password) {
        if (accountDAO.readByEmail(email) != null) {
            throw new IllegalArgumentException("Errore: Un account con questa email e' gia' registrato.");
        }
        
        Account nuovoAccount = new Account(nome, cognome, email);
        nuovoAccount.setPasswordHash(hashPassword(password));
        nuovoAccount.setRuolo("CLIENTE");
        // Come da diagramma UC-01: lo stato iniziale è DA_VERIFICARE
        nuovoAccount.setStato(StatoAccount.DA_VERIFICARE);
        
        accountDAO.create(nuovoAccount);
        return nuovoAccount;
    }

    /**
     * Registra un nuovo account (retrocompatibilità senza password).
     */
    public Account registraAccount(String nome, String cognome, String email) {
        return registraAccount(nome, cognome, email, "");
    }

    /**
     * Verifica le credenziali dell'utente.
     * Come da SequenzaUC-19: verificaCredenziali(email, password) -> Account o null
     */
    public Account verificaCredenziali(String email, String password) {
        Account account = accountDAO.readByEmail(email);
        if (account == null) {
            return null;
        }
        
        // Verifica che l'account sia attivo
        if (account.getStato() != StatoAccount.ATTIVO) {
            return null;
        }
        
        // Confronto hash password
        String hashFornito = hashPassword(password);
        if (hashFornito.equals(account.getPasswordHash())) {
            return account;
        }
        
        return null;
    }

    public boolean convalidaCodice(Account account, String codice) {
        // Simulazione validazione codice (in uno scenario reale si verificherebbe contro un servizio)
        if (account != null && "123456".equals(codice) && account.getStato() == StatoAccount.DA_VERIFICARE) {
            account.setStato(StatoAccount.ATTIVO);
            accountDAO.update(account);
            return true;
        }
        return false;
    }

    public void associaPagamento(Account account, String datiCarta) {
        if (account == null) {
            throw new IllegalArgumentException("L'account fornito non e' valido.");
        }
        
        MetodoPagamento metodo = new MetodoPagamento(datiCarta);
        if (metodo.isValido()) {
            account.setMetodoPagamento(metodo);
            accountDAO.saveMetodoPagamento(account.getEmail(), metodo);
        } else {
            throw new IllegalArgumentException("I dati della carta forniti non sono validi.");
        }
    }

    public void bloccaProfilo(Account account, String motivazione) {
        if (account != null) {
            account.sanziona(StatoAccount.BLOCCATO);
            accountDAO.update(account);
            // La motivazione verrebbe tipicamente salvata in una tabella di log/audit del sistema
            System.out.println("L'account " + account.getEmail() + " e' stato bloccato. Motivazione: " + motivazione);
        }
    }

    /**
     * Hash SHA-256 della password.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 non disponibile", e);
        }
    }
}
