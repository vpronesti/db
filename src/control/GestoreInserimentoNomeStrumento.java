package control;

import bean.BeanSatellite;
import bean.BeanStrumentoSatellite;
import boundary.InterfacciaInserimentoNomeStrumento;
import dao.SatelliteDao;
import dao.StrumentoDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-FN-3.4
 */
public class GestoreInserimentoNomeStrumento {
        private InterfacciaInserimentoNomeStrumento amministratore;
    
    public GestoreInserimentoNomeStrumento(InterfacciaInserimentoNomeStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    /**
     * inserisce nel DB la rappresentazione per la coppia strumento-satellite
     * 
     * @param beanStrumento
     * @return 
     */
    public boolean inserisciNomeStrumento(BeanStrumentoSatellite beanStrumento) {
        boolean res;
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        StrumentoDao strumentoDao = StrumentoDao.getInstance();

        res = strumentoDao.inserisciNomeStrumento(conn, beanStrumento.getNome());
        // se il nome dello strumento esiste gia' nella tabella strumento, si 
        // fa il rollback e poi si procede all'inserimento nella tabella 
        // strumento_satellite
        if (!res)
            DBAccess.getInstance().rollback(conn);
        res = strumentoDao.inserisciSatelliteStrumento(conn, beanStrumento);
        if (res)
            DBAccess.getInstance().commit(conn);
        else
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
