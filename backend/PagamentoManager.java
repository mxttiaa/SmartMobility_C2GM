package backend;

import java.util.UUID;

/**
 * Manager che orchestra la logica di business per la registrazione e gestione
 * dei metodi di pagamento, interfacciandosi con il DAO e il sistema esterno.
 */
public class PagamentoManager {

    private SistemaPagamentoEsterno sistemaPagamentoEsterno;
    private MetodoPagamentoDAO metodoPagamentoDAO;

    public PagamentoManager() {
        this.sistemaPagamentoEsterno = new SistemaPagamentoEsterno();
        this.metodoPagamentoDAO = new MetodoPagamentoDAO();
    }

    /**
     * Registra un nuovo metodo di pagamento per un utente, validandolo tramite
     * il sistema di pagamento esterno e salvandolo nel database.
     * 
     * @param idUtente L'ID dell'utente a cui associare il metodo di pagamento.
     * @param dati I dati in chiaro forniti dal frontend.
     * @return true se l'operazione è andata a buon fine, false altrimenti.
     * @throws IllegalArgumentException Se i dati o la validazione falliscono.
     */
    public boolean registraMetodoPagamento(int idUtente, DatiMetodoPagamento dati) throws IllegalArgumentException {
        MetodoPagamento temp = new MetodoPagamento();
        
        // 1. Validazione del formato dei dati
        if (!temp.validaFormatoDati(dati)) {
            throw new IllegalArgumentException("Formato dati non valido o mancante");
        }

        // 2. Validazione tramite il sistema di pagamento esterno
        boolean esito = sistemaPagamentoEsterno.validaDatiPagamento(dati);
        if (!esito) {
            throw new IllegalArgumentException("Il Sistema di Pagamento ha rifiutato i dati inseriti");
        }

        // 3. Generazione token di appoggio
        String tokenGateway = sistemaPagamentoEsterno.generaToken(dati);

        // 4. Creazione dell'entità MetodoPagamento (mascherando il numero)
        MetodoPagamento mp = new MetodoPagamento();
        mp.setIdMetodo(UUID.randomUUID().toString());
        mp.setTipoPagamento(dati.getTipoPagamento());
        mp.setNumeroCarta(mp.mascheraNumero(dati.getNumeroCarta()));
        mp.setDataScadenza(dati.getDataScadenza());
        mp.setIntestatario(dati.getIntestatario());
        mp.setTokenGateway(tokenGateway);
        mp.attiva();

        // 5. Salvataggio su Database
        return metodoPagamentoDAO.inserisciMetodoPagamento(idUtente, mp);
    }

    /**
     * Verifica se un utente ha almeno un metodo di pagamento attivo salvato.
     *
     * @param idUtente L'ID dell'utente da verificare.
     * @return {@code true} se l'utente ha almeno un metodo di pagamento attivo,
     *         {@code false} altrimenti (incluso il caso di errore DB, per sicurezza).
     */
    public boolean utenteHaMetodoPagamento(int idUtente) {
        int count = metodoPagamentoDAO.contaMetodiPagamento(idUtente);
        return count >= 1;
    }
}
