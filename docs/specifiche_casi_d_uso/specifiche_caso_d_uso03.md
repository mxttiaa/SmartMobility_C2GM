# Caso d'Uso: UC-03

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Visualizzare Caratteristiche Mezzo |
| **ID** | UC-03 |
| **Breve descrizione** | Il sistema mostra all'Utente le caratteristiche tecniche e la stima dell'autonomia del mezzo selezionato. |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Nessuno |
| **Precondizione** | L'Utente è autenticato e sta visualizzando i mezzi disponibili sulla mappa (UC-01). |
| **Sequenza principale degli eventi** | 1. L'Utente seleziona un mezzo specifico dall'interfaccia.<br>2. Il sistema recupera i dati tecnici associati a quel mezzo (tipologia e portata massima).<br>3. Il sistema recupera il livello di batteria attuale e calcola la distanza percorribile stimata.<br>4. Il sistema mostra all'Utente un riepilogo con la tipologia di mezzo, la portata massima e l'autonomia stimata. |
| **Post-condizione per successo** | L'Utente ha visualizzato correttamente le caratteristiche del mezzo. |
| **Post-condizione per fallimento** | L'Utente non visualizza le informazioni e il sistema rimane sulla schermata precedente. |
| **Evento innescante** | L'Utente seleziona un mezzo visibile nell'interfaccia. |
| **Estende il caso d'uso** | UC-01 Visualizzare mezzi disponibili sulla mappa (Punto di estensione: Selezione del mezzo). |
| **Sequenza alternativa degli eventi** | **2a. Il sistema non riesce a recuperare i dati del mezzo dal database:**<br>2a1: Il sistema mostra all'Utente un messaggio di errore.<br>2a2. Il sistema riporta l'Utente alla visualizzazione dei mezzi sulla mappa. |