package entity;

import bean.BeanIdFilamento;

public class Contorno {
    private int idFil;
    private double gLonCont;
    private double gLatCont;
    private String satellite;
    
    public Contorno(int idFil, String satellite, double gLonCont, double gLatCont) {
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
