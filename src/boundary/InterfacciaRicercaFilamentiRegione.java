package boundary;

import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import bean.BeanUtente;
import control.GestoreRicercaFilamentiRegione;
import dao.UtenteDao;
import entity.TipoFigura;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-8
 */
public class InterfacciaRicercaFilamentiRegione {
    private GestoreRicercaFilamentiRegione controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaFilamentiRegione(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanRichiestaFilamentiRegione beanRichiesta) {
        boolean res;
        if (beanRichiesta.getDimensione() > 0) {
            res = true;
        } else {
            res = false;
        }
        return res;
    }
    
    public BeanRispostaFilamenti ricercaFilamentiRegione(BeanRichiestaFilamentiRegione beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanRichiesta)) {
                controllerFilamento = new GestoreRicercaFilamentiRegione(this);
                if (beanRichiesta.getTipoFigura() == TipoFigura.CERCHIO) {
                    return controllerFilamento.ricercaFilamentiCerchio(beanRichiesta);
                } else {
                    return controllerFilamento.ricercaFilamentiQuadrato(beanRichiesta);
                }            
            } else {
                return new BeanRispostaFilamenti(false, true);
            }
        } else {
            return new BeanRispostaFilamenti(false, false);
        }
    }    
}
