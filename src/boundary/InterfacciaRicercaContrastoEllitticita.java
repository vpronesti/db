package boundary;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import bean.BeanUtente;
import control.GestoreRicercaContrastoEllitticita;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-6
 */
public class InterfacciaRicercaContrastoEllitticita {
    private GestoreRicercaContrastoEllitticita controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaContrastoEllitticita(String userId) {
        this.userId = userId;
    }
    
    public boolean controllaBean(BeanRichiestaContrastoEllitticita beanRichiesta) {
        boolean res = true;
        if (beanRichiesta.getBrillanza() < 0)
            res = false;
        if (beanRichiesta.getInizioIntervalloEllitticita() <= 1)
            res = false;
        if (beanRichiesta.getInizioIntervalloEllitticita() >= 9)
            res = false;
        if (beanRichiesta.getFineIntervalloEllitticita() <= 1)
            res = false;
        if (beanRichiesta.getFineIntervalloEllitticita() >= 9)
            res = false;
        return res;
    } 
    
    public BeanRispostaContrastoEllitticita ricercaContrastoEllitticita(BeanRichiestaContrastoEllitticita beanRichiesta) {
        BeanRispostaContrastoEllitticita beanRisposta;
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaUtente(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanRichiesta)) {
                controllerFilamento = new GestoreRicercaContrastoEllitticita(this);
                beanRisposta = controllerFilamento.ricercaContrastoEllitticita(beanRichiesta);
            } else {
                // l'input non e' valido
                beanRisposta = new BeanRispostaContrastoEllitticita(false, true);
            }
        } else {
            // l'utente non e' registrato
            beanRisposta = new BeanRispostaContrastoEllitticita(false, false);
        }
        return beanRisposta;
    }
}
