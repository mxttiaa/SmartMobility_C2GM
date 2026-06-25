# AI Context - Progetto Universitario: Smart Mobility

## 📌 Informazioni Generali
* **Nome Software:** Smart Mobility
* **Tipo di Sviluppo:** AI-Assisted Coding (Generazione per macro-blocchi)
* **Obiettivo:** Unificare in una singola piattaforma digitale i sistemi di bike sharing, car sharing ed e-scooter sharing per il Comune di Zootropolis.

## 🛠️ Stack Tecnologico & Strumenti
* **Backend:** Java (JDK 8 o superiore). Nessun framework (no Spring Boot).
* **Database:** DBMS MySQL.
* **Frontend:** HTML, CSS e JavaScript puro (Vanilla).
* **IDE di Sviluppo:** Antigravity IDE.
* **Documentazione:** Markdown (`.md`) e diagrammi strutturali in PlantUML (`.puml`).

## 🏗️ Macro-Architettura del Sistema
Architettura a tre livelli (3-Tier):
1. **Presentation Tier:** File HTML/JS.
2. **Business Logic Tier:** Manager e Controller Java.
3. **Integration Tier:** Interfacce e classi DAO per le operazioni CRUD su MySQL.

## 🏃‍♂️ Stato dei Lavori
* **Sprint/Blocco Attuale:** Blocco 1 - User Management (Step A)
* **Obiettivo Operativo:** Generazione Entità di Dominio, Enumerazioni e Script SQL di inizializzazione per gli Account.
* **Riferimento UML:** `Vista1_UserManager.puml`

## 🤖 Linee Guida per le Risposte dell'IA
* **Aderenza:** Il codice Java generato deve rispettare fedelmente le classi, gli attributi e i metodi descritti nei file `.puml`.
* **Sviluppo Accelerato:** Produrre il codice raggruppando entità, DAO e Manager in base al modulo architetturale richiesto, fornendo blocchi completi e pronti da testare.