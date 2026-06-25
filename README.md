# Smart Mobility - Progetto di Gruppo

Il presente documento contiene tutte le istruzioni necessarie per configurare l'ambiente locale ed eseguire l'applicazione.

## 🛠️ Prerequisiti
Assicurarsi di avere installato sul proprio computer:
- **Java Development Kit (JDK)** (versione 8 o superiore)
- **MySQL Server**
- **Antigravity IDE / VS Code** (con l'estensione "Live Server" installata)

## ⚙️ 1. Configurazione del Database
Il progetto utilizza un file di configurazione per evitare di condividere le password su GitHub.
1. Aprire il client MySQL ed eseguire gli script SQL (es. `init.sql`, `update_core.sql`) per creare il database `smart_mobility` e le relative tabelle.
2. Nella cartella principale del progetto, creare una copia del file `config.example.properties` e rinominarla in **`config.properties`**.
3. Aprire il nuovo file `config.properties` e inserire l'username e la password locali di MySQL. *(Nota: questo file è ignorato da Git in automatico, garantendo la sicurezza delle credenziali).*

## 🚀 2. Compilazione ed Esecuzione del Backend (Java)
Aprire un terminale nella root del progetto (la cartella principale) ed eseguire i seguenti comandi:

**Per compilare l'intero progetto (Backend e Test):**

*Linux / macOS (Bash/Zsh):*
`javac -d bin -sourcepath backend/src/main/java -cp "lib/*" backend/src/main/java/com/smartmobility/*/*.java test/*.java`

*Windows (PowerShell):*
`javac -d bin -cp "lib/*" (Get-ChildItem -Path backend\src\main\java -Filter *.java -Recurse).FullName test\*.java`

**Per avviare il server principale (MainServer):**
*Linux / macOS:* `java -cp "bin:lib/*" com.smartmobility.api.MainServer`
*Windows:* `java -cp "bin;lib/*" com.smartmobility.api.MainServer`

## 🌐 3. Avvio del Frontend
1. Entrare nella cartella `/frontend/`.
2. Fare clic destro sul file `index.html` e selezionare **"Open with Live Server"**.
3. Il browser si aprirà automaticamente, consentendo di testare le funzionalità dell'interfaccia.

### 🔑 Account di Test Preconfigurati
Il database viene inizializzato con account fittizi provvisti dei tre ruoli di sistema (RBAC).
Tutti gli account di test utilizzano la password predefinita: `password123`

* **CLIENTI (Utenti Standard):**
  * `mario.rossi@email.it`
  * `laura.bianchi@email.it` (Provvista di 5.0€ di crediti bonus)
* **OPERATORI DEL SERVIZIO:**
  * `giuseppe.verdi@email.it`
  * `anna.neri@email.it`
* **AMMINISTRATORE DI SISTEMA:**
  * `admin@smartmobility.it`

---

## 🤝 4. Regole di Sviluppo e Flusso Git
Per mantenere il codice stabile ed evitare conflitti (il ramo `main` è protetto o designato per il codice in produzione), seguire il seguente flusso di lavoro per ogni nuova funzionalità (User Story):

**Step 1: Aggiornare l'ambiente**
Prima di iniziare a programmare, assicurarsi di avere l'ultima versione del progetto:  
`git pull origin main`

**Step 2: Creare il ramo di lavoro (Branch)**
Creare un ramo isolato utilizzando il nome della funzionalità (es. `uc03-prenotazione`):  
`git checkout -b nome-del-ramo`

**Step 3: Lavorare e salvare**
Scrivere il codice, compilare e testare. Al termine, salvare il lavoro:  
`git add .`  
`git commit -m "Breve descrizione delle modifiche effettuate"`

**Step 4: Inviare il ramo su GitHub**
Caricare il lavoro sul repository remoto:  
`git push -u origin nome-del-ramo`

**Step 5: Aprire una Pull Request**
Andare sulla pagina web di GitHub del progetto. Individuare il pulsante verde **"Compare & pull request"**. Cliccarlo, inserire un titolo e confermare. A questo punto il codice verrà revisionato prima di essere unito al progetto principale.

**Step 6: Sincronizzazione e Pulizia (Dopo il Merge)**
Una volta che la Pull Request è stata approvata e unita (merged) nel progetto principale su GitHub, è fondamentale allineare l'ambiente locale e fare pulizia:

1. Tornare sul ramo principale:  
`git checkout main`

2. Scaricare l'ultima versione del codice (che ora contiene anche il lavoro appena approvato):  
`git pull origin main`

3. Eliminare il ramo locale di lavoro ormai concluso per mantenere pulito l'ambiente:  
`git branch -d nome-del-ramo`

*(💡 **Nota:** Qualora Git dovesse bloccarsi riportando il messaggio "The branch is not fully merged", ed essendo assolutamente sicuri che il codice sia già al sicuro sul ramo `main` di GitHub, è possibile forzare l'eliminazione utilizzando la D maiuscola: `git branch -D nome-del-ramo`)*

---

## 🧪 5. Esecuzione dei Test di Modulo e di Sistema

**Comando di compilazione generale:**
`javac -d bin -sourcepath backend/src/main/java -cp "lib/*" test/*.java`

**Comandi per l'esecuzione dei test:**

* **Test Gestione Account (Blocco 1):**
  `java -cp "bin;lib/*" test.TestUserManager`

* **Test Core Noleggio (Blocco 2):**
  `java -cp "bin;lib/*" test.TestBookingManager`

* **Test Amministrazione (Blocco 3):**
  `java -cp "bin;lib/*" test.TestAdminManager`

* **Test di Integrazione Completo (Ciclo di vita utente):**
  *Linux/macOS:* `java -cp "bin:lib/*" test.TestSistema`
  *Windows:* `java -cp "bin;lib/*" test.TestSistema`

---