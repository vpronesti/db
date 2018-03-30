package entity;

public class Segmento {
    private int idFil;
    private int idSegmento;
    private String tipo;
    private double gLonSe;
    private double gLatSe;
    private int n;
    private double flusso;
    private String satellite;
    
    public String toString() {
        return "idFil: " + this.getIdFil() + "idSegmento: " + 
                this.getIdSegmento() + "idLon: " + this.getgLonSe() + "idLat: " + this.getgLatSe();
    }
    
    public Segmento(int idFil, String satellite, int idSegmento, String tipo, double gLonSe, 
            double gLatSe, int n, double flusso) {
        this.idFil = idFil;
        this.satellite = satellite;
        this.idSegmento = idSegmento;
        this.tipo = tipo;
        this.gLonSe = gLonSe;
        this.gLatSe = gLatSe;
        this.n = n;
        this.flusso = flusso;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public int getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(int idSegmento) {
        this.idSegmento = idSegmento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getgLonSe() {
        return gLonSe;
    }

    public void setgLonSe(double gLonSe) {
        this.gLonSe = gLonSe;
    }

    public double getgLatSe() {
        return gLatSe;
    }

    public void setgLatSe(double gLatSe) {
        this.gLatSe = gLatSe;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getFlusso() {
        return flusso;
    }

    public void setFlusso(double flusso) {
        this.flusso = flusso;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }    
}
