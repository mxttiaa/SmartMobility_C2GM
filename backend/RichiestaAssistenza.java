package backend;

import java.sql.Timestamp;

public class RichiestaAssistenza {
    private int id;
    private int idUtente;
    private String descrizioneProblema;
    private String stato;
    private Timestamp dataCreazione;

    public RichiestaAssistenza() {
    }

    public RichiestaAssistenza(int id, int idUtente, String descrizioneProblema, String stato,
            Timestamp dataCreazione) {
        this.id = id;
        this.idUtente = idUtente;
        this.descrizioneProblema = descrizioneProblema;
        this.stato = stato;
        this.dataCreazione = dataCreazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getDescrizioneProblema() {
        return descrizioneProblema;
    }

    public void setDescrizioneProblema(String descrizioneProblema) {
        this.descrizioneProblema = descrizioneProblema;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
