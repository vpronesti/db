package control;

import bean.BeanIdFilamento;
import bean.BeanRispostaStelleFilamento;
import boundary.InterfacciaRicercaStelleFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.StellaDao;
import entity.Contorno;
import entity.Stella;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.DBAccess;

/**
 * REQ-9
 */
public class GestoreRicercaStelleFilamento {
    private InterfacciaRicercaStelleFilamento utente;
    
    public GestoreRicercaStelleFilamento(InterfacciaRicercaStelleFilamento utente) {
        this.utente = utente;
    }
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(BeanIdFilamento idFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        BeanRispostaStelleFilamento beanRisposta;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        if (filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
            StellaDao stellaDao = StellaDao.getInstance();
            List<Stella> listaStelle = stellaDao.queryStelleFilamento(conn, idFil);
            
            /**
             * ottenuta la lista delle stelle si costruisce una struttura 
             * per associare il tipo di stella al numero si stelle di quel tipo
             */
            Map<String, Integer> tipoStellaNumero = new HashMap<>();
            int numeroTotaleStelle = listaStelle.size();
            Iterator<Stella> i = listaStelle.iterator();
            while (i.hasNext()) {
                Stella s = i.next();
                if (tipoStellaNumero.containsKey(s.getTipo()))
                    tipoStellaNumero.put(s.getTipo(), tipoStellaNumero.get(s.getTipo()) + 1);
                else
                    tipoStellaNumero.put(s.getTipo(), 1);
            }
            
            /**
             * si costruisce una struttura per associare ad ogni tipo di 
             * stella la percentuale di stelle corrispondente
             */
            Set<String> tipiStella = tipoStellaNumero.keySet();
            Map<String, Double> tipiStellaPercentuale = new HashMap<>();
            for (String s : tipiStella) {
                int numStelleTipo = tipoStellaNumero.get(s);
                double percentualeTipo = ((numeroTotaleStelle != 0) ? (double) numStelleTipo * 100 / numeroTotaleStelle : 0);
                tipiStellaPercentuale.put(s, percentualeTipo);
            }

            beanRisposta = new BeanRispostaStelleFilamento(numeroTotaleStelle, 
                    tipiStellaPercentuale, true, true);
        } else {
            // filamento non esiste
            beanRisposta = new BeanRispostaStelleFilamento(false, true);
        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }    
}
