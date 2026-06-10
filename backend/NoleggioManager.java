package backend;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager per la logica di business relativa ai noleggi.
 *
 * <p>Gestisce la stima del costo includendo:
 * <ul>
 *   <li>Completamento automatico dei parametri mancanti (durata ↔ distanza)
 *       tramite la velocità media del tipo di mezzo.</li>
 *   <li>Validazione dei limiti massimi di durata e distanza.</li>
 *   <li>Calcolo del costo con la formula: costoBase + (durataMinuti * costoMinuto) + (distanzaKm * costoKm).</li>
 * </ul>
 */
public class NoleggioManager {

    // ---------------------------------------------------------------
    // Costanti di limite (configurabili qui in un'unica posizione)
    // ---------------------------------------------------------------

    /** Durata massima consentita per un noleggio, in minuti. */
    public static final int MAX_DURATA_MINUTI = 120;

    /** Distanza massima consentita per un noleggio, in km. */
    public static final double MAX_DISTANZA_KM = 30.0;

    // ---------------------------------------------------------------
    // Velocità medie per tipo di mezzo (km/h) – case-insensitive
    // ---------------------------------------------------------------

    /**
     * Mappa velocità media (km/h) per tipo di mezzo.
     * La chiave è il nome del tipo in minuscolo; si usa una ricerca
     * per prefisso/sottostringa per gestire varianti (es. "bici elettrica").
     */
    private static final Map<String, Double> VELOCITA_MEDIA_KMH;

    static {
        VELOCITA_MEDIA_KMH = new HashMap<>();
        VELOCITA_MEDIA_KMH.put("bici",        12.0);
        VELOCITA_MEDIA_KMH.put("monopattino", 15.0);
        VELOCITA_MEDIA_KMH.put("scooter",     25.0);
    }

    /** Velocità di default usata se il tipo di mezzo non è riconosciuto (km/h). */
    private static final double VELOCITA_DEFAULT_KMH = 15.0;

    // ---------------------------------------------------------------
    // Dipendenze
    // ---------------------------------------------------------------

    private TariffaManager tariffaManager;
    private MezzoManager   mezzoManager;

    public NoleggioManager() {
        this.tariffaManager = new TariffaManager();
        this.mezzoManager   = new MezzoManager();
    }

    // ---------------------------------------------------------------
    // API pubblica
    // ---------------------------------------------------------------

    /**
     * Metodo principale: orchestra il calcolo della stima del noleggio.
     *
     * <p>Accetta {@code durataMinuti} e/o {@code distanzaKm} (anche 0).
     * Almeno uno dei due deve essere &gt; 0. Il parametro mancante viene
     * calcolato automaticamente dalla velocità media del mezzo.
     *
     * @param idUtente       ID dell'utente che richiede la stima (per log futuro)
     * @param idMezzo        ID del mezzo selezionato
     * @param durataMinuti   Durata stimata in minuti (0 = non fornita)
     * @param distanzaKm     Distanza stimata in km (0 = non fornita)
     * @return               {@link StimaCostoNoleggio} con importo e descrizione
     * @throws IllegalArgumentException se i parametri violano le regole di business
     * @throws RuntimeException         se mezzo o tariffa non trovati
     */
    public StimaCostoNoleggio stimareCostoNoleggio(int idUtente, int idMezzo,
                                                    int durataMinuti, double distanzaKm) {

        // Recupera il mezzo
        Mezzo mezzo = mezzoManager.getMezzo(idMezzo);
        if (mezzo == null) {
            throw new RuntimeException("Mezzo non trovato");
        }

        // Recupera la tariffa
        Tariffa tariffa = tariffaManager.ottieniTariffaVigente(idMezzo, mezzo.getTipologia());
        if (tariffa == null) {
            throw new RuntimeException("Tariffa non trovata per il mezzo selezionato");
        }

        // Recupera velocità media del tipo di mezzo
        double velocita = getVelocitaMedia(mezzo.getTipologia());

        // Completa i parametri mancanti e valida i limiti
        int[]    durata   = { durataMinuti };
        double[] distanza = { distanzaKm };
        completaParametri(durata, distanza, velocita);

        // Calcola importo e costruisci risposta
        double importo     = calcolaStima(tariffa, durata[0], distanza[0]);
        String descrizione = presentaStima(importo, durata[0], distanza[0], mezzo.getTipologia(), tariffa);

        return new StimaCostoNoleggio(importo, descrizione);
    }

    // ---------------------------------------------------------------
    // Metodi di supporto (package-private per testabilità)
    // ---------------------------------------------------------------

    /**
     * Completa il parametro mancante (durata o distanza) usando la velocità media,
     * poi valida che entrambi rientrino nei limiti massimi.
     *
     * <p>Modifica i valori contenuti negli array {@code durata} e {@code distanza}
     * (pattern array-come-riferimento per poter aggiornare i primitivi del chiamante).
     *
     * @param durata   array di 1 elemento con la durata in minuti (0 = non fornita)
     * @param distanza array di 1 elemento con la distanza in km    (0 = non fornita)
     * @param velocita velocità media del mezzo in km/h
     * @throws IllegalArgumentException se i valori violano le regole
     */
    void completaParametri(int[] durata, double[] distanza, double velocita) {
        boolean hasDurata   = durata[0]   > 0;
        boolean hasDistanza = distanza[0] > 0;

        if (!hasDurata && !hasDistanza) {
            throw new IllegalArgumentException(
                "Inserisci almeno la durata (minuti) o la distanza (km) per calcolare la stima.");
        }

        if (hasDurata && !hasDistanza) {
            // Calcola distanza dalla durata
            double distCalcolata = (durata[0] / 60.0) * velocita;
            distanza[0] = Math.round(distCalcolata * 100.0) / 100.0;
        } else if (!hasDurata && hasDistanza) {
            // Calcola durata dalla distanza
            double durataOre = distanza[0] / velocita;
            durata[0] = (int) Math.ceil(durataOre * 60.0);
        }
        // Se entrambi forniti, si usano così come sono.

        // --- Validazione limiti ---
        if (durata[0] > MAX_DURATA_MINUTI) {
            throw new IllegalArgumentException(
                "La durata supera il limite massimo di " + MAX_DURATA_MINUTI + " minuti (2 ore).");
        }
        if (distanza[0] > MAX_DISTANZA_KM) {
            throw new IllegalArgumentException(
                "La distanza supera il limite massimo di " + (int) MAX_DISTANZA_KM + " km.");
        }
    }

    /**
     * Restituisce la velocità media (km/h) per il tipo di mezzo dato.
     * La ricerca è case-insensitive e per sottostringa.
     */
    double getVelocitaMedia(String tipologia) {
        if (tipologia == null) return VELOCITA_DEFAULT_KMH;
        String tipo = tipologia.toLowerCase();
        for (Map.Entry<String, Double> entry : VELOCITA_MEDIA_KMH.entrySet()) {
            if (tipo.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return VELOCITA_DEFAULT_KMH;
    }

    /**
     * Applica la formula tariffaria e arrotonda a 2 decimali.
     */
    double calcolaStima(Tariffa tariffa, int durataMinuti, double distanzaKm) {
        double importo = tariffa.getCostoBase()
                       + (durataMinuti * tariffa.getCostoMinuto())
                       + (distanzaKm  * tariffa.getCostoKm());
        return Math.round(importo * 100.0) / 100.0;
    }

    /**
     * Costruisce la stringa descrittiva della stima da mostrare all'utente.
     */
    String presentaStima(double importo, int durataMinuti, double distanzaKm,
                          String tipologia, Tariffa tariffa) {
        return String.format(java.util.Locale.US,
            "Stima per %s: %d min, %.1f km — sblocco %.2f€ + %.2f€/min + %.2f€/km.",
            tipologia, durataMinuti, distanzaKm,
            tariffa.getCostoBase(), tariffa.getCostoMinuto(), tariffa.getCostoKm());
    }
}
