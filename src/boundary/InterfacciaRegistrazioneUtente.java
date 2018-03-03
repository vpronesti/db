package boundary;

import bean.BeanUtente;
import control.GestoreRegistrazioneUtente;

public class InterfacciaRegistrazioneUtente {
    private GestoreRegistrazioneUtente controllerRegistrazioneUtente;
    private String userId;
    private final int LUNGHEZZAMINIMA = 6;
    
    public InterfacciaRegistrazioneUtente(String userId) {
        this.userId = userId;
    }
    
    private boolean controlloBean(BeanUtente beanUtente) {
        boolean res = true;
        if (beanUtente.getUserId().length() < LUNGHEZZAMINIMA) {
            res = false;
        }
        if (beanUtente.getPassword().length() < LUNGHEZZAMINIMA){
            res = false;
        }
        return res;
    }
    
    public boolean definizioneUtente(BeanUtente beanUtente) {
        if (this.controlloBean(beanUtente)) {
            boolean res;
            controllerRegistrazioneUtente = new GestoreRegistrazioneUtente(this);
            res = controllerRegistrazioneUtente.gestioneRegistrazioneUtente(beanUtente);
            return res;
        } else {
            return false;
        }
    }
}
