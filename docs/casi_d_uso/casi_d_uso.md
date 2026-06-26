| ID | Nome Caso d'Uso | User Stories Incluse | Breve Descrizione | Attore Primario | Attori Secondari |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **UC-01** | RegistrareAccount | IF-01 | Registrazione di un nuovo profilo utente per ottenere l'accesso ai servizi. | Utente | Identity Provider Esterno |
| **UC-02** | EsplorareMappa | IF-02, IF-18, IF-19 | Visualizzazione interattiva su mappa dei veicoli liberi, delle stazioni di ricarica e delle zone non accessibili. | Utente | Maps & GPS Service |
| **UC-03** | ConsultareDettagliMezzo | IF-06, IF-07, IF-13 | Visualizzazione delle specifiche tecniche, dello stato di carica e del tempo di raggiungimento del veicolo selezionato. | Utente | *Nessuno* |
| **UC-04** | PrenotareMezzo | IF-03, IF-04, IF-09, IF-17 | Riserva di uno o più veicoli con calcolo preventivo dei costi. **Include:** `AutenticareUtente`. **Esteso da:** `InviareNotificaScadenza`. | Utente | *Nessuno* |
| **UC-05** | GestireNoleggio | IF-05, IF-14, IF-16 | Sblocco del mezzo tramite identificativo, sosta temporanea e terminazione del servizio. **Include:** `AutenticareUtente`. **Esteso da:** `AssegnareCreditiBonus`. | Utente | *Nessuno* |
| **UC-06** | CalcolarePercorso | IF-08 | Elaborazione del tragitto ottimale escludendo in automatico le ZTL e i cantieri urbani. | Utente | Maps & GPS Service |
| **UC-07** | GestireMetodiPagamento | IF-15 | Registrazione e gestione dei metodi di pagamento associati al profilo per gli addebiti automatici. | Utente | Sistema di Pagamento |
| **UC-08** | GestirePromozioni | IF-10 | di sconti o promozioni attive sul noleggio per ottenere benefici economici. | Utente | *Nessuno* |
| **UC-09** | RichiedereAssistenza | IF-11 | Comunicazione in tempo reale con il supporto clienti per la risoluzione di problematiche in corso. | Utente | *Nessuno* |
| **UC-10** | SegnalareGuasto | IF-12 | Invio di una segnalazione relativa a un mezzo non funzionante, specificandone l'anomalia. | Utente | *Nessuno* |
| **UC-11** | AnalizzareStatisticheMobilità | IF-21, IF-22, IF-23, IF-25, IF-27 | Accesso a report aggregati su tendenze di utilizzo, efficienza della flotta, tratte critiche e abbattimento di CO2. | Amministrazione Pubblica | *Nessuno* |
| **UC-12** | ConfigurareRegoleUrbane | IF-24, IF-26 | Mappatura delle aree soggette a manutenzione e applicazione di limiti di velocità forzati per zone urbane sensibili. | Amministrazione Pubblica | Maps & GPS Service |
| **UC-13** | PianificareDistribuzioneFlotta | IF-28, IF-29 | Controllo della densità dei veicoli sul territorio e ricezione alert per redistribuzione tattica dei mezzi. | Operatore del Servizio | *Nessuno* |
| **UC-14** | PianificareManutenzioneFlotta | IF-30, IF-31 | Gestione degli elenchi dei veicoli segnalati come guasti o con livello di batteria critico per interventi fisici. | Operatore del Servizio | *Nessuno* |
| **UC-15** | MonitorareFlotta | IF-33, IF-34, IF-39 | Verifica in tempo reale delle posizioni GPS di noleggio, delle posizioni di parcheggio e delle prenotazioni attive protratte. | Operatore del Servizio | Maps & GPS Service |
| **UC-16** | GestireSicurezzaMezzi | IF-32, IF-38, IF-40 | Rilevamento immediato di movimenti sospetti, blocco da remoto di veicoli fuori zona e prevenzione chiusura noleggio in aree vietate. | Operatore del Servizio | *Nessuno* |
| **UC-17** | GestireTicketSupporto | IF-35 | Presa in carico, monitoraggio dello stato e risoluzione delle richieste di assistenza in arrivo dagli utenti. | Operatore del Servizio | *Nessuno* |
| **UC-18** | GestireAccountUtenti | IF-37 | Intervento di sospensione o blocco dei profili utente in presenza di comportamenti fraudolenti o illeciti. | Operatore del Servizio | *Nessuno* |
| **UC-19** | AutenticareUtente | *Nessuna (Infrastrutturale)* | Sottoprocesso di verifica delle credenziali d'accesso dell'attore prima di consentire operazioni dispositive sul sistema. | Utente | *Nessuno* |
| **UC-20** | InviareNotificaScadenza | IF-20 | Alert inviato al dispositivo dell'utente in prossimità della scadenza del tempo massimo di prenotazione. | Tempo | *Nessuno* |
| **UC-21** | AssegnareCreditiBonus | IF-36 | Attribuzione di una ricompensa sul portafoglio dell'utente a seguito del corretto posteggio nelle aree di restituzione prioritarie. | Utente | *Nessuno* |