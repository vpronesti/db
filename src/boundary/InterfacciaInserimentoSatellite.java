package boundary;

import bean.BeanSatellite;
import control.GestoreInserimentoSatellite;

public class InterfacciaInserimentoSatellite {
    private GestoreInserimentoSatellite controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoSatellite(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanSatellite beanSatellite) {
        boolean res = true;
        if (beanSatellite.getNome() == null || beanSatellite.getNome().isEmpty())
            res = false;
        if (beanSatellite.getPrimaOsservazione() == null)
            res = false;
        if (beanSatellite.getTermineOperazione() != null) {
            if (beanSatellite.getPrimaOsservazione().isAfter(beanSatellite.getTermineOperazione()))
                res = false;
        }
        if (beanSatellite.getAgenzia() == null || beanSatellite.getAgenzia().isEmpty())
            res = false;
        return res;
    }
    
    public boolean inserisciSatellite(BeanSatellite satellite) {
        if (this.controllaBean(satellite)) {
            controllerInserimento = new GestoreInserimentoSatellite(this);
            return controllerInserimento.inserisciSatellite(satellite);
        } else {
            return false;
        }
    }
}
