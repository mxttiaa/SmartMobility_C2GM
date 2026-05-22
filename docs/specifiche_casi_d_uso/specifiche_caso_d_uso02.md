## UC-02: Effettuare Accesso

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Effettuare Accesso |
| **ID** | UC-02 |
| **Breve descrizione** | Accesso di un utente alla piattaforma |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni** | L'utente è iscritto alla piattaforma |
| **Sequenza principale degli eventi** | 1. L'Utente registrato richiede di accedere al sistema.<br>2. Il sistema mostra il modulo di accesso.<br>3. L'Utente inserisce i propri dati (E-mail, Password) e conferma.<br>4. Il sistema valida le credenziali inserite.<br>5. Il sistema autentica l'utente e lo reindirizza alla schermata Home (Stato: Utente Autenticato). |
| **Post-condizione per successo** | L'Utente si trova in stato "Autenticato" all'interno dell'applicazione |
| **Post-condizione per fallimento** | L'Utente non viene riconosciuto. |
| **Evento innescante** | L'Utente registrato richiede di accedere alla piattaforma |
| **Sequenza alternativa degli eventi** | **3a.** L'Utente seleziona l'opzione di autenticazione rapida (Vedi UC-02.1)<br><br>**4a.** Il sistema rileva che l'e-mail non è associata a un account:<br>• 4a1. Il sistema mostra un errore e suggerisce di riprovare (ritorno al punto 3) o effettuare la Registrazione (reindirizzando al caso d'uso "Registra Utente UC-01"). |

---

## UC-02.1: Effettuare Accesso (Autenticazione Rapida)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Effettuare Accesso (Autenticazione Rapida) |
| **ID** | UC-02.1 |
| **Breve descrizione** | Accesso di un utente alla piattaforma |
| **Attori primari** | Utente registrato |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni** | L'utente è iscritto alla piattaforma |
| **Sequenza principale degli eventi** | **3a.** L'Utente registrato seleziona l'opzione di autenticazione rapida<br>**3a1.** Il sistema reindirizza l'utente al provider esterno.<br>**3a2.** L'utente si autentica sul provider esterno e autorizza la condivisione dei dati.<br>**3a3.** Il sistema riceve i dati (Nome, Cognome, E-mail) dal provider e prosegue al punto 5. |
| **Post-condizione per successo** | L'Utente si trova in stato "Autenticato" all'interno dell'applicazione |
| **Post-condizione per fallimento** | L'Utente non viene riconosciuto. |
| **Evento innescante** | L'Utente registrato richiede di accedere alla piattaforma e seleziona l'opzione di autenticazione rapida |
| **Sequenza alternativa degli eventi** | Nessuna |