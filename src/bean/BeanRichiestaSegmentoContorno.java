package bean;

public class BeanRichiestaSegmentoContorno {
    private int idFil;
    private int idSeg;
    
    public BeanRichiestaSegmentoContorno(int idFil, int idSeg) {
        this.idFil = idFil;
        this.idSeg = idSeg;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public int getIdSeg() {
        return idSeg;
    }

    public void setIdSeg(int idSeg) {
        this.idSeg = idSeg;
    }
}
