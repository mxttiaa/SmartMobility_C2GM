# Caso d'Uso: UC-04

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Registrare Metodo di Pagamento |
| **ID** | UC-04 |
| **Breve descrizione** | Consente all'Utente Registrato di memorizzare un metodo di pagamento all'interno del proprio profilo per abilitare transazioni o addebiti automatici futuri. |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Sistema di Pagamento |
| **Precondizione** | L'utente è autenticato nel sistema (UC EffettuareAccesso già completato). |
| **Sequenza principale degli eventi** | 1. Il sistema presenta il modulo di inserimento dei dati relativi al metodo di pagamento.<br>2. Il sistema chiede i dati del metodo di pagamento.<br>3. L'Utente Registrato inserisce i dati.<br>4. Il sistema valida i dati tramite il Sistema di Pagamento.<br>5. Il sistema rende persistenti le informazioni sul database, associando il metodo di pagamento al profilo dell'utente.<br>6. Il sistema notifica all'Utente Registrato la corretta memorizzazione del metodo di pagamento. |
| **Post-condizione per successo** | Il metodo di pagamento è validato, memorizzato nel sistema e associato univocamente al profilo dell'utente. |
| **Post-condizione per fallimento** | I dati non vengono salvati e lo stato del profilo utente rimane invariato. |
| **Evento innescante** | L'Utente Registrato seleziona la funzione "Aggiungi metodo di pagamento" dal profilo. |
| **Sequenza alternativa degli eventi** | **UC-04.1 DatiPagamentoNonValidi — Precondizione: Il Sistema di Pagamento ha rifiutato i dati inseriti.**<br>1. Il sistema informa l'Utente Registrato che i dati sono non validi.<br>2. La sequenza ritorna al passo 2 della sequenza principale. |