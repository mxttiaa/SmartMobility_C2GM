# Glossario del Progetto Smart Mobility

Il presente glossario raccoglie e definisce in modo formale e univoco tutti i termini di dominio, le entità di business e i concetti cardine utilizzati nella documentazione del progetto Smart Mobility. Le definizioni sono contestualizzate all'ambito del sistema e ordinate alfabeticamente.

---

## A

### Account
Profilo digitale associato a un singolo utente, contenente i dati anagrafici, i metodi di pagamento registrati, lo storico dei noleggi e lo stato di abilitazione ai servizi della piattaforma. La creazione dell'account costituisce il prerequisito per l'accesso a qualsiasi funzionalità dispositiva del sistema.

### Amministrazione Pubblica
Attore istituzionale del sistema, identificato nel Comune di Zootropolis, che esercita funzioni di governo, monitoraggio e regolamentazione della mobilità urbana. Dispone di privilegi per la configurazione delle regole urbane, la consultazione dei dati statistici aggregati e la definizione delle restrizioni territoriali.

### Area di parcheggio designata
Zona geografica predefinita e approvata dall'Operatore del Servizio in cui è consentito il rilascio del veicolo al termine del noleggio. Il sistema impedisce la terminazione della corsa qualora il veicolo si trovi al di fuori di tali aree.

### Area di restituzione prioritaria
Sottoinsieme delle aree di parcheggio designate, individuato dall'Operatore del Servizio per favorire la redistribuzione ottimale della flotta. Il posteggio del veicolo in una di queste aree comporta l'attribuzione automatica di crediti bonus sul portafoglio virtuale dell'utente.

### Area di sosta non consentita
Zona geografica nella quale il sistema vieta la terminazione del noleggio e il rilascio del veicolo. Il tentativo di concludere una corsa in tale area viene bloccato automaticamente dal sistema, che invita l'utente a riposizionare il veicolo in un'area di parcheggio designata.

### Area soggetta a manutenzione urbana
Porzione del territorio urbano temporaneamente interdetta alla circolazione dei veicoli a causa di interventi infrastrutturali o cantieri. L'Amministrazione Pubblica ne definisce il perimetro e la durata prevista, e il sistema esclude automaticamente tali aree dal calcolo dei percorsi.

### Autenticazione
Processo di verifica dell'identità dell'utente mediante la presentazione e la convalida delle credenziali di accesso. Costituisce un sottoprocesso incluso da tutti i casi d'uso che richiedono operazioni dispositive sul sistema. Può avvenire tramite credenziali dirette o tramite un Identity Provider Esterno.

## C

### Cartografia
Rappresentazione grafica interattiva del territorio urbano, fornita dal servizio Maps & GPS Service, sulla quale il sistema sovrappone indicatori relativi ai veicoli disponibili, alle stazioni di ricarica, alle zone interdette e alle aree di parcheggio. Costituisce l'interfaccia primaria di consultazione per utenti e operatori.

### Codice di verifica
Codice temporaneo a validità limitata generato dal sistema e inviato all'indirizzo di posta elettronica dell'utente durante la procedura di registrazione dell'account, al fine di certificare la titolarità dell'indirizzo fornito.

### Codice promozionale
Sequenza alfanumerica che l'utente può inserire durante la procedura di prenotazione o noleggio per attivare una riduzione tariffaria. Il sistema ne verifica la validità, la compatibilità con il noleggio in corso e l'eventuale precedente utilizzo prima di applicarne il beneficio economico.

### Credenziali
Insieme dei dati riservati necessari all'identificazione dell'utente durante il processo di autenticazione. Il sistema convalida tali dati per concedere o negare l'accesso alle funzionalità dispositive della piattaforma.

### Crediti bonus
Ricompensa economica virtuale accreditata sul portafoglio dell'utente dal sistema a seguito del posteggio del veicolo in un'area di restituzione prioritaria al termine del noleggio. I crediti possono essere utilizzati come forma di sconto sulle tariffe dei noleggi successivi e costituiscono un meccanismo di incentivazione al corretto utilizzo del servizio.

### Cruscotto istituzionale
Interfaccia riservata all'Amministrazione Pubblica per la consultazione dei dati statistici aggregati relativi alla mobilità urbana, all'efficienza della flotta e all'impatto ambientale del servizio.

## D

### Destinazione
Punto geografico indicato dall'utente come obiettivo del proprio spostamento. Il sistema utilizza la destinazione per il calcolo del percorso ottimale, per il suggerimento del veicolo più idoneo e per la stima preventiva del costo del noleggio.

## F

### Filtro di ricerca
Criterio di selezione applicabile dall'utente o dall'operatore per restringere l'insieme dei risultati visualizzati. Nell'ambito della piattaforma, i filtri consentono di circoscrivere la ricerca per tipologia di veicolo, stato operativo, raggio d'azione geografico o categoria di segnalazione.

### Flotta
Insieme complessivo dei veicoli gestiti dall'Operatore del Servizio e resi disponibili agli utenti tramite la piattaforma. La flotta è soggetta a monitoraggio continuo per quanto concerne distribuzione geografica, stato operativo e livello di carica residua.

## G

### Geolocalizzazione
Processo di determinazione della posizione geografica dell'utente o del veicolo mediante il Sistema di Posizionamento Globale (GPS). Costituisce la base informativa per la visualizzazione della cartografia, il calcolo dei percorsi e il monitoraggio della flotta.

### Guasto
Malfunzionamento, danno fisico o anomalia tecnica rilevata su un veicolo della flotta, segnalata dall'utente o individuata autonomamente dal sistema. La segnalazione di guasto viene associata allo storico del veicolo e utilizzata dall'Operatore del Servizio per la pianificazione degli interventi di manutenzione.

## I

### Identity Provider Esterno
Servizio di identità di terze parti utilizzato dal sistema per offrire all'utente la possibilità di registrarsi o autenticarsi in modalità rapida, delegando la verifica dell'identità a un soggetto esterno di fiducia.

### Indicatore di stato
Elemento grafico sovrapposto alla cartografia che rappresenta sinteticamente lo stato operativo di un veicolo. Consente all'Operatore del Servizio di identificare rapidamente le condizioni di ciascun veicolo della flotta.

## L

### Livello di carica residua
Indicatore dello stato energetico corrente di un veicolo a propulsione elettrica o muscolare assistita, espresso in termini di autonomia operativa residua. Il sistema lo utilizza per il calcolo della distanza percorribile stimata e per l'individuazione dei veicoli con carica inferiore alla soglia minima operativa.

### Limite di velocità forzato
Restrizione imposta dall'Amministrazione Pubblica su specifiche zone geografiche sensibili, in virtù della quale il sistema vincola la velocità massima consentita ai veicoli in transito, a tutela della sicurezza pedonale.

## M

### Maps & GPS Service
Attore secondario del sistema che fornisce i servizi di cartografia, geolocalizzazione e calcolo dei percorsi. Costituisce il componente esterno responsabile della rappresentazione grafica del territorio e della generazione delle rotte di navigazione.

### Metodo di pagamento
Strumento finanziario registrato dall'utente nel proprio profilo per l'addebito automatico dei costi di noleggio. Il sistema ne delega la validazione a un Sistema di Pagamento esterno e lo associa stabilmente al profilo dell'utente.

## N

### Noleggio
Periodo di utilizzo di un veicolo da parte dell'utente, compreso tra lo sblocco e il blocco definitivo del veicolo stesso, durante il quale il sistema applica la tariffazione vigente. Il noleggio può prevedere soste temporanee e si conclude con l'addebito del costo finale e la visualizzazione del riepilogo della corsa.

### Notifica di scadenza
Promemoria automatico inviato dal sistema all'utente quando il tempo residuo della prenotazione raggiunge una soglia temporale di preavviso predefinita, al fine di consentire il tempestivo raggiungimento e sblocco del veicolo prima dell'annullamento della riserva.

## O

### Operatore del Servizio
Attore del sistema responsabile della gestione operativa della flotta, della manutenzione dei veicoli, del supporto tecnico agli utenti e dell'applicazione delle misure di sicurezza. Dispone di un pannello di controllo dedicato per il monitoraggio in tempo reale, la pianificazione della distribuzione e la gestione degli account utente.

## P

### Pannello di controllo
Interfaccia operativa riservata all'Operatore del Servizio per la gestione, il monitoraggio e l'amministrazione della flotta, delle segnalazioni di supporto, degli account utente e delle misure di sicurezza.

### Percorso ottimale
Tragitto calcolato dal sistema in collaborazione con Maps & GPS Service che consente all'utente di raggiungere la destinazione nel modo più efficiente, escludendo automaticamente le Zone a Traffico Limitato e le aree soggette a manutenzione urbana.

### Piattaforma
Sistema digitale integrato che unifica in un'unica interfaccia tutti i servizi di mobilità condivisa del Comune di Zootropolis. Costituisce l'ambiente operativo attraverso il quale utenti, operatori e amministrazione pubblica interagiscono con il sistema.

### Portafoglio virtuale
Saldo associato al profilo dell'utente nel quale vengono accreditati i crediti bonus ottenuti e dal quale possono essere detratti sconti sulle tariffe di noleggio successive.

### Prenotazione
Riserva temporanea di uno o più veicoli effettuata dall'utente, con una durata massima stabilita dall'Operatore del Servizio, al fine di garantire la disponibilità del veicolo fino al raggiungimento fisico dello stesso. Alla scadenza della prenotazione senza sblocco, il veicolo torna automaticamente disponibile per altri utenti.

### Profilo utente
Insieme strutturato delle informazioni anagrafiche, dei metodi di pagamento, dello storico dei noleggi, del portafoglio virtuale e dello stato di abilitazione associati a un singolo account registrato sulla piattaforma.

### Promozione
Agevolazione economica applicabile al costo di un noleggio, attivabile dall'utente tramite l'inserimento di un codice promozionale o la selezione di un'offerta disponibile. Il sistema ne verifica la validità e la compatibilità prima di applicare la riduzione tariffaria.

## R

### Raggio di ricerca
Parametro geografico impostabile dall'utente per delimitare l'area circostante entro la quale il sistema visualizza i veicoli disponibili sulla cartografia. Il valore massimo consentito è definito dal sistema.

### Redistribuzione della flotta
Operazione logistica pianificata dall'Operatore del Servizio per riequilibrare la distribuzione geografica dei veicoli, trasferendoli dalle zone con sovrabbondanza a quelle con carenza di disponibilità.

### Regola urbana
Direttiva territoriale definita dall'Amministrazione Pubblica che impone vincoli operativi ai veicoli della flotta in specifiche zone geografiche. Le regole urbane comprendono la segnalazione di aree soggette a manutenzione urbana e l'applicazione di limiti di velocità forzati.

### Riepilogo della corsa
Resoconto sintetico presentato all'utente al termine del noleggio, contenente la durata complessiva, il costo finale addebitato, gli eventuali crediti bonus ottenuti e i dettagli della tratta percorsa.

## S

### Segnalazione di guasto
Comunicazione formale inviata dall'utente al sistema per notificare un malfunzionamento, un danno o un'anomalia riscontrata su un veicolo specifico. La segnalazione viene associata allo storico del veicolo e resa disponibile all'Operatore del Servizio per la pianificazione degli interventi di manutenzione.

### Segnalazione di supporto
Richiesta di assistenza inoltrata dall'utente al servizio clienti per la risoluzione di una problematica. L'Operatore del Servizio ne gestisce la presa in carico, il monitoraggio dello stato di avanzamento e la chiusura.

### Sessione di assistenza
Canale di comunicazione in tempo reale instaurato tra l'utente e un operatore del servizio clienti per la risoluzione immediata di una problematica in corso.

### Sistema di Pagamento
Attore secondario esterno al quale il sistema delega la validazione dei metodi di pagamento registrati dall'utente e l'elaborazione delle transazioni di addebito al termine dei noleggi.

### Sistema di Posizionamento Globale (GPS)
Tecnologia di geolocalizzazione satellitare utilizzata dalla piattaforma per determinare in tempo reale la posizione dei veicoli della flotta e dei dispositivi degli utenti. Costituisce il presupposto tecnico per la visualizzazione della cartografia, il calcolo dei percorsi e il monitoraggio operativo.

### Soglia minima operativa
Livello minimo di carica residua al di sotto del quale un veicolo viene considerato in stato energetico critico e segnalato all'Operatore del Servizio per l'intervento di ricarica o sostituzione della fonte energetica.

### Soglia temporale di preavviso
Intervallo di tempo predefinito dal sistema che precede la scadenza di una prenotazione, al raggiungimento del quale il sistema invia automaticamente una notifica di promemoria all'utente.

### Sosta temporanea
Sospensione provvisoria dell'utilizzo attivo del veicolo durante un noleggio in corso, durante la quale il veicolo viene bloccato ma il conteggio della tariffa prosegue. La durata massima della sosta è stabilita dall'Operatore del Servizio.

### Stazione di ricarica
Postazione fisica predisposta sul territorio urbano presso la quale è possibile ricaricare la fonte energetica dei veicoli della flotta. La posizione delle stazioni è visualizzabile dall'utente sulla cartografia della piattaforma.

## T

### Tariffa
Regime economico applicato dal sistema per il calcolo del costo del noleggio in funzione della durata di utilizzo, della tipologia del veicolo e delle eventuali promozioni attive. Il sistema fornisce all'utente una stima preventiva del costo prima della conferma della prenotazione.

### Tempo di raggiungimento
Stima del tempo necessario all'utente per raggiungere fisicamente un veicolo disponibile dalla propria posizione corrente, calcolata dal sistema in base alla distanza geografica. Costituisce un'informazione di supporto alla decisione nella fase di selezione del veicolo.

### Tipologia di veicolo
Classificazione dei veicoli della flotta in base alle loro caratteristiche funzionali e costruttive. Il sistema utilizza la tipologia per filtrare i veicoli sulla cartografia, suggerire il veicolo più idoneo alla destinazione e determinare le zone accessibili.

### Tratta
Percorso urbano effettivamente percorso da un veicolo durante un noleggio. L'analisi aggregata delle tratte più frequentate consente all'Amministrazione Pubblica di individuare le strade soggette a maggiore usura e di pianificare gli interventi di manutenzione.

## U

### Utente
Attore primario del sistema, identificato come cittadino o turista che utilizza la piattaforma per noleggiare veicoli condivisi. L'Utente deve essere registrato e autenticato per accedere alle funzionalità dispositive del sistema.

## V

### Veicolo
Unità di trasporto condiviso appartenente alla flotta gestita dall'Operatore del Servizio e resa disponibile agli utenti tramite la piattaforma. Ciascun veicolo è identificato univocamente da un codice identificativo e possiede attributi quali tipologia, portata massima, livello di carica residua e stato operativo.

## Z

### Zona a Traffico Limitato (ZTL)
Area del territorio urbano soggetta a restrizioni permanenti o temporanee alla circolazione veicolare, definita dalle normative comunali. Il sistema esclude automaticamente tali zone dal calcolo dei percorsi ottimali proposti all'utente.

### Zona interdetta
Termine generico che comprende le Zone a Traffico Limitato, le aree soggette a manutenzione urbana e le aree di sosta non consentite. Rappresenta qualsiasi porzione del territorio nella quale la circolazione o il rilascio dei veicoli della flotta è limitato o vietato.
