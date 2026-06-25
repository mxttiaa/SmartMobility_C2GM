package com.smartmobility.manager;

import com.smartmobility.dao.RegolaUrbanaDAO;
import com.smartmobility.dao.SegnalazioneDAO;
import com.smartmobility.dao.VeicoloDAO;
import com.smartmobility.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdminManager {

    private SegnalazioneDAO segnalazioneDAO;
    private RegolaUrbanaDAO regolaUrbanaDAO;
    private VeicoloDAO veicoloDAO;

    public AdminManager() {
        this.segnalazioneDAO = new SegnalazioneDAO();
        this.regolaUrbanaDAO = new RegolaUrbanaDAO();
        this.veicoloDAO = new VeicoloDAO();
    }

    public List<SegnalazioneSupporto> visualizzaCodaSegnalazioni() {
        return segnalazioneDAO.getSegnalazioniInAttesa();
    }

    public void assegnaSegnalazione(Account operatore, SegnalazioneSupporto segnalazione) {
        if (operatore == null || segnalazione == null)
            return;

        segnalazione.prendiInCarico();
        segnalazioneDAO.updateStatoSupporto(segnalazione, operatore.getEmail());
    }

    public void aggiornaStatoSegnalazione(SegnalazioneSupporto segnalazione, String nota) {
        if (segnalazione != null) {
            // Aggiorniamo la nota e sospendiamo la segnalazione come esempio
            segnalazione.sospendi(nota);
            // Aggiorniamo a DB senza sovrascrivere l'assegnatario
            segnalazioneDAO.updateStatoSupporto(segnalazione, null);
        }
    }

    public void aggiungiRegolaUrbana(Account adminLoggato, RegolaUrbana regola) {
        if (regola != null && adminLoggato != null) {
            // Ora passiamo dinamicamente l'email dell'amministratore che sta compiendo
            // l'azione
            regolaUrbanaDAO.save(regola, adminLoggato.getEmail());
        }
    }

    public ReportStatistico generaReportStatistico(String criteri) {
        // Logica per estrarre statistiche dal database...
        Map<String, Object> datiDummy = new HashMap<>();
        datiDummy.put("veicoli_attivi", 120);
        datiDummy.put("noleggi_giornalieri", 450);

        ReportStatistico report = new ReportStatistico(
                UUID.randomUUID().toString(),
                criteri,
                LocalDateTime.now(),
                datiDummy);

        // Un ipotetico salvataggio nel database andrebbe qui
        return report;
    }

    public List<Veicolo> visualizzaVeicoliDaManutenere(List<String> filtri) {
        // Query semplificata per manager.
        // Normalmente interpellerebbe VeicoloDAO per recuperare lo stato
        // 'IN_MANUTENZIONE'
        System.out.println("Ricerca veicoli con filtri applicati: " + filtri);
        return new ArrayList<>();
    }

    public void gestisciAllarmeSicurezza(Veicolo veicolo) {
        if (veicolo != null) {
            veicolo.blocca();
            veicoloDAO.updateStatoEPosizione(veicolo);
            System.out.println("Allarme gestito: il veicolo " + veicolo.getCodiceIdentificativo()
                    + " è stato bloccato in sicurezza.");
        }
    }

    public void forzaBloccoRemoto(Veicolo veicolo) {
        if (veicolo != null) {
            veicolo.setStatoOperativo(StatoVeicolo.IN_MANUTENZIONE);
            veicoloDAO.updateStatoEPosizione(veicolo);
            System.out.println("Blocco remoto forzato eseguito sul veicolo " + veicolo.getCodiceIdentificativo());
        }
    }
}
