package backend;

/**
 * Manager per la logica di business relativa alle Tariffe.
 */
public class TariffaManager {
    private TariffaDAO tariffaDAO;

    public TariffaManager() {
        this.tariffaDAO = new TariffaDAO();
    }

    /**
     * Recupera la tariffa corrente per un dato tipo di mezzo.
     * @param tipoMezzo Il tipo di mezzo (es. "bici", "monopattino").
     * @return La Tariffa associata.
     */
    public Tariffa ottieniTariffaVigente(int idMezzo, String tipoMezzo) {
        if (tipoMezzo == null || tipoMezzo.isEmpty()) {
            return null;
        }

        String tipoLower = tipoMezzo.toLowerCase();
        String chiaveDatabase;
        
        if (tipoLower.contains("bici")) {
            chiaveDatabase = "bici";
        } else if (tipoLower.contains("auto")) {
            chiaveDatabase = "auto";
        } else {
            chiaveDatabase = "monopattino";
        }

        return tariffaDAO.findByTipoMezzo(chiaveDatabase);
    }
}
