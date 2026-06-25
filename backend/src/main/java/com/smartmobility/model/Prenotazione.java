package com.smartmobility.model;

import java.time.LocalDateTime;

public class Prenotazione {
    private String destinazione;
    private LocalDateTime istanteCreazione;
    private int durataMassima;
    private double costoStimato;
    private StatoPrenotazione stato;

    public Prenotazione(String destinazione, LocalDateTime istanteCreazione, int durataMassima, double costoStimato) {
        this.destinazione = destinazione;
        this.istanteCreazione = istanteCreazione;
        this.durataMassima = durataMassima;
        this.costoStimato = costoStimato;
        this.stato = StatoPrenotazione.ATTIVA;
    }

    public String getDestinazione() { return destinazione; }
    public void setDestinazione(String destinazione) { this.destinazione = destinazione; }

    public LocalDateTime getIstanteCreazione() { return istanteCreazione; }
    public void setIstanteCreazione(LocalDateTime istanteCreazione) { this.istanteCreazione = istanteCreazione; }

    public int getDurataMassima() { return durataMassima; }
    public void setDurataMassima(int durataMassima) { this.durataMassima = durataMassima; }

    public double getCostoStimato() { return costoStimato; }
    public void setCostoStimato(double costoStimato) { this.costoStimato = costoStimato; }

    public StatoPrenotazione getStato() { return stato; }
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    public boolean isScaduta() {
        if (this.stato == StatoPrenotazione.SCADUTA) {
            return true;
        }
        LocalDateTime scadenzaPrevista = this.istanteCreazione.plusMinutes(this.durataMassima);
        return LocalDateTime.now().isAfter(scadenzaPrevista);
    }
}
