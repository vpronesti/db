package entity;

public class Contorno {
    private int idFil;
    private float gLonCont;
    private float gLatCont;
    
    public Contorno(int idFil, float gLonCont, float gLatCont) {
        this.idFil = idFil;
        this.gLonCont = gLonCont;
        this.gLatCont = gLatCont;
    }
    
    @Override
    public String toString() {
        return "idFil: " + idFil + "lo: " + gLonCont + "la: " + gLatCont;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public float getgLonCont() {
        return gLonCont;
    }

    public void setgLonCont(float gLonCont) {
        this.gLonCont = gLonCont;
    }

    public float getgLatCont() {
        return gLatCont;
    }

    public void setgLatCont(float gLatCont) {
        this.gLatCont = gLatCont;
    }

}
