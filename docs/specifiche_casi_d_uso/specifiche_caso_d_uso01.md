## UC-01: Registrare Utente

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Registrare Utente |
| **ID** | UC-01 |
| **Breve descrizione** | Registrazione di un nuovo utente alla piattaforma |
| **Attori primari** | Utente non registrato |
| **Attori secondari** | Identity Provider Esterno, Sistema di Autenticazione |
| **Precondizioni** | L'Utente non è iscritto alla piattaforma |
| **Sequenza principale degli eventi** | 1. L'Utente non registrato richiede di registrarsi al sistema.<br>2. Il sistema mostra il modulo di registrazione.<br>3. L'Utente non registrato inserisce i propri dati (Nome, Cognome, E-mail, Password) e conferma.<br>4. Il sistema richiede al Sistema di Autenticazione la generazione e l'invio di un codice OTP.<br>5. Il sistema chiede all'Utente non registrato l'inserimento del codice ОТР.<br>6. L'Utente non registrato inserisce il codice OTP.<br>7. Il sistema valida il codice OTP interagendo con il Sistema di Autenticazione.<br>8. Il sistema crea un nuovo account Utente utilizzando i dati inseriti.<br>9. Il sistema mostra un messaggio di registrazione avvenuta con successo.<br>10. Il sistema autentica automaticamente l'utente e mostra la schermata Home. |
| **Post-condizione per successo** | L'Utente è iscritto alla piattaforma e si trova in stato "Autenticato" |
| **Post-condizione per fallimento** | L'Utente non è iscritto alla piattaforma |
| **Evento innescante** | L'Utente richiede di registrarsi alla piattaforma |
| **Sequenza alternativa degli eventi** | **3a.** L'Utente seleziona l'opzione di autenticazione rapida (Vedi UC-01.1)<br><br>**4a.** Il sistema rileva che l'e-mail è già associata a un account:<br>• 4a1. Il sistema mostra un errore e suggerisce di effettuare il Login (reindirizzando al caso d'uso "Accesso Utente UC-02").<br><br>**7a.** Il sistema rileva che il codice OTP è errato o scaduto:<br>• 7a1. Il sistema mostra un errore di validazione e suggerisce di riprovare o di correggere i dati (ritorno al punto 3 o 5). |

---

## UC-01.1: Registrare Utente (Autenticazione Rapida)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | Registrare Utente (Autenticazione Rapida) |
| **ID** | UC-01.1 |
| **Breve descrizione** | Registrazione di un nuovo utente alla piattaforma |
| **Attori primari** | Utente non registrato |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni** | L'Utente non è iscritto alla piattaforma |
| **Sequenza principale degli eventi** | **3a.** L'Utente non registrato seleziona l'opzione di autenticazione rapida<br>**3a1.** Il sistema reindirizza l'utente al provider esterno.<br>**3a2.** L'utente si autentica sul provider esterno e autorizza la condivisione dei dati.<br>**3a3.** Il sistema riceve i dati (Nome, Cognome, E-mail) dal provider, crea l'account e prosegue al punto 10. |
| **Post-condizione per successo** | L'Utente è iscritto alla piattaforma e si trova in stato "Autenticato" |
| **Post-condizione per fallimento** | L'Utente non è iscritto alla piattaforma |
| **Evento innescante** | L'Utente richiede di registrarsi alla piattaforma e seleziona l'opzione di autenticazione rapida |
| **Sequenza alternativa degli eventi** | Nessuna |

