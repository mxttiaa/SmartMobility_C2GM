# Regole di Modellazione UML - Casi d'Uso
Queste direttive sono estratte dal materiale didattico ufficiale (Corso ITPS) e devono essere applicate rigorosamente a tutte le specifiche del progetto.

## Struttura e Sintassi della Sequenza degli Eventi
*   Ogni passo deve essere espresso rigorosamente con una frase strutturata come: `<numero> il <soggetto> fa <azione>`.
*   Le affermazioni devono essere semplici e non devono mai essere espresse in forma passiva.
*   Il caso d'uso inizia sempre con l'attore primario che fa qualcosa per avviare il caso d'uso.
*   Le ramificazioni si esprimono unicamente con la parola chiave `Se`.
*   Le ripetizioni di eventi si esprimono con le parole chiave `Per` (iterazione) o `Fintantoché` (condizione booleana).
*   Definire il comportamento offerto dal sistema senza alcun riferimento alla struttura interna dello stesso.

## Errori Comuni da Evitare Assolutamente (Regole di Bonifica)
*   È vietato usare i casi d'uso per descrivere le funzionalità interne del sistema, come le funzioni elementari di creazione, lettura, aggiornamento ed eliminazione (CRUD) di singole entità.
*   È vietato fornire la descrizione della logica interna di funzionamento del sistema o la specifica di algoritmi.
*   È vietato inserire indicazioni di livello tecnico relative ai singoli campi di input e output o ai relativi data type.
*   Evitare la scomposizione funzionale: il caso d'uso non deve esprimere chiamate di funzioni annidate, ma un insieme cooperante di comportamenti per l'utente.
*   Esempio formale di correzione: Invece di scrivere "Il sistema chiede al cliente di confermare l'ordine" seguito da "Il cliente digita OK", si deve scrivere unicamente "Il cliente accetta l'ordine".

## Regole sulle Sequenze Alternative
*   Le sequenze alternative descrivono errori, interruzioni o ramificazioni complesse e non devono essere create per ogni singolo possibile errore di digitazione dell'attore.
*   L'identificatore della sequenza alternativa deve essere un numero composito la cui prima cifra è l'identificatore del caso d'uso principale (es. 4.1).
*   Il nome della sequenza alternativa deve fare riferimento a quello della sequenza principale.