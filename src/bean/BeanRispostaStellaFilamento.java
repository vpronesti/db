package bean;

import entity.Stella;
import java.util.List;

public class BeanRispostaStellaFilamento {
    private List<Stella> listaStelle;
    private List<Double> listaDistanze;
    private boolean filamentoEsiste;
    private boolean azioneConsentita;
    
    public BeanRispostaStellaFilamento(List<Stella> listaStelle, 
            List<Double> listaDistanze, boolean filamentoEsiste, boolean azioneConsentita) {
        this.listaStelle = listaStelle;
        this.listaDistanze = listaDistanze;
        this.filamentoEsiste = filamentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaStellaFilamento(boolean filamentoEsiste, boolean azioneConsentita) {
        this.filamentoEsiste = filamentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }

    public boolean isFilamentoEsiste() {
        return filamentoEsiste;
    }

    public void setFilamentoEsiste(boolean filamentoEsiste) {
        this.filamentoEsiste = filamentoEsiste;
    }

    public List<Stella> getListaStelle() {
        return listaStelle;
    }

    public void setListaStelle(List<Stella> listaStelle) {
        this.listaStelle = listaStelle;
    }

    public List<Double> getListaDistanze() {
        return listaDistanze;
    }

    public void setListaDistanze(List<Double> listaDistanze) {
        this.listaDistanze = listaDistanze;
    }

    public boolean isAzioneConsentita() {
        return azioneConsentita;
    }

    public void setAzioneConsentita(boolean azioneConsentita) {
        this.azioneConsentita = azioneConsentita;
    }
}
