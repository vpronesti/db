package boundary;

import control.GestoreInserimentoNomeStrumento;

public class InterfacciaInserimentoNomeStrumento {
    private GestoreInserimentoNomeStrumento controllerInserimento;
    private String userId;
    
    public InterfacciaInserimentoNomeStrumento(String userId) {
        this.userId = userId;
    }
    public boolean inserisciNomeStrumento(String nomeStrumento) {
        controllerInserimento = new GestoreInserimentoNomeStrumento(this);
        return controllerInserimento.inserisciNomeStrumento(nomeStrumento);
    }
}
