## UC-18: GestireAccountUtenti

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireAccountUtenti |
| **ID** | UC-18 |
| **Breve descrizione** | Intervento di sospensione o blocco dei profili utente in presenza di comportamenti fraudolenti o illeciti. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Operatore del Servizio è autenticato sulla piattaforma e possiede i privilegi di amministrazione per la gestione degli account utente. |
| **Post-condizioni per successo** | L'account dell'utente viene sospeso o bloccato, impedendone futuri utilizzi del servizio. |
| **Post-condizioni per fallimento** | Il sistema non riesce a modificare lo stato dell'account e mostra un messaggio di errore all'Operatore del Servizio. |
| **Evento innescante** | L'Operatore del Servizio decide di sanzionare un profilo a seguito del rilevamento di frodi o infrazioni gravi. |
| **Sequenza principale degli eventi** | 1. L'Operatore del Servizio richiede la visualizzazione del profilo di un utente specifico.<br>2. Il sistema mostra i dati anagrafici, lo storico dei noleggi e lo stato dell'account.<br>3. L'Operatore del Servizio seleziona l'opzione per sospendere o bloccare il profilo e inserisce la motivazione del provvedimento.<br>4. Il sistema convalida la richiesta e aggiorna lo stato dell'account revocando i permessi di utilizzo.<br>5. Il sistema invia una notifica di blocco all'utente interessato.<br>6. Il sistema registra l'azione e conferma all'Operatore del Servizio l'avvenuto blocco. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 4:** Se l'utente selezionato ha un noleggio attualmente in corso, il sistema notifica l'Operatore del Servizio dell'impossibilità di procedere con il blocco e richiede la preventiva terminazione forzata della corsa. |