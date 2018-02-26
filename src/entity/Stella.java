package entity;

public class Stella {
    private double idStar;
    private String name;
    private double gLonSt;
    private double gLatSt;
    private double fluxSt;
    private String type;
    
    public Stella(double idStar, String name, double gLonSt, double gLatSt, 
            double fluxSt, String type) {
        this.idStar = idStar;
        this.name = name;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
        this.fluxSt = fluxSt;
        this.type = type;
        
    }

    public double getIdStar() {
        return idStar;
    }

    public void setIdStar(double idStar) {
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
}
