package boundary;

import bean.BeanStrumento;
import control.GestoreInserimentoBandaStrumento;

public class InterfacciaInserimentoBandaStrumento {
    private GestoreInserimentoBandaStrumento controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoBandaStrumento(String userId) {
        this.userId = userId;
    }
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {

        controllerInserimento = new GestoreInserimentoBandaStrumento(this);
        return controllerInserimento.inserisciBandaStrumento(beanStrumento);
    }
}
