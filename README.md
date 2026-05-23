# Smart Mobility - Progetto di Gruppo

Benvenuti nel repository del progetto Smart Mobility. Questo documento contiene tutte le istruzioni necessarie per configurare l'ambiente locale ed eseguire l'applicazione.

## 🛠️ Prerequisiti
Assicurati di avere installato sul tuo computer:
- **Java Development Kit (JDK)** (versione 8 o superiore)
- **MySQL Server**
- **Antigravity IDE / VS Code** (con l'estensione "Live Server" installata)

## ⚙️ 1. Configurazione del Database
Il progetto utilizza un file di configurazione per evitare di condividere le password su GitHub.
1. Apri il tuo client MySQL ed esegui il file `init.sql` per creare il database `smart_mobility` e le tabelle.
2. Nella cartella principale del progetto, fai una copia del file `config.example.properties` e rinominala in **`config.properties`**.
3. Apri il nuovo file `config.properties` e inserisci il tuo username e la tua password locale di MySQL. *(Nota: questo file è ignorato da Git in automatico, quindi le tue credenziali sono al sicuro).*

## 🚀 2. Avvio del Server Backend (Java)
Apri un terminale nella root del progetto (la cartella principale) ed esegui questi due comandi:

**Per compilare il codice:**
`javac -d bin backend/*.java`

**Per avviare il server (inclusi i driver MySQL):**
`java -cp "bin;lib/*" backend.UtenteController`

Se vedi la scritta *"API in ascolto sulla porta 8080"*, il backend è pronto. Lascia il terminale aperto.

## 🌐 3. Avvio del Frontend
1. Entra nella cartella `/frontend/`.
2. Fai clic destro sul file `index.html` e seleziona **"Open with Live Server"**.
3. Il browser si aprirà automaticamente e potrai testare la registrazione e l'accesso!