package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaContrastoEllitticita {
    private List<Filamento> listaFilamenti;
    private double percentuale;
    private boolean inputValido;
    
    public BeanRispostaContrastoEllitticita(List<Filamento> listaFilamenti, 
            double percentuale, boolean inputValido) {
        this.setListaFilamenti(listaFilamenti);
        this.percentuale = percentuale;
        this.inputValido = inputValido;
    }
    
    public BeanRispostaContrastoEllitticita(boolean inputValido) {
        this.inputValido = inputValido;
        
    }

    public List<Filamento> getListaFilamenti() {
        return listaFilamenti;
    }

    public void setListaFilamenti(List<Filamento> listaFilamenti) {
        this.listaFilamenti = listaFilamenti;
    }

    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }

    public boolean isInputValido() {
        return inputValido;
    }

    public void setInputValido(boolean inputValido) {
        this.inputValido = inputValido;
    }
}
