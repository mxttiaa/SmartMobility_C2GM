package com.smartmobility.model;

import java.util.List;

public class Percorso {
    private String destinazione;
    private int tempoStimato;
    private float distanzaTotale;
    private List<Posizione> coordinateTragitto;

    public Percorso(String destinazione, int tempoStimato, float distanzaTotale, List<Posizione> coordinateTragitto) {
        this.destinazione = destinazione;
        this.tempoStimato = tempoStimato;
        this.distanzaTotale = distanzaTotale;
        this.coordinateTragitto = coordinateTragitto;
    }

    public String getDestinazione() { return destinazione; }
    public void setDestinazione(String destinazione) { this.destinazione = destinazione; }

    public int getTempoStimato() { return tempoStimato; }
    public void setTempoStimato(int tempoStimato) { this.tempoStimato = tempoStimato; }

    public float getDistanzaTotale() { return distanzaTotale; }
    public void setDistanzaTotale(float distanzaTotale) { this.distanzaTotale = distanzaTotale; }

    public List<Posizione> getCoordinateTragitto() { return coordinateTragitto; }
    public void setCoordinateTragitto(List<Posizione> coordinateTragitto) { this.coordinateTragitto = coordinateTragitto; }
}
