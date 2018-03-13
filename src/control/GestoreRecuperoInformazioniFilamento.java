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
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        boolean res = true;
        BeanIdFilamento idFil = new BeanIdFilamento(beanFil.getIdFil(), beanFil.getSatellite());
        if (filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
            SegmentoDao segmentoDao = SegmentoDao.getInstance();
            int numSegmenti = segmentoDao.queryNumeroSegmentiFilamento(conn, idFil);
            beanFil.setNumSegmenti(numSegmenti);
            ContornoDao contornoDao = ContornoDao.getInstance();
            contornoDao.queryPosizioneCentroide(conn, beanFil);
            contornoDao.queryEstensioneContorno(conn, beanFil);
        } else {
            res = false; // segmento non esistente
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
}
