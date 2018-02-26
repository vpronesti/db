package entity;

import exception.DimensioneCampoNonSufficienteException;

public class UtenteRegistrato extends Utente {


    public UtenteRegistrato(String nome, String cognome, String userId,
            String password, String email) {
        super(nome, cognome, userId, password, email, "Registrato");
//        this.nome = nome;
//        this.cognome = cognome;
//        this.userId = userId; // cambiare con setter
//        this.password = password; // cambiare con setter
//        this.email = email;
    }
    
//    public UtenteRegistrato(String nome, String cognome, String userId,
//            String password, String email, String tipo) throws 
//            DimensioneCampoNonSufficienteException {
//        this.nome = nome;
//        this.cognome = cognome;
////        this.setUserId(userId);
////        this.setPassword(password);
//        this.userId = userId; // cambiare con setter
//        this.password = password; // cambiare con setter
//        this.email = email;
//        if (!tipo.equals("Registrato") && !tipo.equals("Amministratore")) {
//            System.err.println("Utente amministratore non puo' essere istanziato," + 
//                    " il tipo passato non e' corretto");
//        }
//        this.tipo = tipo;
//    }

//    public void setUserId(String userId) throws DimensioneCampoNonSufficienteException {
//        if (userId.length() < MIN_LENGTH) {
//            throw new DimensioneCampoNonSufficienteException("L'id " + 
//                    "dell'utente deve essere di almeno 6 caratteri");
//        } else {
//            this.userId = userId;
//        }
//    }



//    public void setPassword(String password) throws DimensioneCampoNonSufficienteException {
//        if (password.length() < MIN_LENGTH) {
//            throw new DimensioneCampoNonSufficienteException("La password " + 
//                    "deve essere di almeno 6 caratteri");
//        } else {
//            this.password = password;
//        }
//    }
    

    
//    @Override
//    public String toString() {
//        return "nome: " + this.nome + "\ncognome: " + this.cognome;
//    }
}
