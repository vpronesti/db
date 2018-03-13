package control;

import bean.BeanStrumento;
import boundary.InterfacciaInserimentoBandaStrumento;
import dao.StrumentoDao;
import java.sql.Connection;
import util.DBAccess;

public class GestoreInserimentoBandaStrumento {
    private InterfacciaInserimentoBandaStrumento amministratore;
    
    public GestoreInserimentoBandaStrumento(InterfacciaInserimentoBandaStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res;
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        if (strumentoDao.queryEsistenzaStrumento(conn, beanStrumento.getNome())) {
            if (!strumentoDao.queryEsistenzaBanda(conn, beanStrumento.getBanda())) {
                strumentoDao.inserisciBanda(conn, beanStrumento.getBanda());
            }
            if (!strumentoDao.queryEsistenzaStrumentoBanda(conn, beanStrumento)) {
                strumentoDao.inserisciStrumentoBanda(conn, beanStrumento.getNome(), beanStrumento.getBanda());
                res = true;
            } else {
                res = false;
            }
        } else {
            res = false;
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
