## UC-16: GestireSicurezzaVeicoli

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestireSicurezzaVeicoli |
| **ID** | UC-16 |
| **Breve descrizione** | Rilevamento immediato di movimenti sospetti, blocco da remoto di veicoli fuori zona e prevenzione della terminazione del noleggio in aree di sosta non consentite. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Operatore del Servizio è autenticato sulla piattaforma e dispone dei privilegi di amministrazione per la sicurezza. |
| **Post-condizioni per successo** | Il sistema applica le misure di sicurezza, notificando gli eventi anomali o bloccando i veicoli interessati. |
| **Post-condizioni per fallimento** | Il sistema non riesce a forzare il blocco del veicolo a causa di un errore e notifica l'Operatore del Servizio. |
| **Evento innescante** | Il sistema rileva automaticamente un'anomalia di sicurezza o l'Operatore del Servizio decide di intervenire manualmente. |
| **Sequenza principale degli eventi** | 1. Il sistema rileva il movimento di un veicolo non associato a un noleggio attivo.<br>2. Il sistema invia un avviso di sicurezza all'Operatore del Servizio.<br>3. L'Operatore del Servizio accede alla notifica e visualizza i dettagli del veicolo in movimento.<br>4. L'Operatore del Servizio invia il comando di blocco remoto del veicolo.<br>5. Il sistema esegue il blocco e conferma all'Operatore del Servizio la messa in sicurezza del veicolo. |
| **Sequenza alternativa degli eventi** | **Deviazione per sosta non consentita:** Se l'anomalia riguarda il tentativo di terminare un noleggio fuori dalle aree di parcheggio designate, il sistema impedisce la terminazione della corsa al fine di garantire la restituzione nelle posizioni prestabilite. Il sistema registra l'evento e lo rende disponibile all'Operatore del Servizio. |