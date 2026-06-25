## UC-13: PianificareDistribuzioneFlotta

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | PianificareDistribuzioneFlotta |
| **ID** | UC-13 |
| **Breve descrizione** | L'Operatore del Servizio visualizza la distribuzione della flotta sulla cartografia per individuare aree con carenza o sovrabbondanza di veicoli e pianificarne la redistribuzione. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | Maps & GPS Service |
| **Precondizioni** | L'Operatore del Servizio è autenticato e ha accesso al pannello di controllo direzionale. |
| **Post-condizioni per successo** | L'Operatore del Servizio ha analizzato la distribuzione dei veicoli e ha registrato un piano di redistribuzione. |
| **Post-condizioni per fallimento** | Il sistema non riesce a caricare la cartografia o i dati della flotta, impedendo la pianificazione. |
| **Evento innescante** | Il caso d'uso si avvia per decisione proattiva dell'Operatore del Servizio oppure a seguito di una notifica automatica inviata dal sistema quando il numero di veicoli disponibili in una specifica area scende sotto la soglia minima predefinita. |
| **Sequenza principale degli eventi** | 1. L'Operatore del Servizio richiede la visualizzazione della cartografia globale della distribuzione della flotta.<br>2. Il sistema richiede a Maps & GPS Service il caricamento della cartografia dell'area urbana.<br>3. Il sistema recupera i dati di geolocalizzazione e lo stato operativo di tutti i veicoli.<br>4. Il sistema mostra all'Operatore del Servizio la cartografia completa, evidenziando le zone con criticità di distribuzione.<br>5. L'Operatore del Servizio seleziona un'area critica e definisce le direttive di redistribuzione.<br>6. Il sistema registra il piano di redistribuzione e conferma l'avvenuto salvataggio all'Operatore del Servizio. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 3:** Se il sistema non riesce a recuperare le posizioni aggiornate dei veicoli, mostra all'Operatore del Servizio un messaggio di errore e interrompe la procedura di pianificazione. |