document.addEventListener('DOMContentLoaded', () => {
    // Riferimenti ai form
    const registerForm = document.getElementById('register-form');
    const loginForm = document.getElementById('login-form');
    const paymentForm = document.getElementById('payment-form');

    // Riferimenti ai div dei messaggi
    const registerMessage = document.getElementById('register-message');
    const loginMessage = document.getElementById('login-message');
    const paymentMessage = document.getElementById('payment-message');

    /**
     * Funzione helper per mostrare messaggi di successo o errore
     * @param {HTMLElement} element - Il div del messaggio
     * @param {boolean} isSuccess - True per successo, false per errore
     * @param {string} text - Il testo da visualizzare
     */
    const showMessage = (element, isSuccess, text) => {
        element.textContent = text;
        element.className = 'message ' + (isSuccess ? 'success' : 'error');

        // Pulisce il messaggio dopo 5 secondi
        setTimeout(() => {
            element.style.display = 'none';
            element.className = 'message';
            element.style.display = '';
        }, 5000);
    };

    /**
     * Gestione Registrazione (UC-01)
     */
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // Evita il ricaricamento della pagina

            // Raccolta dei dati dal form
            const formData = new FormData(registerForm);
            const data = Object.fromEntries(formData.entries());

            try {
                // Chiamata fetch() nativa in POST
                const response = await fetch('http://localhost:8080/api/registrazione', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    // Risposta 2xx (es. 200 OK, 201 Created)
                    showMessage(registerMessage, true, 'Registrazione completata con successo!');
                    registerForm.reset(); // Svuota i campi
                } else {
                    // Risposta di errore (es. 400, 409)
                    const errorMsg = await response.text();
                    showMessage(registerMessage, false, `Errore: ${errorMsg || 'Registrazione fallita.'}`);
                }
            } catch (error) {
                // Errore di rete / Connessione rifiutata
                showMessage(registerMessage, false, 'Errore di connessione al server.');
                console.error('Registration Error:', error);
            }
        });
    }

    /**
     * Gestione Accesso (UC-02)
     */
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // Evita il ricaricamento della pagina

            // Raccolta dei dati dal form
            const formData = new FormData(loginForm);
            const data = Object.fromEntries(formData.entries());

            try {
                // Chiamata fetch() nativa in POST
                const response = await fetch('http://localhost:8080/api/accesso', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    const responseData = await response.json();
                    if (responseData.token) {
                        localStorage.setItem('token', responseData.token);
                    }
                    if (responseData.ruolo) {
                        localStorage.setItem('ruolo', responseData.ruolo);
                    }

                    // Accesso consentito
                    showMessage(loginMessage, true, 'Accesso effettuato con successo!');
                    loginForm.reset();

                    // Transizione alla scheda successiva
                    setTimeout(() => {
                        document.getElementById('auth-view').classList.add('hidden');
                        if (responseData.ruolo === 'OPERATORE') {
                            document.getElementById('dashboard-view').remove();
                            document.getElementById('operator-view').classList.remove('hidden');
                            fetchFlottaOperatore();
                        } else {
                            document.getElementById('operator-view').remove();
                            document.getElementById('dashboard-view').classList.remove('hidden');
                        }
                    }, 1000); // Mostra il messaggio per 1 secondo prima di cambiare vista
                } else {
                    // Accesso negato (es. 401 Unauthorized)
                    const errorMsg = await response.text();
                    showMessage(loginMessage, false, `Errore: ${errorMsg || 'Credenziali non valide.'}`);
                }
            } catch (error) {
                // Errore di rete / Connessione rifiutata
                showMessage(loginMessage, false, 'Errore di connessione al server.');
                console.error('Login Error:', error);
            }
        });
    }
    /**
     * Gestione Registrazione Metodo Pagamento (UC-04)
     */
    if (paymentForm) {
        paymentForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // Evita il ricaricamento della pagina

            // Raccolta dei dati dal form
            const formData = new FormData(paymentForm);
            const data = Object.fromEntries(formData.entries());

            try {
                // Chiamata fetch() nativa in POST
                const response = await fetch('http://localhost:8080/api/pagamenti/registrazione', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    // Risposta 2xx
                    showMessage(paymentMessage, true, 'Metodo di pagamento salvato con successo!');
                    paymentForm.reset(); // Svuota i campi
                } else {
                    // Risposta di errore
                    let errorMessage = 'Operazione fallita.';
                    try {
                        const errorJson = await response.json();
                        if (errorJson.errore) errorMessage = errorJson.errore;
                    } catch (e) {
                        // fallback a text se non è json
                        const errorText = await response.text();
                        if (errorText) errorMessage = errorText;
                    }
                    showMessage(paymentMessage, false, `Errore: ${errorMessage}`);
                }
            } catch (error) {
                // Errore di rete / Connessione rifiutata
                showMessage(paymentMessage, false, 'Errore di connessione al server.');
                console.error('Payment Error:', error);
            }
        });
    }

    /**
     * Gestione Assistenza Clienti (UC-05)
     */
    const assistenzaForm = document.getElementById('assistenza-form');
    const assistenzaMessage = document.getElementById('assistenza-message');
    const formContainer = document.getElementById('formContainer');
    const chatContainer = document.getElementById('chatContainer');
    const chatBody = document.getElementById('chatBody');
    const btnChiudiChat = document.getElementById('btn-chiudi-chat');

    if (assistenzaForm) {
        assistenzaForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const descrizione = document.getElementById('descrizioneProblema').value.trim();
            if (!descrizione) {
                showMessage(assistenzaMessage, false, 'La descrizione non può essere vuota.');
                return;
            }

            const token = localStorage.getItem('token') || '';

            try {
                const response = await fetch('http://localhost:8080/api/assistenza', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify({ descrizioneProblema: descrizione })
                });

                if (response.ok) {
                    assistenzaMessage.style.display = 'none';
                    apriChat(descrizione);
                } else {
                    const errorMsg = await response.text();
                    showMessage(assistenzaMessage, false, `Errore: ${errorMsg || 'Richiesta di assistenza fallita.'}`);
                }
            } catch (error) {
                showMessage(assistenzaMessage, false, 'Errore di connessione al server.');
                console.error('Assistenza Error:', error);
            }
        });
    }

    function apriChat(messaggioUtente) {
        formContainer.style.display = "none";
        chatContainer.style.display = "block";
        chatBody.innerHTML = '';

        const msgUser = document.createElement('div');
        msgUser.style.cssText = 'background-color: var(--primary-color); color: white; padding: 12px 15px; border-radius: 15px; border-bottom-right-radius: 2px; max-width: 80%; align-self: flex-end; line-height: 1.4; word-wrap: break-word;';
        msgUser.innerText = "Tu: " + messaggioUtente;
        chatBody.appendChild(msgUser);

        chatBody.scrollTop = chatBody.scrollHeight;

        setTimeout(() => {
            const msgBot = document.createElement('div');
            msgBot.style.cssText = 'background-color: #e9ecef; color: #333; padding: 12px 15px; border-radius: 15px; border-bottom-left-radius: 2px; max-width: 80%; align-self: flex-start; line-height: 1.4; word-wrap: break-word;';
            msgBot.innerText = `Assistente: Grazie per la segnalazione. La tua richiesta è stata presa in carico. Riceverai assistenza entro breve.`;
            chatBody.appendChild(msgBot);
            chatBody.scrollTop = chatBody.scrollHeight;
        }, 800);
    }

    if (btnChiudiChat) {
        btnChiudiChat.addEventListener('click', () => {
            chatContainer.style.display = "none";
            formContainer.style.display = "block";
            if (assistenzaForm) assistenzaForm.reset();
        });
    }

    /**
     * Gestione Mappa Mezzi Vicini (UC-06)
     */
    const mapForm = document.getElementById('map-form');
    const mapMessage = document.getElementById('map-message');
    let mappaMezzi = null;
    let markersLayer = null;

    if (mapForm) {
        mapForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const raggioInput = document.getElementById('map-raggio');
            let raggio = raggioInput.value;
            const categoria = document.getElementById('map-categoria').value;
            const defaultLat = 41.1171;
            const defaultLon = 16.8719;

            // Validazione Raggio
            const raggioNum = parseInt(raggio, 10);
            if (isNaN(raggioNum) || raggioNum < 1) {
                alert("Inserisci un raggio valido.");
                return;
            }
            if (raggioNum > 1500) {
                alert("Il raggio massimo consentito è 1500 metri.");
                return;
            }

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        fetchMezziVicini(position.coords.latitude, position.coords.longitude, raggio, categoria);
                    },
                    (error) => {
                        console.warn("Geolocalizzazione fallita o negata. Fallback su Zootropolis.");
                        fetchMezziVicini(defaultLat, defaultLon, raggio, categoria);
                    },
                    { timeout: 5000, maximumAge: 0, enableHighAccuracy: true }
                );
            } else {
                console.warn("Geolocalizzazione non supportata dal browser. Fallback su Zootropolis.");
                fetchMezziVicini(defaultLat, defaultLon, raggio, categoria);
            }
        });
    }

    async function fetchMezziVicini(lat, lon, raggio, categoria) {
        const token = localStorage.getItem('token') || 'mock-token-123';

        try {
            const url = new URL('http://localhost:8080/api/mezzi/vicini');
            url.searchParams.append('lat', lat);
            url.searchParams.append('lon', lon);
            url.searchParams.append('raggio', raggio);
            url.searchParams.append('categoria', categoria);

            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const mezzi = await response.json();

                renderMap(lat, lon, mezzi);

                if (mezzi.length === 0) {
                    mapMessage.textContent = 'Nessun mezzo disponibile nel raggio scelto.';
                    mapMessage.className = 'message error';
                    mapMessage.style.display = 'block';
                } else {
                    mapMessage.style.display = 'none';
                }
            } else {
                let errorMessage = 'Errore nel recupero dei mezzi.';
                try {
                    const errorJson = await response.json();
                    if (errorJson.errore) errorMessage = errorJson.errore;
                } catch (e) {
                    const errorText = await response.text();
                    if (errorText) errorMessage = errorText;
                }
                showMessage(mapMessage, false, errorMessage);
            }
        } catch (error) {
            showMessage(mapMessage, false, 'Errore di connessione al server.');
            console.error('Fetch Mappa Error:', error);
        }
    }

    function renderMap(userLat, userLon, mezzi) {
        // Pulisce l'istanza precedente della mappa per evitare errori di re-inizializzazione
        if (mappaMezzi !== null) {
            mappaMezzi.remove();
        }

        mappaMezzi = L.map('map').setView([userLat, userLon], 14);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(mappaMezzi);
        markersLayer = L.layerGroup().addTo(mappaMezzi);

        // Marker utente
        L.marker([userLat, userLon], {
            icon: L.icon({
                iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34],
                shadowSize: [41, 41]
            })
        }).addTo(markersLayer).bindPopup("<b>La tua posizione</b>").openPopup();

        // Marker mezzi
        mezzi.forEach(m => {
            const portata = m.portataMassima !== undefined ? m.portataMassima : 'N/D';
            const batteria = m.livelloBatteria !== undefined ? m.livelloBatteria : 'N/D';
            const distanza = m.distanzaStimata !== undefined ? m.distanzaStimata.toFixed(2) : 'N/D';

            const popupContent = `
                <b>ID Mezzo:</b> ${m.idMezzo}<br>
                <b>Tipologia:</b> ${m.tipologia}<br>
                <b>Portata Massima:</b> ${portata} kg<br>
                <b>Livello Batteria:</b> ${batteria}%<br>
                <b>Distanza Stimata:</b> ${distanza} km<br>
                <button id="btn-prenota-mock" disabled style="margin-top: 10px; cursor: not-allowed;">Prenota (Prossimamente)</button>
            `;
            L.marker([m.latitudine, m.longitudine]).addTo(markersLayer)
                .bindPopup(popupContent);
        });
    }

    async function fetchFlottaOperatore() {
        const token = localStorage.getItem('token') || '';
        try {
            const response = await fetch('http://localhost:8080/api/admin/flotta', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.ok) {
                const mezzi = await response.json();
                renderOperatorMap(41.1171, 16.8719, mezzi); // Zootropolis coords
            } else {
                console.error("Errore fetch flotta operatore");
            }
        } catch (error) {
            console.error("Errore di connessione operatore", error);
        }
    }

    let mappaOperatore = null;
    let markersLayerOperatore = null;

    function renderOperatorMap(lat, lon, mezzi) {
        if (mappaOperatore !== null) {
            mappaOperatore.remove();
        }
        mappaOperatore = L.map('map-operator').setView([lat, lon], 13);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; OpenStreetMap contributors'
        }).addTo(mappaOperatore);
        markersLayerOperatore = L.layerGroup().addTo(mappaOperatore);

        mezzi.forEach(m => {
            let color = 'green';
            if (m.statoOperativo === 'guasto') color = 'red';
            else if (m.statoOperativo === 'in uso') color = 'gold';

            const icon = L.icon({
                iconUrl: `https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-${color}.png`,
                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34],
                shadowSize: [41, 41]
            });

            L.marker([m.latitudine, m.longitudine], { icon }).addTo(markersLayerOperatore)
                .bindPopup(`<b>ID Mezzo:</b> ${m.idMezzo}<br><b>Tipologia:</b> ${m.tipologia}<br><b>Stato:</b> ${m.statoOperativo || 'N/D'}`);
        });
    }
    /**
     * Gestione Stima Costo Noleggio (UC-09)
     */
    const stimaForm = document.getElementById('stima-form');
    const stimaMessage = document.getElementById('stima-message');

    if (stimaForm) {
        stimaForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const formData = new FormData(stimaForm);
            const data = Object.fromEntries(formData.entries());
            
            // Parsifica correttamente i valori
            data.idMezzo = parseInt(data.idMezzo, 10);
            data.durataMinuti = parseInt(data.durataMinuti, 10);
            data.distanzaKm = data.distanzaKm ? parseFloat(data.distanzaKm) : 0;

            const token = localStorage.getItem('token') || '';

            try {
                const response = await fetch('http://localhost:8080/api/noleggio/stima', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    const result = await response.json();
                    stimaMessage.innerHTML = `<strong>Importo stimato:</strong> ${result.importo} €<br><small>${result.descrizione}</small>`;
                    stimaMessage.className = 'message success';
                    stimaMessage.style.display = 'block';
                    
                    // Rimuovi il messaggio dopo 8 secondi
                    setTimeout(() => {
                        stimaMessage.style.display = 'none';
                    }, 8000);
                } else {
                    let errorMessage = 'Calcolo della stima fallito.';
                    try {
                        const errorJson = await response.json();
                        if (errorJson.errore) errorMessage = errorJson.errore;
                    } catch (ex) {
                        const errorText = await response.text();
                        if (errorText) errorMessage = errorText;
                    }
                    showMessage(stimaMessage, false, `Errore: ${errorMessage}`);
                }
            } catch (error) {
                showMessage(stimaMessage, false, 'Errore di connessione al server.');
                console.error('Stima Error:', error);
            }
        });
    }
});


