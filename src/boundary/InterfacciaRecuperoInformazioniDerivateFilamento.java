package boundary;

import bean.BeanInformazioniFilamento;
import bean.BeanUtente;
import control.GestoreRecuperoInformazioniFilamento;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-5
 */
public class InterfacciaRecuperoInformazioniDerivateFilamento {
    private GestoreRecuperoInformazioniFilamento controllerFilamento;
    private String userId;
    
    public InterfacciaRecuperoInformazioniDerivateFilamento(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanInformazioniFilamento beanFil) {
        boolean res = true;
        if (beanFil.getSatellite() == null || beanFil.getSatellite().isEmpty())
            res = false;
        if (!beanFil.isRicercaId() && (beanFil.getNome() == null || beanFil.getNome().isEmpty()))
            res = false;
        return res;
    }
    
    public boolean recuperaInfoFilamento(BeanInformazioniFilamento beanFil) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanFil)) {
                controllerFilamento = new GestoreRecuperoInformazioniFilamento(this);
                return controllerFilamento.recuperaInfoFilamento(beanFil);
            }
        }
        return false;
    }
}
