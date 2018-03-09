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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.DBAccess;

/**
 * REQ-10
 */
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
        
        
        Map<String, Integer> tipiStelleNumeroInterne = new HashMap<>();
        Map<String, Integer> tipiStelleNumeroEsterne = new HashMap<>();
            int totaleStIUnbound = 0;
            int totaleStIPrestellar = 0;
            int totaleStIProtostellar = 0;
            int totaleStEUnbound = 0;
            int totaleStEPrestellar = 0;
            int totaleStEProtostellar = 0;
        int totaleStelleRegione = listaStelleRegione.size();
        
        List<Stella> listaStelleEsterne = new ArrayList<>();
        Iterator<Stella> iS = listaStelleRegione.iterator();
        while (iS.hasNext()) {
            Stella s = iS.next();
            listaStelleEsterne.add(s);
        }
        List<Stella> listaStelleInterne = new ArrayList<>();

        while (i.hasNext()) {
            Filamento f = i.next();
            List<Contorno> listaPunti = contornoDao.queryPuntiContornoFilamento(conn, f.getIdFil());
            Iterator<Stella> j = listaStelleRegione.iterator();
            while (j.hasNext()) {
                Stella s = j.next();
                if (listaStelleInterne.contains(s))
                    continue;
                if (s.internoFilamento(listaPunti)) {
                    listaStelleInterne.add(s);
                    listaStelleEsterne.remove(s);
                }
            }
        }
        int totaleStelleInterne = listaStelleInterne.size();
        int totaleStelleEsterne = listaStelleEsterne.size();
        iS = listaStelleInterne.iterator();
        while (iS.hasNext()) {
            Stella s = iS.next();
            if (tipiStelleNumeroInterne.containsKey(s.getType()))
                tipiStelleNumeroInterne.put(s.getType(), tipiStelleNumeroInterne.get(s.getType()) + 1);
            else
                tipiStelleNumeroInterne.put(s.getType(), 1);
        }
        iS = listaStelleEsterne.iterator();
        while (iS.hasNext()) {
            Stella s = iS.next();
            if (tipiStelleNumeroEsterne.containsKey(s.getType()))
                tipiStelleNumeroEsterne.put(s.getType(), tipiStelleNumeroEsterne.get(s.getType()) + 1);
            else
                tipiStelleNumeroEsterne.put(s.getType(), 1);
        }

        float percentualeStelleInterne = ((totaleStelleRegione != 0) ? (float) totaleStelleInterne * 100 /totaleStelleRegione : 0); 
        Map<String, Float> tipiStellePercentualeInterne = new HashMap<>();
        Set<String> tipiStelleInterne = tipiStelleNumeroInterne.keySet();
        for (String s : tipiStelleInterne) {
            int numTipoStella = tipiStelleNumeroInterne.get(s);
            float percentualeTipoStella = ((totaleStelleInterne != 0) ? (float) numTipoStella * 100 /totaleStelleInterne : 0); 
            tipiStellePercentualeInterne.put(s, percentualeTipoStella);
        }
           
        float percentualeStelleEsterne = ((totaleStelleRegione != 0) ? (float) totaleStelleEsterne * 100 /totaleStelleRegione : 0); 
        Map<String, Float> tipiStellePercentualeEsterne = new HashMap<>();
        Set<String> tipiStelleEsterne = tipiStelleNumeroEsterne.keySet();
        for (String s : tipiStelleEsterne) {
            int numTipoStella = tipiStelleNumeroEsterne.get(s);
            float percentualeTipoStella = ((totaleStelleEsterne != 0) ? (float) numTipoStella * 100 /totaleStelleEsterne : 0); 
            tipiStellePercentualeEsterne.put(s, percentualeTipoStella);
        }
        
        BeanRispostaStelleRegione beanRisposta = 
                new BeanRispostaStelleRegione(percentualeStelleInterne, 
                        percentualeStelleEsterne, tipiStellePercentualeInterne, 
                        tipiStellePercentualeEsterne);

        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }            
}
