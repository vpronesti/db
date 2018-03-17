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
 * @author vi
 */
public class GestoreRicercaStelleFilamento {
    private InterfacciaRicercaStelleFilamento amministratore;
    
    public GestoreRicercaStelleFilamento(InterfacciaRicercaStelleFilamento amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(BeanIdFilamento idFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        BeanRispostaStelleFilamento beanRisposta;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        if (filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
            
            StellaDao stellaDao = StellaDao.getInstance();
            ContornoDao contornoDao = ContornoDao.getInstance();
            List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
            List<Stella> listaStelle = stellaDao.queryStelleContornoFilamento(conn, puntiContorno);
//            List<Stella> listaStelle = stellaDao.queryStelleFilamento(conn, idFil);
            
            Map<String, Integer> tipoStellaNumero = new HashMap<>();
            int numeroTotaleStelle = listaStelle.size();
            Iterator<Stella> i = listaStelle.iterator();
            while (i.hasNext()) {
                Stella s = i.next();
                if (tipoStellaNumero.containsKey(s.getType()))
                    tipoStellaNumero.put(s.getType(), tipoStellaNumero.get(s.getType()) + 1);
                else
                    tipoStellaNumero.put(s.getType(), 1);
            }
            Set<String> tipiStella = tipoStellaNumero.keySet();
            Map<String, Double> tipiStellaPercentuale = new HashMap<>();
            for (String s : tipiStella) {
                int numStelleTipo = tipoStellaNumero.get(s);
                double percentualeTipo = ((numeroTotaleStelle != 0) ? (double) numStelleTipo * 100 / numeroTotaleStelle : 0);
                tipiStellaPercentuale.put(s, percentualeTipo);
            }

            beanRisposta = new BeanRispostaStelleFilamento(numeroTotaleStelle, 
                    tipiStellaPercentuale, true, true);
            
            
//            ContornoDao contornoDao = ContornoDao.getInstance();
//            StellaDao stellaDao = StellaDao.getInstance();
//            List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
//            if (puntiContorno.size() != 0) {
//                Map<String, Integer> tipoStellaNumero = new HashMap<>();
//
//                List<Stella> listaStelle = stellaDao.queryStelleContornoFilamento(conn, puntiContorno);
//                int numeroTotaleStelle = listaStelle.size();
//                Iterator<Stella> i = listaStelle.iterator();
//
//                while (i.hasNext()) {
//                    Stella s = i.next();
//                    if (tipoStellaNumero.containsKey(s.getType()))
//                        tipoStellaNumero.put(s.getType(), tipoStellaNumero.get(s.getType()) + 1);
//                    else
//                        tipoStellaNumero.put(s.getType(), 1);
//                }
//                Set<String> tipiStella = tipoStellaNumero.keySet();
//                Map<String, Double> tipiStellaPercentuale = new HashMap<>();
//                for (String s : tipiStella) {
//                    int numStelleTipo = tipoStellaNumero.get(s);
//                    double percentualeTipo = ((numeroTotaleStelle != 0) ? (double) numStelleTipo * 100 / numeroTotaleStelle : 0);
//                    tipiStellaPercentuale.put(s, percentualeTipo);
//                }
//                
//                beanRisposta = new BeanRispostaStelleFilamento(numeroTotaleStelle, 
//                        tipiStellaPercentuale, true);
//            } else {
//                beanRisposta = new BeanRispostaStelleFilamento(false); // non ci sono punti di contorno
//            }
        } else {
            beanRisposta = new BeanRispostaStelleFilamento(false, true); // filamento non esiste
        }
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }    
}
