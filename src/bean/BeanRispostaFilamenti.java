package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaFilamenti {    
    private List<Filamento> listaFilamenti;
    private boolean inputValido;
    private boolean azioneConsentita;
    
    public BeanRispostaFilamenti(List<Filamento> listaFilamenti, 
            boolean inputValido, boolean azioneConsentita) {
        this.listaFilamenti = listaFilamenti;
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaFilamenti(boolean inputValido, boolean azioneConsentita) {
        this.inputValido = inputValido;
        this.azioneConsentita = azioneConsentita;
    }

    public List<Filamento> getListaFilamenti() {
        return listaFilamenti;
    }

    public void setListaFilamenti(List<Filamento> listaFilamenti) {
        this.listaFilamenti = listaFilamenti;
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
