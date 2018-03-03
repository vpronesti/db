package bean;

import entity.Filamento;
import entity.FilamentoG;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanRispostaContrastoEllitticitaG {
    private List<FilamentoG> listaFilamenti;
    private float percentuale;
    
    public BeanRispostaContrastoEllitticitaG(BeanRispostaContrastoEllitticita beanRisposta) {
        this.setListaFilamenti(beanRisposta.getListaFilamenti());
        this.percentuale = beanRisposta.getPercentuale();
    }
    
    public BeanRispostaContrastoEllitticitaG(List<Filamento> listaFilamenti, float percentuale) {
        this.setListaFilamenti(listaFilamenti);
        this.percentuale = percentuale;
    }

    public List<FilamentoG> getListaFilamenti() {
        return listaFilamenti;
    }

    public void setListaFilamenti(List<Filamento> listaFilamenti) {
        Iterator<Filamento> i = listaFilamenti.iterator();
        List<FilamentoG> listaFilamentiG = new ArrayList<>();
        while (i.hasNext()) {
            Filamento f = i.next();
            FilamentoG fg = new FilamentoG(f);
            listaFilamentiG.add(fg);
        }
        this.listaFilamenti = listaFilamentiG;
    }

    public float getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(float percentuale) {
        this.percentuale = percentuale;
    }
}
