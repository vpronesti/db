package control;

import bean.BeanRispostaStelleFilamento;
import boundary.InterfacciaRicercaStelleFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.StellaDao;
import entity.Contorno;
import entity.Stella;
import entity.TipoStella;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
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
                int numeroTotaleStelle = 0;
                int numeroStelleUnbound = 0;
                int numeroStellePrestellar = 0;
                int numeroStelleProtostellar = 0;

        //        ResultSet rs = stellaDao.queryStelle(conn);
                List<Stella> listaStelle = stellaDao.queryStelleFilamento(conn, puntiContorno);
                numeroTotaleStelle = listaStelle.size();
                Iterator<Stella> i = listaStelle.iterator();

                while (i.hasNext()) {
                    Stella s = i.next();
        //            if (s.internoFilamento(puntiContorno)) {
        //                numeroTotaleStelle++;
                        if (s.getType() == TipoStella.UNBOUND)
                            numeroStelleUnbound++;
                        if (s.getType() == TipoStella.PRESTELLAR)
                            numeroStellePrestellar++;
                        if (s.getType() == TipoStella.PROTOSTELLAR)
                            numeroStelleProtostellar++;
        //            }
                }
                float percentualeUnbound = ((numeroTotaleStelle != 0) ? numeroStelleUnbound * 100 / numeroTotaleStelle : 0);
                float percentualePrestellar = ((numeroTotaleStelle != 0) ? numeroStellePrestellar * 100 / numeroTotaleStelle : 0);
                float percentualeProtostellar = ((numeroTotaleStelle != 0) ? numeroStelleProtostellar * 100 / numeroTotaleStelle : 0);

                beanRisposta = new BeanRispostaStelleFilamento(numeroTotaleStelle, 
                                percentualeUnbound, percentualePrestellar, 
                                percentualeProtostellar, true);
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
