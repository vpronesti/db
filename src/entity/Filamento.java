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
    private String nome;
    private double flussoTotale;
    private double densMedia;
    private double tempMedia;
    private double ellitticita;
    private double contrasto;
    private String satellite;
    private String strumento;
    
    public String toString() {
        return "Filamento id: " + this.idFil + "\n\tnome: " + this.nome;
    }
    
    public Filamento(int idFil, String nome, double flussoTotale, 
            double densMedia, double tempMedia, double ellitticita, 
            double contrasto, String satellite, String strumento) {
        this.idFil = idFil;
        this.nome = nome;
        this.flussoTotale = flussoTotale;
        this.densMedia = densMedia;
        this.tempMedia = tempMedia;
        this.ellitticita = ellitticita;
        this.contrasto = contrasto;
        this.satellite = satellite;
        this.strumento = strumento;
    }
    
    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getFlussoTotale() {
        return flussoTotale;
    }

    public void setFlussoTotale(double flussoTotale) {
        this.flussoTotale = flussoTotale;
    }

    public double getDensMedia() {
        return densMedia;
    }

    public void setDensMedia(double densMedia) {
        this.densMedia = densMedia;
    }

    public double getTempMedia() {
        return tempMedia;
    }

    public void setTempMedia(double tempMedia) {
        this.tempMedia = tempMedia;
    }

    public double getEllitticita() {
        return ellitticita;
    }

    public void setEllitticita(double ellitticita) {
        this.ellitticita = ellitticita;
    }

    public double getContrasto() {
        return contrasto;
    }

    public void setContrasto(double contrasto) {
        this.contrasto = contrasto;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getStrumento() {
        return strumento;
    }

    public void setStrumento(String strumento) {
        this.strumento = strumento;
    }
}
