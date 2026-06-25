package com.smartmobility.model;

import java.time.LocalDateTime;

public class SegnalazioneGuasto {
    private String idSegnalazione;
    private String categoriaGuasto;
    private String descrizioneAnomalia;
    private LocalDateTime istanteCreazione;
    private StatoSegnalazione stato;

    public SegnalazioneGuasto(String idSegnalazione, String categoriaGuasto, String descrizioneAnomalia, LocalDateTime istanteCreazione) {
        this.idSegnalazione = idSegnalazione;
        this.categoriaGuasto = categoriaGuasto;
        this.descrizioneAnomalia = descrizioneAnomalia;
        this.istanteCreazione = istanteCreazione;
        this.stato = StatoSegnalazione.IN_ATTESA;
    }

    public String getIdSegnalazione() { return idSegnalazione; }
    public void setIdSegnalazione(String idSegnalazione) { this.idSegnalazione = idSegnalazione; }

    public String getCategoriaGuasto() { return categoriaGuasto; }
    public void setCategoriaGuasto(String categoriaGuasto) { this.categoriaGuasto = categoriaGuasto; }

    public String getDescrizioneAnomalia() { return descrizioneAnomalia; }
    public void setDescrizioneAnomalia(String descrizioneAnomalia) { this.descrizioneAnomalia = descrizioneAnomalia; }

    public LocalDateTime getIstanteCreazione() { return istanteCreazione; }
    public void setIstanteCreazione(LocalDateTime istanteCreazione) { this.istanteCreazione = istanteCreazione; }

    public StatoSegnalazione getStato() { return stato; }
    public void setStato(StatoSegnalazione stato) { this.stato = stato; }
}
