package backend;

import java.util.UUID;

/**
 * Classe mock per simulare un sistema di pagamento esterno (es. gateway bancario).
 */
public class SistemaPagamentoEsterno {

    /**
     * Valida i dati forniti per il pagamento. In una vera applicazione,
     * effettuerebbe una chiamata REST a un provider (es. Stripe, PayPal).
     * @param dati I dati della carta di credito e intestatario.
     * @return true se i dati sono considerati validi dal gateway, false altrimenti.
     */
    public boolean validaDatiPagamento(DatiMetodoPagamento dati) {
        // Simulazione: rifiutiamo le carte che iniziano con "0000"
        if (dati.getNumeroCarta() != null && dati.getNumeroCarta().startsWith("0000")) {
            return false;
        }
        return true;
    }

    /**
     * Genera un token univoco di appoggio restituito dal gateway
     * per evitare di memorizzare i dati della carta in chiaro nel sistema.
     * @param dati I dati del metodo di pagamento.
     * @return Stringa rappresentante il token.
     */
    public String generaToken(DatiMetodoPagamento dati) {
        // Simulazione: generazione di un UUID fittizio
        return "tok_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
