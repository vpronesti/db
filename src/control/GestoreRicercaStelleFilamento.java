package control;

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

public class GestoreRicercaStelleFilamento {
    private InterfacciaRicercaStelleFilamento amministratore;
    
    public GestoreRicercaStelleFilamento(InterfacciaRicercaStelleFilamento amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaStelleFilamento ricercaStelleFilamento(int idFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        BeanRispostaStelleFilamento beanRisposta;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        if (filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
            ContornoDao contornoDao = ContornoDao.getInstance();
            StellaDao stellaDao = StellaDao.getInstance();
            List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
            if (puntiContorno.size() != 0) {
                Map<String, Integer> tipoStellaNumero = new HashMap<>();
                
                
                int numeroStelleUnbound = 0;
                int numeroStellePrestellar = 0;
                int numeroStelleProtostellar = 0;

                List<Stella> listaStelle = stellaDao.queryStelleFilamento(conn, puntiContorno);
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
                Map<String, Float> tipiStellaPercentuale = new HashMap<>();
                for (String s : tipiStella) {
                    int numStelleTipo = tipoStellaNumero.get(s);
                    float percentualeTipo = ((numeroTotaleStelle != 0) ? (float) numStelleTipo * 100 / numeroTotaleStelle : 0);
                    tipiStellaPercentuale.put(s, percentualeTipo);
                }
                
                beanRisposta = new BeanRispostaStelleFilamento(numeroTotaleStelle, 
                        tipiStellaPercentuale, true);
            } else {
                beanRisposta = new BeanRispostaStelleFilamento(false); // non ci sono punti di contorno
            }
        } else {
            beanRisposta = new BeanRispostaStelleFilamento(false); // filamento non esiste
        }
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }        
}
