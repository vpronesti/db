package bean;

import entity.TipoFigura;
import static java.lang.Math.abs;

public class BeanRichiestaFilamentiRegione {
    private double longCentroide;
    private double latiCentroide;
    private double dimensione;
    private TipoFigura tipoFigura;
    
    public BeanRichiestaFilamentiRegione(double longCentroide, 
            double latiCentroide, double dimensione, TipoFigura tipoFigura) {
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = dimensione;
        this.tipoFigura = tipoFigura;
    }

    public double getLongCentroide() {
        return longCentroide;
    }

    public void setLongCentroide(double longCentroide) {
        this.longCentroide = longCentroide;
    }

    public double getLatiCentroide() {
        return latiCentroide;
    }

    public void setLatiCentroide(double latiCentroide) {
        this.latiCentroide = latiCentroide;
    }

    public double getDimensione() {
        return dimensione;
    }

    public void setDimensione(double dimensione) {
        this.dimensione = dimensione;
    }

    public TipoFigura getTipoFigura() {
        return tipoFigura;
    }

    public void setTipoFigura(TipoFigura tipoFigura) {
        this.tipoFigura = tipoFigura;
    }
            
}
