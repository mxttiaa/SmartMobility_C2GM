package backend;

/**
 * Modello di dominio per la Tariffa di noleggio di un mezzo.
 */
public class Tariffa {
    private int id;
    private String tipoMezzo;
    private double costoBase;
    private double costoMinuto;
    private double costoKm;

    public Tariffa(int id, String tipoMezzo, double costoBase, double costoMinuto, double costoKm) {
        this.id = id;
        this.tipoMezzo = tipoMezzo;
        this.costoBase = costoBase;
        this.costoMinuto = costoMinuto;
        this.costoKm = costoKm;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipoMezzo() { return tipoMezzo; }
    public void setTipoMezzo(String tipoMezzo) { this.tipoMezzo = tipoMezzo; }

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }

    public double getCostoMinuto() { return costoMinuto; }
    public void setCostoMinuto(double costoMinuto) { this.costoMinuto = costoMinuto; }

    public double getCostoKm() { return costoKm; }
    public void setCostoKm(double costoKm) { this.costoKm = costoKm; }
}
