package backend;

/**
 * Manager per la logica di business relativa ai noleggi.
 */
public class NoleggioManager {
    private TariffaManager tariffaManager;
    private MezzoManager mezzoManager;

    public NoleggioManager() {
        this.tariffaManager = new TariffaManager();
        this.mezzoManager = new MezzoManager();
    }

    public boolean validaParametri(int durataMinuti, double distanzaKm) {
        return durataMinuti > 0 && distanzaKm >= 0;
    }

    public double calcolaStima(Tariffa tariffa, int durataMinuti, double distanzaKm) {
        double importo = tariffa.getCostoBase() + (durataMinuti * tariffa.getCostoMinuto()) + (distanzaKm * tariffa.getCostoKm());
        return Math.round(importo * 100.0) / 100.0;
    }

    public String presentaStima(double importo, int durataMinuti, Tariffa tariffa) {
        String baseStr = String.format(java.util.Locale.US, "%.2f", tariffa.getCostoBase());
        return "Stima per noleggio di durata " + durataMinuti + " minuti (inclusi " + baseStr + "€ di sblocco).";
    }

    /**
     * Metodo principale per l'orchestrazione del calcolo della stima del noleggio.
     */
    public StimaCostoNoleggio stimareCostoNoleggio(int idUtente, int idMezzo, int durataMinuti, double distanzaKm) {
        if (!validaParametri(durataMinuti, distanzaKm)) {
            throw new IllegalArgumentException("Parametri non validi");
        }

        Mezzo mezzo = mezzoManager.getMezzo(idMezzo);
        if (mezzo == null) {
            throw new RuntimeException("Mezzo non trovato");
        }

        Tariffa tariffa = tariffaManager.ottieniTariffaVigente(idMezzo, mezzo.getTipologia());
        if (tariffa == null) {
            throw new RuntimeException("Tariffa non trovata per il mezzo selezionato");
        }

        double importo = calcolaStima(tariffa, durataMinuti, distanzaKm);
        String descrizione = presentaStima(importo, durataMinuti, tariffa);

        return new StimaCostoNoleggio(importo, descrizione);
    }
}
