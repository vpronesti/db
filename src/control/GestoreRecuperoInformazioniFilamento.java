package control;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import boundary.InterfacciaRecuperoInformazioniDerivateFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SegmentoDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-5
 */
public class GestoreRecuperoInformazioniFilamento {
    private InterfacciaRecuperoInformazioniDerivateFilamento utente;
    
    public GestoreRecuperoInformazioniFilamento(InterfacciaRecuperoInformazioniDerivateFilamento utente) {
        this.utente = utente;
    }
    
    public boolean recuperaInfoFilamento(BeanInformazioniFilamento beanFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        boolean res = true;
        if (!beanFil.isRicercaId()) {
            // in caso si voglia effettuare la ricerca per nome, 
            // questo viene convertito in id
            res = filamentoDao.queryIdFilamento(conn, beanFil);
        } else {
            res = filamentoDao.queryEsistenzaFilamento(conn, new BeanIdFilamento(beanFil.getIdFil(), beanFil.getSatellite()));
        }
        
        BeanIdFilamento idFil = new BeanIdFilamento(beanFil.getIdFil(), beanFil.getSatellite());
        if (res) { // se il filamento esiste
            SegmentoDao segmentoDao = SegmentoDao.getInstance();
            int numSegmenti = segmentoDao.queryNumeroSegmentiFilamento(conn, idFil);
            beanFil.setNumSegmenti(numSegmenti);
            ContornoDao contornoDao = ContornoDao.getInstance();
            // posizione del centroide ed estensione del contorno 
            // vengono inserite nel bean
            contornoDao.queryInfoContornoFilamento(conn, beanFil);
        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
