package com.smartmobility.model;

import java.time.LocalDateTime;
import java.util.List;

public class RegolaUrbana {
    private String idRegola;
    private TipoRestrizione tipo;
    private List<Posizione> perimetro;
    private int valoreLimiteVelocita;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

    public RegolaUrbana(String idRegola, TipoRestrizione tipo, List<Posizione> perimetro, int valoreLimiteVelocita, LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.idRegola = idRegola;
        this.tipo = tipo;
        this.perimetro = perimetro;
        this.valoreLimiteVelocita = valoreLimiteVelocita;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public String getIdRegola() { return idRegola; }
    public void setIdRegola(String idRegola) { this.idRegola = idRegola; }

    public TipoRestrizione getTipo() { return tipo; }
    public void setTipo(TipoRestrizione tipo) { this.tipo = tipo; }

    public List<Posizione> getPerimetro() { return perimetro; }
    public void setPerimetro(List<Posizione> perimetro) { this.perimetro = perimetro; }

    public int getValoreLimiteVelocita() { return valoreLimiteVelocita; }
    public void setValoreLimiteVelocita(int valoreLimiteVelocita) { this.valoreLimiteVelocita = valoreLimiteVelocita; }

    public LocalDateTime getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDateTime dataInizio) { this.dataInizio = dataInizio; }

    public LocalDateTime getDataFine() { return dataFine; }
    public void setDataFine(LocalDateTime dataFine) { this.dataFine = dataFine; }

    public boolean isAttiva(LocalDateTime istante) {
        if (istante == null) return false;
        return (istante.isEqual(dataInizio) || istante.isAfter(dataInizio)) &&
               (istante.isEqual(dataFine) || istante.isBefore(dataFine));
    }
}
