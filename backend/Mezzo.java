package backend;

/**
 * Modello di dominio per un Mezzo.
 */
public class Mezzo {
    private int idMezzo;
    private String tipologia;
    private double portataMassima;
    private double livelloBatteria;
    
    public Mezzo(int idMezzo, String tipologia, double portataMassima, double livelloBatteria) {
        this.idMezzo = idMezzo;
        this.tipologia = tipologia;
        this.portataMassima = portataMassima;
        this.livelloBatteria = livelloBatteria;
    }

    public int getIdMezzo() { return idMezzo; }
    public void setIdMezzo(int idMezzo) { this.idMezzo = idMezzo; }

    public String getTipologia() { return tipologia; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }

    public double getPortataMassima() { return portataMassima; }
    public void setPortataMassima(double portataMassima) { this.portataMassima = portataMassima; }

    public double getLivelloBatteria() { return livelloBatteria; }
    public void setLivelloBatteria(double livelloBatteria) { this.livelloBatteria = livelloBatteria; }
}
