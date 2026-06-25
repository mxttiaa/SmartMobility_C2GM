package com.smartmobility.manager;

import com.smartmobility.model.Noleggio;
import com.smartmobility.model.Tariffa;

import java.time.Duration;

public class PricingManager {

    public double calcolaPreventivo(Tariffa tariffa, float distanza) {
        // Simulazione per calcolare il tempo basato sulla distanza (es. 1 km = 3 minuti in media)
        double tempoStimatoMinuti = distanza * 3.0;
        return tariffa.getCostoSblocco() + (tempoStimatoMinuti * tariffa.getCostoAlMinuto());
    }

    public double calcolaCostoTotale(Noleggio noleggio, Tariffa tariffa) {
        if (noleggio.getFineNoleggio() == null) {
            throw new IllegalArgumentException("Il noleggio non è ancora terminato.");
        }
        
        long minutiEffettivi = Duration.between(noleggio.getInizioNoleggio(), noleggio.getFineNoleggio()).toMinutes();
        if (minutiEffettivi < 1) {
            minutiEffettivi = 1; // Fissiamo un minimo di 1 minuto
        }
        
        return tariffa.getCostoSblocco() + (minutiEffettivi * tariffa.getCostoAlMinuto());
    }
}
