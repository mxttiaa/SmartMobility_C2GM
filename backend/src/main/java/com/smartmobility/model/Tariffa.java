package com.smartmobility.model;

public class Tariffa {
    private double costoSblocco;
    private double costoAlMinuto;
    private String tipologiaVeicolo;

    public Tariffa(double costoSblocco, double costoAlMinuto, String tipologiaVeicolo) {
        this.costoSblocco = costoSblocco;
        this.costoAlMinuto = costoAlMinuto;
        this.tipologiaVeicolo = tipologiaVeicolo;
    }

    public double getCostoSblocco() { return costoSblocco; }
    public void setCostoSblocco(double costoSblocco) { this.costoSblocco = costoSblocco; }

    public double getCostoAlMinuto() { return costoAlMinuto; }
    public void setCostoAlMinuto(double costoAlMinuto) { this.costoAlMinuto = costoAlMinuto; }

    public String getTipologiaVeicolo() { return tipologiaVeicolo; }
    public void setTipologiaVeicolo(String tipologiaVeicolo) { this.tipologiaVeicolo = tipologiaVeicolo; }
}
