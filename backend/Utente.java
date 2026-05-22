package backend;

/**
 * Modello dati che rispecchia la tabella Utente nel database.
 */
public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String numeroTelefono;
    private String password;
    private boolean statoAutenticato;

    public Utente() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatoAutenticato() {
        return statoAutenticato;
    }

    public void setStatoAutenticato(boolean statoAutenticato) {
        this.statoAutenticato = statoAutenticato;
    }
}
