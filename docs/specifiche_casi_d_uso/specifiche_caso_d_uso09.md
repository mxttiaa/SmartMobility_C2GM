| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Stimare Costo Noleggio |
| **ID** | UC-09 |
| **Breve descrizione** | L'Utente Registrato seleziona un mezzo e visualizza la stima del costo per decidere se confermare o meno il noleggio. |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Nessuno |
| **Precondizione** | L'Utente Registrato ha effettuato l'accesso e ha selezionato un mezzo. |
| **Evento innescante** | L'Utente Registrato richiede la stima del costo di un noleggio. |
| **Post-condizione per successo** | La stima del costo del noleggio è stata visualizzata dall'Utente Registrato. |
| **Post-condizione per fallimento** | Il sistema rileva che i parametri inseriti dall'Utente Registrato per il calcolo della stima non sono validi e ne richiede il corretto inserimento. |
| **Punti di estensione** | Nessuno |
| **Sequenza principale degli eventi** | 1. Il caso d'uso inizia quando l'Utente Registrato richiede la stima del costo di un noleggio.<br>2. Il sistema richiede all'Utente Registrato di specificare i parametri del noleggio.<br>3. L'Utente Registrato inserisce i parametri richiesti.<br>4. Il sistema calcola la stima del costo sulla base delle tariffe vigenti.<br>5. Il sistema presenta all'Utente Registrato la stima del costo del noleggio. |
| **Sequenza alternativa degli eventi** | **UC-09.1 – Parametri Non Valid:**<br>1. La sequenza alternativa inizia dopo il passo 3 della sequenza principale.<br>2. Il sistema rileva che i parametri inseriti non sono validi.<br>3. Il sistema notifica l'Utente Registrato dell'errore e richiede il nuovo inserimento dei dati. |
