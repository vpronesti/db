package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaContrastoEllitticita {
    private List<Filamento> listaFilamenti;
    private double percentualeFilamentiTrovati;
    private boolean inputValido;
    private boolean azioneConsentita;
    
    public BeanRispostaContrastoEllitticita(List<Filamento> listaFilamenti, 
            double percentualeFilamentiTrovati, boolean inputValido, 
            boolean azioneConsentita) {
        this.setListaFilamenti(listaFilamenti);
        this.percentualeFilamentiTrovati = percentualeFilamentiTrovati;
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaContrastoEllitticita(boolean inputValido, 
            boolean azioneConsentita) {
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }

    public List<Filamento> getListaFilamenti() {
        return listaFilamenti;
    }

    public void setListaFilamenti(List<Filamento> listaFilamenti) {
        this.listaFilamenti = listaFilamenti;
    }

    public double getPercentualeFilamentiTrovati() {
        return percentualeFilamentiTrovati;
    }

    public void setPercentualeFilamentiTrovati(double percentualeFilamentiTrovati) {
        this.percentualeFilamentiTrovati = percentualeFilamentiTrovati;
    }

    public boolean isInputValido() {
        return inputValido;
    }

    public void setInputValido(boolean inputValido) {
        this.inputValido = inputValido;
    }

    public boolean isAzioneConsentita() {
        return azioneConsentita;
    }

    public void setAzioneConsentita(boolean azioneConsentita) {
        this.azioneConsentita = azioneConsentita;
    }
}
