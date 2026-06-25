## UC-08: GestirePromozioni

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | GestirePromozioni |
| **ID** | UC-08 |
| **Breve descrizione** | Applicazione di sconti o promozioni attive sul noleggio per ottenere benefici economici. |
| **Attori primari** | Utente |
| **Attori secondari** | *Nessuno* |
| **Precondizioni** | L'Utente è autenticato ed è in fase di prenotazione o noleggio di un veicolo, e dispone di un codice promozionale o di una promozione attivabile. |
| **Post-condizioni per successo** | La promozione viene applicata e il costo del noleggio viene ridotto di conseguenza. |
| **Post-condizioni per fallimento** | La promozione non risulta applicabile, il sistema notifica l'Utente e la tariffa standard rimane invariata. |
| **Evento innescante** | L'Utente seleziona una promozione o inserisce un codice sconto durante la procedura di noleggio. |
| **Sequenza principale degli eventi** | 1. L'Utente richiede l'applicazione di un codice promozionale o di un'offerta.<br>2. Il sistema verifica la validità della promozione.<br>3. Il sistema calcola il nuovo importo applicando lo sconto previsto dalla promozione.<br>4. Il sistema mostra all'Utente il beneficio economico ottenuto e il nuovo costo stimato. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 2:** Se la promozione risulta scaduta, già utilizzata o non compatibile con il noleggio in corso, il sistema notifica l'Utente dell'invalidità e lo invita a procedere con la tariffazione standard o a inserire un nuovo codice. |