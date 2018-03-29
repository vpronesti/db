package control;

import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import boundary.InterfacciaRicercaFilamentiRegione;
import dao.ContornoDao;
import dao.FilamentoDao;
import entity.Contorno;
import entity.Filamento;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;
import static util.DistanzaEuclidea.distanza;

/**
 * REQ-8
 */
public class GestoreRicercaFilamentiRegione {
    private InterfacciaRicercaFilamentiRegione utente;
    
    public GestoreRicercaFilamentiRegione(InterfacciaRicercaFilamentiRegione utente) {
        this.utente = utente;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiCerchio(BeanRichiestaFilamentiRegione beanRichiesta) {
//long start = System.currentTimeMillis();
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        
        List<Filamento> listaFilamenti = contornoDao.queryFilamentiInterniCerchio(conn, beanRichiesta);
        
//System.out.println("check time: " + (System.currentTimeMillis() - start));
//System.out.println("len: " + listaFilamenti.size());
        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti, true, true); //listaIdInterni);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiQuadrato(BeanRichiestaFilamentiRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        
        List<Filamento> listaFilamenti = contornoDao.queryFilamentiInterniQuadrato(conn, beanRichiesta);

        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti, true, true);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
}
