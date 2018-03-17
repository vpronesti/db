package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaFilamenti {    
    private List<Filamento> filamenti;
    private boolean inputValido;
    private boolean azioneConsentita;
    
    public BeanRispostaFilamenti(List<Filamento> filamenti, boolean inputValido, boolean azioneConsentita) {
        this.filamenti = filamenti;
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaFilamenti(boolean inputValido, boolean azioneConsentita) {
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }

    public List<Filamento> getFilamenti() {
        return filamenti;
    }

    public void setFilamenti(List<Filamento> filamenti) {
        this.filamenti = filamenti;
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
