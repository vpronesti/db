package control;

import bean.BeanUtente;
import boundary.InterfacciaRegistrazioneUtente;
import dao.UtenteDao;

public class GestoreRegistrazioneUtente {
    private InterfacciaRegistrazioneUtente amministratore;
    
    public GestoreRegistrazioneUtente(InterfacciaRegistrazioneUtente amministratore) {
        this.amministratore = amministratore;
    }

    public boolean gestioneRegistrazioneUtente(BeanUtente beanUtente) {
        UtenteDao utenteDao = UtenteDao.getInstance();
        return utenteDao.inserisciUtente(beanUtente);
    }
}
