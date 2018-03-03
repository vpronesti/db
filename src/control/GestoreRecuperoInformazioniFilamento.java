package control;

import bean.BeanInformazioniFilamento;
import boundary.InterfacciaRecuperoInformazioniDerivateFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SegmentoDao;
import entity.Filamento;
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
        if (filamentoDao.queryEsistenzaFilamento(conn, beanFil.getIdFil())) {
            SegmentoDao segmentoDao = SegmentoDao.getInstance();
            int numSegmenti = segmentoDao.queryNumeroSegmentiFilamento(conn, beanFil.getIdFil());
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
