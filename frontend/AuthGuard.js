// AuthGuard.js - Autenticazione basata su token server-side
(function() {
    // In-memory cache: localStorage is synchronous and slow if called repeatedly
    const token    = localStorage.getItem('authToken');
    const userRole = localStorage.getItem('userRole');
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';

    // Pagine pubbliche che non richiedono autenticazione
    const publicPages = ['login.html', 'registrazione.html', 'accesso_negato.html'];
    
    // Se l'utente non è loggato e non è su una pagina pubblica, rimanda alla login
    if (!token && !publicPages.includes(currentPage)) {
        window.location.replace('login.html'); // replace() avoids a history entry
        return;
    }

    // Rotte protette con ruoli specifici
    const protectedRoutes = {
        'dashboard.html': ['OPERATORE', 'AMMINISTRATORE'],
        'admin.html':     ['AMMINISTRATORE'],
        'gestione_ticket.html': ['OPERATORE', 'AMMINISTRATORE']
    };

    // Controllo permessi sulla pagina corrente
    if (protectedRoutes[currentPage] && userRole) {
        const allowedRoles = protectedRoutes[currentPage];
        if (!allowedRoles.includes(userRole)) {
            window.location.replace('accesso_negato.html');
            return;
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        // Gestione visibilità link NavBar in base al ruolo
        const navLinks = document.querySelectorAll('.navbar a[data-role]');
        navLinks.forEach(link => {
            const requiredRoles = link.getAttribute('data-role');
            if (requiredRoles && !requiredRoles.split(',').includes(userRole)) {
                link.style.display = 'none';
            }
        });
        
        // Aggiunta tasto Logout se loggato e non in pagina pubblica
        if (!publicPages.includes(currentPage) && token) {
            const nav = document.querySelector('.navbar');
            if (nav) {
                const userName = localStorage.getItem('userName') || '';
                const logoutBtn = document.createElement('a');
                logoutBtn.href = '#';
                logoutBtn.textContent = userName ? (userName + ' | Logout') : 'Logout';
                logoutBtn.style.cssText = 'color:#ff4d6d;margin-left:auto;font-weight:600;';
                logoutBtn.addEventListener('click', (e) => {
                    e.preventDefault();
                    // Chiamata logout al server (fire-and-forget)
                    fetch('http://localhost:8080/api/auth/logout', {
                        method: 'POST',
                        headers: { 'Authorization': 'Bearer ' + token },
                        keepalive: true  // ensures request completes even if page unloads
                    }).finally(() => {
                        localStorage.clear(); // clear all auth keys in one call
                        window.location.replace('login.html');
                    });
                });
                nav.appendChild(logoutBtn);
            }
        }
    });

    /**
     * Helper globale: restituisce gli headers di autenticazione per le fetch.
     * Usa il token già letto all'avvio — evita chiamate ripetute a localStorage.
     */
    window.getAuthHeaders = function() {
        return token ? { 'Authorization': 'Bearer ' + token } : {};
    };

    /**
     * Helper globale: restituisce gli headers per richieste POST con JSON.
     */
    window.getAuthJsonHeaders = function() {
        const headers = { 'Content-Type': 'application/json' };
        if (token) headers['Authorization'] = 'Bearer ' + token;
        return headers;
    };
})();
