package backend;

/**
 * Modello di dominio per il Metodo di Pagamento.
 * Contiene i dati persistenti e le regole di business per la formattazione.
 */
public class MetodoPagamento {
    private String idMetodo;
    private String tipoPagamento;
    private String numeroCarta; // Mascherato nel DB
    private String dataScadenza;
    private String intestatario;
    private boolean statoAttivo;
    private String tokenGateway;

    public MetodoPagamento() {
    }

    public MetodoPagamento(String idMetodo, String tipoPagamento, String numeroCarta, String dataScadenza,
            String intestatario, boolean statoAttivo, String tokenGateway) {
        this.idMetodo = idMetodo;
        this.tipoPagamento = tipoPagamento;
        this.numeroCarta = numeroCarta;
        this.dataScadenza = dataScadenza;
        this.intestatario = intestatario;
        this.statoAttivo = statoAttivo;
        this.tokenGateway = tokenGateway;
    }

    /**
     * Valida il formato dei dati (es. se la scadenza ha senso, se la carta ha un
     * numero corretto).
     * 
     * @param dati I dati ricevuti.
     * @return true se il formato di base è corretto.
     */
    public boolean validaFormatoDati(DatiMetodoPagamento dati) {
        if (dati.getNumeroCarta() == null || !dati.getNumeroCarta().matches("\\d{13,19}"))
            return false;
        if (dati.getCvv() == null || !dati.getCvv().matches("\\d{3,4}"))
            return false;
        if (dati.getDataScadenza() == null || !dati.getDataScadenza().matches("(0[1-9]|1[0-2])/\\d{2}"))
            return false;

        try {
            String[] parts = dati.getDataScadenza().split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000;
            if (java.time.YearMonth.of(year, month).isBefore(java.time.YearMonth.now())) {
                return false; // Carta scaduta
            }
        } catch (Exception e) {
            return false;
        }

        if (dati.getIntestatario() == null || dati.getIntestatario().trim().isEmpty())
            return false;
        return true;
    }

    /**
     * Maschera il numero della carta per non salvarlo in chiaro (mantiene solo le
     * ultime 4 cifre).
     * 
     * @param numeroCartaChiaro Il numero di carta originario.
     * @return La stringa mascherata.
     */
    public String mascheraNumero(String numeroCartaChiaro) {
        if (numeroCartaChiaro == null || numeroCartaChiaro.length() < 4)
            return numeroCartaChiaro;
        String ultimeQuattro = numeroCartaChiaro.substring(numeroCartaChiaro.length() - 4);
        return "****-****-****-" + ultimeQuattro;
    }

    public void attiva() {
        this.statoAttivo = true;
    }

    public void disattiva() {
        this.statoAttivo = false;
    }

    // Getters e Setters

    public String getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(String idMetodo) {
        this.idMetodo = idMetodo;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
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

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public boolean isStatoAttivo() {
        return statoAttivo;
    }

    public void setStatoAttivo(boolean statoAttivo) {
        this.statoAttivo = statoAttivo;
    }

    public String getTokenGateway() {
        return tokenGateway;
    }

    public void setTokenGateway(String tokenGateway) {
        this.tokenGateway = tokenGateway;
    }
}
