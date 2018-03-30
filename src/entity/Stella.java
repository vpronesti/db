package entity;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import java.util.List;

public class Stella {
    private int idStella;
    private String satellite;
    private String nome;
    private double gLonSt;
    private double gLatSt;
    private double flussoSt;
    private String tipo;

    public Stella(int idStella, String satellite, String nome, 
            double gLonSt, double gLatSt, double flussoSt, String tipo) {
        this.idStella = idStella;
        this.satellite = satellite;
        this.nome = nome;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
        this.flussoSt = flussoSt;
        this.tipo = tipo;
    }

    public Stella(int idStella, String satellite, double gLonSt, double gLatSt) {
        this.idStella = idStella;
        this.satellite = satellite;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
    }
    
    /**
     * data la lista di punti che forma il contorno di un filamento controlla 
     * se una stella e' interna o esterna
     * @param listaPunti
     * @return 
     */
    public boolean internoFilamento(List<Contorno> listaPunti) {
        double sum = 0;
        double STL = this.getgLonSt();
        double STB = this.getgLatSt();
        for (int i = 0; i < listaPunti.size() - 1; i++) {
            double CLi = listaPunti.get(i).getgLonCont();
            double CBip1 = listaPunti.get(i + 1).getgLatCont();
            double CBi = listaPunti.get(i).getgLatCont();
            double CLip1 = listaPunti.get(i + 1).getgLonCont();
                    
            double numeratore =  
                    (CLi - STL) * (CBip1 - STB) - (CBi - STB) * (CLip1 - STL);
            double denominatore = 
                    (CLi - STL) * (CLip1 - STL) + (CBi - STB) * (CBip1 - STB);
            double addendo = Math.toRadians(atan(numeratore / denominatore));
            sum += addendo;
        }
        return abs(sum) >= 0.01;
    }

    public int getIdStella() {
        return idStella;
    }

    public void setIdStella(int idStella) {
        this.idStella = idStella;
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

    public double getgLonSt() {
        return gLonSt;
    }

    public void setgLonSt(double gLonSt) {
        this.gLonSt = gLonSt;
    }

    public double getgLatSt() {
        return gLatSt;
    }

    public void setgLatSt(double gLatSt) {
        this.gLatSt = gLatSt;
    }

    public double getFlussoSt() {
        return flussoSt;
    }

    public void setFlussoSt(double flussoSt) {
        this.flussoSt = flussoSt;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
