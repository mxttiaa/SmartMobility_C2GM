## UC-04: PrenotareVeicolo

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | PrenotareVeicolo |
| **ID** | UC-04 |
| **Breve descrizione** | Riserva temporanea di uno o più veicoli con calcolo preventivo dei costi e suggerimento sul veicolo più idoneo. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente ha selezionato uno o più veicoli disponibili sulla cartografia. |
| **Post-condizioni per successo** | Il veicolo risulta riservato all'Utente e non è più disponibile per altri. |
| **Post-condizioni per fallimento** | La prenotazione non viene effettuata e i veicoli rimangono disponibili sulla cartografia. |
| **Evento innescante** | L'Utente avvia la procedura di prenotazione per garantirsi la disponibilità del veicolo. |
| **Include il caso d'uso** | `UC-19 AutenticareUtente` |
| **Esteso dal caso d'uso** | `UC-20 InviareNotificaScadenza` (Punto di estensione: *scadenzaPrenotazione*) |
| **Sequenza principale degli eventi** | 1. include (AutenticareUtente).<br>2. L'Utente inserisce la destinazione prevista.<br>3. Il sistema suggerisce il tipo di veicolo più adatto in base alla distanza e alle zone accessibili.<br>4. L'Utente seleziona la tipologia e indica il numero di veicoli desiderati.<br>5. Il sistema mostra una stima del costo del noleggio.<br>6. L'Utente conferma la prenotazione dei veicoli.<br>7. Il sistema riserva i veicoli per una durata massima stabilita e ne impedisce la disponibilità per altri utenti.<br>8. *punto di estensione: scadenzaPrenotazione*.<br>9. Il sistema mostra la conferma dell'avvenuta prenotazione. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 5:** Se il sistema rileva che i parametri inseriti non sono validi, notifica l'Utente e richiede il corretto inserimento.<br><br>**Deviazione al passo 7:** Se nel lasso di tempo della conferma il veicolo è stato prenotato da un altro utente, si attiva la sequenza UC-04.1. |

---

## UC-04.1: PrenotareVeicolo (Veicolo non più disponibile)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | PrenotareVeicolo: Veicolo non più disponibile |
| **ID** | UC-04.1 |
| **Breve descrizione** | Il sistema informa l'Utente che il veicolo selezionato è stato prenotato da un altro utente a causa di un conflitto di tempistiche. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni del segmento** | Il veicolo selezionato non è più disponibile al momento dell'elaborazione della conferma. |
| **Post-condizione di segmento** | Nessun veicolo viene riservato e l'Utente deve effettuare una nuova selezione. |
| **Sequenza degli eventi del segmento** | La sequenza alternativa inizia dopo il passo 6 della sequenza principale.<br>1. Il sistema rileva che uno o più veicoli richiesti non sono più disponibili.<br>2. Il sistema comunica all'Utente l'indisponibilità dei veicoli selezionati.<br>3. Il sistema riporta l'Utente alla visualizzazione della cartografia per una nuova selezione. |
| **Sequenza alternativa degli eventi** | Nessuna. |