package entity;

import bean.BeanIdFilamento;

public class Contorno {
    private int idFil;
    private float gLonCont;
    private float gLatCont;
    private String satellite;
    
    public Contorno(int idFil, String satellite, float gLonCont, float gLatCont) {
        this.idFil = idFil;
        this.satellite = satellite;
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

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }
    
    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof Contorno){
            Contorno ptr = (Contorno) v;
            retVal = ptr.getIdFil() == this.idFil && 
                    ptr.getSatellite().equals(this.satellite) && 
                    ptr.getgLonCont() == this.gLonCont && 
                    ptr.getgLatCont() == this.gLatCont;
        }

        return retVal;
    }
    
}
