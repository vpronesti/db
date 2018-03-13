package entity;

import bean.BeanInformazioniFilamento;
import bean.BeanSatellite;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SatelliteDao;
import dao.SegmentoDao;
import dao.StrumentoDao;
import java.sql.Connection;
import util.DBAccess;

public class Filamento {
    private int idFil;
    private String name;
    private float totalFlux;
    private float meanDens;
    private float meanTemp;
    private float ellipticity;
    private float contrast;
    private String satellite;
    private String instrument;
    
    public String toString() {
        return "Filamento id: " + this.idFil + "\n\tnome: " + this.name;
    }
    
    /**
     * si puo anche togliere
     * @param idFil 
     */
    public Filamento(int idFil, String satellite) {
        this.idFil = idFil;
        this.satellite = satellite;
    }
    
    public Filamento(int idFil, String name, float totalFlux, 
            float meanDens, float meanTemp, float ellipticity, 
            float contrast, String satellite, String instrument) {
        this.idFil = idFil;
        this.name = name;
        this.totalFlux = totalFlux;
        this.meanDens = meanDens;
        this.meanTemp = meanTemp;
        this.ellipticity = ellipticity;
        this.contrast = contrast;
        
        this.satellite = satellite;
        this.instrument = instrument;
    }

    private boolean controllaSatellite(String satellite) {
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        BeanSatellite beanSatellite = new BeanSatellite(satellite);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res = satelliteDao.queryEsistenzaSatellite(conn, beanSatellite);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    private boolean controllaStrumento(String strumento) {
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res = strumentoDao.queryEsistenzaStrumento(conn, strumento);
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTotalFlux() {
        return totalFlux;
    }

    public void setTotalFlux(float totalFlux) {
        this.totalFlux = totalFlux;
    }

    public float getMeanDens() {
        return meanDens;
    }

    public void setMeanDens(float meanDens) {
        this.meanDens = meanDens;
    }

    public float getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(float meanTemp) {
        this.meanTemp = meanTemp;
    }

    public float getEllipticity() {
        return ellipticity;
    }

    public void setEllipticity(float ellipticity) {
        this.ellipticity = ellipticity;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
