package control;

import bean.BeanSatellite;
import boundary.InterfacciaInserimentoSatellite;
import dao.SatelliteDao;
import entity.Satellite;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-3.3
 */
public class GestoreInserimentoSatellite {
    private InterfacciaInserimentoSatellite amministratore;
    
    public GestoreInserimentoSatellite(InterfacciaInserimentoSatellite amministratore) {
        this.amministratore = amministratore;
    }

    public boolean inserisciSatellite(BeanSatellite beanSatellite) {
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        boolean res = satelliteDao.inserisciSatellite(conn, beanSatellite);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }    
}
