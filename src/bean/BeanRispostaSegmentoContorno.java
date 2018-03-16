package bean;

public class BeanRispostaSegmentoContorno {
    private double distanzaA;
    private double distanzaB;
    private boolean segmentoEsiste;
    
    public BeanRispostaSegmentoContorno(double distanzaA, double distanzaB, 
            boolean segmentoEsiste) {
        this.distanzaA = distanzaA;
        this.distanzaB = distanzaB;
        this.segmentoEsiste = segmentoEsiste;
    }
    
    public BeanRispostaSegmentoContorno(boolean segmentoEsiste) {
        this.segmentoEsiste = segmentoEsiste;
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
}
