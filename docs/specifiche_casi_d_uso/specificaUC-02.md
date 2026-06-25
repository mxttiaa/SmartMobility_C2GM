## UC-02: EsplorareMappa

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | EsplorareMappa |
| **ID** | UC-02 |
| **Breve descrizione** | Visualizzazione interattiva sulla cartografia dei veicoli disponibili, delle stazioni di ricarica e delle zone non accessibili in base alla posizione rilevata. |
| **Attori primari** | Utente |
| **Attori secondari** | Maps & GPS Service |
| **Precondizioni** | L'Utente si trova nella schermata principale della piattaforma. |
| **Post-condizioni per successo** | L'Utente visualizza la cartografia aggiornata con la propria posizione, i veicoli disponibili e le relative infrastrutture. |
| **Post-condizioni per fallimento** | Il sistema non riesce a caricare la cartografia e mostra un messaggio di errore all'Utente. |
| **Evento innescante** | L'Utente accede alla schermata principale o richiede l'aggiornamento della cartografia. |
| **Sequenza principale degli eventi** | 1. Il sistema richiede a Maps & GPS Service il rilevamento delle coordinate correnti dell'Utente.<br>2. Il sistema recupera i veicoli disponibili, le stazioni di ricarica e le zone interdette presenti nei dintorni della posizione rilevata.<br>3. Il sistema mostra all'Utente la cartografia interattiva con gli indicatori corrispondenti.<br>4. L'Utente imposta un raggio di ricerca personalizzato e seleziona le categorie di veicoli di interesse.<br>5. Il sistema aggiorna la visualizzazione in base ai nuovi filtri applicati. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 1:** Se il segnale di geolocalizzazione è assente o disattivato, il sistema notifica l'Utente e applica una posizione geografica predefinita prima di proseguire al passo 2.<br><br>**Deviazione al passo 2:** Se nessun veicolo è disponibile nell'area rilevata, il sistema mostra la cartografia con un messaggio informativo e omette gli indicatori. |