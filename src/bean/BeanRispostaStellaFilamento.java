package bean;

import entity.Stella;
import java.util.List;

public class BeanRispostaStellaFilamento {
    private List<Stella> listaStelle;
    private List<Float> listaDistanze;
    private boolean filamentoEsiste;
    
    public BeanRispostaStellaFilamento(List<Stella> listaStelle, List<Float> listaDistanze, boolean filamentoEsiste) {
        this.listaStelle = listaStelle;
        this.listaDistanze = listaDistanze;
        this.filamentoEsiste = filamentoEsiste;
    }
    
    public BeanRispostaStellaFilamento(boolean filamentoEsiste) {
        this.filamentoEsiste = filamentoEsiste;
    }

    public boolean isFilamentoEsiste() {
        return filamentoEsiste;
    }

    public void setFilamentoEsiste(boolean filamentoEsiste) {
        this.filamentoEsiste = filamentoEsiste;
    }

    public List<Stella> getListaStelle() {
        return listaStelle;
    }

    public void setListaStelle(List<Stella> listaStelle) {
        this.listaStelle = listaStelle;
    }

    public List<Float> getListaDistanze() {
        return listaDistanze;
    }

    public void setListaDistanze(List<Float> listaDistanze) {
        this.listaDistanze = listaDistanze;
    }
}
