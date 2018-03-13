package control;

import bean.BeanIdFilamento;
import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import boundary.InterfacciaRicercaDistanzaSegmentoContorno;
import dao.ContornoDao;
import dao.SegmentoDao;
import entity.Contorno;
import entity.Segmento;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;
import util.DistanzaEuclidea;

public class GestoreRicercaDistanzaSegmentoContorno {
    private InterfacciaRicercaDistanzaSegmentoContorno amministratore;
    
    public GestoreRicercaDistanzaSegmentoContorno(InterfacciaRicercaDistanzaSegmentoContorno amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaSegmentoContorno ricercaDistanzaSegmentoContorno(BeanRichiestaSegmentoContorno beanRichiesta) {
        BeanRispostaSegmentoContorno beanRisposta;
        Connection conn = DBAccess.getInstance().getConnection();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        boolean segmentoEsiste = segmentoDao.queryEsistenzaSegmento(conn, beanRichiesta.getIdFil(), beanRichiesta.getSatellite(), beanRichiesta.getIdSeg());
        if (segmentoEsiste) { // controlla implicitamente l'esistenza del filamento
            Segmento verticeMax = segmentoDao.queryMaxSegmento(conn, beanRichiesta);
            Segmento verticeMin = segmentoDao.queryMinSegmento(conn, beanRichiesta);
            ContornoDao contornoDao = ContornoDao.getInstance();
            BeanIdFilamento idFil = new BeanIdFilamento(beanRichiesta.getIdFil(), beanRichiesta.getSatellite());
            List<Contorno> listaContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
            Iterator<Contorno> i = listaContorno.iterator();
            float distanzaVerticeMax = Float.POSITIVE_INFINITY;
            float distanzaVerticeMin = Float.POSITIVE_INFINITY;
            float calc;
            while (i.hasNext()) {
                Contorno c = i.next();
                calc = DistanzaEuclidea.distanza(c.getgLonCont(), c.getgLatCont(), 
                        verticeMax.getgLonBr(), verticeMax.getgLatBr());
                if (calc < distanzaVerticeMax)
                    distanzaVerticeMax = calc;
                calc = DistanzaEuclidea.distanza(c.getgLonCont(), c.getgLatCont(), 
                        verticeMin.getgLonBr(), verticeMin.getgLatBr());
                if (calc < distanzaVerticeMin)
                    distanzaVerticeMin = calc;
            }
            beanRisposta = new BeanRispostaSegmentoContorno(distanzaVerticeMax, 
                            distanzaVerticeMin, true);
        } else {
            beanRisposta = new BeanRispostaSegmentoContorno(false);
        }
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }       
}
