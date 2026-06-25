package com.smartmobility.model;

import java.time.LocalDateTime;

public class SessioneAssistenza {
    private String idSessione;
    private String categoriaProblema;
    private String dettagliPreliminari;
    private LocalDateTime istanteAvvio;
    private StatoSessione stato;

    public SessioneAssistenza(String idSessione, String categoriaProblema, String dettagliPreliminari, LocalDateTime istanteAvvio) {
        this.idSessione = idSessione;
        this.categoriaProblema = categoriaProblema;
        this.dettagliPreliminari = dettagliPreliminari;
        this.istanteAvvio = istanteAvvio;
        this.stato = StatoSessione.IN_ATTESA;
    }

    public String getIdSessione() { return idSessione; }
    public void setIdSessione(String idSessione) { this.idSessione = idSessione; }

    public String getCategoriaProblema() { return categoriaProblema; }
    public void setCategoriaProblema(String categoriaProblema) { this.categoriaProblema = categoriaProblema; }

    public String getDettagliPreliminari() { return dettagliPreliminari; }
    public void setDettagliPreliminari(String dettagliPreliminari) { this.dettagliPreliminari = dettagliPreliminari; }

    public LocalDateTime getIstanteAvvio() { return istanteAvvio; }
    public void setIstanteAvvio(LocalDateTime istanteAvvio) { this.istanteAvvio = istanteAvvio; }

    public StatoSessione getStato() { return stato; }
    public void setStato(StatoSessione stato) { this.stato = stato; }

    public void avvia() {
        this.stato = StatoSessione.IN_CORSO;
    }

    public void termina() {
        this.stato = StatoSessione.TERMINATA;
    }
}
