package com.smartmobility.manager;

import com.smartmobility.dao.NoleggioDAO;
import com.smartmobility.dao.PrenotazioneDAO;
import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.model.Account;
import com.smartmobility.model.Noleggio;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.StatoVeicolo;
import com.smartmobility.model.Veicolo;

import java.time.LocalDateTime;

public class BookingManager {

    private VeicoloDAO veicoloDAO;
    private PrenotazioneDAO prenotazioneDAO;
    private NoleggioDAO noleggioDAO;
    private com.smartmobility.dao.AccountDAO accountDAO;

    public BookingManager() {
        this.veicoloDAO = new VeicoloDAO();
        this.prenotazioneDAO = new PrenotazioneDAO();
        this.noleggioDAO = new NoleggioDAO();
        this.accountDAO = new com.smartmobility.dao.AccountDAO();
    }

    public Prenotazione prenotaVeicolo(Account account, Veicolo veicolo, String destinazione) {
        if (veicolo.getStatoOperativo() != StatoVeicolo.DISPONIBILE) {
            throw new IllegalStateException("Il veicolo selezionato non è al momento disponibile.");
        }
        
        Prenotazione prenotazione = new Prenotazione(destinazione, LocalDateTime.now(), 15, 0.0);
        
        veicolo.sblocca(); 
        veicoloDAO.updateStatoEPosizione(veicolo);
        
        prenotazioneDAO.create(prenotazione, account.getEmail(), veicolo.getCodiceIdentificativo());
        
        return prenotazione;
    }

    public Noleggio avviaNoleggio(Account account, Veicolo veicolo) {
        if (veicolo.getStatoOperativo() != StatoVeicolo.DISPONIBILE) {
            throw new IllegalStateException("Veicolo non disponibile");
        }
        
        veicolo.sblocca();
        veicoloDAO.updateStatoEPosizione(veicolo);
        
        Noleggio noleggio = new Noleggio(LocalDateTime.now());
        noleggioDAO.create(noleggio, account.getEmail(), veicolo.getCodiceIdentificativo());
        
        return noleggio;
    }

    public void mettiInPausa(Noleggio noleggio) {
        if (noleggio != null) {
            noleggio.sospendi();
            noleggioDAO.update(noleggio);
        }
    }

    public void concludiNoleggio(Noleggio noleggio) {
        if (noleggio != null) {
            noleggio.termina();
            noleggioDAO.update(noleggio);
        }
    }

    public void terminaNoleggio(String email, String codiceVeicolo) {
        Noleggio noleggio = noleggioDAO.readActiveByEmailAndVeicolo(email, codiceVeicolo);
        if (noleggio == null) {
            throw new IllegalStateException("Nessun noleggio attivo trovato per questo veicolo.");
        }
        
        noleggio.termina();
        
        // UC-21: Calcolo costo e applicazione crediti bonus
        double costoStimato = 5.0; // Costo fisso per prototipo
        Account account = accountDAO.readByEmail(email);
        if (account != null) {
            double saldoBonus = account.getSaldoCreditiBonus();
            if (saldoBonus >= costoStimato) {
                account.setSaldoCreditiBonus(saldoBonus - costoStimato);
                noleggio.setCostoFinale(0.0); // Completamente coperto da bonus
            } else {
                noleggio.setCostoFinale(costoStimato - saldoBonus);
                account.setSaldoCreditiBonus(0.0); // Bonus esaurito
            }
            accountDAO.update(account);
        } else {
            noleggio.setCostoFinale(costoStimato);
        }

        noleggioDAO.update(noleggio);
        
        Veicolo veicolo = veicoloDAO.readByCodice(codiceVeicolo);
        if (veicolo != null) {
            veicolo.setStatoOperativo(StatoVeicolo.DISPONIBILE);
            veicoloDAO.updateStatoEPosizione(veicolo);
        }
    }
}
