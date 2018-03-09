package bean;

public class BeanRichiestaContrastoEllitticita {
    private float brillanza;
    private float contrasto;
    private float inizioIntervalloEllitticita;
    private float fineIntervalloEllitticita;
    
    public BeanRichiestaContrastoEllitticita(float brillanza, 
            float inizioIntervalloEllitticita, 
            float fineIntervalloEllitticita) {
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;
    }

    public float getBrillanza() {
        return brillanza;
    }

    public void setBrillanza(float brillanza) {
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
    }
    
    public float getContrasto() {
        return contrasto;
    }

    public void setContrasto(float contrasto) {
        this.contrasto = contrasto;
        this.brillanza = 100 * (this.contrasto - 1);
    }

    public float getInizioIntervalloEllitticita() {
        return inizioIntervalloEllitticita;
    }

    public void setInizioIntervalloEllitticita(float inizioIntervalloEllitticita) {
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
    }

    public float getFineIntervalloEllitticita() {
        return fineIntervalloEllitticita;
    }

    public void setFineIntervalloEllitticita(float fineIntervalloEllitticita) {
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;
    }
}
