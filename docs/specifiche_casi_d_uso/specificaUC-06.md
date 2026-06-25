## UC-06: CalcolarePercorso

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | CalcolarePercorso |
| **ID** | UC-06 |
| **Breve descrizione** | Elaborazione del tragitto ottimale escludendo automaticamente le Zone a Traffico Limitato e le aree soggette a manutenzione urbana. |
| **Attori primari** | Utente |
| **Attori secondari** | Maps & GPS Service |
| **Precondizioni** | L'Utente è geolocalizzato e ha inserito una destinazione valida. |
| **Post-condizioni per successo** | Il sistema elabora e mostra sulla cartografia il percorso ottimizzato verso la destinazione. |
| **Post-condizioni per fallimento** | Il sistema non riesce a calcolare il percorso e notifica l'Utente dell'impossibilità di procedere. |
| **Evento innescante** | L'Utente richiede le indicazioni stradali per raggiungere la destinazione desiderata. |
| **Sequenza principale degli eventi** | 1. L'Utente indica la destinazione da raggiungere.<br>2. Il sistema richiede a Maps & GPS Service la generazione dei percorsi possibili dalla posizione dell'Utente alla destinazione.<br>3. Il sistema recupera la lista delle Zone a Traffico Limitato e delle aree soggette a manutenzione urbana.<br>4. Il sistema comunica a Maps & GPS Service i vincoli territoriali per ricalcolare la rotta escludendo le zone interdette.<br>5. Maps & GPS Service fornisce il percorso ottimale validato.<br>6. Il sistema mostra all'Utente il percorso sulla cartografia. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 5:** Se Maps & GPS Service determina che non esiste alcun percorso accessibile verso la destinazione, il sistema notifica l'Utente dell'impossibilità di generare la rotta e lo invita a scegliere una destinazione alternativa. |