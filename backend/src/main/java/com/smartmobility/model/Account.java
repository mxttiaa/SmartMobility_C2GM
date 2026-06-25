package com.smartmobility.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String nome;
    private String cognome;
    private String email;
    private double saldoCreditiBonus;
    private StatoAccount stato;
    
    private MetodoPagamento metodoPagamento;
    private List<Promozione> promozioni;

    public Account(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.saldoCreditiBonus = 0.0;
        this.stato = StatoAccount.DA_VERIFICARE;
        this.promozioni = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldoCreditiBonus() {
        return saldoCreditiBonus;
    }

    public void setSaldoCreditiBonus(double saldoCreditiBonus) {
        this.saldoCreditiBonus = saldoCreditiBonus;
    }

    public StatoAccount getStato() {
        return stato;
    }

    public void setStato(StatoAccount stato) {
        this.stato = stato;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<Promozione> getPromozioni() {
        return promozioni;
    }

    public void setPromozioni(List<Promozione> promozioni) {
        this.promozioni = promozioni;
    }

    public void applicaCreditiBonus(double importo) {
        if (importo > 0) {
            this.saldoCreditiBonus += importo;
        }
    }

    public void sanziona(StatoAccount nuovoStato) {
        this.stato = nuovoStato;
    }
}
