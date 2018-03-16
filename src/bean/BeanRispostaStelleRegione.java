package bean;

import java.util.Map;

public class BeanRispostaStelleRegione {
    private double percentualeStelleInterne;
    private double percentualeStelleEsterne;
    private Map<String, Double> tipiStellePercentualeInterne;
    private Map<String, Double> tipiStellePercentualeEsterne;
    
    public BeanRispostaStelleRegione(double percentualeStelleInterne, 
            double percentualeStelleEsterne, 
            Map<String, Double> tipiStellePercentualeInterne, 
            Map<String, Double> tipiStellePercentualeEsterne) {
        this.percentualeStelleInterne = percentualeStelleInterne;
        this.percentualeStelleEsterne = percentualeStelleEsterne;
        this.tipiStellePercentualeInterne = tipiStellePercentualeInterne;
        this.tipiStellePercentualeEsterne = tipiStellePercentualeEsterne;

    }

    public double getPercentualeStelleInterne() {
        return percentualeStelleInterne;
    }

    public void setPercentualeStelleInterne(double percentualeStelleInterne) {
        this.percentualeStelleInterne = percentualeStelleInterne;
    }

    public double getPercentualeStelleEsterne() {
        return percentualeStelleEsterne;
    }

    public void setPercentualeStelleEsterne(double percentualeStelleEsterne) {
        this.percentualeStelleEsterne = percentualeStelleEsterne;
    }

    public Map<String, Double> getTipiStellePercentualeInterne() {
        return tipiStellePercentualeInterne;
    }

    public void setTipiStellePercentualeInterne(Map<String, Double> tipiStellePercentualeInterne) {
        this.tipiStellePercentualeInterne = tipiStellePercentualeInterne;
    }

    public Map<String, Double> getTipiStellePercentualeEsterne() {
        return tipiStellePercentualeEsterne;
    }

    public void setTipiStellePercentualeEsterne(Map<String, Double> tipiStellePercentualeEsterne) {
        this.tipiStellePercentualeEsterne = tipiStellePercentualeEsterne;
    }

}
