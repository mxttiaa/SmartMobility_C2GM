## UC-09: RichiedereAssistenza

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | RichiedereAssistenza |
| **ID** | UC-09 |
| **Breve descrizione** | L'Utente apre un canale di comunicazione in tempo reale con il servizio clienti per la risoluzione di una problematica. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente si trova all'interno della piattaforma e necessita di supporto. |
| **Post-condizioni per successo** | Il problema dell'Utente viene preso in carico e si avvia la sessione di supporto in tempo reale. |
| **Post-condizioni per fallimento** | Il sistema non riesce a stabilire la connessione con il servizio clienti e notifica l'Utente dell'indisponibilità. |
| **Evento innescante** | L'Utente seleziona l'opzione per contattare l'assistenza. |
| **Sequenza principale degli eventi** | 1. L'Utente richiede l'avvio di una sessione di assistenza.<br>2. L'Utente inserisce i dettagli preliminari del problema o seleziona una categoria di riferimento.<br>3. L'Utente conferma la richiesta di assistenza.<br>4. Il sistema verifica la disponibilità degli operatori del servizio clienti.<br>5. Il sistema inoltra la richiesta all'operatore disponibile.<br>6. Il sistema avvia la sessione di comunicazione in tempo reale tra l'Utente e l'operatore. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 4:** Se nessun operatore del servizio clienti è attualmente disponibile, il sistema notifica l'Utente e suggerisce l'invio di una segnalazione per essere ricontattato in seguito. |