package bean;

public class BeanInformazioniFilamento {
    private int idFil;
    private String nome;
    private String satellite;
    private double gLonCentroide;
    private double gLatCentroide;
    private double maxGLonContorno;
    private double minGLonContorno;
    private double maxGLatContorno;
    private double minGLatContorno;
    private int numSegmenti;
    private boolean ricercaId;

    public BeanInformazioniFilamento(String nome, String satellite) {
        this.nome = nome;
        this.satellite = satellite;
        this.ricercaId = false;
    }
    
    public BeanInformazioniFilamento(int idFil, String satellite) {
        this.idFil = idFil;
        this.satellite = satellite;
        this.ricercaId = true;
    }
    
    public BeanInformazioniFilamento(int idFil, String satellite, 
            double gLonCentroide, double gLatCentroide, double maxGLonContorno, 
            double minGLonContorno, double maxGLatContorno, 
            double minGLatContorno, int numSegmenti) {
        this.idFil = idFil;
        this.satellite = satellite;
        this.gLonCentroide = gLonCentroide;
        this.gLatCentroide = gLatCentroide;
        this.maxGLonContorno = maxGLonContorno;
        this.minGLonContorno = minGLonContorno;
        this.maxGLatContorno = maxGLatContorno;
        this.minGLatContorno = minGLatContorno;
        this.numSegmenti = numSegmenti;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public double getgLonCentroide() {
        return gLonCentroide;
    }

    public void setgLonCentroide(double gLonCentroide) {
        this.gLonCentroide = gLonCentroide;
    }

    public double getgLatCentroide() {
        return gLatCentroide;
    }

    public void setgLatCentroide(double gLatCentroide) {
        this.gLatCentroide = gLatCentroide;
    }

    public double getMaxGLonContorno() {
        return maxGLonContorno;
    }

    public void setMaxGLonContorno(double maxGLonContorno) {
        this.maxGLonContorno = maxGLonContorno;
    }

    public double getMinGLonContorno() {
        return minGLonContorno;
    }

    public void setMinGLonContorno(double minGLonContorno) {
        this.minGLonContorno = minGLonContorno;
    }

    public double getMaxGLatContorno() {
        return maxGLatContorno;
    }

    public void setMaxGLatContorno(double maxGLatContorno) {
        this.maxGLatContorno = maxGLatContorno;
    }

    public double getMinGLatContorno() {
        return minGLatContorno;
    }

    public void setMinGLatContorno(double minGLatContorno) {
        this.minGLatContorno = minGLatContorno;
    }

    public int getNumSegmenti() {
        return numSegmenti;
    }

    public void setNumSegmenti(int numSegmenti) {
        this.numSegmenti = numSegmenti;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isRicercaId() {
        return ricercaId;
    }

    public void setRicercaId(boolean ricercaId) {
        this.ricercaId = ricercaId;
    }


}
