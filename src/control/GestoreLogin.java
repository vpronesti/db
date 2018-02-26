package control;

import dao.UtenteDao;
import entity.Utente;

public class GestoreLogin {

    private static GestoreLogin istance;

    public static synchronized GestoreLogin getInstance() {
        if (istance == null) {
            istance = new GestoreLogin();
        }
        return istance;
    }

    public String login(String userId, String password) {
        UtenteDao utenteDao = UtenteDao.getInstance();
        Utente utente = utenteDao.findUtente(userId, password);
        if (utente == null) {
            return null;
        } else {
            return utente.getTipo();
        }

    }

}




