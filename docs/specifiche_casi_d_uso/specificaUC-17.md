## UC-17: GestireSegnalazioniSupporto

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireSegnalazioniSupporto |
| **ID** | UC-17 |
| **Breve descrizione** | Presa in carico, monitoraggio dello stato e risoluzione delle richieste di assistenza in arrivo dagli utenti. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Operatore del Servizio è autenticato sulla piattaforma e ha accesso al modulo di gestione dell'assistenza. |
| **Post-condizioni per successo** | La richiesta di assistenza viene presa in carico, aggiornata o chiusa correttamente. |
| **Post-condizioni per fallimento** | Il sistema non riesce a registrare l'aggiornamento della segnalazione e notifica l'Operatore del Servizio dell'errore. |
| **Evento innescante** | L'Operatore del Servizio accede alla coda di supporto per gestire le richieste inoltrate dagli utenti. |
| **Sequenza principale degli eventi** | 1. L'Operatore del Servizio richiede la visualizzazione della coda delle richieste di assistenza.<br>2. Il sistema mostra l'elenco delle segnalazioni con il relativo stato di avanzamento e i tempi di risposta.<br>3. L'Operatore del Servizio seleziona una segnalazione in attesa per prenderla in carico.<br>4. Il sistema assegna la segnalazione all'Operatore del Servizio e ne aggiorna lo stato.<br>5. L'Operatore del Servizio inserisce l'esito dell'intervento e richiede la chiusura della segnalazione.<br>6. Il sistema registra la risoluzione, aggiorna lo stato finale e archivia la segnalazione. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 5:** Se l'Operatore del Servizio non dispone degli elementi per risolvere immediatamente il problema, inserisce una nota di aggiornamento e aggiorna lo stato della segnalazione a sospeso. Il sistema registra la modifica e la sequenza si interrompe temporaneamente. |