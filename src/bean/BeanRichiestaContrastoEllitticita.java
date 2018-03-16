package bean;

public class BeanRichiestaContrastoEllitticita {
    private double brillanza;
    private double contrasto;
    private double inizioIntervalloEllitticita;
    private double fineIntervalloEllitticita;
    
    public BeanRichiestaContrastoEllitticita(double brillanza, 
            double inizioIntervalloEllitticita, 
            double fineIntervalloEllitticita) {
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;
    }

    public double getBrillanza() {
        return brillanza;
    }

    public void setBrillanza(double brillanza) {
        this.brillanza = brillanza;
        this.contrasto = 1 + this.brillanza/100;
    }
    
    public double getContrasto() {
        return contrasto;
    }

    public void setContrasto(double contrasto) {
        this.contrasto = contrasto;
        this.brillanza = 100 * (this.contrasto - 1);
    }

    public double getInizioIntervalloEllitticita() {
        return inizioIntervalloEllitticita;
    }

    public void setInizioIntervalloEllitticita(double inizioIntervalloEllitticita) {
        this.inizioIntervalloEllitticita = inizioIntervalloEllitticita;
    }

    public double getFineIntervalloEllitticita() {
        return fineIntervalloEllitticita;
    }

    public void setFineIntervalloEllitticita(double fineIntervalloEllitticita) {
        this.fineIntervalloEllitticita = fineIntervalloEllitticita;
    }
}
