package bean;

public class BeanRispostaStelleFilamento {
    private int totaleStelleTrovate;
    private float percentualeUnbound;
    private float percentualePrestellar;
    private float percentualeProtostellar;
    private boolean contornoFilamento;
    
    public BeanRispostaStelleFilamento(int totaleStelleTrovate, 
            float percentualeUnbound, float percentualePrestellar, 
            float percentualeProtostellar, boolean contornoFilamento) {
        this.totaleStelleTrovate = totaleStelleTrovate;
        this.percentualeUnbound = percentualeUnbound;
        this.percentualePrestellar = percentualePrestellar;
        this.percentualeProtostellar = percentualeProtostellar;
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

    public float getPercentualeUnbound() {
        return percentualeUnbound;
    }

    public void setPercentualeUnbound(float percentualeUnbound) {
        this.percentualeUnbound = percentualeUnbound;
    }

    public float getPercentualePrestellar() {
        return percentualePrestellar;
    }

    public void setPercentualePrestellar(float percentualePrestellar) {
        this.percentualePrestellar = percentualePrestellar;
    }

    public float getPercentualeProtostellar() {
        return percentualeProtostellar;
    }

    public void setPercentualeProtostellar(float percentualeProtostellar) {
        this.percentualeProtostellar = percentualeProtostellar;
    }

    public boolean isContornoFilamento() {
        return contornoFilamento;
    }

    public void setContornoFilamento(boolean contornoFilamento) {
        this.contornoFilamento = contornoFilamento;
    }
}
