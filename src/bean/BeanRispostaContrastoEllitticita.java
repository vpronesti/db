package bean;

import entity.Filamento;
import entity.FilamentoG;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanRispostaContrastoEllitticita {
    private List<Filamento> listaFilamenti;
    private float percentuale;
    
    public BeanRispostaContrastoEllitticita(List<Filamento> listaFilamenti, float percentuale) {
        this.setListaFilamenti(listaFilamenti);
        this.percentuale = percentuale;
    }

    public List<Filamento> getListaFilamenti() {
        return listaFilamenti;
    }

    public void setListaFilamenti(List<Filamento> listaFilamenti) {
        this.listaFilamenti = listaFilamenti;
    }

    public float getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(float percentuale) {
        this.percentuale = percentuale;
    }
}
