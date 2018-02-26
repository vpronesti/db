package entity;

public class Segmento {
    private double idFil;
    private double idBranch;
    private String type;
    private double gLonBr;
    private double gLatBr;
    private int n;
    private double flux;
    
    public Segmento(double idFil, double idBranch, String type, double gLonBr, 
            double gLatBr, int n, double flux) {
        this.idFil = idFil;
        this.idBranch = idBranch;
        this.type = type;
        this.gLonBr = gLonBr;
        this.gLatBr = gLatBr;
        this.n = n;
        this.flux = flux;
    }

    public double getIdFil() {
        return idFil;
    }

    public void setIdFil(double idFil) {
        this.idFil = idFil;
    }

    public double getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(double idBranch) {
        this.idBranch = idBranch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getgLonBr() {
        return gLonBr;
    }

    public void setgLonBr(double gLonBr) {
        this.gLonBr = gLonBr;
    }

    public double getgLatBr() {
        return gLatBr;
    }

    public void setgLatBr(double gLatBr) {
        this.gLatBr = gLatBr;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getFlux() {
        return flux;
    }

    public void setFlux(double flux) {
        this.flux = flux;
    }
    
}
