package entity;

public class Contorno {
    private double idFil;
    private double gLonCont;
    private double gLatCont;
    
    public Contorno(double idFil, double gLonCont, double gLatCont) {
        this.idFil = idFil;
        this.gLonCont = gLonCont;
        this.gLatCont = gLatCont;
    }

    public double getIdFil() {
        return idFil;
    }

    public void setIdFil(double idFil) {
        this.idFil = idFil;
    }

    public double getgLonCont() {
        return gLonCont;
    }

    public void setgLonCont(double gLonCont) {
        this.gLonCont = gLonCont;
    }

    public double getgLatCont() {
        return gLatCont;
    }

    public void setgLatCont(double gLatCont) {
        this.gLatCont = gLatCont;
    }

}
