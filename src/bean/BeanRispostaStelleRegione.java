package bean;

import java.util.Map;

public class BeanRispostaStelleRegione {
    private float percentualeStelleInterne;
    private float percentualeStelleEsterne;
    private Map<String, Float> tipiStellePercentualeInterne;
    private Map<String, Float> tipiStellePercentualeEsterne;
    
    public BeanRispostaStelleRegione(float percentualeStelleInterne, 
            float percentualeStelleEsterne, 
            Map<String, Float> tipiStellePercentualeInterne, 
            Map<String, Float> tipiStellePercentualeEsterne) {
        this.percentualeStelleInterne = percentualeStelleInterne;
        this.percentualeStelleEsterne = percentualeStelleEsterne;
        this.tipiStellePercentualeInterne = tipiStellePercentualeInterne;
        this.tipiStellePercentualeEsterne = tipiStellePercentualeEsterne;

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

    public Map<String, Float> getTipiStellePercentualeInterne() {
        return tipiStellePercentualeInterne;
    }

    public void setTipiStellePercentualeInterne(Map<String, Float> tipiStellePercentualeInterne) {
        this.tipiStellePercentualeInterne = tipiStellePercentualeInterne;
    }

    public Map<String, Float> getTipiStellePercentualeEsterne() {
        return tipiStellePercentualeEsterne;
    }

    public void setTipiStellePercentualeEsterne(Map<String, Float> tipiStellePercentualeEsterne) {
        this.tipiStellePercentualeEsterne = tipiStellePercentualeEsterne;
    }

}
