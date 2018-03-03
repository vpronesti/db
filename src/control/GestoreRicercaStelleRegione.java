package control;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
import boundary.InterfacciaRicercaStelleRegione;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.StellaDao;
import entity.Contorno;
import entity.Filamento;
import entity.Stella;
import entity.TipoStella;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;

public class GestoreRicercaStelleRegione {
    private InterfacciaRicercaStelleRegione amministratore;
    
    public GestoreRicercaStelleRegione(InterfacciaRicercaStelleRegione amministratore) {
        this.amministratore = amministratore;
    }
    
    private List<Filamento> ricercaFilamentiRegione(Connection conn, BeanRichiestaStelleRegione beanRichiesta) {
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
                if (c.getgLonCont() > beanRichiesta.getLongCentr() + beanRichiesta.getLatoA() / 2)
                    puntoInterno = false;
                if (c.getgLonCont() < beanRichiesta.getLongCentr() - beanRichiesta.getLatoA() / 2)
                    puntoInterno = false;
                if (c.getgLatCont() > beanRichiesta.getLatiCentr() + beanRichiesta.getLatoB() / 2)
                    puntoInterno = false;
                if (c.getgLatCont() < beanRichiesta.getLatiCentr() - beanRichiesta.getLatoB() / 2)
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
        return listaFilamenti;
    }
    
    public BeanRispostaStelleRegione ricercaStelleRegione(BeanRichiestaStelleRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        StellaDao stellaDao = StellaDao.getInstance();
        ContornoDao contornoDao = ContornoDao.getInstance();
        List<Stella> listaStelleRegione = stellaDao.queryStelleRegione(conn, beanRichiesta);
        List<Filamento> listaFilamentiRegione = this.ricercaFilamentiRegione(conn, beanRichiesta);
        Iterator<Filamento> i = listaFilamentiRegione.iterator();
        
        float percentualeStelleInterne;
        float percentualeStelleEsterne;
        float percentualeStIUnbound;
        float percentualeStIPrestellar;
        float percentualeStIProtostellar;
        float percentualeStEUnbound;
        float percentualeStEPrestellar;
        float percentualeStEProtostellar;
        
        int totaleStelleInterne = 0;
        int totaleStelleEsterne = 0;
        int totaleStIUnbound = 0;
        int totaleStIPrestellar = 0;
        int totaleStIProtostellar = 0;
        int totaleStEUnbound = 0;
        int totaleStEPrestellar = 0;
        int totaleStEProtostellar = 0;
        int totaleStelleRegione = listaStelleRegione.size();

        while (i.hasNext()) {
            Filamento f = i.next();
            List<Contorno> listaPunti = contornoDao.queryPuntiContornoFilamento(conn, f.getIdFil());
            Iterator<Stella> j = listaStelleRegione.iterator();
            while (j.hasNext()) {
                Stella s = j.next();
                if (s.internoFilamento(listaPunti)) {
                    totaleStelleInterne++;
                    if (s.getType() == TipoStella.UNBOUND)
                        totaleStIUnbound++;
                    if (s.getType() == TipoStella.PRESTELLAR)
                        totaleStIPrestellar++;
                    if (s.getType() == TipoStella.PROTOSTELLAR)
                        totaleStIProtostellar++;
                } else {
                    totaleStelleEsterne++;
                    if (s.getType() == TipoStella.UNBOUND)
                        totaleStEUnbound++;
                    if (s.getType() == TipoStella.PRESTELLAR)
                        totaleStEPrestellar++;
                    if (s.getType() == TipoStella.PROTOSTELLAR)
                        totaleStEProtostellar++;
                }
            }
        }
        
        percentualeStelleInterne = ((totaleStelleRegione != 0) ? totaleStelleInterne * 100 /totaleStelleRegione : 0); 
        percentualeStelleEsterne = ((totaleStelleRegione != 0) ? totaleStelleEsterne * 100 /totaleStelleRegione : 0); 
        
        percentualeStIUnbound = ((totaleStelleInterne != 0) ? totaleStIUnbound * 100 /totaleStelleInterne : 0); 
        percentualeStIPrestellar = ((totaleStelleInterne != 0) ? totaleStIPrestellar * 100 /totaleStelleInterne : 0); 
        percentualeStIProtostellar = ((totaleStelleInterne != 0) ? totaleStIProtostellar * 100 /totaleStelleInterne : 0); 
        
        percentualeStEUnbound = ((totaleStelleEsterne != 0) ? totaleStEUnbound * 100 /totaleStelleEsterne : 0); 
        percentualeStEPrestellar = ((totaleStelleEsterne != 0) ? totaleStEPrestellar * 100 /totaleStelleEsterne : 0); 
        percentualeStEProtostellar = ((totaleStelleEsterne != 0) ? totaleStEProtostellar * 100 /totaleStelleEsterne : 0); 

        BeanRispostaStelleRegione beanRisposta = 
                new BeanRispostaStelleRegione(percentualeStelleInterne, 
                        percentualeStelleEsterne, percentualeStIUnbound, 
                        percentualeStIPrestellar, percentualeStIProtostellar, 
                        percentualeStEUnbound, percentualeStEPrestellar, 
                        percentualeStEProtostellar);

//        BeanRispostaStelleFilamento beanRisposta = new BeanRispostaStelleFilamento(false);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }            
}