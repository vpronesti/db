package bean;

public class BeanRispostaStelleRegione {
    private float percentualeStelleInterne;
    private float percentualeStelleEsterne;
    private float percentualeStIUnbound;
    private float percentualeStIPrestellar;
    private float percentualeStIProtostellar;
    private float percentualeStEUnbound;
    private float percentualeStEPrestellar;
    private float percentualeStEProtostellar;
    
    public BeanRispostaStelleRegione(float percentualeStelleInterne, 
            float percentualeStelleEsterne, float percentualeStIUnbound, 
            float percentualeStIPrestellar, float percentualeStIProtostellar, 
            float percentualeStEUnbound, float percentualeStEPrestellar, 
            float percentualeStEProtostellar) {
        this.percentualeStelleInterne = percentualeStelleInterne;
        this.percentualeStelleEsterne = percentualeStelleEsterne;
        this.percentualeStIUnbound = percentualeStIUnbound;
        this.percentualeStIPrestellar = percentualeStIPrestellar;
        this.percentualeStIProtostellar = percentualeStIProtostellar;
        this.percentualeStEUnbound = percentualeStEUnbound;
        this.percentualeStEPrestellar = percentualeStEPrestellar;
        this.percentualeStEProtostellar = percentualeStEProtostellar;
    }

    public float getPercentualeStelleInterne() {
        return percentualeStelleInterne;
    }

    public void setPercentualeStelleInterne(float percentualeStelleInterne) {
        this.percentualeStelleInterne = percentualeStelleInterne;
    }

    public float getPercentualeStelleEsterne() {
        return percentualeStelleEsterne;
    }

    public void setPercentualeStelleEsterne(float percentualeStelleEsterne) {
        this.percentualeStelleEsterne = percentualeStelleEsterne;
    }

    public float getPercentualeStIUnbound() {
        return percentualeStIUnbound;
    }

    public void setPercentualeStIUnbound(float percentualeStIUnbound) {
        this.percentualeStIUnbound = percentualeStIUnbound;
    }

    public float getPercentualeStIPrestellar() {
        return percentualeStIPrestellar;
    }

    public void setPercentualeStIPrestellar(float percentualeStIPrestellar) {
        this.percentualeStIPrestellar = percentualeStIPrestellar;
    }

    public float getPercentualeStIProtostellar() {
        return percentualeStIProtostellar;
    }

    public void setPercentualeStIProtostellar(float percentualeStIProtostellar) {
        this.percentualeStIProtostellar = percentualeStIProtostellar;
    }

    public float getPercentualeStEUnbound() {
        return percentualeStEUnbound;
    }

    public void setPercentualeStEUnbound(float percentualeStEUnbound) {
        this.percentualeStEUnbound = percentualeStEUnbound;
    }

    public float getPercentualeStEPrestellar() {
        return percentualeStEPrestellar;
    }

    public void setPercentualeStEPrestellar(float percentualeStEPrestellar) {
        this.percentualeStEPrestellar = percentualeStEPrestellar;
    }

    public float getPercentualeStEProtostellar() {
        return percentualeStEProtostellar;
    }

    public void setPercentualeStEProtostellar(float percentualeStEProtostellar) {
        this.percentualeStEProtostellar = percentualeStEProtostellar;
    }
}
