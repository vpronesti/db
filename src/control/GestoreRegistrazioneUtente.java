package control;

import bean.BeanUtente;
import boundary.InterfacciaRegistrazioneUtente;
import dao.UtenteDao;
import java.sql.Connection;
import java.sql.SQLException;
import util.DBAccess;

/**
 * REQ-2
 * REQ-3.2
 */
public class GestoreRegistrazioneUtente {
    private InterfacciaRegistrazioneUtente amministratore;
    
    public GestoreRegistrazioneUtente(InterfacciaRegistrazioneUtente amministratore) {
        this.amministratore = amministratore;
    }
    
    /**
     * @param beanUtente
     * @return true se l'utente viene inserito, 
     * false se non e' possibile perche' userId e' gia' stato usato
     */
    public boolean gestioneRegistrazioneUtente(BeanUtente beanUtente) {
        Connection conn = DBAccess.getInstance().getConnection();
        UtenteDao utenteDao = UtenteDao.getInstance();
        boolean res = utenteDao.inserisciUtente(conn, beanUtente);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
