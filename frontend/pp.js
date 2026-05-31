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

                    // Accesso consentito
                    showMessage(loginMessage, true, 'Accesso effettuato con successo!');
                    loginForm.reset();

                    // Transizione alla scheda successiva
                    setTimeout(() => {
                        document.getElementById('auth-view').classList.add('hidden');
                        document.getElementById('dashboard-view').classList.remove('hidden');
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
     * Gestione Mappa Mezzi Vicini (UC-06)
     */
    const btnCercaMappa = document.getElementById('btn-cerca-mappa');
    const mapMessage = document.getElementById('map-message');
    let mappaMezzi = null;
    let markersLayer = null;

    if (btnCercaMappa) {
        btnCercaMappa.addEventListener('click', () => {
            const raggio = document.getElementById('map-raggio').value;
            const categoria = document.getElementById('map-categoria').value;
            const defaultLat = 41.1171;
            const defaultLon = 16.8719;

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
});


