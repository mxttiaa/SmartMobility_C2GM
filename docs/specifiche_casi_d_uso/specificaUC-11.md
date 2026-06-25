## UC-11: AnalizzareStatisticheMobilità

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | AnalizzareStatisticheMobilità |
| **ID** | UC-11 |
| **Breve descrizione** | Consultazione di report aggregati sulle tendenze di utilizzo, sull'efficienza della flotta, sulle tratte critiche e sull'abbattimento delle emissioni di anidride carbonica. |
| **Attori primari** | Amministrazione Pubblica |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Amministrazione Pubblica è autenticata nella piattaforma e dispone dei permessi per accedere al cruscotto istituzionale. |
| **Post-condizioni per successo** | Il sistema elabora e mostra i report statistici richiesti. |
| **Post-condizioni per fallimento** | Il sistema non riesce a generare le statistiche e mostra un messaggio di errore. |
| **Evento innescante** | L'Amministrazione Pubblica accede alla sezione dedicata all'analisi dei dati di mobilità. |
| **Sequenza principale degli eventi** | 1. L'Amministrazione Pubblica richiede la generazione di un report statistico.<br>2. L'Amministrazione Pubblica inserisce i criteri di analisi desiderati.<br>3. L'Amministrazione Pubblica conferma la richiesta.<br>4. Il sistema aggrega i dati storici in base ai criteri specificati.<br>5. Il sistema elabora le informazioni e genera il report.<br>6. Il sistema mostra all'Amministrazione Pubblica i risultati aggregati. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 4:** Se non sono presenti dati per i parametri impostati, il sistema notifica l'assenza di risultati e richiede di modificare i criteri di ricerca. |