package com.smartmobility.manager;

import com.smartmobility.dao.AccountDAO;
import com.smartmobility.model.Account;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.StatoAccount;

public class UserManager {
    
    private AccountDAO accountDAO;

    public UserManager() {
        this.accountDAO = new AccountDAO();
    }

    public Account registraAccount(String nome, String cognome, String email) {
        if (accountDAO.readByEmail(email) != null) {
            throw new IllegalArgumentException("Errore: Un account con questa email è già registrato.");
        }
        
        Account nuovoAccount = new Account(nome, cognome, email);
        accountDAO.create(nuovoAccount);
        return nuovoAccount;
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
            throw new IllegalArgumentException("L'account fornito non è valido.");
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
            System.out.println("L'account " + account.getEmail() + " è stato bloccato. Motivazione: " + motivazione);
        }
    }
}
