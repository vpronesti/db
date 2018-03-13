package entity;

public class Segmento {
    private int idFil;
    private int idBranch;
    private String type;
    private float gLonBr;
    private float gLatBr;
    private int n;
    private float flux;
    private String satellite;
    
    public Segmento() {
        
    }
    
    public String toString() {
        return "idFil: " + this.idFil + "idBranch: " + 
                this.idBranch + "idLon: " + this.gLonBr + "idLat: " + this.gLatBr;
    }
    
    public Segmento(int idFil, String satellite, int idBranch, String type, float gLonBr, 
            float gLatBr, int n, float flux) {
        this.idFil = idFil;
        this.satellite = satellite;
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

    public float getgLonBr() {
        return gLonBr;
    }

    public void setgLonBr(float gLonBr) {
        this.gLonBr = gLonBr;
    }

    public float getgLatBr() {
        return gLatBr;
    }

    public void setgLatBr(float gLatBr) {
        this.gLatBr = gLatBr;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public float getFlux() {
        return flux;
    }

    public void setFlux(float flux) {
        this.flux = flux;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }
    
}
