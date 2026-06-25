package com.smartmobility.model;

import java.time.LocalDateTime;

public class Promozione {
    private String codiceAlfanumerico;
    private double valoreSconto;
    private LocalDateTime dataScadenza;

    public Promozione(String codiceAlfanumerico, double valoreSconto, LocalDateTime dataScadenza) {
        this.codiceAlfanumerico = codiceAlfanumerico;
        this.valoreSconto = valoreSconto;
        this.dataScadenza = dataScadenza;
    }

    public String getCodiceAlfanumerico() {
        return codiceAlfanumerico;
    }

    public void setCodiceAlfanumerico(String codiceAlfanumerico) {
        this.codiceAlfanumerico = codiceAlfanumerico;
    }

    public double getValoreSconto() {
        return valoreSconto;
    }

    public void setValoreSconto(double valoreSconto) {
        this.valoreSconto = valoreSconto;
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isValida() {
        return LocalDateTime.now().isBefore(this.dataScadenza);
    }
}
