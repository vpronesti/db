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

public class GestoreRicercaNumeroSegmenti {
    private InterfacciaRicercaNumeroSegmenti amministratore;
    
    public GestoreRicercaNumeroSegmenti(InterfacciaRicercaNumeroSegmenti amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaFilamenti ricercaNumeroSegmenti(BeanRichiestaNumeroSegmenti beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        List<Integer> listaIdFil = segmentoDao.queryIdFilamentiConNSegmenti(conn, beanRichiesta);
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        List<Filamento> listaFilamenti = new ArrayList<>();
        for (int i = 0; i < listaIdFil.size(); i++) {
            Filamento f = filamentoDao.queryCampiFilamento(conn, listaIdFil.get(i));
            listaFilamenti.add(f);
        }
        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }    
}