package bean;

import entity.TipoFigura;
import static java.lang.Math.abs;

public class BeanRichiestaFilamentiRegione {
    private float longCentroide;
    private float latiCentroide;
    private float dimensione;
    private TipoFigura tipoFigura;
    
    public BeanRichiestaFilamentiRegione(float longCentroide, 
            float latiCentroide, float dimensione, TipoFigura tipoFigura) {
        this.longCentroide = longCentroide;
        this.latiCentroide = latiCentroide;
        this.dimensione = abs(dimensione);
        this.tipoFigura = tipoFigura;
    }

    public float getLongCentroide() {
        return longCentroide;
    }

    public void setLongCentroide(float longCentroide) {
        this.longCentroide = longCentroide;
    }

    public float getLatiCentroide() {
        return latiCentroide;
    }

    public void setLatiCentroide(float latiCentroide) {
        this.latiCentroide = latiCentroide;
    }

    public float getDimensione() {
        return dimensione;
    }

    public void setDimensione(float dimensione) {
        this.dimensione = abs(dimensione);
    }

    public TipoFigura getTipoFigura() {
        return tipoFigura;
    }

    public void setTipoFigura(TipoFigura tipoFigura) {
        this.tipoFigura = tipoFigura;
    }
            
}
