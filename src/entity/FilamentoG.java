package entity;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FilamentoG {
    private IntegerProperty idFil;
    private SimpleStringProperty name;
    private FloatProperty totalFlux;
    private FloatProperty meanDens;
    private FloatProperty meanTemp;
    private FloatProperty ellipticity;
    private FloatProperty contrast;
    private SimpleStringProperty satellite;
    private SimpleStringProperty instrument;
    
    public String toString() {
        return "Filamento id: " + this.idFil + "\n\tnome: " + this.name;
    }
    
    public FilamentoG(Filamento f) {
        this.setIdFil(f.getIdFil());
        this.setName(f.getName());
        this.setTotalFlux(f.getTotalFlux());
        this.setMeanDens(f.getMeanDens());
        this.setMeanTemp(f.getMeanTemp());
        this.setEllipticity(f.getEllipticity());
        this.setContrast(f.getContrast());
        this.setSatellite(f.getSatellite());
        this.setInstrument(f.getInstrument());
    }
    
    public FilamentoG(int idFil, String name, float totalFlux, 
            float meanDens, float meanTemp, float ellipticity, 
            float contrast, String satellite, String instrument) {
        this.idFil = new SimpleIntegerProperty();
        this.idFil.set(idFil);
        this.name.set(name);
        this.totalFlux.set(totalFlux);
        this.meanDens.set(meanDens);
        this.meanTemp.set(meanTemp);
        this.ellipticity.set(ellipticity);
        this.contrast.set(contrast);
        this.satellite.set(satellite);
        this.instrument.set(instrument);
    }
    
    public int getIdFil() {
        return idFil.get();
    }

    public void setIdFil(int idFil) {
        this.idFil.set(idFil);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public float getTotalFlux() {
        return totalFlux.get();
    }

    public void setTotalFlux(float totalFlux) {
        this.totalFlux.set(totalFlux);
    }

    public float getMeanDens() {
        return meanDens.get();
    }

    public void setMeanDens(float meanDens) {
        this.meanDens.set(meanDens);
    }

    public float getMeanTemp() {
        return meanTemp.get();
    }

    public void setMeanTemp(float meanTemp) {
        this.meanTemp.set(meanTemp);
    }

    public float getEllipticity() {
        return ellipticity.get();
    }

    public void setEllipticity(float ellipticity) {
        this.ellipticity.set(ellipticity);
    }

    public float getContrast() {
        return contrast.get();
    }

    public void setContrast(float contrast) {
        this.contrast.set(contrast);
    }

    public String getSatellite() {
        return satellite.get();
    }

    public void setSatellite(String satellite) {
        this.satellite.set(satellite);
    }

    public String getInstrument() {
        return instrument.get();
    }

    public void setInstrument(String instrument) {
        this.instrument.set(instrument);
    }
}
