package control;

import bean.BeanUtente;
import boundary.InterfacciaRegistrazioneUtente;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

public class GestoreRegistrazioneUtente {
    private InterfacciaRegistrazioneUtente amministratore;
    
    public GestoreRegistrazioneUtente(InterfacciaRegistrazioneUtente amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean gestioneRegistrazioneUtente(BeanUtente beanUtente) {
        Connection conn = DBAccess.getInstance().getConnection();
        UtenteDao utenteDao = UtenteDao.getInstance();
        boolean res = false;
        if (!utenteDao.queryEsistenzaUtente(conn, beanUtente))
        res = utenteDao.inserisciUtente(conn, beanUtente);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
