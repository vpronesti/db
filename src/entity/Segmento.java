package entity;

public class Segmento {
    private int idFil;
    private int idBranch;
    private String type;
    private double gLonBr;
    private double gLatBr;
    private int n;
    private double flux;
    
    public Segmento() {
        
    }
    
    public String toString() {
        return "idFil: " + this.idFil + "idBranch: " + 
                this.idBranch + "idLon: " + this.gLonBr + "idLat: " + this.gLatBr;
    }
    
    public Segmento(int idFil, int idBranch, String type, double gLonBr, 
            double gLatBr, int n, double flux) {
        this.idFil = idFil;
        this.idBranch = idBranch;
        this.type = type;
        this.gLonBr = gLonBr;
        this.gLatBr = gLatBr;
        this.n = n;
        this.flux = flux;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public int getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(int idBranch) {
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
