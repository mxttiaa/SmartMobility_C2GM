## UC-12: ConfigurareRegoleUrbane

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | ConfigurareRegoleUrbane |
| **ID** | UC-12 |
| **Breve descrizione** | Definizione delle aree soggette a manutenzione urbana e applicazione di limiti di velocità forzati per zone urbane sensibili. |
| **Attori primari** | Amministrazione Pubblica |
| **Attori secondari** | Maps & GPS Service |
| **Precondizioni** | L'Amministrazione Pubblica è autenticata nel sistema e possiede i privilegi per la gestione territoriale. |
| **Post-condizioni per successo** | Le nuove direttive territoriali sono registrate e attivate a sistema. |
| **Post-condizioni per fallimento** | Le configurazioni non vengono registrate a causa di un errore e il sistema notifica l'Amministrazione Pubblica. |
| **Evento innescante** | L'Amministrazione Pubblica accede alla sezione di configurazione territoriale. |
| **Sequenza principale degli eventi** | 1. L'Amministrazione Pubblica richiede l'inserimento di una nuova regola urbana.<br>2. Il sistema richiede la cartografia aggiornata a Maps & GPS Service.<br>3. Il sistema mostra la cartografia interattiva all'Amministrazione Pubblica.<br>4. L'Amministrazione Pubblica traccia il perimetro dell'area interessata sulla cartografia.<br>5. L'Amministrazione Pubblica seleziona il tipo di restrizione e inserisce i parametri associati.<br>6. L'Amministrazione Pubblica conferma la configurazione.<br>7. Il sistema convalida i parametri e i confini tracciati.<br>8. Il sistema registra la nuova regola rendendola operativa per la flotta.<br>9. Il sistema conferma l'avvenuta registrazione all'Amministrazione Pubblica. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 7:** Se i confini dell'area non sono validi o mancano parametri obbligatori, il sistema interrompe il salvataggio, segnala l'errore e richiede la correzione dei dati inseriti. |