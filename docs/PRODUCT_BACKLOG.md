PRODUCT BACKLOG
C2GM
SMART MOBILITY

1 PRODUCT BACKLOG

1.1 Introduzione
Il progetto SMART MOBILITY nasce dalla necessità del Comune di Zootropolis di sviluppare un sistema di mobilità sostenibile.
L’ intento principale consiste nell’unif icare in una singola
piattaforma digitale tutti i differenti sistemi di condivisione, tra cu i bike sharing, car sharing ed e-scooter sharing.
La piattaforma non si limita solo alla prenotazione di mezzi, ma tende anche a:
- Ottimizzare le esigenze degli utenti: tramite suggerimenti intelligenti basati sulle coordinate geograf iche e sulla distanza che è necessario coprire.
- Promuovere la sicurezza: sia da un punto di vista economico che urbanistico, grazie al monitoring continuo e alla gestione remota dei mezzi stessi.
- Fornisce strumenti di supporto: fornendo all'amministrazione pubblica informazioni utili alla programmazione di interventi strategici di minimizzazione delle emissioni di CO2.


1.2 Contesto di business
La piattaforma si inserisce nel settore delle Smart Cities e della Green Economy.
Il valore di business si distingue in tre punti principali: - Ambientale: Riduzione del traff ico veicolare privato e minore emissione di gas inquinanti.
- Operativo: Ottimizzazione della gestione delle flotte per gli operatori privati con prevenzione di furti, vandalismo e costi di manutenzione.
- Strategico: Trasformazione dei dati in informazioni utili per aiutare gli enti pubblici a pianificare meglio la gestione della città.

1.3 Stakeholder
In base all'analisi dei bisogni, gli attori principali coinvolti sono: - Utenti: Cittadini o turisti che necessitano di un mezzo per spostarsi in modo rapido, economico e sicuro.
- Operatori del Servizio: Enti responsabili della flotta, della manutenzione dei mezzi e del supporto tecnico.
- Amministrazione Pubblica: Il Comune di Zootropolis, che monitora l'efficienza del sistema e definisce le regole di sicurezza urbana (es. zone ZTL o limiti di velocità).

1.4 Item funzionali
Elenco di tutti i requisiti funzionali espressi attraverso lo schema delle user stories: <br>COME *ruolo* <br>DEVO POTER *fare qualcosa*<br> PER CONSEGUIRE *un risultato* <br> *breve desrizione*
1.4.1 IF-01
Utente – UT.00
COME utente DEVO POTER registrare un account fornendo nome, cognome, indirizzo e-mail
PER CONSEGUIRE l’accesso ai servizi di noleggio della
piattaforma.
Gestione della registrazione tramite inserimento dei dati anagraf ici per la creazione di un profilo abilitato all'utilizzo del sistema.
1.4.2 IF-02
Utente – UT.01
COME utente DEVO POTER visualizzare su una mappa i mezzi disponibili
nelle vicinanze entro un raggio scelto dall’utente fino a un massimo consentito dal sistema PER CONSEGUIRE la possibilità di selezionarne uno.
Visualizzazione interattiva dei veicoli liberi nelle vicinanze basata sulla posizione GPS dell'utente, con la possibilità di f iltrare la ricerca impostando un raggio d'azione.
1.4.3 IF-03
Utente – UT.02
COME utente DEVO POTER prenotare un mezzo selezionato per una durata massima stabilita dall'operatore del servizio PER CONSEGUIRE la riserva del veicolo fino al mio arrivo.
Servizio di prenotazione temporanea con durata massima stabilita dall'operatore per garantire la disponibilità del mezzo.
1.4.4 IF-04
Utente – UT.03
COME utente DEVO POTER visualizzare una stima del costo del noleggio prima di confermarlo PER CONSEGUIRE la decisione se procedere o meno con la prenotazione.
Calcolo preventivo della spesa basato sulle tariffe correnti, fornito prima dell'attivazione del noleggio per supportare l'utente nella scelta economica.
1.4.5 IF-05
Utente – UT.04a
COME utente DEVO POTER terminare il noleggio tramite l'applicazione
PER CONSEGUIRE l’ interruzione della tariffazione.
Funzionalità di chiusura della sessione di noleggio che blocca il mezzo e ferma il conteggio economico.
Utente – UT.04b
COME utente DEVO POTER visualizzare il riepilogo dei costi al termine del noleggio
PER CONSEGUIRE la verif ica dell’ importo addebitato.
Presentazione dei dettagli della transazione subito dopo la chiusura della corsa.
1.4.6 IF-06
Utente – UT.05
COME utente DEVO POTER visualizzare le caratteristiche del mezzo selezionato(tipologia, portata massima e distanza percorribile stimata) PER CONSEGUIRE l'adeguatezza rispetto al percorso previsto.
Esposizione delle caratteristiche tecniche e dello stato di carica del veicolo selezionato per una scelta consapevole.
1.4.7 IF-07
Utente – UT.06
COME utente DEVO POTER conoscere il tempo stimato necessario al mezzo disponibile più vicino per raggiungere la mia posizione
PER CONSEGUIRE la decisione se attendere o scegliere un mezzo alternativo.
Calcolo del tempo di percorrenza previsto per l’arrivo del mezzo
verso la posizione dell'utente.
1.4.8 IF-08
Utente – UT.07
COME utente DEVO POTER visualizzare il percorso più breve verso la destinazione che escluda le zone a traff ico limitato e le zone soggette a manutenzione urbana
PER CONSEGUIRE l’ottimizzazione del tragitto.
Elaborazione del percorso ottimale tenendo dinamicamente conto delle Zone a Traffico Limitato(ZTL) e di eventuali aree soggette a manutenzione urbana.
1.4.9 IF-09
Utente – UT.08
COME utente DEVO POTER ricevere un suggerimento sul tipo di mezzo più adatto alla mia destinazione in base alla distanza da percorrere, alle zone accessibili e alla disponibilità attuale
PER CONSEGUIRE l’assenza di p roblemi di p ercorribilità
lungo il tragitto.
Funzionalità di suggerimento che analizza la distanza, restrizioni di accesso e disponibilità dei veicoli per raccomandare il mezzo più idoneo, prevenendo criticità durante il percorso.
1.4.10 IF-10
Utente – UT.09
COME utente DEVO POTER usufruire di promozioni PER CONSEGUIRE benef ici economici al momento del noleggio.
Applicazione di promozioni direttamente durante la procedura di noleggio per ridurre il costo f inale
1.4.11 IF-11
Utente – UT.10
COME utente DEVO POTER inviare richieste di assistenza in tempo reale
PER CONSEGUIRE l’assistenza necessaria alla risoluzione di
problemi.
Canale di comunicazione con il servizio clienti per ricevere aiuto immediato durante l'utilizzo.
1.4.12 IF-12
Utente – UT.11
COME utente DEVO POTER segnalare un mezzo non funzionante indicandone identif icativo e anomalia riscontrata PER CONSEGUIRE la corretta informazione all'operatore responsabile della manutenzione.
Funzionalità per inviare segnalazioni sui guasti specif icando il codice del veicolo e il problema.
1.4.13 IF-13
Utente – UT.12
COME utente DEVO POTER visualizzare il livello di carica residua di ogni mezzo disponibile PER CONSEGUIRE la scelta del mezzo con autonomia suff iciente a coprire la distanza che intendo percorrere.
Funzionalità che mostra la batteria residua dei mezzi per consentire di effettuare una scelta su quale mezzo noleggiare.
1.4.14 IF-14
Utente – UT.13
COME utente DEVO POTER bloccare il mezzo tramite il suo codice identificativo univoco PER CONSEGUIRE l'inizio effettivo del noleggio.
Sblocco del mezzo e avvio del noleggio tramite il suo codice identificativo.
1.4.15 IF-15
Utente – UT.14
COME utente DEVO POTER registrare i dati di un metodo di pagamento nel mio prof ilo PER CONSEGUIRE la possibilità di utilizzarlo per gli addebiti automatici successivi.
Possibilità di impostare un metodo di pagamento come predef inito nel prof ilo, in modo da utilizzarlo automaticamente per gli addebiti futuri.
1.4.16 IF-16
Utente – UT.15
COME utente
DEVO POTER mettere temporaneamente in pausa la mia
corsa per una durata massima stabilita dall’operatore del
servizio PER CONSEGUIRE la possibilità di effettuare brevi soste senza terminare il noleggio.
Funzionalità per sospendere temporaneamente la corsa, mantenendo bloccato il mezzo per soste rapide senza chiudere def initivamente il noleggio.
1.4.17 IF-17
Utente – UT.16
COME utente
DEVO POTER prenotare simultaneamente più mezzi indicando il numero desiderato PER CONSEGUIRE la disponibilità di mezzi per gli altri membri del mio gruppo.
Possibilità per un singolo utente di riservare più veicoli contemporaneamente.
1.4.18 IF-18
Utente – UT.17
COME utente DEVO POTER visualizzare stazioni di ricarica per il mezzo PER CONSEGUIRE la possibilità di ricaricarlo durante o al termine del tragitto.
Funzionalità che mostra la posizione delle postazioni fisiche in cui collegare il mezzo per ricaricarlo.
1.4.19 IF-19
Utente – UT.18
COME utente DEVO POTER visualizzare sulla mappa le zone non accessibili alla tipologia di mezzo noleggiato
PER CONSEGUIRE una corretta pianif icazione del percorso.
Funzionalità che mostra le aree non accessibili in base al mezzo noleggiato.
1.4.20 IF-20
Utente – UT.19
COME utente DEVO POTER ricevere una notifica prima della scadenza della mia prenotazione con un anticipo temporale predefinito dal sistema PER CONSEGUIRE il mantenimento della priorità sul mezzo.
Alert inviato prima che il tempo di prenotazione scada e il veicolo torni libero per altri.
1.4.21 IF-21
Amministrazione Pubblica – AP.01
COME amministrazione comunale DEVO POTER monitorare la frequenza di utilizzo per ogni tipologia di mezzo(bici, auto, monopattino elettrico) PER CONSEGUIRE l'analisi delle tendenze di utilizzo della mobilità urbana nel tempo.
Cruscotto per visualizzare i dati storici delle corse divisi per veicolo e analizzare le abitudini di spostamento dei cittadini.
1.4.22 IF-22
Amministrazione Pubblica – AP.02
COME amministrazione comunale DEVO POTER accedere a report aggregati contenenti numero di noleggi, distanze percorse e tipologie di mezzo utilizzate per un periodo selezionato PER CONSEGUIRE il supporto alle decisioni strategiche sulla mobilità urbana.
Generazione di report statistici su noleggi e distanze per supportare le decisioni sulla mobilità urbana.
1.4.23 IF-23
Amministrazione Pubblica – AP.03
COME amministrazione comunale DEVO POTER visualizzare la percentuale di mezzi operativi rispetto a quelli in manutenzione PER CONSEGUIRE la valutazione dell'eff icienza complessiva del servizio.
Indicatore che confronta i veicoli disponibili con quelli guasti per misurare l'affidabilità della flotta in tempo reale.
1.4.24 IF-24
Amministrazione Pubblica – AP.04
COME amministrazione comunale DEVO POTER segnalare le zone soggette a manutenzione urbana indicando il perimetro dell'area e la durata prevista dei lavori PER CONSEGUIRE la comunicazione delle zone critiche agli operatori e agli utenti del sistema.
Strumento per segnalare eventuali zone soggette a manutenzione urbana, escludendole così dai percorsi di navigazione degli utenti.
1.4.25 IF-25
Amministrazione Pubblica – AP.05
COME amministrazione comunale DEVO POTER conoscere le tratte più utilizzate
PER CONSEGUIRE la possibilità di pianif icare la manutenzione in specif iche aree Strumento di analisi visiva per identif icare i percorsi urbani più frequentati dai mezzi, agevolando l'individuazione delle strade soggette a maggiore usura.
1.4.26 IF-26
Amministrazione Pubblica – AP.06
COME amministrazione comunale DEVO POTER impostare un limite di velocità forzato per i mezzi in specif iche zone sensibili PER CONSEGUIRE la garanzia della sicurezza pedonale.
Conf igurazione di aree geograf iche specif iche in cui applicare restrizioni di velocità ai mezzi in transito.
1.4.27 IF-27
Amministrazione Pubblica – AP.07
COME amministrazione comunale DEVO POTER visualizzare statistiche sull'utilizzo dei mezzi a propulsione elettrica o muscolare rispetto ai mezzi a motore termico PER CONSEGUIRE la valutazione del contributo del servizio alla riduzione delle emissioni di anidride carbonica nell'area
urbana.
Consultazione dei dati di utilizzo dei veicoli ecosostenibili per calcolare la stima della riduzione di inquinamento nell'area urbana.
1.4.28 IF-28
Operatore del Servizio – OP.01
COME operatore DEVO POTER visualizzare la distribuzione dei mezzi PER CONSEGUIRE l'ottimizzazione del posizionamento della flotta.
Controllo della disposizione dei veicoli sull'area urbana per un’ott imizzazione del posizionamento della flotta.
1.4.29 IF-29
Operatore del Servizio – OP.02
COME operatore
DEVO POTER ricevere una notif ica quando il numero di mezzi disponibili in una zona scende al di sotto di una soglia numerica def inita PER CONSEGUIRE la tempestiva redistribuzione dei mezzi nelle zone con carenza di disponibilità.
Sistema di avviso automatico che segnala le aree in cui la quantità di veicoli disponibili scende sotto il limite minimo tollerato.
1.4.30 IF-30
Operatore del Servizio – OP.03a
COME operatore DEVO POTER visualizzare un elenco filtrabile dei mezzi segnalati come guasti dagli utenti o dal sistema PER CONSEGUIRE la pianif icazione degli interventi di riparazione dei tecnici.
Consultazione e filtraggio delle segnalazioni di malfunzionamento per coordinare e pianif icare gli interventi di riparazione sulla f lotta.
1.4.31 IF-31
Operatore del Servizio – OP.03b
COME operatore DEVO POTER visualizzare un elenco filtrabile dei mezzi con livello di carica residua inferiore alla soglia minima operativa def inita PER CONSEGUIRE la pianif icazione della ricarica o la sostituzione delle batterie.
Lista aggiornata dei veicoli in stato energetico critico, necessaria per organizzare le operazioni logistiche di ricarica.
1.4.32 IF-32
Operatore del Servizio – OP.04
COME operatore
DEVO POTER impedire all'utente di terminare il noleggio se il mezzo non si trova all'interno di un'area di parcheggio designata PER CONSEGUIRE la garanzia che i mezzi siano sempre restituiti nelle posizioni prestabilite al termine della corsa.
Controllo automatico che impedisce la chiusura del noleggio se il veicolo viene lasciato al di fuori delle zone autorizzate per la sosta.
1.4.33 IF-33
Operatore del Servizio – OP.05
COME operatore DEVO POTER verif icare la posizione di parcheggio effettiva di ciascun mezzo rispetto alle aree di parcheggio designate
PER CONSEGUIRE l'identif icazione di violazioni di parcheggio o errori di geolocalizzazione.
Controllo per individuare eventuali parcheggi irregolari ed eventuali errori di geolocalizzazione.
1.4.34 IF-34
Operatore del Servizio – OP.06
COME operatore DEVO POTER monitorare la posizione rilevata dal Sistema di Posizionamento Globale(GPS) del mezzo durante un noleggio attivo PER CONSEGUIRE la localizzazione del mezzo in caso di furto o incidente segnalato.
Tracciamento in tempo reale della posizione del veicolo in uso, f inalizzato esclusivamente alla gestione tempestiva di emergenze, incidenti o recuperi.
1.4.35 IF-35
Operatore del Servizio – OP.07
COME operatore
DEVO POTER monitorare le richieste di assistenza degli utenti con relativi tempi di risposta e stato di avanzamento PER CONSEGUIRE la corretta gestione della coda di supporto.
Gestione delle richieste di supporto, utile ad assegnare, tracciare e risolvere tempestivamente i problemi segnalati dai clienti.
1.4.36 IF-36
Operatore del Servizio – OP.08
COME operatore DEVO POTER assegnare crediti sul conto dell'utente quando il mezzo viene parcheggiato in un'area designata al termine della corsa PER CONSEGUIRE l'incentivo al corretto utilizzo del servizio.
Attribuzione di una ricompensa all'utente a seguito del corretto posteggio nelle aree di restituzione prioritarie.
1.4.37 IF-37
Operatore del Servizio – OP.09
COME operatore DEVO POTER bloccare l'account di un utente PER CONSEGUIRE la prevenzione di futuri utilizzi in caso di frode.
Intervento di sospensione del prof ilo utente per impedire futuri noleggi in caso di comportamenti illeciti.
1.4.38 IF-38
Operatore del Servizio – OP.10
COME operatore DEVO POTER forzare da remoto il blocco di un mezzo non associato a un noleggio attivo PER CONSEGUIRE la sua messa in sicurezza quando viene rilevato al di fuori delle aree consentite.
Blocco a distanza dell'utilizzo del veicolo, attivabile qualora questo venga rilevato in aree non autorizzate in assenza di un noleggio in corso.
1.4.39 IF-39
Operatore del Servizio – OP.11
COME operatore DEVO POTER visualizzare le prenotazioni attive sui mezzi con la durata trascorsa dall'inizio di ciascuna prenotazione PER CONSEGUIRE l'identificazione dei casi in cui un mezzo risulta prenotato ma non sbloccato oltre la durata consentita
dall’ operatore.
Monitoraggio dello stato delle prenotazioni in corso per individuare e svincolare i veicoli trattenuti dagli utenti oltre il tempo massimo previsto.
1.4.40 IF-40
Operatore del Servizio – OP.12
COME operatore DEVO POTER ricevere un avviso di sistema dal rilevamento se un mezzo risulta fisicamente in movimento senza essere associato a un noleggio attivo PER CONSEGUIRE la pronta rilevazione di un possibile furto.
Rilevamento e segnalazione di movimenti del veicolo in assenza di un noleggio attivo, a supporto degli interventi di sicurezza e recupero.