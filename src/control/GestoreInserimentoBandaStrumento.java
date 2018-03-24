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
    
    /**
     * inserisce nel DB la rappresentazione per la coppia strumento-banda
     * 
     * verifica l'esistenza dello strumento, poi se la banda non e' gia' 
     * presente nel BD se ne inserisce il valore nella relativa tabella 
     * 
     * si inserisce la coppia strumento-banda nella tabella del DB che 
     * associa le due entita'
     * se la corrispondenza esiste gia' l'inserimento viene rifiutato
     * @param beanStrumento
     * @return 
     */
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res;
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
//        if (strumentoDao.queryEsistenzaStrumento(conn, beanStrumento.getNome())) {
//            if (!strumentoDao.queryEsistenzaBanda(conn, beanStrumento.getBanda())) {
                strumentoDao.inserisciBanda(conn, beanStrumento.getBanda());
//            }
//            if (!strumentoDao.queryEsistenzaStrumentoBanda(conn, beanStrumento)) {
                res = strumentoDao.inserisciStrumentoBanda(conn, beanStrumento.getNome(), beanStrumento.getBanda());
//                res = true;
//            } else {
//                res = false;
//            }
//        } else {
//            res = false;
//        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
