# UC-04 – Registrare Metodo di Pagamento

| Campo | Dettaglio |
|---|---|
| **Nome** | Registrare Metodo Pagamento |
| **ID UC** | UC-04 |

## Breve Descrizione

Consente all'Utente Registrato di memorizzare un metodo di pagamento all'interno del proprio profilo per abilitare transazioni o addebiti automatici futuri.

## Attori

- **Primari:** Utente Registrato
- **Secondari:** Sistema di Pagamento

## Precondizione

L'utente è autenticato nel sistema (*EffettuareAccesso* già completato).

## Evento Innescante

L'Utente Registrato seleziona la funzione **"Aggiungi metodo di pagamento"** dal profilo.

---

## Sequenza Principale degli Eventi

1. Il sistema presenta il modulo di inserimento dei dati relativi al metodo di pagamento.
2. Il sistema chiede i dati del metodo di pagamento.
3. L'Utente Registrato inserisce i dati.
4. Il sistema valida i dati tramite il Sistema di Pagamento.
5. Il sistema rende persistenti le informazioni sul database, associando il metodo di pagamento al profilo dell'utente.
6. Il sistema notifica all'Utente Registrato la corretta memorizzazione del metodo di pagamento.

---

## Post-Condizioni

| Esito | Descrizione |
|---|---|
| **Successo** | Il metodo di pagamento è validato, memorizzato nel sistema e associato univocamente al profilo dell'utente. |
| **Fallimento** | I dati non vengono salvati e lo stato del profilo utente rimane invariato. |

---

## Sequenza Alternativa degli Eventi

### DatiPagamentoNonValidi — ID: UC-04.1

**Precondizione:** Il Sistema di Pagamento ha rifiutato i dati inseriti.

**Passi:**

1. Il sistema informa l'Utente Registrato che i dati sono non validi.
2. La sequenza ritorna al **passo 2** della sequenza principale.
