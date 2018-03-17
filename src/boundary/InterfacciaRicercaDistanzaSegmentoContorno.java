package boundary;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import bean.BeanUtente;
import control.GestoreRicercaDistanzaSegmentoContorno;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-11
 */
public class InterfacciaRicercaDistanzaSegmentoContorno {
    private GestoreRicercaDistanzaSegmentoContorno controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaDistanzaSegmentoContorno(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanRichiestaSegmentoContorno beanRichiesta) {
        boolean res = true;
        if (beanRichiesta.getSatellite() == null || beanRichiesta.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public BeanRispostaSegmentoContorno ricercaDistanzaSegmentoContorno(BeanRichiestaSegmentoContorno beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanRichiesta)) {
                controllerFilamento = new GestoreRicercaDistanzaSegmentoContorno(this);
                return controllerFilamento.ricercaDistanzaSegmentoContorno(beanRichiesta);
            } else {
                return new BeanRispostaSegmentoContorno(false, true);
            }
        } else {
            return new BeanRispostaSegmentoContorno(false, false);
        }
    }      
}
