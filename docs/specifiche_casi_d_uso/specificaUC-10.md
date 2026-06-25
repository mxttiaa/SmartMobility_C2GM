## UC-10: SegnalareGuasto

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | SegnalareGuasto |
| **ID** | UC-10 |
| **Breve descrizione** | Segnalazione di un guasto, un malfunzionamento o un danno fisico relativo a un veicolo specifico. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente è identificato nel sistema e ha selezionato il veicolo da segnalare. |
| **Post-condizioni per successo** | La segnalazione di guasto è registrata nel sistema e associata allo storico del veicolo. |
| **Post-condizioni per fallimento** | La segnalazione non viene registrata e il sistema notifica l'Utente dell'errore. |
| **Evento innescante** | L'Utente attiva la funzione di segnalazione guasti per il veicolo. |
| **Sequenza principale degli eventi** | 1. L'Utente richiede l'apertura di una nuova segnalazione di guasto.<br>2. Il sistema mostra le categorie di guasto predefinite.<br>3. L'Utente seleziona la categoria, inserisce la descrizione dell'anomalia e invia la segnalazione.<br>4. Il sistema convalida i dati ricevuti.<br>5. Il sistema registra la segnalazione e la associa al veicolo.<br>6. Il sistema conferma all'Utente l'avvenuta ricezione della segnalazione. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 4:** Se i dati inviati non sono validi o risultano incompleti, il sistema richiede all'Utente di correggere le informazioni. |