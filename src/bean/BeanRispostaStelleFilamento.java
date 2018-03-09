package bean;

import java.util.Map;

public class BeanRispostaStelleFilamento {
    private int totaleStelleTrovate;
    private Map<String, Float> tipiStellaPercentuale;
    private boolean contornoFilamento;
    
    public BeanRispostaStelleFilamento(int totaleStelleTrovate, 
            Map<String, Float> tipiStellaPercentuale, boolean contornoFilamento) {
        this.totaleStelleTrovate = totaleStelleTrovate;
        this.tipiStellaPercentuale = tipiStellaPercentuale;
        this.contornoFilamento = contornoFilamento;
    }
    
    public BeanRispostaStelleFilamento(boolean contornoFilamento) {
        this.contornoFilamento = contornoFilamento;
    }

    public int getTotaleStelleTrovate() {
        return totaleStelleTrovate;
    }

    public void setTotaleStelleTrovate(int totaleStelleTrovate) {
        this.totaleStelleTrovate = totaleStelleTrovate;
    }

    public boolean isContornoFilamento() {
        return contornoFilamento;
    }

    public void setContornoFilamento(boolean contornoFilamento) {
        this.contornoFilamento = contornoFilamento;
    }

    public Map<String, Float> getTipiStellaPercentuale() {
        return tipiStellaPercentuale;
    }

    public void setTipiStellaPercentuale(Map<String, Float> tipiStellaPercentuale) {
        this.tipiStellaPercentuale = tipiStellaPercentuale;
    }
}
