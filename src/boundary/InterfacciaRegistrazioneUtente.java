package boundary;

import bean.BeanUtente;
import control.GestoreRegistrazioneUtente;

public class InterfacciaRegistrazioneUtente {
    private GestoreRegistrazioneUtente controllerRegistrazioneUtente;
    private String userId;
    
    public InterfacciaRegistrazioneUtente(String userId) {
        this.userId = userId;
    }
    
    public boolean definizioneUtente(BeanUtente beanUtente) {
        boolean res;
        controllerRegistrazioneUtente = new GestoreRegistrazioneUtente(this);
        res = controllerRegistrazioneUtente.gestioneRegistrazioneUtente(beanUtente);
        return res;
    }
}
