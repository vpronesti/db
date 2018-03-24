package control;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import boundary.InterfacciaRecuperoInformazioniDerivateFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SegmentoDao;
import java.sql.Connection;
import util.DBAccess;

public class GestoreRecuperoInformazioniFilamento {
    private InterfacciaRecuperoInformazioniDerivateFilamento amministratore;
    
    public GestoreRecuperoInformazioniFilamento(InterfacciaRecuperoInformazioniDerivateFilamento amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean recuperaInfoFilamento(BeanInformazioniFilamento beanFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        boolean res = true;
        if (!beanFil.isRicercaId()) {
            // inserisce l'id del filamento nel bean
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
            contornoDao.queryInfoContornoFilamento(conn, beanFil);
//            contornoDao.queryPosizioneCentroide(conn, beanFil);
//            contornoDao.queryEstensioneContorno(conn, beanFil);
        }
//        else {
//            res = false; // segmento non esistente
//        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
