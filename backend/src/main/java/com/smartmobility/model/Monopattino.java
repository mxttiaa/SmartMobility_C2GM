package com.smartmobility.model;

public class Monopattino extends Veicolo {
    private int velocitaMassima;

    public Monopattino(String codiceIdentificativo, float livelloCaricaResidua, float portataMassima, Posizione coordinateAttuali, int velocitaMassima) {
        super(codiceIdentificativo, livelloCaricaResidua, portataMassima, coordinateAttuali);
        this.velocitaMassima = velocitaMassima;
    }

    public int getVelocitaMassima() { return velocitaMassima; }
    public void setVelocitaMassima(int velocitaMassima) { this.velocitaMassima = velocitaMassima; }
}
