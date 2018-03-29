package boundary;

import bean.BeanStrumento;
import bean.BeanUtente;
import control.GestoreInserimentoBandaStrumento;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-3.4
 */
public class InterfacciaInserimentoBandaStrumento {
    private GestoreInserimentoBandaStrumento controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoBandaStrumento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanStrumento beanStrumento) {
        boolean res = true;
        if (beanStrumento.getNome() == null || beanStrumento.getNome().isEmpty())
            res = false;
        return res;
    }
    
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanStrumento)) {
                controllerInserimento = new GestoreInserimentoBandaStrumento(this);
                return controllerInserimento.inserisciBandaStrumento(beanStrumento);
            }
        }
        return false;
    }
}
