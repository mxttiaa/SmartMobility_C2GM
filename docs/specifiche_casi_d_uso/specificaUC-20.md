## UC-20: InviareNotificaScadenza

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | InviareNotificaScadenza |
| **ID** | UC-20 |
| **Breve descrizione** | Invio di un promemoria automatico all'Utente per avvisarlo dell'imminente scadenza della prenotazione in corso. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente ha una prenotazione attiva in corso (UC-04) e non ha ancora sbloccato il veicolo. |
| **Condizione di estensione** | Il tempo rimanente per raggiungere e sbloccare il veicolo raggiunge la soglia temporale di preavviso configurata dal sistema. |
| **Post-condizioni per successo** | La notifica di preavviso viene recapitata all'Utente. |
| **Post-condizioni per fallimento** | La notifica non viene recapitata, ma il conteggio della prenotazione prosegue normalmente. |
| **Evento innescante** | Il sistema raggiunge la soglia temporale di preavviso definita. |
| **Estende il caso d'uso** | `UC-04 PrenotareVeicolo` (Punto di estensione: *scadenzaPrenotazione*) |
| **Sequenza principale degli eventi** | 1. Il sistema rileva che il tempo residuo della prenotazione ha raggiunto la soglia temporale di avviso.<br>2. Il sistema genera un messaggio di promemoria contenente i dettagli del veicolo e il tempo rimanente per lo sblocco.<br>3. Il sistema invia la notifica all'Utente.<br>4. Il sistema comunica al caso d'uso chiamante (UC-04) l'avvenuto invio dell'avviso e il flusso principale prosegue. |
| **Sequenza alternativa degli eventi** | Nessuna. |