package control;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import boundary.InterfacciaRicercaDistanzaStellaFilamento;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SegmentoDao;
import dao.StellaDao;
import entity.Contorno;
import entity.Segmento;
import entity.Stella;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;
import static util.DistanzaEuclidea.distanza;

public class GestoreRicercaDistanzaStellaFilamento {
    private InterfacciaRicercaDistanzaStellaFilamento amministratore;
    
    public GestoreRicercaDistanzaStellaFilamento(InterfacciaRicercaDistanzaStellaFilamento amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaStellaFilamento ricercaDistanzaStellaFilamento(BeanIdFilamento idFil) {
        BeanRispostaStellaFilamento beanRisposta;
        Connection conn = DBAccess.getInstance().getConnection();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        boolean filamentoEsiste = filamentoDao.queryEsistenzaFilamento(conn, idFil);
        if (filamentoEsiste) {
            SegmentoDao segmentoDao = SegmentoDao.getInstance();
            List<Segmento> listaSegmentiPrincipali = segmentoDao.queryPuntiSegmentoPrincipaleFilamento(conn, idFil);
              
            ContornoDao contornoDao = ContornoDao.getInstance();
            List<Contorno> listaPunti = contornoDao.queryPuntiContornoFilamento(conn, idFil);
              
            StellaDao stellaDao = StellaDao.getInstance();
            List<Stella> listaStelleInterne = stellaDao.queryStelleContornoFilamento(conn, listaPunti);
            Iterator<Stella> iSt = listaStelleInterne.iterator();
              
            List<Double> listaDistanze = new ArrayList<>();
             
            while (iSt.hasNext()) {
                Stella st = iSt.next();
                Iterator<Segmento> iSe = listaSegmentiPrincipali.iterator();
                double distanzaMin = Double.POSITIVE_INFINITY;
                while (iSe.hasNext()) {
                    Segmento se = iSe.next();
                    if (distanza(st.getgLonSt(), st.getgLatSt(), se.getgLonBr(), se.getgLatBr()) < distanzaMin)
                        distanzaMin = distanza(st.getgLonSt(), st.getgLatSt(), se.getgLonBr(), se.getgLatBr());
                }
                listaDistanze.add(distanzaMin);
            }
            beanRisposta = new BeanRispostaStellaFilamento(listaStelleInterne, 
                            listaDistanze, true, true);
        } else {
            beanRisposta = new BeanRispostaStellaFilamento(false, true);
        }
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    } 
}
