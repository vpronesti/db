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

public class GestoreRicercaFilamentiRegione {
    private InterfacciaRicercaFilamentiRegione amministratore;
    
    public GestoreRicercaFilamentiRegione(InterfacciaRicercaFilamentiRegione amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiCerchio(BeanRichiestaFilamentiRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        List<Integer> listaIdInterni = new ArrayList<>();
        List<Integer> listaIdEsistenti = contornoDao.queryIdFilamentiContorno(conn);
        Iterator<Integer> i = listaIdEsistenti.iterator();
        while (i.hasNext()) {
            int id = i.next();
            List<Contorno> listaPunti = contornoDao.queryPuntiContornoFilamento(conn, id);
            Iterator<Contorno> j = listaPunti.iterator();
            boolean puntoInterno = true;
            while (j.hasNext() && puntoInterno) {
                Contorno c = j.next();
                if (distanza(c.getgLonCont(), c.getgLatCont(), 
                        beanRichiesta.getLongCentroide(), 
                        beanRichiesta.getLatiCentroide()) 
                        > beanRichiesta.getDimensione())
                    puntoInterno = false;
            }
            if (puntoInterno)
                listaIdInterni.add(id);
        }
        
        List<Filamento> listaFilamenti = new ArrayList<>();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Iterator<Integer> it = listaIdInterni.iterator();
        while (it.hasNext()) {
            Filamento f = filamentoDao.queryCampiFilamento(conn, it.next());
            listaFilamenti.add(f);
        }
        
        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti); //listaIdInterni);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiQuadrato(BeanRichiestaFilamentiRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        List<Integer> listaIdInterni = new ArrayList<>();
        List<Integer> listaIdEsistenti = contornoDao.queryIdFilamentiContorno(conn);
        Iterator<Integer> i = listaIdEsistenti.iterator();
        while (i.hasNext()) {
            int id = i.next();
            List<Contorno> listaPunti = contornoDao.queryPuntiContornoFilamento(conn, id);
            Iterator<Contorno> j = listaPunti.iterator();
            boolean puntoInterno = true;
            while (j.hasNext() && puntoInterno) {
                Contorno c = j.next();
                if (c.getgLonCont() > beanRichiesta.getLongCentroide() + beanRichiesta.getDimensione() / 2)
                    puntoInterno = false;
                if (c.getgLonCont() < beanRichiesta.getLongCentroide() - beanRichiesta.getDimensione() / 2)
                    puntoInterno = false;
                if (c.getgLatCont() > beanRichiesta.getLatiCentroide() + beanRichiesta.getDimensione() / 2)
                    puntoInterno = false;
                if (c.getgLatCont() < beanRichiesta.getLatiCentroide() - beanRichiesta.getDimensione() / 2)
                    puntoInterno = false;
            }
            if (puntoInterno)
                listaIdInterni.add(id);
        }
        
        List<Filamento> listaFilamenti = new ArrayList<>();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Iterator<Integer> it = listaIdInterni.iterator();
        while (it.hasNext()) {
            Filamento f = filamentoDao.queryCampiFilamento(conn, it.next());
            listaFilamenti.add(f);
        }
        
        BeanRispostaFilamenti beanRisposta = new BeanRispostaFilamenti(listaFilamenti);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
}
