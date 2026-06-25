## UC-19: AutenticareUtente

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | AutenticareUtente |
| **ID** | UC-19 |
| **Breve descrizione** | Sottoprocesso di verifica delle credenziali di autenticazione dell'Utente prima di consentire operazioni dispositive sul sistema. |
| **Attori primari** | Utente |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni** | Il sistema o un'azione specifica richiede che l'Utente sia identificato per proseguire. |
| **Post-condizione per successo** | L'Utente è riconosciuto e il caso d'uso chiamante può proseguire. |
| **Post-condizione per fallimento** | L'Utente non viene riconosciuto e l'operazione in corso viene interrotta. |
| **Evento innescante** | L'Utente tenta di eseguire un'azione che richiede l'autenticazione o richiede esplicitamente l'autenticazione. |
| **Sequenza principale degli eventi** | 1. Il sistema mostra il modulo di autenticazione.<br>2. L'Utente inserisce le proprie credenziali e conferma.<br>3. Il sistema convalida le credenziali inserite.<br>4. Il sistema autentica l'Utente e restituisce il controllo al processo chiamante. |
| **Sequenza alternativa degli eventi** | **Deviazione al passo 2:** Se l'Utente seleziona l'autenticazione rapida tramite provider esterno, si attiva il segmento UC-19.1.<br><br>**Deviazione al passo 3:** Se le credenziali non sono valide, il sistema notifica l'Utente dell'errore e suggerisce di riprovare o di effettuare la registrazione. |

---

## UC-19.1: AutenticareUtente (Autenticazione Rapida)

| Campo | Descrizione |
| :--- | :--- |
| **Nome** | AutenticareUtente: Autenticazione Rapida |
| **ID** | UC-19.1 |
| **Breve descrizione** | L'Utente effettua l'autenticazione utilizzando i dati di un servizio di identità di terze parti. |
| **Attori primari** | Utente |
| **Attori secondari** | Identity Provider Esterno |
| **Precondizioni del segmento** | L'Utente ha selezionato l'opzione di autenticazione tramite provider esterno. |
| **Post-condizione di segmento** | L'Utente è riconosciuto e il caso d'uso chiamante può proseguire. |
| **Sequenza degli eventi del segmento** | La sequenza alternativa inizia al passo 2 della sequenza principale.<br>1. L'Utente seleziona l'opzione di autenticazione rapida.<br>2. Il sistema trasferisce l'Utente all'Identity Provider Esterno.<br>3. L'Utente si autentica presso il provider esterno.<br>4. Il sistema riceve la conferma di validazione dal provider esterno.<br>5. Il sistema autentica l'Utente e restituisce il controllo al processo chiamante. |
| **Sequenza alternativa degli eventi** | Nessuna. |