package boundary;

import bean.BeanStrumentoSatellite;
import bean.BeanUtente;
import control.GestoreInserimentoNomeStrumento;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-FN-3.4
 */
public class InterfacciaInserimentoNomeStrumento {
    private GestoreInserimentoNomeStrumento controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoNomeStrumento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanStrumentoSatellite beanStrumento) {
        boolean res = true;
        if (beanStrumento.getNome() == null || beanStrumento.getNome().isEmpty())
            res = false;
        if (beanStrumento.getSatellite() == null || beanStrumento.getSatellite().isEmpty())
            res = false;
        return res;
    }
    
    public boolean inserisciNomeStrumento(BeanStrumentoSatellite beanStrumento) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanStrumento)) {
                controllerInserimento = new GestoreInserimentoNomeStrumento(this);
                return controllerInserimento.inserisciNomeStrumento(beanStrumento);
            }
        }
        return false;
    }
}
