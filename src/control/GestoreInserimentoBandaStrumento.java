package control;

import bean.BeanStrumento;
import boundary.InterfacciaInserimentoBandaStrumento;
import dao.StrumentoDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-3.4
 */
public class GestoreInserimentoBandaStrumento {
    private InterfacciaInserimentoBandaStrumento amministratore;
    
    public GestoreInserimentoBandaStrumento(InterfacciaInserimentoBandaStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    /**
     * inserisce nel DB la rappresentazione per la coppia strumento-banda
     * 
     * @param beanStrumento
     * @return 
     */
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        boolean res;
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        
        res = strumentoDao.inserisciBanda(conn, beanStrumento.getBanda());
        // se la banda dello strumento esiste gia' nella tabella banda, si 
        // fa il rollback e poi si procede all'inserimento nella tabella 
        // strumento_banda
        if (!res)
            DBAccess.getInstance().rollback(conn);
        res = strumentoDao.inserisciStrumentoBanda(conn, beanStrumento.getNome(), beanStrumento.getBanda());
        if (res)
            DBAccess.getInstance().commit(conn);
        else 
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
