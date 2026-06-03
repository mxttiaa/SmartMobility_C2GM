package backend;

/**
 * DTO per incapsulare il risultato di una stima di costo di noleggio.
 */
public class StimaCostoNoleggio {
    private double importo;
    private String descrizione;

    public StimaCostoNoleggio(double importo, String descrizione) {
        this.importo = importo;
        this.descrizione = descrizione;
    }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}
