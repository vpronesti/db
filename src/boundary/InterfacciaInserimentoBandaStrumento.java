package boundary;

import bean.BeanStrumento;
import control.GestoreInserimentoBandaStrumento;

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
        if (this.controllaBean(beanStrumento)) {
            controllerInserimento = new GestoreInserimentoBandaStrumento(this);
            return controllerInserimento.inserisciBandaStrumento(beanStrumento);
        } else {
            return false;
        }
    }
}
