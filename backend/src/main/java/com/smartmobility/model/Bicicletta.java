package com.smartmobility.model;

public class Bicicletta extends Veicolo {
    private boolean pedalataAssistita;

    public Bicicletta(String codiceIdentificativo, float livelloCaricaResidua, float portataMassima, Posizione coordinateAttuali, boolean pedalataAssistita) {
        super(codiceIdentificativo, livelloCaricaResidua, portataMassima, coordinateAttuali);
        this.pedalataAssistita = pedalataAssistita;
    }

    public boolean isPedalataAssistita() { return pedalataAssistita; }
    public void setPedalataAssistita(boolean pedalataAssistita) { this.pedalataAssistita = pedalataAssistita; }
}
