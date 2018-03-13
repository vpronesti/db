package boundary;

import bean.BeanStrumentoSatellite;
import control.GestoreInserimentoNomeStrumento;

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
        if (this.controllaBean(beanStrumento)) {
            controllerInserimento = new GestoreInserimentoNomeStrumento(this);
            return controllerInserimento.inserisciNomeStrumento(beanStrumento);
        } else {
            return false;
        }
    }
}
