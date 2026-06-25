# Report di Revisione Documentazione Smart Mobility

## TASK 1 — Verifica di Copertura e Tracciabilità

### Matrice di Tracciabilità User Story ↔ Casi d'Uso

| User Story | Descrizione sintetica | Caso d'Uso | Copertura |
|:---|:---|:---|:---|
| IF-01 | Registrazione account | UC-01 RegistrareAccount | ✅ Coperta |
| IF-02 | Visualizzare veicoli su mappa | UC-02 EsplorareMappa | ✅ Coperta |
| IF-03 | Prenotare veicolo con durata max | UC-04 PrenotareVeicolo (passo 7) | ✅ Coperta |
| IF-04 | Stima costo prima della conferma | UC-04 PrenotareVeicolo (passo 5) | ✅ Coperta |
| IF-05 | Terminare noleggio e riepilogo costi | UC-05 GestireNoleggio (passi 5-9) | ✅ Coperta |
| IF-06 | Caratteristiche del veicolo | UC-03 ConsultareDettagliVeicolo | ✅ Coperta |
| IF-07 | Tempo stimato per raggiungere veicolo | UC-03 ConsultareDettagliVeicolo (passo 4) | ✅ Coperta |
| IF-08 | Percorso che escluda ZTL e cantieri | UC-06 CalcolarePercorso | ✅ Coperta |
| IF-09 | Suggerimento veicolo più adatto | UC-04 PrenotareVeicolo (passo 3) | ✅ Coperta |
| IF-10 | Usufruire di promozioni | UC-08 GestirePromozioni | ✅ Coperta |
| IF-11 | Richieste di assistenza in tempo reale | UC-09 RichiedereAssistenza | ✅ Coperta |
| IF-12 | Segnalare veicolo non funzionante | UC-10 SegnalareGuasto | ✅ Coperta |
| IF-13 | Livello carica residua veicoli | UC-03 ConsultareDettagliVeicolo (passo 2) + UC-02 EsplorareMappa | ✅ Coperta |
| IF-14 | Sbloccare veicolo tramite identificativo | UC-05 GestireNoleggio (passi 2-3) | ✅ Coperta |
| IF-15 | Registrare metodo di pagamento | UC-07 GestireMetodiPagamento | ✅ Coperta |
| IF-16 | Pausa temporanea della corsa | UC-05.1 GestireNoleggio: Sosta Temporanea | ✅ Coperta |
| IF-17 | Prenotazione simultanea più veicoli | UC-04 PrenotareVeicolo (passo 4) | ✅ Coperta |
| IF-18 | Visualizzare stazioni di ricarica | UC-02 EsplorareMappa (passo 2) | ✅ Coperta |
| IF-19 | Zone non accessibili per tipologia veicolo | UC-02 EsplorareMappa (passo 2) | ✅ Coperta |
| IF-20 | Notifica prima scadenza prenotazione | UC-20 InviareNotificaScadenza | ✅ Coperta |
| IF-21 | Monitorare frequenza utilizzo per tipologia | UC-11 AnalizzareStatisticheMobilità | ✅ Coperta |
| IF-22 | Report aggregati noleggi/distanze | UC-11 AnalizzareStatisticheMobilità | ✅ Coperta |
| IF-23 | Percentuale veicoli operativi vs manutenzione | UC-11 AnalizzareStatisticheMobilità + UC-14 PianificareManutenzioneFlotta | ✅ Coperta |
| IF-24 | Segnalare zone soggette a manutenzione | UC-12 ConfigurareRegoleUrbane | ✅ Coperta |
| IF-25 | Tratte più utilizzate | UC-11 AnalizzareStatisticheMobilità | ✅ Coperta |
| IF-26 | Limite velocità forzato in zone sensibili | UC-12 ConfigurareRegoleUrbane (passo 5) | ✅ Coperta |
| IF-27 | Statistiche propulsione elettrica vs termica | UC-11 AnalizzareStatisticheMobilità | ✅ Coperta |
| IF-28 | Distribuzione veicoli per operatore | UC-13 PianificareDistribuzioneFlotta | ✅ Coperta |
| IF-29 | Notifica soglia minima disponibilità | UC-13 PianificareDistribuzioneFlotta (evento innescante) | ✅ Coperta |
| IF-30 | Elenco filtrabile veicoli guasti | UC-14 PianificareManutenzioneFlotta | ✅ Coperta |
| IF-31 | Elenco filtrabile veicoli con carica critica | UC-14 PianificareManutenzioneFlotta (passo 2) | ✅ Coperta |
| IF-32 | Impedire terminazione fuori area designata | UC-05 GestireNoleggio (passo 6, deviazione) + UC-16 GestireSicurezzaVeicoli | ✅ Coperta |
| IF-33 | Verifica parcheggio vs aree designate | UC-15 MonitorareFlotta (deviazione passo 4) | ✅ Coperta |
| IF-34 | Monitorare posizione GPS durante noleggio | UC-15 MonitorareFlotta (passo 3) | ✅ Coperta |
| IF-35 | Monitorare richieste assistenza con tempi | UC-17 GestireSegnalazioniSupporto | ✅ Coperta |
| IF-36 | Assegnare crediti per parcheggio corretto | UC-21 AssegnareCreditiBonus | ✅ Coperta |
| IF-37 | Bloccare account utente | UC-18 GestireAccountUtenti | ✅ Coperta |
| IF-38 | Blocco remoto veicolo fuori zona | UC-16 GestireSicurezzaVeicoli (passi 4-5) | ✅ Coperta |
| IF-39 | Prenotazioni attive con durata trascorsa | UC-15 MonitorareFlotta (passi 5-8) | ✅ Coperta |
| IF-40 | Avviso veicolo in movimento senza noleggio | UC-16 GestireSicurezzaVeicoli (passi 1-2) | ✅ Coperta |

---

### User Story Orfane (non associate ad alcun Caso d'Uso)

> [!NOTE]
> Nessuna User Story risulta completamente orfana. Tutte le 40 User Story (IF-01 → IF-40) hanno copertura nei 21 Casi d'Uso.


---

### Casi d'Uso Orfani o Puramente Infrastrutturali

| Caso d'Uso | Tipo | Note |
|:---|:---|:---|
| UC-19 AutenticareUtente | **Infrastrutturale (include)** | Non soddisfa direttamente alcuna User Story. È un sottoprocesso incluso da UC-04 e UC-05. Giustificato dal pattern UML `<<include>>`. |
| UC-20 InviareNotificaScadenza | **Estensione** | Modella la User Story IF-20, ma dal punto di vista architetturale è un caso d'uso di estensione (`<<extend>>`) di UC-04. |
| UC-21 AssegnareCreditiBonus | **Estensione** | Modella la User Story IF-36, ma è un caso d'uso di estensione (`<<extend>>`) di UC-05. |

> [!NOTE]
> Nessun Caso d'Uso risulta totalmente orfano. UC-19, UC-20 e UC-21 sono legittimi sottoprocessi o estensioni e sono correttamente collegati ai rispettivi casi d'uso principali.

---

## TASK 2 — Riepilogo Bonifica UML

Interventi applicati sistematicamente su tutti i 21 file:

| Regola | Interventi |
|:---|:---|
| **Forma attiva obbligatoria** | Eliminata ogni forma passiva e ogni frase in cui "il sistema chiede all'utente di fare X" → riformulata come "L'Utente fa X". |
| **Abolizione tecnicalità** | Rimossi riferimenti a: database, routing interno ("il sistema reindirizza"), token, log, QR code, OTP, timer, query, campi di input specifici. |
| **Black-box** | Eliminati tutti i riferimenti a funzioni CRUD, struttura interna del database, algoritmi di calcolo. Sostituiti con risultati osservabili. |
| **Pulizia parentesi esemplificative** | Eliminati tutti gli esempi tra parentesi: "(Nome, Cognome, E-mail, Password)", "(es. numero carta, scadenza, CVV)", "(es. auto, bici)", "(es. centro città)", "(es. 5 minuti alla scadenza)", ecc. |

---

## TASK 3 — Riepilogo Normalizzazione Lessicale

| Termine originale | Termine normalizzato |
|:---|:---|
| mezzo, mezzi | veicolo, veicoli |
| auto, bici, monopattino, scooter, e-scooter | veicolo (termine generico) |
| mappa | cartografia |
| applicazione, app | piattaforma / sistema |
| login, accesso (nel senso di autenticazione) | autenticazione |
| OTP | codice di verifica |
| reindirizza | trasferisce / riporta |
| alert | notifica |
| no-parking zone | area di sosta non consentita |
| ticket (assistenza) | segnalazione |
| dashboard | cruscotto |
| cantiere, cantieri | area soggetta a manutenzione urbana |
| business (regole di) | incentivazione / vigenti |

---

## TASK 4 — Glossario

Il file [GLOSSARIO.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/GLOSSARIO.md) è stato creato con **45 voci** in ordine alfabetico, comprensive di tutti i concetti di dominio richiesti.

---

## Riepilogo File Modificati

| File | Stato | Note principali |
|:---|:---|:---|
| [specificaUC-01.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-01.md) | ✅ Riscritto | Rimossi campi specifici, OTP → codice di verifica |
| [specificaUC-02.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-02.md) | ✅ Riscritto | Mappa → cartografia, rimossi dettagli implementativi |
| [specificaUC-03.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-03.md) | ✅ Riscritto | Mezzo → Veicolo nel nome, rimossi dati da database |
| [specificaUC-04.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-04.md) | ✅ Riscritto | PrenotareMezzo → PrenotareVeicolo, black-box |
| [specificaUC-05.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-05.md) | ✅ Riscritto | QR code rimosso, no-parking zone → area di sosta non consentita |
| [specificaUC-06.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-06.md) | ✅ Riscritto | ZTL espanso, cantieri → aree soggette a manutenzione |
| [specificaUC-07.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-07.md) | ✅ Riscritto | Rimossi campi carta/CVV, stile black-box |
| [specificaUC-08.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-08.md) | ✅ Riscritto | Mezzo → veicolo, rimossi esempi parentetici |
| [specificaUC-09.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-09.md) | ✅ Riscritto | Applicazione → piattaforma, ticket → segnalazione |
| [specificaUC-10.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-10.md) | ✅ Riscritto | Salvata → registrata, forma attiva |
| [specificaUC-11.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-11.md) | ✅ Riscritto | Dashboard → cruscotto, rimosso database/query |
| [specificaUC-12.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-12.md) | ✅ Riscritto | Mappa → cartografia, rimossi esempi |
| [specificaUC-13.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-13.md) | ✅ Riscritto | Operatore → Operatore del Servizio, black-box |
| [specificaUC-14.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-14.md) | ✅ Riscritto | Mezzi → veicoli, parco mezzi → flotta |
| [specificaUC-15.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-15.md) | ✅ Riscritto | Piattaforma Turnio → piattaforma, mezzo → veicolo |
| [specificaUC-16.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-16.md) | ✅ Riscritto | Mezzi → Veicoli nel nome, policy → misure |
| [specificaUC-17.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-17.md) | ✅ Riscritto | Ticket → Segnalazione nel nome e nel corpo |
| [specificaUC-18.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-18.md) | ✅ Riscritto | Stile formale, rimosso "bersaglio" |
| [specificaUC-19.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-19.md) | ✅ Riscritto | Login/accesso → autenticazione, token rimosso |
| [specificaUC-20.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-20.md) | ✅ Riscritto | Timer → sistema rileva, mezzo → veicolo |
| [specificaUC-21.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/specificaUC-21.md) | ✅ Riscritto | Business → incentivazione, stile formale |
| [GLOSSARIO.md](file:///c:/Users/matti/Desktop/ITPS/Documentazione_SmartMobility/Specifiche/GLOSSARIO.md) | ✅ Creato | 45 voci di dominio in ordine alfabetico |
