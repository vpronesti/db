package control;

import bean.BeanUtente;
import boundary.InterfacciaRegistrazioneUtente;
import dao.UtenteDao;
import java.sql.Connection;
import java.sql.SQLException;
import util.DBAccess;

public class GestoreRegistrazioneUtente {
    private InterfacciaRegistrazioneUtente amministratore;
    
    public GestoreRegistrazioneUtente(InterfacciaRegistrazioneUtente amministratore) {
        this.amministratore = amministratore;
    }
    
    /**
     * se l'utente non e' gia' definito lo inserisce nel DB 
     * per evitare modifiche tra il controllo dell'esistenza 
     * dell'utente e l'inserimento, l'autocommit e' impostato a false 
     * quindi viene fatto manualmente dopo l'eventuale inserimento
     * @param beanUtente
     * @return 
     */
    public boolean gestioneRegistrazioneUtente(BeanUtente beanUtente) {
        Connection conn = DBAccess.getInstance().getConnection();
        UtenteDao utenteDao = UtenteDao.getInstance();
//        try {
//            conn.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        boolean res = false;
//        if (!utenteDao.queryEsistenzaUtente(conn, beanUtente))
            res = utenteDao.inserisciUtente(conn, beanUtente);
//        try {
//            conn.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
