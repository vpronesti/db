package bean;

public class BeanRichiestaStelleRegione {
    private double longCentr;
    private double latiCentr;
    private double latoA;
    private double latoB;
    
    public BeanRichiestaStelleRegione(double longCentr, double latiCentr, double latoA, double latoB) {
        this.longCentr = longCentr;
        this.latiCentr = latiCentr;
        this.latoA = latoA;
        this.latoB = latoB;
    }

    public double getLongCentr() {
        return longCentr;
    }

    public void setLongCentr(double longCentr) {
        this.longCentr = longCentr;
    }

    public double getLatiCentr() {
        return latiCentr;
    }

    public void setLatiCentr(double latiCentr) {
        this.latiCentr = latiCentr;
    }

    public double getLatoA() {
        return latoA;
    }

    public void setLatoA(double latoA) {
        this.latoA = latoA;
    }

    public double getLatoB() {
        return latoB;
    }

    public void setLatoB(double latoB) {
        this.latoB = latoB;
    }
}
