## UC-03: ConsultareDettagliVeicolo

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | ConsultareDettagliVeicolo |
| **ID** | UC-03 |
| **Breve descrizione** | Visualizzazione delle specifiche tecniche, dello stato di carica e del tempo di raggiungimento del veicolo selezionato. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente sta visualizzando la cartografia con i veicoli disponibili (UC-02). |
| **Post-condizioni per successo** | L'Utente visualizza il riepilogo completo delle informazioni di dettaglio del veicolo. |
| **Post-condizioni per fallimento** | Il sistema non mostra i dettagli, notifica l'Utente e ripristina la visualizzazione della cartografia. |
| **Evento innescante** | L'Utente seleziona un veicolo specifico dalla cartografia. |
| **Sequenza principale degli eventi** | 1. L'Utente seleziona un veicolo specifico dalla cartografia.<br>2. Il sistema recupera le specifiche tecniche e lo stato operativo del veicolo.<br>3. Il sistema determina la distanza percorribile stimata in base alla carica residua.<br>4. Il sistema determina il tempo necessario per raggiungere il veicolo in base alla distanza dall'Utente.<br>5. Il sistema mostra all'Utente il riepilogo con la tipologia, la portata, l'autonomia stimata e il tempo di raggiungimento. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 2:** Se il sistema non riesce a recuperare i dati del veicolo, mostra un messaggio di errore all'Utente e ripristina la visualizzazione della cartografia. |