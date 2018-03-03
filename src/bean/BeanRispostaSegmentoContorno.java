package bean;

public class BeanRispostaSegmentoContorno {
    private float distanzaA;
    private float distanzaB;
    private boolean segmentoEsiste;
    
    public BeanRispostaSegmentoContorno(float distanzaA, float distanzaB, boolean segmentoEsiste) {
        this.distanzaA = distanzaA;
        this.distanzaB = distanzaB;
        this.segmentoEsiste = segmentoEsiste;
    }
    
    public BeanRispostaSegmentoContorno(boolean segmentoEsiste) {
        this.segmentoEsiste = segmentoEsiste;
    }

    public float getDistanzaA() {
        return distanzaA;
    }

    public void setDistanzaA(float distanzaA) {
        this.distanzaA = distanzaA;
    }

    public float getDistanzaB() {
        return distanzaB;
    }

    public void setDistanzaB(float distanzaB) {
        this.distanzaB = distanzaB;
    }

    public boolean isSegmentoEsiste() {
        return segmentoEsiste;
    }

    public void setSegmentoEsiste(boolean segmentoEsiste) {
        this.segmentoEsiste = segmentoEsiste;
    }
}
