package control;

import bean.BeanIdFilamento;
import bean.BeanIdStella;
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
    private InterfacciaRicercaStelleRegione utente;
    
    public GestoreRicercaStelleRegione(InterfacciaRicercaStelleRegione utente) {
        this.utente = utente;
    }
    
    public BeanRispostaStelleRegione ricercaStelleRegione(BeanRichiestaStelleRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        StellaDao stellaDao = StellaDao.getInstance();
        List<Stella> listaStelleRegione = stellaDao.queryStelleRegione(conn, beanRichiesta);
        
        Map<String, Integer> tipiStelleNumeroInterne = new HashMap<>();
        Map<String, Integer> tipiStelleNumeroEsterne = new HashMap<>();

        int totaleStelleRegione = listaStelleRegione.size();
        
        List<Stella> listaStelleEsterne = new ArrayList<>();
        List<Stella> listaStelleInterne = new ArrayList<>();
        
        /**
         * per ciascuna delle stelle della regione si stabilisce 
         * se sono interne ad almeno un filamento oppure no
         */
        Iterator<Stella> iS = listaStelleRegione.iterator();
        while (iS.hasNext()) {
            Stella s = iS.next();
            BeanIdStella idStella = new BeanIdStella(s.getIdStella(), s.getSatellite());
            if (stellaDao.queryStellaAppartieneFilamento(conn, idStella))
                listaStelleInterne.add(s);
            else
                listaStelleEsterne.add(s);
        }
        
        int totaleStelleInterne = listaStelleInterne.size();
        int totaleStelleEsterne = listaStelleEsterne.size();
        
        iS = listaStelleInterne.iterator();
        /**
         * si associa ad ogni tipo di stella interna il 
         * corrispettivo numero di stelle trovate
         */
        while (iS.hasNext()) {
            Stella s = iS.next();
            if (tipiStelleNumeroInterne.containsKey(s.getTipo()))
                tipiStelleNumeroInterne.put(s.getTipo(), tipiStelleNumeroInterne.get(s.getTipo()) + 1);
            else
                tipiStelleNumeroInterne.put(s.getTipo(), 1);
        }
        iS = listaStelleEsterne.iterator();
        
        /**
         * si associa ad ogni tipo di stella esterna il corrispettivo numero di stelle trovate
         */
        while (iS.hasNext()) {
            Stella s = iS.next();
            if (tipiStelleNumeroEsterne.containsKey(s.getTipo()))
                tipiStelleNumeroEsterne.put(s.getTipo(), tipiStelleNumeroEsterne.get(s.getTipo()) + 1);
            else
                tipiStelleNumeroEsterne.put(s.getTipo(), 1);
        }

        /**
         * passaggio dalle associazioni tipoStella-numeroStelle 
         * ad associazioni del tipo tipoStella-percentuale
         */
        double percentualeStelleInterne = ((totaleStelleRegione != 0) ? (double) totaleStelleInterne * 100 /totaleStelleRegione : 0); 
        Map<String, Double> tipiStellePercentualeInterne = new HashMap<>();
        Set<String> tipiStelleInterne = tipiStelleNumeroInterne.keySet();
        for (String s : tipiStelleInterne) {
            int numTipoStella = tipiStelleNumeroInterne.get(s);
            double percentualeTipoStella = ((totaleStelleInterne != 0) ? (double) numTipoStella * 100 /totaleStelleInterne : 0); 
            tipiStellePercentualeInterne.put(s, percentualeTipoStella);
        }     
        double percentualeStelleEsterne = ((totaleStelleRegione != 0) ? (double) totaleStelleEsterne * 100 /totaleStelleRegione : 0); 
        Map<String, Double> tipiStellePercentualeEsterne = new HashMap<>();
        Set<String> tipiStelleEsterne = tipiStelleNumeroEsterne.keySet();
        for (String s : tipiStelleEsterne) {
            int numTipoStella = tipiStelleNumeroEsterne.get(s);
            double percentualeTipoStella = ((totaleStelleEsterne != 0) ? (double) numTipoStella * 100 /totaleStelleEsterne : 0); 
            tipiStellePercentualeEsterne.put(s, percentualeTipoStella);
        }
        
        BeanRispostaStelleRegione beanRisposta = 
                new BeanRispostaStelleRegione(percentualeStelleInterne, 
                        percentualeStelleEsterne, tipiStellePercentualeInterne, 
                        tipiStellePercentualeEsterne, true);

        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
}
