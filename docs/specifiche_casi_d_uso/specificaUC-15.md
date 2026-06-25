## UC-15: MonitorareFlotta

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | MonitorareFlotta |
| **ID** | UC-15 |
| **Breve descrizione** | Verifica in tempo reale delle posizioni dei veicoli in noleggio, delle posizioni di parcheggio e delle prenotazioni attive oltre la soglia temporale consentita. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | Maps & GPS Service |
| **Precondizioni** | L'Operatore del Servizio è autenticato e ha accesso alla sezione di monitoraggio in tempo reale della piattaforma. |
| **Post-condizioni per successo** | Il sistema mostra sulla cartografia la posizione, lo stato e la telemetria in tempo reale dei veicoli della flotta. |
| **Post-condizioni per fallimento** | Il sistema non riesce a caricare la cartografia o i dati di geolocalizzazione e mostra un messaggio di errore. |
| **Evento innescante** | L'Operatore del Servizio attiva la funzione di monitoraggio della flotta. |
| **Sequenza principale degli eventi** | 1. L'Operatore del Servizio richiede la visualizzazione della cartografia di monitoraggio.<br>2. Il sistema richiede a Maps & GPS Service il caricamento della cartografia aggiornata.<br>3. Il sistema recupera le coordinate in tempo reale e lo stato operativo di tutti i veicoli.<br>4. Il sistema mostra all'Operatore del Servizio la cartografia interattiva con gli indicatori di stato per ciascun veicolo.<br>5. L'Operatore del Servizio applica filtri specifici per isolare determinate categorie di veicoli.<br>6. Il sistema aggiorna la cartografia mostrando esclusivamente i veicoli conformi ai filtri selezionati.<br>7. L'Operatore del Servizio seleziona un veicolo specifico sulla cartografia per esaminarne il dettaglio.<br>8. Il sistema mostra la scheda informativa con la durata dello stato attuale e la posizione del veicolo. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 4:** Se il sistema individua violazioni di parcheggio o veicoli prenotati oltre la durata massima consentita senza essere sbloccati, il sistema contrassegna tali veicoli con un indicatore di allerta per richiamare l'attenzione dell'Operatore del Servizio. Il flusso riprende dal passo 5. |