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

            // Il bottone porta i dati del mezzo via data-attributes; la classe .btn-prenota-mezzo
            // è usata dalla event delegation sul container #map per aprire il modal.
            const popupContent = `
                <b>ID Mezzo:</b> ${m.idMezzo}<br>
                <b>Tipologia:</b> ${m.tipologia}<br>
                <b>Portata Massima:</b> ${portata} kg<br>
                <b>Livello Batteria:</b> ${batteria}%<br>
                <b>Distanza Stimata:</b> ${distanza} km<br>
                <button
                    id="btn-prenota-${m.idMezzo}"
                    class="btn-prenota-mezzo"
                    data-id="${m.idMezzo}"
                    data-tipo="${m.tipologia}"
                    data-lat="${m.latitudine}"
                    data-lon="${m.longitudine}"
                    style="margin-top:10px;background:#2563eb;color:white;border:none;border-radius:6px;padding:0.5rem 1rem;cursor:pointer;font-size:0.9rem;font-weight:600;width:100%;"
                >🛔 Prenota</button>
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
    // UC-09 (Stima Costo Noleggio) è integrata nel flusso di prenotazione:
    // apriModalPrenotazione() → modal-stima-form → POST /api/noleggio/stima
    // Non esiste più un percorso diretto per la stima standalone.


    // ================================================================
    // HAVERSINE – calcolo distanza tra due coordinate geografiche
    // ================================================================
    /**
     * Distanza in km tra (lat1,lon1) e (lat2,lon2) con formula di Haversine.
     * Moltiplica per FATTORE_TORTUOSITA (1.30) per approssimare la distanza
     * stradale urbana rispetto alla linea d'aria.
     */
    function distanzaHaversineKm(lat1, lon1, lat2, lon2) {
        const R      = 6371;
        const dLat   = (lat2 - lat1) * Math.PI / 180;
        const dLon   = (lon2 - lon1) * Math.PI / 180;
        const a      = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                     + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180)
                     * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c      = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        const linea  = R * c;
        return Math.round(linea * 1.30 * 100) / 100; // fattore tortuosità 1.30
    }

    // ================================================================
    // MODAL PRENOTAZIONE + STIMA (UC-09 integrato nel flusso mappa)
    // ================================================================

    const velocitaMediaKmh = {
        'bici elettrica': 12,
        'bici': 12,
        'monopattino': 15,
        'scooter elettrico': 25
    };

    const modalOverlay     = document.getElementById('modal-prenotazione');
    const modalCloseBtn    = document.getElementById('modal-close-btn');
    const modalMezzoLabel  = document.getElementById('modal-mezzo-label');
    const modalStepForm    = document.getElementById('modal-step-form');
    const modalStepResult  = document.getElementById('modal-step-result');
    const modalStepConfirm = document.getElementById('modal-step-confirm');
    const modalStimaForm   = document.getElementById('modal-stima-form');
    const modalFormError   = document.getElementById('modal-form-error');
    const modalLoader      = document.getElementById('modal-loader');
    const modalImporto     = document.getElementById('modal-importo');
    const modalDescrizione = document.getElementById('modal-descrizione');
    const btnModificaStima = document.getElementById('btn-modifica-stima');
    const btnConferma      = document.getElementById('btn-conferma-prenotazione');
    const modalConfermaSub = document.getElementById('modal-conferma-sub');
    const btnChiudiConfirm = document.getElementById('btn-chiudi-modal-confirm');

    // Stato corrente del modal
    let modalIdMezzo    = null;
    let modalTipologia  = null;
    /** Coordinate lat/lon del mezzo selezionato (sorgente della distanza). */
    let mezzoLat = null;
    let mezzoLon = null;
    /** Coordinate lat/lon della destinazione scelta dall'utente. */
    let destLat  = null;
    let destLon  = null;
    /** Distanza calcolata in km (null = nessuna destinazione selezionata). */
    let distanzaCalcolata = null;
    /** Durata calcolata in minuti (null = nessuna destinazione selezionata). */
    let durataCalcolata = null;
    /** Istanza della mini-mappa Leaflet all'interno del modal. */
    let miniMappa = null;
    /** Marker della destinazione sulla mini-mappa. */
    let destMarker = null;

    // Riferimenti DOM specifici della destinazione
    const destInput           = document.getElementById('modal-dest-input');
    const btnCercaDest        = document.getElementById('btn-cerca-dest');
    const distanzaBadge       = document.getElementById('modal-distanza-badge');
    const distanzaValore      = document.getElementById('modal-distanza-valore');
    const modalDurataValore   = document.getElementById('modal-durata-valore');
    const modalVelocitaValore = document.getElementById('modal-velocita-valore');

    /**
     * Aggiorna il badge della distanza calcolata e stima il tempo di percorrenza.
     * @param {number|null} km  null = nessuna destinazione selezionata
     */
    function aggiornaDistanzaBadge(km) {
        distanzaCalcolata = km;
        if (km === null || !distanzaBadge || !distanzaValore || !modalDurataValore) {
            durataCalcolata = null;
            return;
        }

        // Calcolo velocità
        const tipoKey = modalTipologia ? modalTipologia.toLowerCase().trim() : '';
        const vMedia = velocitaMediaKmh[tipoKey] || 15; // default 15 km/h se non mappato

        // Calcolo durata stimata: (km / km/h) * 60 minuti, arrotondata all'eccesso. Minimo 1 minuto.
        durataCalcolata = Math.max(1, Math.ceil((km / vMedia) * 60));

        distanzaBadge.style.display = 'flex';
        distanzaValore.textContent  = `${km.toFixed(2)} km`;
        modalDurataValore.textContent = `${durataCalcolata} min`;
        if (modalVelocitaValore) modalVelocitaValore.textContent = `${vMedia} km/h`;

        // Se l'utente seleziona una destinazione valida, togliamo l'avviso di errore (se presente)
        if (typeof nascondErroreModal === 'function' && modalFormError) {
            nascondErroreModal(modalFormError);
        }
    }

    /**
     * Imposta il marker destinazione sulla mini-mappa e aggiorna la distanza.
     * @param {number} lat  @param {number} lon
     */
    function impostaDestinazione(lat, lon) {
        destLat = lat;
        destLon = lon;
        if (destMarker) {
            destMarker.setLatLng([lat, lon]);
        } else if (miniMappa) {
            destMarker = L.marker([lat, lon], {
                icon: L.icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [1, -34], shadowSize: [41, 41]
                }),
                draggable: true
            }).addTo(miniMappa).bindPopup('Destinazione');

            // Drag del marker: ricalcola distanza
            destMarker.on('dragend', () => {
                const pos = destMarker.getLatLng();
                impostaDestinazione(pos.lat, pos.lng);
            });
        }
        if (miniMappa) miniMappa.setView([lat, lon], 14);
        if (mezzoLat !== null && mezzoLon !== null) {
            aggiornaDistanzaBadge(distanzaHaversineKm(mezzoLat, mezzoLon, lat, lon));
        }
    }

    /**
     * Apre il modal di prenotazione per il mezzo selezionato.
     * Riporta sempre allo Step 1 (form) e resetta i campi.
     * Inizializza (o reinizializza) la mini-mappa della destinazione.
     */
    function apriModalPrenotazione(idMezzo, tipologia, latMezzo, lonMezzo) {
        modalIdMezzo   = idMezzo;
        modalTipologia = tipologia;
        mezzoLat = latMezzo != null && !isNaN(latMezzo) ? latMezzo : null;
        mezzoLon = lonMezzo != null && !isNaN(lonMezzo) ? lonMezzo : null;
        // Reset destinazione
        destLat = null; destLon = null; distanzaCalcolata = null; durataCalcolata = null;
        destMarker = null;
        if (distanzaBadge) distanzaBadge.style.display = 'none';
        if (distanzaValore) distanzaValore.textContent = '—';
        if (modalDurataValore) modalDurataValore.textContent = '—';
        if (modalVelocitaValore) modalVelocitaValore.textContent = '—';
        if (destInput) destInput.value = '';

        modalMezzoLabel.textContent = `ID: ${idMezzo} | Tipo: ${tipologia}`;
        modalStimaForm.reset();
        nascondErroreModal(modalFormError);
        goToModalStep('form');
        modalOverlay.classList.add('active');
        document.body.style.overflow = 'hidden';

        // Inizializza la mini-mappa dopo che il modal è visibile
        requestAnimationFrame(() => {
            const container = document.getElementById('modal-dest-map');
            if (!container) return;

            // Distruggi eventuale istanza precedente
            if (miniMappa) {
                miniMappa.off(); miniMappa.remove(); miniMappa = null;
            }

            // Centro: posizione del mezzo (se disponibile) oppure Bari
            const centerLat = mezzoLat !== null ? mezzoLat : 41.1171;
            const centerLon = mezzoLon !== null ? mezzoLon : 16.8719;

            miniMappa = L.map('modal-dest-map', { zoomControl: true }).setView([centerLat, centerLon], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; OpenStreetMap'
            }).addTo(miniMappa);

            // Marker del mezzo (blu, non spostabile)
            if (mezzoLat !== null) {
                L.marker([mezzoLat, mezzoLon], {
                    icon: L.icon({
                        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png',
                        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                        iconSize: [25, 41], iconAnchor: [12, 41], popupAnchor: [1, -34], shadowSize: [41, 41]
                    })
                }).addTo(miniMappa).bindPopup(`🛔 Mezzo ${idMezzo}`);
            }

            // Click sulla mappa → imposta destinazione
            miniMappa.on('click', (e) => {
                impostaDestinazione(e.latlng.lat, e.latlng.lng);
                if (destInput) destInput.value = `${e.latlng.lat.toFixed(5)}, ${e.latlng.lng.toFixed(5)}`;
            });

            // Forza il ridisegno (il modal potrebbe non essere ancora al layout definitivo)
            setTimeout(() => miniMappa.invalidateSize(), 100);
        });

        setTimeout(() => { if (destInput) destInput.focus(); }, 50);
    }

    /** Chiude il modal e ripristina lo scroll della pagina. */
    function chiudiModal() {
        modalOverlay.classList.remove('active');
        document.body.style.overflow = '';
        // Non distruggiamo la mini-mappa qui: viene ricreata alla prossima apertura
    }

    /**
     * Naviga tra i 3 step del modal.
     * @param {'form'|'result'|'confirm'} step
     */
    function goToModalStep(step) {
        modalStepForm.style.display    = step === 'form'    ? 'block' : 'none';
        modalStepResult.style.display  = step === 'result'  ? 'block' : 'none';
        modalStepConfirm.style.display = step === 'confirm' ? 'block' : 'none';
    }

    function mostraErroreModal(el, testo) {
        el.textContent = testo;
        el.style.display = 'block';
    }

    function nascondErroreModal(el) {
        el.textContent = '';
        el.style.display = 'none';
    }

    // --- Chiusura modal: bottone X ---
    if (modalCloseBtn) {
        modalCloseBtn.addEventListener('click', chiudiModal);
    }

    // --- Chiusura modal: click sull'overlay (fuori dalla box) ---
    if (modalOverlay) {
        modalOverlay.addEventListener('click', (e) => {
            if (e.target === modalOverlay) chiudiModal();
        });
    }

    // --- Chiusura modal: tasto Escape ---
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && modalOverlay && modalOverlay.classList.contains('active')) {
            chiudiModal();
        }
    });

    /**
     * Event delegation sul container #map.
     * Ora legge anche data-lat e data-lon per passare le coordinate al modal.
     */
    const mapContainer = document.getElementById('map');
    if (mapContainer) {
        mapContainer.addEventListener('click', (e) => {
            const btn = e.target.closest('.btn-prenota-mezzo');
            if (!btn) return;
            const idMezzo   = parseInt(btn.dataset.id, 10);
            const tipologia = btn.dataset.tipo || 'Mezzo';
            const latMezzo  = parseFloat(btn.dataset.lat);
            const lonMezzo  = parseFloat(btn.dataset.lon);
            apriModalPrenotazione(idMezzo, tipologia, latMezzo, lonMezzo);
        });
    }

    // --- Geocodifica Nominatim (frontend-only) per il campo testo destinazione ---
    if (btnCercaDest) {
        btnCercaDest.addEventListener('click', async () => {
            const query = destInput ? destInput.value.trim() : '';
            if (!query) {
                mostraErroreModal(modalFormError, 'Scrivi un indirizzo da cercare.');
                return;
            }
            btnCercaDest.disabled = true;
            btnCercaDest.textContent = '…';
            nascondErroreModal(modalFormError);
            try {
                const encoded = encodeURIComponent(query);
                const res = await fetch(
                    `https://nominatim.openstreetmap.org/search?q=${encoded}&format=json&limit=1`,
                    { headers: { 'User-Agent': 'SmartMobilityC2GM/1.0' } }
                );
                const data = await res.json();
                if (!data || data.length === 0) {
                    mostraErroreModal(modalFormError, `Indirizzo non trovato: "${query}". Prova a essere più preciso.`);
                    return;
                }
                const { lat, lon, display_name } = data[0];
                destInput.value = display_name;
                impostaDestinazione(parseFloat(lat), parseFloat(lon));
            } catch (err) {
                mostraErroreModal(modalFormError, 'Errore nella ricerca indirizzo. Usa la mappa direttamente.');
                console.error('Nominatim error:', err);
            } finally {
                btnCercaDest.disabled = false;
                btnCercaDest.textContent = '🔍 Cerca';
            }
        });

        // Enter nel campo testo → cerca
        if (destInput) {
            destInput.addEventListener('keydown', (e) => {
                if (e.key === 'Enter') { e.preventDefault(); btnCercaDest.click(); }
            });
        }
    }

    // --- STEP 1 → STEP 2: submit del form parametri → chiama API stima ---
    if (modalStimaForm) {
        modalStimaForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            nascondErroreModal(modalFormError);

            // Entrambi calcolati automaticamente in base alla destinazione
            const distanza = distanzaCalcolata !== null ? distanzaCalcolata : 0;
            const durata   = durataCalcolata !== null ? durataCalcolata : 0;

            // Validazione: destinazione obbligatoria (può essere a distanza 0 se si seleziona lo stesso posto, ma deve essere stata selezionata)
            if (distanzaCalcolata === null || durataCalcolata === null) {
                mostraErroreModal(modalFormError,
                    'Seleziona una destinazione sulla mappa (o cercala testualmente) per calcolare distanza e tempo stimato.');
                return;
            }

            const token = localStorage.getItem('token') || '';

            // Mostra loader e disabilita submit
            modalLoader.classList.add('active');
            const btnCalcola = document.getElementById('btn-calcola-modal');
            btnCalcola.disabled = true;

            try {
                const payload = {
                    idMezzo:      modalIdMezzo,
                    durataMinuti: durata,
                    distanzaKm:   distanza
                };

                const response = await fetch('http://localhost:8080/api/noleggio/stima', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(payload)
                });

                if (response.status === 401) {
                    mostraErroreModal(modalFormError,
                        '⚠️ Devi essere loggato per procedere. Effettua il login e riprova.');
                    return;
                }

                if (response.ok) {
                    const result = await response.json();
                    modalImporto.textContent     = `€ ${parseFloat(result.importo).toFixed(2)}`;
                    modalDescrizione.textContent = result.descrizione || '';
                    goToModalStep('result');
                } else {
                    let errMsg = 'Calcolo della stima fallito.';
                    try {
                        const errJson = await response.json();
                        if (errJson.errore) errMsg = errJson.errore;
                    } catch (_) {
                        const errText = await response.text();
                        if (errText) errMsg = errText;
                    }
                    mostraErroreModal(modalFormError, `Errore: ${errMsg}`);
                }
            } catch (err) {
                mostraErroreModal(modalFormError,
                    'Errore di connessione al server. Verifica che il backend sia avviato.');
                console.error('Modal stima error:', err);
            } finally {
                modalLoader.classList.remove('active');
                btnCalcola.disabled = false;
            }
        });
    }

    // --- STEP 2 → STEP 1: bottone "← Modifica" ---
    if (btnModificaStima) {
        btnModificaStima.addEventListener('click', () => {
            // Nasconde anche l'avviso pagamento quando si torna al form
            const nopayWarning = document.getElementById('modal-nopay-warning');
            if (nopayWarning) nopayWarning.style.display = 'none';
            goToModalStep('form');
        });
    }

    // --- STEP 2 → STEP 3: bottone "✓ Conferma prenotazione" ---
    // Prima di procedere verifica che l'utente abbia almeno un metodo di pagamento.
    if (btnConferma) {
        btnConferma.addEventListener('click', async () => {
            const nopayWarning = document.getElementById('modal-nopay-warning');
            const token = localStorage.getItem('token') || '';

            // Disabilita il bottone durante la verifica per evitare doppi click
            btnConferma.disabled = true;
            btnConferma.textContent = 'Verifica in corso…';

            try {
                const res = await fetch('http://localhost:8080/api/pagamenti/verifica', {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}` }
                });

                if (res.status === 401) {
                    // Sessione scaduta
                    if (nopayWarning) {
                        nopayWarning.innerHTML =
                            '⚠️ <strong>Sessione scaduta.</strong> Effettua nuovamente il login per prenotare.';
                        nopayWarning.style.display = 'block';
                    }
                    return;
                }

                if (res.ok) {
                    const data = await res.json();

                    if (!data.haMetodo) {
                        // Nessun metodo di pagamento: mostra avviso, blocca conferma
                        if (nopayWarning) nopayWarning.style.display = 'block';
                        return;
                    }

                    // Metodo di pagamento presente → procedi con la conferma
                    if (nopayWarning) nopayWarning.style.display = 'none';
                    // TODO: qui verrà integrata la chiamata POST /api/noleggio/avvia
                    // quando l'endpoint di avvio noleggio sarà implementato nel backend.
                    modalConfermaSub.textContent =
                        `Il mezzo ${modalTipologia} (ID: ${modalIdMezzo}) è stato prenotato con successo. ` +
                        `Riceverai una notifica di conferma a breve.`;
                    goToModalStep('confirm');

                } else {
                    // Errore generico dall'API di verifica
                    if (nopayWarning) {
                        nopayWarning.innerHTML =
                            '⚠️ Impossibile verificare il metodo di pagamento. Riprova tra poco.';
                        nopayWarning.style.display = 'block';
                    }
                }
            } catch (err) {
                console.error('Errore verifica pagamento:', err);
                if (nopayWarning) {
                    nopayWarning.innerHTML =
                        '⚠️ Errore di connessione al server. Verifica la tua rete e riprova.';
                    nopayWarning.style.display = 'block';
                }
            } finally {
                btnConferma.disabled = false;
                btnConferma.textContent = '✓ Conferma prenotazione';
            }
        });
    }

    // --- STEP 3: bottone "Chiudi" ---
    if (btnChiudiConfirm) {
        btnChiudiConfirm.addEventListener('click', chiudiModal);
    }

});


