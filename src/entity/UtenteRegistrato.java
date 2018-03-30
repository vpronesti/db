package entity;

public class UtenteRegistrato extends Utente {
    public UtenteRegistrato(String nome, String cognome, String userId,
            String password, String email) {
        super(nome, cognome, userId, password, email, "Registrato");
    }
}
