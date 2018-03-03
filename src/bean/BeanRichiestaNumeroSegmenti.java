package bean;

public class BeanRichiestaNumeroSegmenti {
    private int inizioIntervallo;
    private int fineIntervallo;
    
    public BeanRichiestaNumeroSegmenti(int inizioIntervallo, int fineIntervallo) {
        this.inizioIntervallo = inizioIntervallo;
        this.fineIntervallo = fineIntervallo;
    }

    public int getInizioIntervallo() {
        return inizioIntervallo;
    }

    public void setInizioIntervallo(int inizioIntervallo) {
        this.inizioIntervallo = inizioIntervallo;
    }

    public int getFineIntervallo() {
        return fineIntervallo;
    }

    public void setFineIntervallo(int fineIntervallo) {
        this.fineIntervallo = fineIntervallo;
    }
}
