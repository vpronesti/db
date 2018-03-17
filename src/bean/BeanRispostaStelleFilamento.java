package bean;

import java.util.Map;

public class BeanRispostaStelleFilamento {
    private int totaleStelleTrovate;
    private Map<String, Double> tipiStellaPercentuale;
    private boolean contornoFilamento;
    private boolean azioneConsentita;
    
    public BeanRispostaStelleFilamento(int totaleStelleTrovate, 
            Map<String, Double> tipiStellaPercentuale, 
            boolean contornoFilamento, boolean azioneConsentita) {
        this.totaleStelleTrovate = totaleStelleTrovate;
        this.tipiStellaPercentuale = tipiStellaPercentuale;
        this.contornoFilamento = contornoFilamento;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaStelleFilamento(boolean contornoFilamento, boolean azioneConsentita) {
        this.contornoFilamento = contornoFilamento;
        this.azioneConsentita = azioneConsentita;
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

    public Map<String, Double> getTipiStellaPercentuale() {
        return tipiStellaPercentuale;
    }

    public void setTipiStellaPercentuale(Map<String, Double> tipiStellaPercentuale) {
        this.tipiStellaPercentuale = tipiStellaPercentuale;
    }

    public boolean isAzioneConsentita() {
        return azioneConsentita;
    }

    public void setAzioneConsentita(boolean azioneConsentita) {
        this.azioneConsentita = azioneConsentita;
    }
}
