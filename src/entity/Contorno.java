package entity;

public class Contorno {
    private int idFil;
    private double gLonCont;
    private double gLatCont;
    
    public Contorno(int idFil, double gLonCont, double gLatCont) {
        this.idFil = idFil;
        this.gLonCont = gLonCont;
        this.gLatCont = gLatCont;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
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
