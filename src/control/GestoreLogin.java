package control;

import dao.UtenteDao;
import entity.Utente;
import java.sql.Connection;
import util.DBAccess;

public class GestoreLogin {
    private static GestoreLogin instance;

    public static synchronized GestoreLogin getInstance() {
        if (instance == null) {
            instance = new GestoreLogin();
        }
        return instance;
    }

    public String login(String userId, String password) {
        Connection conn = DBAccess.getInstance().getConnection();
        UtenteDao utenteDao = UtenteDao.getInstance();
        Utente utente = utenteDao.queryUtente(conn, userId, password);
        DBAccess.getInstance().closeConnection(conn);
        if (utente == null) {
            return null;
        } else {
            return utente.getTipo();
        }

    }

}




