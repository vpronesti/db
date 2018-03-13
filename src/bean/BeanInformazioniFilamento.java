package bean;

public class BeanInformazioniFilamento {
    private int idFil;
    private String satellite;
    private float gLonCentroide;
    private float gLatCentroide;
    private float maxGLonContorno;
    private float minGLonContorno;
    private float maxGLatContorno;
    private float minGLatContorno;
    private int numSegmenti;
    
    public BeanInformazioniFilamento(int idFil, String satellite) {
        this.idFil = idFil;
        this.satellite = satellite;
    }
    
    public BeanInformazioniFilamento(int idFil, String satellite, 
            float gLonCentroide, float gLatCentroide, float maxGLonContorno, 
            float minGLonContorno, float maxGLatContorno, 
            float minGLatContorno, int numSegmenti) {
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

    public float getgLonCentroide() {
        return gLonCentroide;
    }

    public void setgLonCentroide(float gLonCentroide) {
        this.gLonCentroide = gLonCentroide;
    }

    public float getgLatCentroide() {
        return gLatCentroide;
    }

    public void setgLatCentroide(float gLatCentroide) {
        this.gLatCentroide = gLatCentroide;
    }

    public float getMaxGLonContorno() {
        return maxGLonContorno;
    }

    public void setMaxGLonContorno(float maxGLonContorno) {
        this.maxGLonContorno = maxGLonContorno;
    }

    public float getMinGLonContorno() {
        return minGLonContorno;
    }

    public void setMinGLonContorno(float minGLonContorno) {
        this.minGLonContorno = minGLonContorno;
    }

    public float getMaxGLatContorno() {
        return maxGLatContorno;
    }

    public void setMaxGLatContorno(float maxGLatContorno) {
        this.maxGLatContorno = maxGLatContorno;
    }

    public float getMinGLatContorno() {
        return minGLatContorno;
    }

    public void setMinGLatContorno(float minGLatContorno) {
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


}
