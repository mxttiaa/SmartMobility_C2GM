package com.smartmobility.manager;

import com.smartmobility.model.Percorso;
import com.smartmobility.model.Posizione;
import com.smartmobility.model.RegolaUrbana;

import java.util.ArrayList;
import java.util.List;

public class LogicService {

    public Percorso calcolaPercorsoOttimale(Posizione posizioneAttuale, String destinazione, List<RegolaUrbana> regoleAttive) {
        // Simulazione della logica di calcolo del percorso
        float distanzaSimulata = 5.4f; // in chilometri
        int tempoStimato = (int) (distanzaSimulata * 4); // stimiamo 4 minuti al chilometro
        
        List<Posizione> tragitto = new ArrayList<>();
        if (posizioneAttuale != null) {
            tragitto.add(posizioneAttuale);
            // Aggiungiamo una posizione fittizia intermedia per simulare il tragitto
            tragitto.add(new Posizione(posizioneAttuale.getLatitudine() + 0.01, posizioneAttuale.getLongitudine() + 0.01));
        }
        
        int numRegole = (regoleAttive != null) ? regoleAttive.size() : 0;
        System.out.println("Calcolo percorso ottimale per '" + destinazione + "' considerando " + numRegole + " regole urbane.");
        
        return new Percorso(destinazione, tempoStimato, distanzaSimulata, tragitto);
    }
}
