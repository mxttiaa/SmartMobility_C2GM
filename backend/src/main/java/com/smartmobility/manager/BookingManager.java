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

    public BookingManager() {
        this.veicoloDAO = new VeicoloDAO();
        this.prenotazioneDAO = new PrenotazioneDAO();
        this.noleggioDAO = new NoleggioDAO();
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
        if (veicolo.getStatoOperativo() != StatoVeicolo.DISPONIBILE && veicolo.getStatoOperativo() != StatoVeicolo.IN_USO) {
            throw new IllegalStateException("Impossibile avviare il noleggio per questo veicolo.");
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
}
