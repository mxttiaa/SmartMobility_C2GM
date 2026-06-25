package com.smartmobility.model;

import java.time.LocalDateTime;

public class SegnalazioneSupporto {
    private String idSegnalazione;
    private String descrizioneProblema;
    private LocalDateTime istanteCreazione;
    private String notaAggiornamento;
    private String esitoIntervento;
    private StatoSegnalazione stato;

    public SegnalazioneSupporto(String idSegnalazione, String descrizioneProblema, LocalDateTime istanteCreazione) {
        this.idSegnalazione = idSegnalazione;
        this.descrizioneProblema = descrizioneProblema;
        this.istanteCreazione = istanteCreazione;
        this.stato = StatoSegnalazione.IN_ATTESA;
    }

    public String getIdSegnalazione() { return idSegnalazione; }
    public void setIdSegnalazione(String idSegnalazione) { this.idSegnalazione = idSegnalazione; }

    public String getDescrizioneProblema() { return descrizioneProblema; }
    public void setDescrizioneProblema(String descrizioneProblema) { this.descrizioneProblema = descrizioneProblema; }

    public LocalDateTime getIstanteCreazione() { return istanteCreazione; }
    public void setIstanteCreazione(LocalDateTime istanteCreazione) { this.istanteCreazione = istanteCreazione; }

    public String getNotaAggiornamento() { return notaAggiornamento; }
    public void setNotaAggiornamento(String notaAggiornamento) { this.notaAggiornamento = notaAggiornamento; }

    public String getEsitoIntervento() { return esitoIntervento; }
    public void setEsitoIntervento(String esitoIntervento) { this.esitoIntervento = esitoIntervento; }

    public StatoSegnalazione getStato() { return stato; }
    public void setStato(StatoSegnalazione stato) { this.stato = stato; }

    public void prendiInCarico() {
        this.stato = StatoSegnalazione.IN_CARICO;
    }

    public void sospendi(String nota) {
        this.stato = StatoSegnalazione.SOSPESA;
        this.notaAggiornamento = nota;
    }

    public void chiudi(String esito) {
        this.stato = StatoSegnalazione.CHIUSA;
        this.esitoIntervento = esito;
    }
}
