package bean;

public class BeanRispostaSegmentoContorno {
    private double distanzaA;
    private double distanzaB;
    private boolean segmentoEsiste;
    private boolean azioneConsentita;
    
    public BeanRispostaSegmentoContorno(double distanzaA, double distanzaB, 
            boolean segmentoEsiste, boolean azioneConsentita) {
        this.distanzaA = distanzaA;
        this.distanzaB = distanzaB;
        this.segmentoEsiste = segmentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }
    
    public BeanRispostaSegmentoContorno(boolean segmentoEsiste, boolean azioneConsentita) {
        this.segmentoEsiste = segmentoEsiste;
        this.azioneConsentita = azioneConsentita;
    }

    public double getDistanzaA() {
        return distanzaA;
    }

    public void setDistanzaA(double distanzaA) {
        this.distanzaA = distanzaA;
    }

    public double getDistanzaB() {
        return distanzaB;
    }

    public void setDistanzaB(double distanzaB) {
        this.distanzaB = distanzaB;
    }

    public boolean isSegmentoEsiste() {
        return segmentoEsiste;
    }

    public void setSegmentoEsiste(boolean segmentoEsiste) {
        this.segmentoEsiste = segmentoEsiste;
    }

    public boolean isAzioneConsentita() {
        return azioneConsentita;
    }

    public void setAzioneConsentita(boolean azioneConsentita) {
        this.azioneConsentita = azioneConsentita;
    }
}
