package bean;

import java.util.Map;

public class BeanRispostaStelleFilamento {
    private int totaleStelleTrovate;
    private Map<String, Double> tipiStellaPercentuale;
    private boolean filamentoEsiste;
    private boolean azioneConsentita;
    
    public BeanRispostaStelleFilamento(int totaleStelleTrovate, 
            Map<String, Double> tipiStellaPercentuale, 
            boolean filamentoEsiste, boolean azioneConsentita) {
        this.totaleStelleTrovate = totaleStelleTrovate;
        this.tipiStellaPercentuale = tipiStellaPercentuale;
        this.filamentoEsiste = filamentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaStelleFilamento(boolean filamentoEsiste, boolean azioneConsentita) {
        this.filamentoEsiste = filamentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }

    public int getTotaleStelleTrovate() {
        return totaleStelleTrovate;
    }

    public void setTotaleStelleTrovate(int totaleStelleTrovate) {
        this.totaleStelleTrovate = totaleStelleTrovate;
    }

    public boolean isFilamentoEsiste() {
        return filamentoEsiste;
    }

    public void setFilamentoEsiste(boolean filamentoEsiste) {
        this.filamentoEsiste = filamentoEsiste;
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
