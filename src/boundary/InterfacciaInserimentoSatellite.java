package boundary;

import bean.BeanSatellite;
import control.GestoreInserimentoSatellite;

public class InterfacciaInserimentoSatellite {
    private GestoreInserimentoSatellite controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoSatellite(String userId) {
        this.userId = userId;
    }
    
    public boolean inserisciSatellite(BeanSatellite satellite) {
        controllerInserimento = new GestoreInserimentoSatellite(this);
        return controllerInserimento.inserisciSatellite(satellite);
    }
}
