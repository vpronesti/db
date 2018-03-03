package entity;

public class UtenteAmministratore extends Utente {
    public UtenteAmministratore(String nome, String cognome, 
            String userId, String password, String email) {
        super(nome, cognome, userId, password, email, "Amministratore");
    }
}
