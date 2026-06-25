package com.smartmobility.model;

public class MetodoPagamento {
    private String tokenDati;

    public MetodoPagamento(String tokenDati) {
        this.tokenDati = tokenDati;
    }

    public String getTokenDati() {
        return tokenDati;
    }

    public void setTokenDati(String tokenDati) {
        this.tokenDati = tokenDati;
    }

    public boolean isValido() {
        return tokenDati != null && !tokenDati.trim().isEmpty();
    }
}
