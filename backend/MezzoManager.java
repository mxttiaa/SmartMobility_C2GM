package backend;

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
}
