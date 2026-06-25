## UC-21: AssegnareCreditiBonus

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | AssegnareCreditiBonus |
| **ID** | UC-21 |
| **Breve descrizione** | Attribuzione automatica di una ricompensa sul portafoglio virtuale dell'Utente a seguito del corretto posteggio del veicolo nelle aree di restituzione prioritarie. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente ha richiesto la terminazione del noleggio (UC-05) e il veicolo si trova all'interno di un'area consentita. |
| **Condizione di estensione** | La posizione finale del veicolo corrisponde a una delle aree di restituzione prioritarie definite per la redistribuzione della flotta. |
| **Post-condizioni per successo** | I crediti bonus vengono accreditati sul profilo dell'Utente e inclusi nel riepilogo. |
| **Post-condizioni per fallimento** | I crediti non vengono assegnati a causa di un'anomalia e il saldo dell'Utente rimane invariato. |
| **Evento innescante** | Il sistema verifica positivamente la posizione del veicolo durante il punto di estensione *chiusuraNoleggio*. |
| **Estende il caso d'uso** | `UC-05 GestireNoleggio` (Punto di estensione: *chiusuraNoleggio*) |
| **Sequenza principale degli eventi** | 1. Il sistema rileva che il veicolo è stato parcheggiato in un'area di restituzione prioritaria.<br>2. Il sistema calcola l'ammontare dei crediti bonus previsti dalle regole di incentivazione vigenti.<br>3. Il sistema accredita i crediti calcolati sul portafoglio dell'Utente.<br>4. Il sistema comunica al caso d'uso chiamante (UC-05) il bonus ottenuto, affinché venga mostrato nel riepilogo finale della corsa. |
| **Sequenza alternativa degli eventi** | Nessuna. |