document.addEventListener('DOMContentLoaded', () => {
    // Riferimenti ai form
    const registerForm = document.getElementById('register-form');
    const loginForm = document.getElementById('login-form');
    
    // Riferimenti ai div dei messaggi
    const registerMessage = document.getElementById('register-message');
    const loginMessage = document.getElementById('login-message');

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
                    // Accesso consentito
                    showMessage(loginMessage, true, 'Accesso effettuato con successo!');
                    loginForm.reset();
                    // Qui si potrebbe, per esempio, salvare un token nel localStorage o ridirigere
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
});
