package test.backend;

import backend.Mezzo;
import backend.MezzoDAO;
import backend.MezzoManager;

import java.util.ArrayList;
import java.util.List;

public class FlottaTest {
    public static void main(String[] args) {
        System.out.println("Esecuzione test per Flotta Operatore (MezzoManager.getAllMezzi)...");

        // Mock del MezzoDAO
        MezzoDAO mockDAO = new MezzoDAO() {
            @Override
            public List<Mezzo> getAllMezzi() {
                List<Mezzo> mezzi = new ArrayList<>();
                mezzi.add(new Mezzo(1, "Monopattino", 100, 100, 41.1171, 16.8719, "disponibile"));
                mezzi.add(new Mezzo(2, "Bici Elettrica", 120, 80, 41.1180, 16.8720, "guasto"));
                mezzi.add(new Mezzo(3, "Scooter Elettrico", 150, 50, 41.1190, 16.8730, "in uso"));
                return mezzi;
            }
        };

        // Iniezione nel Manager
        MezzoManager manager = new MezzoManager(mockDAO);

        // Recupero di tutta la flotta
        List<Mezzo> flotta = manager.getAllMezzi();

        // Verifiche rigorose
        if (flotta == null) {
            throw new RuntimeException("ERRORE: La flotta restituita e' null.");
        }
        
        if (flotta.size() != 3) {
            throw new RuntimeException("ERRORE: Ci si aspettava 3 mezzi, ma ne sono stati trovati " + flotta.size());
        }

        if (!"disponibile".equals(flotta.get(0).getStatoOperativo()) || flotta.get(0).getIdMezzo() != 1) {
            throw new RuntimeException("ERRORE: Dati alterati o errati per il mezzo 1.");
        }

        if (!"guasto".equals(flotta.get(1).getStatoOperativo()) || flotta.get(1).getIdMezzo() != 2) {
            throw new RuntimeException("ERRORE: Dati alterati o errati per il mezzo 2.");
        }

        if (!"in uso".equals(flotta.get(2).getStatoOperativo()) || flotta.get(2).getIdMezzo() != 3) {
            throw new RuntimeException("ERRORE: Dati alterati o errati per il mezzo 3.");
        }

        System.out.println("SUCCESSO: Il test di recupero della flotta operatore e' passato!");
    }
}
