package entity;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import java.util.List;

public class Stella {
    private int idStar;
    private String satellite;
    private String name;
    private double gLonSt;
    private double gLatSt;
    private double fluxSt;
    private String type;
    
    public Stella(int idStar, String satellite, String name, 
            double gLonSt, double gLatSt, double fluxSt, String type) {
        this.idStar = idStar;
        this.satellite = satellite;
        this.name = name;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
        this.fluxSt = fluxSt;
        this.type = type;
        
    }

    public Stella(int idStar, String satellite, double gLonSt, double gLatSt) {
        this.idStar = idStar;
        this.satellite = satellite;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
    }

    
    public boolean internoFilamento(List<Contorno> listaPunti) {
        double sum = 0;
        double STL = this.getgLonSt();
        double STB = this.getgLatSt();
        for (int i = 0; i < listaPunti.size() - 1; i++) {
            double CLi = listaPunti.get(i).getgLonCont();
            double CBip1 = listaPunti.get(i + 1).getgLatCont();
            double CBi = listaPunti.get(i).getgLatCont();
            double CLip1 = listaPunti.get(i + 1).getgLonCont();
                    
            double numeratore =  
                    (CLi - STL) * (CBip1 - STB) - (CBi - STB) * (CLip1 - STL);
            double denominatore = 
                    (CLi - STL) * (CLip1 - STL) + (CBi - STB) * (CBip1 - STB);
            double addendo = Math.toRadians(atan(numeratore / denominatore));
            sum += addendo;
        }
        return abs(sum) >= 0.01;
    }

    public int getIdStar() {
        return idStar;
    }

    public void setIdStar(int idStar) {
        this.idStar = idStar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getgLonSt() {
        return gLonSt;
    }

    public void setgLonSt(double gLonSt) {
        this.gLonSt = gLonSt;
    }

    public double getgLatSt() {
        return gLatSt;
    }

    public void setgLatSt(double gLatSt) {
        this.gLatSt = gLatSt;
    }

    public double getFluxSt() {
        return fluxSt;
    }

    public void setFluxSt(double fluxSt) {
        this.fluxSt = fluxSt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }
}
