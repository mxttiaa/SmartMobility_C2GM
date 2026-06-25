package com.smartmobility.model;

import java.time.LocalDateTime;

public class Noleggio {
    private LocalDateTime inizioNoleggio;
    private LocalDateTime fineNoleggio;
    private double costoFinale;
    private StatoNoleggio stato;

    public Noleggio(LocalDateTime inizioNoleggio) {
        this.inizioNoleggio = inizioNoleggio;
        this.stato = StatoNoleggio.IN_CORSO;
    }

    public LocalDateTime getInizioNoleggio() { return inizioNoleggio; }
    public void setInizioNoleggio(LocalDateTime inizioNoleggio) { this.inizioNoleggio = inizioNoleggio; }

    public LocalDateTime getFineNoleggio() { return fineNoleggio; }
    public void setFineNoleggio(LocalDateTime fineNoleggio) { this.fineNoleggio = fineNoleggio; }

    public double getCostoFinale() { return costoFinale; }
    public void setCostoFinale(double costoFinale) { this.costoFinale = costoFinale; }

    public StatoNoleggio getStato() { return stato; }
    public void setStato(StatoNoleggio stato) { this.stato = stato; }

    public void sospendi() {
        if (this.stato == StatoNoleggio.IN_CORSO) {
            this.stato = StatoNoleggio.IN_PAUSA;
        }
    }

    public void riprendi() {
        if (this.stato == StatoNoleggio.IN_PAUSA) {
            this.stato = StatoNoleggio.IN_CORSO;
        }
    }

    public void termina() {
        this.stato = StatoNoleggio.TERMINATO;
        this.fineNoleggio = LocalDateTime.now();
    }
}
