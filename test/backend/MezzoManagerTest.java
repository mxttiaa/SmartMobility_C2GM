package test.backend;

import backend.Mezzo;
import backend.MezzoDAO;
import backend.MezzoManager;

import java.util.ArrayList;
import java.util.List;

public class MezzoManagerTest {
    public static void main(String[] args) {
        System.out.println("Esecuzione test per MezzoManager...");

        // Coordinate utente di riferimento
        double refLat = 41.1171;
        double refLon = 16.8719;

        // Mezzo 1: vicinissimo (dovrebbe essere trovato all'interno del raggio)
        double latVicino = 41.1180;
        double lonVicino = 16.8719;

        // Mezzo 2: lontanissimo (fuori raggio 1500m)
        double latLontano = 42.1171;
        double lonLontano = 17.8719;

        // Creazione di un mock nativo del MezzoDAO
        MezzoDAO mockDAO = new MezzoDAO() {
            @Override
            public List<Mezzo> getAllMezzi() {
                List<Mezzo> mezzi = new ArrayList<>();
                mezzi.add(new Mezzo(1, "Monopattino", 100, 100, latVicino, lonVicino, "disponibile"));
                mezzi.add(new Mezzo(2, "Bici Elettrica", 100, 100, latLontano, lonLontano, "disponibile"));
                return mezzi;
            }
        };

        // Iniezione del mock nel manager
        MezzoManager manager = new MezzoManager(mockDAO);

        // Esecuzione del metodo da testare: cerchiamo in un raggio di 1500 metri
        List<Mezzo> vicini = manager.trovaMezziInVicinanza(refLat, refLon, 1500, "Tutte");

        // Asserzioni (verifiche manuali)
        boolean success = true;
        if (vicini.size() != 1) {
            System.err.println("ERRORE: Ci si aspettava 1 mezzo nel raggio, ma ne sono stati trovati " + vicini.size());
            success = false;
        } else if (vicini.get(0).getIdMezzo() != 1) {
            System.err.println("ERRORE: Il mezzo trovato non è quello atteso. Trovato ID: " + vicini.get(0).getIdMezzo());
            success = false;
        }

        if (success) {
            System.out.println("SUCCESSO: Il test di trovaMezziInVicinanza e calcolo Haversine e' passato!");
        } else {
            System.err.println("FALLIMENTO: Il test non e' passato.");
            System.exit(1);
        }
    }
}
