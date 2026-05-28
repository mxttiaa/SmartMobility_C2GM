package backend;

/**
 * Record/DTO per incapsulare i dati del metodo di pagamento provenienti dal
 * frontend.
 */
public class DatiMetodoPagamento {
    private String numeroCarta;
    private String dataScadenza;
    private String cvv;
    private String intestatario;
    private String tipoPagamento;

    public DatiMetodoPagamento() {
    }

    public DatiMetodoPagamento(String numeroCarta, String dataScadenza, String cvv, String intestatario,
            String tipoPagamento) {
        this.numeroCarta = numeroCarta;
        this.dataScadenza = dataScadenza;
        this.cvv = cvv;
        this.intestatario = intestatario;
        this.tipoPagamento = tipoPagamento;
    }

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
}
