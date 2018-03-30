package boundary;

import bean.BeanUtente;
import control.GestoreRegistrazioneUtente;
import dao.UtenteDao;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-2
 * REQ-3.2
 */
public class InterfacciaRegistrazioneUtente {
    private GestoreRegistrazioneUtente controllerRegistrazioneUtente;
    private String userId;
    private final int LUNGHEZZAMINIMA = 6;
    
    public InterfacciaRegistrazioneUtente(String userId) {
        this.userId = userId;
    }
    
    /**
     * controlla che i valori nel bean siano validi e che i 
     * vincoli sulla lunghezza minima di userId e password siano rispettati
     * @param beanUtente
     * @return
     */
    private boolean controlloBean(BeanUtente beanUtente) {
        boolean res = true;
        if (beanUtente.getUserId() == null || 
                beanUtente.getUserId().length() < LUNGHEZZAMINIMA) {
            res = false;
        }
        if (beanUtente.getPassword() == null || 
                beanUtente.getPassword().length() < LUNGHEZZAMINIMA){
            res = false;
        }
        if (beanUtente.getNome() == null || beanUtente.getNome().isEmpty()) {
            res = false;
        }
        if (beanUtente.getCognome() == null || beanUtente.getCognome().isEmpty()) {
            res = false;
        }
        if (beanUtente.getEmail() == null || beanUtente.getEmail().isEmpty()) {
            res = false;
        }
        if (beanUtente.getTipo() == null || beanUtente.getTipo().isEmpty()) {
            res = false;
        }
        return res;
    }
    
    public boolean definizioneUtente(BeanUtente beanUtente) {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controlloBean(beanUtente)) {
                boolean res;
                controllerRegistrazioneUtente = new GestoreRegistrazioneUtente(this);
                res = controllerRegistrazioneUtente.gestioneRegistrazioneUtente(beanUtente);
                return res;
            }
        }
        return false;
    }
}
