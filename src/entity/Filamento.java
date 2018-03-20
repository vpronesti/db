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
    private double totalFlux;
    private double meanDens;
    private double meanTemp;
    private double ellipticity;
    private double contrast;
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
    
    public Filamento(int idFil, String name, double totalFlux, 
            double meanDens, double meanTemp, double ellipticity, 
            double contrast, String satellite, String instrument) {
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

    public double getTotalFlux() {
        return totalFlux;
    }

    public void setTotalFlux(double totalFlux) {
        this.totalFlux = totalFlux;
    }

    public double getMeanDens() {
        return meanDens;
    }

    public void setMeanDens(double meanDens) {
        this.meanDens = meanDens;
    }

    public double getMeanTemp() {
        return meanTemp;
    }

    public void setMeanTemp(double meanTemp) {
        this.meanTemp = meanTemp;
    }

    public double getEllipticity() {
        return ellipticity;
    }

    public void setEllipticity(double ellipticity) {
        this.ellipticity = ellipticity;
    }

    public double getContrast() {
        return contrast;
    }

    public void setContrast(double contrast) {
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
