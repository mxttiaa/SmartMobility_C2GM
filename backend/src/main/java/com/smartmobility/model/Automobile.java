package com.smartmobility.model;

public class Automobile extends Veicolo {
    private String targa;
    private int numeroPosti;

    public Automobile(String codiceIdentificativo, float livelloCaricaResidua, float portataMassima, Posizione coordinateAttuali, String targa, int numeroPosti) {
        super(codiceIdentificativo, livelloCaricaResidua, portataMassima, coordinateAttuali);
        this.targa = targa;
        this.numeroPosti = numeroPosti;
    }

    public String getTarga() { return targa; }
    public void setTarga(String targa) { this.targa = targa; }

    public int getNumeroPosti() { return numeroPosti; }
    public void setNumeroPosti(int numeroPosti) { this.numeroPosti = numeroPosti; }
}
