package control;

import bean.BeanSatellite;
import boundary.InterfacciaInserimentoSatellite;
import dao.SatelliteDao;
import entity.Satellite;
import java.sql.Connection;
import util.DBAccess;

public class GestoreInserimentoSatellite {
    private InterfacciaInserimentoSatellite amministratore;
    
    public GestoreInserimentoSatellite(InterfacciaInserimentoSatellite amministratore) {
        this.amministratore = amministratore;
    }

    public boolean inserisciSatellite(BeanSatellite beanSatellite) {
        boolean res = false;
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        if (!satelliteDao.queryEsistenzaSatellite(conn, beanSatellite)) {
            res = satelliteDao.inserisciSatellite(conn, beanSatellite);
        }
        return res;
    }    
}
