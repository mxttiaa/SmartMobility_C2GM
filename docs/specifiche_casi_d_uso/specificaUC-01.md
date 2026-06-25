## UC-01: RegistrareAccount

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | RegistrareAccount |
| **ID** | UC-01 |
| **Breve descrizione** | Registrazione di un nuovo utente alla piattaforma per ottenere l'abilitazione ai servizi di noleggio. |
| **Attori primari** | Utente |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni** | L'Utente non possiede ancora un account attivo sulla piattaforma. |
| **Post-condizione per successo** | Il sistema registra il nuovo account e lo associa all'Utente. |
| **Post-condizione per fallimento** | Il sistema non crea l'account e l'Utente rimane non registrato. |
| **Evento innescante** | L'Utente richiede la creazione di un nuovo profilo. |
| **Sequenza principale degli eventi** | 1. L'Utente richiede la registrazione al sistema.<br>2. Il sistema mostra il modulo di registrazione.<br>3. L'Utente inserisce i propri dati anagrafici e conferma.<br>4. Il sistema genera e invia un codice di verifica all'indirizzo di posta elettronica fornito.<br>5. L'Utente inserisce il codice di verifica ricevuto.<br>6. Il sistema convalida il codice di verifica.<br>7. Il sistema registra il nuovo account e ne conferma la creazione all'Utente. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 3:** Se l'Utente seleziona la registrazione rapida tramite provider esterno, si attiva il segmento UC-01.1.<br><br>**Deviazione al passo 4:** Se l'indirizzo di posta elettronica è già associato a un account esistente, il sistema notifica l'Utente dell'impossibilità di procedere.<br><br>**Deviazione al passo 6:** Se il codice di verifica risulta errato o scaduto, il sistema notifica l'errore e richiede un nuovo inserimento. |

---

## UC-01.1: RegistrareAccount (Registrazione Rapida)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | RegistrareAccount: Registrazione Rapida |
| **ID** | UC-01.1 |
| **Breve descrizione** | Il sistema crea l'account utilizzando i dati forniti da un servizio di identità di terze parti. |
| **Attori primari** | Utente |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni del segmento** | L'Utente ha selezionato l'opzione di registrazione tramite provider esterno. |
| **Post-condizione di segmento** | Il sistema registra il nuovo account utilizzando i dati ricevuti dal provider esterno. |
| **Sequenza degli eventi del segmento** | La sequenza alternativa inizia al passo 3 della sequenza principale.<br>1. L'Utente seleziona l'opzione di registrazione rapida.<br>2. Il sistema trasferisce l'Utente all'Identity Provider Esterno.<br>3. L'Utente si autentica presso il provider esterno e autorizza la condivisione dei propri dati anagrafici.<br>4. Il sistema riceve i dati dal provider esterno e registra il nuovo account.<br>5. Il sistema conferma all'Utente l'avvenuta registrazione. |
| **Sequenza alternativa degli eventi** | Nessuna. |