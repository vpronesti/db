package control;

import bean.BeanSatellite;
import bean.BeanStrumentoSatellite;
import boundary.InterfacciaInserimentoNomeStrumento;
import dao.SatelliteDao;
import dao.StrumentoDao;
import java.sql.Connection;
import util.DBAccess;

public class GestoreInserimentoNomeStrumento {
        private InterfacciaInserimentoNomeStrumento amministratore;
    
    public GestoreInserimentoNomeStrumento(InterfacciaInserimentoNomeStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean inserisciNomeStrumento(BeanStrumentoSatellite beanStrumento) {
        boolean res;
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        if (satelliteDao.queryEsistenzaSatellite(conn, new BeanSatellite(beanStrumento.getSatellite()))){
            if (!strumentoDao.queryEsistenzaStrumento(conn, beanStrumento.getNome())) {
                strumentoDao.inserisciNomeStrumento(conn, beanStrumento.getNome());
            }
            if (!strumentoDao.queryEsistenzaSatelliteStrumento(conn, beanStrumento.getSatellite(), beanStrumento.getNome())) {
                strumentoDao.inserisciSatelliteStrumento(conn, beanStrumento);
                res = true;
            } else 
                res = false;
        } else {
            res = false;
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
