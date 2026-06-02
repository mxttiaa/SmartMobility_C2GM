| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Visualizzare Distribuzione Flotta |
| **ID** | UC-08 |
| **Breve descrizione** | Il sistema mostra su una mappa interattiva la posizione attuale e lo stato operativo di tutti i veicoli appartenenti alla flotta. |
| **Attori primari** | Operatore del Servizio |
| **Attori secondari** | Nessuno |
| **Precondizione** | L'account è autenticato e il sistema ha verificato i permessi di accesso per il ruolo "Operatore". |
| **Sequenza principale degli eventi** | 1. L'Operatore richiede di visualizzare la mappa globale della flotta.<br>2. Il sistema recupera i dati di geolocalizzazione e lo stato (es. disponibile, guasto, in uso) di tutti i mezzi appartenenti all'area urbana gestita.<br>3. Il sistema mostra all'Operatore la mappa completa con i segnalini dei mezzi, consentendo una visione panoramica. |
| **Post-condizione per successo** | L'Operatore visualizza correttamente la mappa con la disposizione aggiornata dell'intera flotta. |
| **Post-condizione per fallimento** | La mappa non viene caricata o i mezzi non vengono mostrati, e il sistema restituisce un messaggio d'errore. |
| **Evento innescante** | L'Operatore seleziona la funzione "Distribuzione Flotta" dal proprio pannello di controllo. |
| **Punti di estensione** | Nessuno |
| **Sequenza alternativa degli eventi** | **2a. Errore di recupero dati:**<br>1. Il sistema non riesce a recuperare le posizioni dei mezzi.<br>2. Il sistema mostra all'Operatore un messaggio di errore ("Impossibile caricare la flotta") e lo invita a riprovare. |