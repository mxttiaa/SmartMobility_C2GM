| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Inviare Richiesta Assistenza |
| **ID** | UC-05 |
| **Breve descrizione** | L'Utente Registrato apre un canale di comunicazione in tempo reale con il Servizio Clienti per risolvere un problema. |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Nessuno |
| **Precondizione** | L'Utente Registrato ha effettuato l'accesso al sistema. |
| **Evento innescante** | L'Utente Registrato seleziona l'opzione per richiedere assistenza. |
| **Post-condizione per successo** | Il Servizio Clienti prende in carico il problema dell'Utente Registrato tramite il canale di comunicazione. |
| **Post-condizione per fallimento** | Il sistema informa l'Utente Registrato che l'assistenza in tempo reale non è al momento erogabile. |
| **Punti di estensione** | Nessuno |
| **Sequenza principale degli eventi** | 1. Il caso d'uso inizia quando l'Utente Registrato seleziona l'opzione per richiedere assistenza.<br>2. Il sistema chiede all'Utente Registrato di inserire i dettagli del problema.<br>3. L'Utente Registrato inserisce i dettagli del problema.<br>4. Il sistema inoltra la richiesta di assistenza al Servizio Clienti.<br>5. Il Servizio Clienti avvia la sessione di supporto in tempo reale con l'Utente Registrato. |
| **Sequenza alternativa degli eventi** | **UC-05.1 – Operatori Non Disponibili:**<br>1. La sequenza alternativa inizia dopo il passo 3 della sequenza principale.<br>2. Il sistema rileva che nessun operatore del Servizio Clienti è disponibile.<br>3. Il sistema informa l'Utente Registrato che nessun operatore è attualmente disponibile per la chat in tempo reale. |
