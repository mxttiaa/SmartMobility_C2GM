## UC-14: PianificareManutenzioneFlotta

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | PianificareManutenzioneFlotta |
| **ID** | UC-14 |
| **Breve descrizione** | Consultazione degli elenchi dei veicoli segnalati come guasti o con livello di carica critico per la pianificazione degli interventi. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Operatore del Servizio è autenticato nel sistema e possiede i permessi per accedere al pannello di controllo tecnico della flotta. |
| **Post-condizioni per successo** | L'Operatore del Servizio consulta e filtra correttamente l'elenco dei veicoli che necessitano di intervento. |
| **Post-condizioni per fallimento** | Il sistema non riesce a recuperare lo stato aggiornato dei veicoli e notifica l'Operatore del Servizio dell'errore. |
| **Evento innescante** | L'Operatore del Servizio seleziona la funzione di visualizzazione dei veicoli da manutenere. |
| **Sequenza principale degli eventi** | 1. L'Operatore del Servizio richiede la visualizzazione dell'elenco dei veicoli che necessitano di intervento.<br>2. Il sistema recupera i dati relativi ai veicoli segnalati come guasti e a quelli con livello di carica inferiore alla soglia minima operativa.<br>3. Il sistema mostra all'Operatore del Servizio l'elenco combinato dei veicoli individuati.<br>4. L'Operatore del Servizio applica uno o più filtri di ricerca.<br>5. Il sistema aggiorna l'elenco in base ai filtri selezionati.<br>6. L'Operatore del Servizio analizza i dati per pianificare le operazioni logistiche di manutenzione o ricarica. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 3:** Se nessun veicolo della flotta presenta segnalazioni di guasto o livelli di carica critici, il sistema mostra un elenco vuoto e conferma all'Operatore del Servizio lo stato ottimale della flotta. |