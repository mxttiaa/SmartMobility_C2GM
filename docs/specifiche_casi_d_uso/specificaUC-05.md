## UC-05: GestireNoleggio

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireNoleggio |
| **ID** | UC-05 |
| **Breve descrizione** | Sblocco del veicolo tramite identificativo, gestione di eventuali soste temporanee e terminazione del servizio con addebito. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente si trova fisicamente in prossimità del veicolo che intende utilizzare. |
| **Post-condizioni per successo** | Il noleggio è concluso, il veicolo è bloccato e tornato disponibile, e il costo finale è stato addebitato. |
| **Post-condizioni per fallimento** | Il veicolo non viene sbloccato per un'anomalia, oppure il noleggio non può essere terminato a causa di una posizione non consentita. |
| **Evento innescante** | L'Utente avvia la procedura per sbloccare il veicolo. |
| **Include il caso d'uso** | `UC-19 AutenticareUtente` |
| **Esteso dal caso d'uso** | `UC-21 AssegnareCreditiBonus` (Punto di estensione: *chiusuraNoleggio*) |
| **Sequenza principale degli eventi** | 1. include (AutenticareUtente).<br>2. L'Utente fornisce al sistema l'identificativo del veicolo.<br>3. Il sistema verifica lo stato del veicolo e lo sblocca, avviando il conteggio della tariffa.<br>4. L'Utente utilizza il veicolo per il proprio tragitto.<br>5. L'Utente richiede al sistema la terminazione del noleggio.<br>6. Il sistema verifica che la posizione attuale del veicolo rientri in un'area consentita per il rilascio.<br>7. Il sistema blocca il veicolo e interrompe il conteggio della tariffa.<br>8. *punto di estensione: chiusuraNoleggio*.<br>9. Il sistema mostra all'Utente il riepilogo della corsa e processa l'addebito. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 3:** Se il veicolo risulta in manutenzione, con batteria insufficiente o prenotato da un altro utente, il sistema nega lo sblocco e notifica l'Utente dell'impossibilità di procedere.<br><br>**Deviazione al passo 4:** Se l'Utente richiede di sospendere temporaneamente il noleggio, si attiva la sequenza UC-05.1.<br><br>**Deviazione al passo 6:** Se il veicolo si trova in un'area di sosta non consentita, il sistema nega la terminazione del noleggio e invita l'Utente a riposizionare il veicolo. |

---

## UC-05.1: GestireNoleggio (Sosta Temporanea)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireNoleggio: Sosta Temporanea |
| **ID** | UC-05.1 |
| **Breve descrizione** | L'Utente sospende temporaneamente il noleggio mantenendo il veicolo bloccato, senza terminare la tariffazione della corsa in corso. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni del segmento** | L'Utente ha un noleggio in corso ed è fermo con il veicolo. |
| **Post-condizione di segmento** | Il veicolo viene sbloccato e l'Utente riprende il proprio tragitto. |
| **Sequenza degli eventi del segmento** | La sequenza alternativa inizia dopo il passo 4 della sequenza principale.<br>1. L'Utente richiede la sospensione temporanea del noleggio.<br>2. Il sistema blocca il veicolo.<br>3. Il sistema mantiene attivo il noleggio e prosegue con il conteggio tariffario.<br>4. L'Utente richiede la ripresa del noleggio.<br>5. Il sistema sblocca il veicolo.<br>6. L'Utente riprende il tragitto (ritorno al passo 4 della sequenza principale). |
| **Sequenza alternativa degli eventi** | Nessuna. |