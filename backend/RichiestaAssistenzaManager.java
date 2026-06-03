package backend;

import java.util.List;

public class RichiestaAssistenzaManager {
    private RichiestaAssistenzaDAO dao;

    public RichiestaAssistenzaManager() {
        this.dao = new RichiestaAssistenzaDAO();
    }

    public RichiestaAssistenza creaRichiesta(int idUtente, String descrizione) {
        if (descrizione == null || descrizione.trim().isEmpty()) {
            return null;
        }

        RichiestaAssistenza richiesta = new RichiestaAssistenza();
        richiesta.setIdUtente(idUtente);
        richiesta.setDescrizioneProblema(descrizione);
        richiesta.setStato("aperta");

        return dao.save(richiesta);
    }

    public RichiestaAssistenza getRichiesta(int id) {
        return dao.findById(id);
    }

    public List<RichiestaAssistenza> getRichiesteUtente(int idUtente) {
        return dao.findAllByUtente(idUtente);
    }

    public boolean aggiornaStato(int id, String stato) {
        if (stato == null || stato.trim().isEmpty()) {
            return false;
        }
        return dao.updateStato(id, stato);
    }
}
