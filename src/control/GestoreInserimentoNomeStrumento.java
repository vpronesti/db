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
    
    /**
     * inserisce nel DB la rappresentazione per la coppia strumento-satellite
     * 
     * verifica l'esistenza del satellite, poi se lo strumento non e' gia' 
     * stato definito nel DB si provvede ad inserirne il nome nella 
     * relativa tabella
     * 
     * successivamente si inserisce la coppia strumento-satellite nella 
     * tabella del DB che associa le due entita'
     * se la corrispondenza esiste gia' l'inserimento viene rifiutato
     * @param beanStrumento
     * @return 
     */
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
