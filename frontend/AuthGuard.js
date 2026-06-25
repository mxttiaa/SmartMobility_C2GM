// AuthGuard.js - Autenticazione basata su token server-side
(function() {
    const token = localStorage.getItem('authToken');
    const userRole = localStorage.getItem('userRole');
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';

    // Pagine pubbliche che non richiedono autenticazione
    const publicPages = ['login.html', 'registrazione.html', 'accesso_negato.html'];
    
    // Se l'utente non è loggato e non è su una pagina pubblica, rimanda alla login
    if (!token && !publicPages.includes(currentPage)) {
        window.location.href = 'login.html';
        return;
    }

    // Rotte protette con ruoli specifici
    const protectedRoutes = {
        'dashboard.html': ['OPERATORE', 'AMMINISTRATORE'],
        'admin.html': ['AMMINISTRATORE'],
        'gestione_ticket.html': ['OPERATORE', 'AMMINISTRATORE']
    };

    // Controllo permessi sulla pagina corrente
    if (protectedRoutes[currentPage] && userRole) {
        const allowedRoles = protectedRoutes[currentPage];
        if (!allowedRoles.includes(userRole)) {
            window.location.href = 'accesso_negato.html';
            return;
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        // Gestione visibilità link NavBar in base al ruolo
        const navLinks = document.querySelectorAll('.navbar a');
        navLinks.forEach(link => {
            const requiredRoles = link.getAttribute('data-role');
            if (requiredRoles) {
                const roles = requiredRoles.split(',');
                if (!roles.includes(userRole)) {
                    link.style.display = 'none';
                }
            }
        });
        
        // Aggiunta tasto Logout se loggato e non in pagina pubblica
        if (!publicPages.includes(currentPage) && token) {
            const nav = document.querySelector('.navbar');
            if (nav) {
                const userName = localStorage.getItem('userName') || '';
                const logoutBtn = document.createElement('a');
                logoutBtn.href = "#";
                logoutBtn.innerText = userName ? (userName + ' | Logout') : 'Logout';
                logoutBtn.style.color = "#ff4d6d";
                logoutBtn.style.marginLeft = "auto";
                logoutBtn.style.fontWeight = "600";
                logoutBtn.onclick = (e) => {
                    e.preventDefault();
                    // Chiamata logout al server
                    fetch('http://localhost:8080/api/auth/logout', {
                        method: 'POST',
                        headers: { 'Authorization': 'Bearer ' + token }
                    }).finally(() => {
                        localStorage.removeItem('authToken');
                        localStorage.removeItem('userRole');
                        localStorage.removeItem('userName');
                        localStorage.removeItem('userEmail');
                        window.location.href = 'login.html';
                    });
                };
                nav.appendChild(logoutBtn);
            }
        }
    });

    /**
     * Helper globale: restituisce gli headers di autenticazione per le fetch.
     * Utilizzabile da qualsiasi pagina: getAuthHeaders()
     */
    window.getAuthHeaders = function() {
        const t = localStorage.getItem('authToken');
        return t ? { 'Authorization': 'Bearer ' + t } : {};
    };

    /**
     * Helper globale: restituisce gli headers per richieste POST con JSON.
     */
    window.getAuthJsonHeaders = function() {
        const t = localStorage.getItem('authToken');
        const headers = { 'Content-Type': 'application/json' };
        if (t) headers['Authorization'] = 'Bearer ' + t;
        return headers;
    };
})();
