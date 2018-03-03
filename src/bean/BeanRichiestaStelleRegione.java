package bean;

public class BeanRichiestaStelleRegione {
    private float longCentr;
    private float latiCentr;
    private float latoA;
    private float latoB;
    
    public BeanRichiestaStelleRegione(float longCentr, float latiCentr, float latoA, float latoB) {
        this.longCentr = longCentr;
        this.latiCentr = latiCentr;
        this.latoA = latoA;
        this.latoB = latoB;
    }

    public float getLongCentr() {
        return longCentr;
    }

    public void setLongCentr(float longCentr) {
        this.longCentr = longCentr;
    }

    public float getLatiCentr() {
        return latiCentr;
    }

    public void setLatiCentr(float latiCentr) {
        this.latiCentr = latiCentr;
    }

    public float getLatoA() {
        return latoA;
    }

    public void setLatoA(float latoA) {
        this.latoA = latoA;
    }

    public float getLatoB() {
        return latoB;
    }

    public void setLatoB(float latoB) {
        this.latoB = latoB;
    }
}
