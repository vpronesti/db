package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaFilamenti {    
    private List<Filamento> filamenti;
    private boolean inputValido;
    
    public BeanRispostaFilamenti(List<Filamento> filamenti, boolean inputValido) {
        this.filamenti = filamenti;
        this.inputValido = inputValido;
    }
    
    public BeanRispostaFilamenti(boolean inputValido) {
        this.inputValido = inputValido;
    }

    public List<Filamento> getFilamenti() {
        return filamenti;
    }

    public void setFilamenti(List<Filamento> filamenti) {
        this.filamenti = filamenti;
    }

    public boolean isInputValido() {
        return inputValido;
    }

    public void setInputValido(boolean inputValido) {
        this.inputValido = inputValido;
    }
}
