| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Visualizzare Mappa |
| **ID** | UC-06 |
| **Breve descrizione** | Il sistema mostra su una mappa interattiva i veicoli liberi nelle vicinanze basandosi sulla posizione GPS dell'utente, permettendo di filtrare i risultati per categoria e per raggio d'azione fino a 1500m. |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Nessuno |
| **Precondizione** | L'Utente è autenticato all'interno dell'applicazione (Stato: Utente Autenticato). |
| **Evento innescante** | L'Utente accede alla schermata principale dell'applicazione. |
| **Post-condizione per successo** | L'Utente visualizza sulla mappa i veicoli disponibili filtrati secondo la posizione e i criteri d'azione scelti. |
| **Post-condizione per fallimento** | L'Utente non visualizza la mappa o i segnalini dei mezzi e il sistema mostra un messaggio di errore. |
| **Punti di estensione** | **Selezione Mezzo:** Avviene quando l'Utente seleziona un veicolo specifico tra quelli visibili sulla mappa (innesca UC-03). |
| **Sequenza principale degli eventi** | 1. L'Utente richiede la visualizzazione dei mezzi disponibili nelle vicinanze (es. accedendo alla schermata principale).<br>2. Il sistema rileva la posizione GPS corrente del dispositivo dell'Utente.<br>3. Il sistema recupera i dati dei veicoli disponibili entro il raggio di ricerca predefinito rispetto alla posizione rilevata.<br>4. Il sistema mostra all'Utente la mappa con la sua posizione e i segnalini dei veicoli disponibili.<br>5. L'Utente imposta un raggio d'azione personalizzato (fino a un massimo di 1500m) e seleziona le categorie di mezzi desiderate (es. solo bici, solo auto).<br>6. Il sistema aggiorna la visualizzazione dei segnalini sulla mappa in base ai nuovi criteri impostati dall'Utente. |
| **Sequenza alternativa degli eventi** | **2a. Segnale GPS assente o disattivato:**<br>1. Il sistema rileva che i servizi di localizzazione del dispositivo sono disattivati o non raggiungibili.<br>2. Il sistema mostra all'Utente una notifica richiedendo l'attivazione del GPS.<br>3. Il sistema applica una posizione geografica predefinita (es. centro della città di Zootropolis) e prosegue dal passo 3 del flusso principale.<br><br>**3a. Nessun mezzo disponibile nell'area:**<br>1. Il sistema rileva che non sono presenti veicoli liberi entro il raggio selezionato.<br>2. Il sistema mostra la mappa incentrata sulla posizione dell'utente ma priva di segnalini, visualizzando un messaggio informativo (*"Nessun mezzo disponibile nel raggio scelto"*). |