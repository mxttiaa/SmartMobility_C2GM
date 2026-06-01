package backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager per la logica di business relativa ai Mezzi.
 */
public class MezzoManager {
    private MezzoDAO mezzoDAO;

    public MezzoManager() {
        this.mezzoDAO = new MezzoDAO();
    }

    public Mezzo getMezzo(int idMezzo) {
        return mezzoDAO.getMezzoById(idMezzo);
    }

    /**
     * Calcola la distanza stimata percorribile dal mezzo.
     * La logica dipende dal livello di batteria e dalla tipologia del mezzo.
     * @param mezzo Il mezzo.
     * @return Distanza stimata in chilometri.
     */
    public double calcolaDistanzaStimata(Mezzo mezzo) {
        if (mezzo == null) return 0.0;
        
        double efficienza = 1.0;
        if ("Monopattino".equalsIgnoreCase(mezzo.getTipologia())) {
            efficienza = 0.3; // 1% batteria = 0.3 km
        } else if ("Bici Elettrica".equalsIgnoreCase(mezzo.getTipologia())) {
            efficienza = 0.5; // 1% batteria = 0.5 km
        } else if ("Scooter Elettrico".equalsIgnoreCase(mezzo.getTipologia())) {
            efficienza = 0.8; // 1% batteria = 0.8 km
        }
        
        return mezzo.getLivelloBatteria() * efficienza;
    }

    /**
     * Trova i mezzi in vicinanza, filtrati per raggio e opzionalmente per categoria.
     * @param lat Latitudine utente.
     * @param lon Longitudine utente.
     * @param raggio Raggio in metri.
     * @param categoria Categoria del mezzo (può essere null o vuota).
     * @return Lista di mezzi nel raggio specificato.
     */
    public List<Mezzo> trovaMezziInVicinanza(double lat, double lon, int raggio, String categoria) {
        List<Mezzo> tuttiIMezzi = mezzoDAO.getAllMezzi();
        List<Mezzo> vicini = new ArrayList<>();

        for (Mezzo m : tuttiIMezzi) {
            if (categoria != null && !categoria.trim().isEmpty() && !categoria.equalsIgnoreCase("Tutte")) {
                if (!m.getTipologia().equalsIgnoreCase(categoria)) {
                    continue;
                }
            }

            double distanza = calcolaDistanzaHaversine(lat, lon, m.getLatitudine(), m.getLongitudine());
            if (distanza <= raggio) {
                vicini.add(m);
            }
        }
        return vicini;
    }

    /**
     * Calcola la distanza in metri tra due coordinate geografiche usando la formula di Haversine.
     */
    private double calcolaDistanzaHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Raggio della Terra in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = R * c;
        return distanceKm * 1000; // Converte in metri
    }
}
