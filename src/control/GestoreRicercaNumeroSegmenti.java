package control;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import boundary.InterfacciaRicercaNumeroSegmenti;
import dao.FilamentoDao;
import dao.SegmentoDao;
import entity.Filamento;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import util.DBAccess;

/**
 * REQ-7
 */
public class GestoreRicercaNumeroSegmenti {
    private InterfacciaRicercaNumeroSegmenti amministratore;
    
    public GestoreRicercaNumeroSegmenti(InterfacciaRicercaNumeroSegmenti amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaFilamenti ricercaNumeroSegmenti(BeanRichiestaNumeroSegmenti beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        List<Filamento> listaFilamenti = segmentoDao.queryFilamentiConNSegmenti(conn, beanRichiesta);
        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti, true, true);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }    
}
