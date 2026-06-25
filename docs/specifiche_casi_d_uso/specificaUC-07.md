## UC-07: GestireMetodiPagamento

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireMetodiPagamento |
| **ID** | UC-07 |
| **Breve descrizione** | Registrazione e gestione dei metodi di pagamento associati al profilo dell'Utente per gli addebiti automatici. |
| **Attori primari** | Utente |
| **Attori secondari** | Sistema di Pagamento |
| **Precondizioni** | L'Utente si trova nella sezione di gestione del proprio profilo personale. |
| **Post-condizioni per successo** | Il metodo di pagamento è convalidato e associato al profilo dell'Utente. |
| **Post-condizioni per fallimento** | Il metodo di pagamento viene rifiutato e lo stato del profilo rimane invariato. |
| **Evento innescante** | L'Utente seleziona l'opzione per aggiungere un nuovo metodo di pagamento. |
| **Sequenza principale degli eventi** | 1. L'Utente richiede l'aggiunta di un nuovo metodo di pagamento.<br>2. Il sistema mostra il modulo per l'inserimento dei dati.<br>3. L'Utente inserisce i dati richiesti e conferma.<br>4. Il sistema invia i dati al Sistema di Pagamento per la validazione.<br>5. Il Sistema di Pagamento conferma la validità del metodo di pagamento.<br>6. Il sistema associa il metodo di pagamento al profilo dell'Utente.<br>7. Il sistema conferma all'Utente l'avvenuta registrazione del metodo di pagamento. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 5:** Se il Sistema di Pagamento rifiuta i dati inseriti, il sistema notifica l'Utente dell'errore di validazione e lo riporta al modulo di inserimento. |