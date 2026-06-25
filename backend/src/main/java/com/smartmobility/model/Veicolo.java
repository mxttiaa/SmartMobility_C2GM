package com.smartmobility.model;

public abstract class Veicolo {
    private String codiceIdentificativo;
    private float livelloCaricaResidua;
    private float portataMassima;
    private StatoVeicolo statoOperativo;
    private Posizione coordinateAttuali;

    public Veicolo(String codiceIdentificativo, float livelloCaricaResidua, float portataMassima, Posizione coordinateAttuali) {
        this.codiceIdentificativo = codiceIdentificativo;
        this.livelloCaricaResidua = livelloCaricaResidua;
        this.portataMassima = portataMassima;
        this.statoOperativo = StatoVeicolo.DISPONIBILE;
        this.coordinateAttuali = coordinateAttuali;
    }

    public String getCodiceIdentificativo() { return codiceIdentificativo; }
    public void setCodiceIdentificativo(String codiceIdentificativo) { this.codiceIdentificativo = codiceIdentificativo; }

    public float getLivelloCaricaResidua() { return livelloCaricaResidua; }
    public void setLivelloCaricaResidua(float livelloCaricaResidua) { this.livelloCaricaResidua = livelloCaricaResidua; }

    public float getPortataMassima() { return portataMassima; }
    public void setPortataMassima(float portataMassima) { this.portataMassima = portataMassima; }

    public StatoVeicolo getStatoOperativo() { return statoOperativo; }
    public void setStatoOperativo(StatoVeicolo statoOperativo) { this.statoOperativo = statoOperativo; }

    public Posizione getCoordinateAttuali() { return coordinateAttuali; }
    public void setCoordinateAttuali(Posizione coordinateAttuali) { this.coordinateAttuali = coordinateAttuali; }

    public float getAutonomiaStimata() {
        // Implementazione dummy: autonomia stimata proporzionale alla carica residua
        return livelloCaricaResidua * 1.5f; 
    }

    public void sblocca() {
        this.statoOperativo = StatoVeicolo.IN_USO;
    }

    public void blocca() {
        if (this.livelloCaricaResidua < 10) {
            this.statoOperativo = StatoVeicolo.BATTERIA_SCARICA;
        } else {
            this.statoOperativo = StatoVeicolo.DISPONIBILE;
        }
    }

    public void aggiornaPosizione(Posizione nuovaPosizione) {
        this.coordinateAttuali = nuovaPosizione;
    }
}
