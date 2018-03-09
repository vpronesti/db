package entity;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import java.util.List;

public class Stella {
    private int idStar;
    private String name;
    private float gLonSt;
    private float gLatSt;
    private float fluxSt;
    private String type;
    
    public Stella(int idStar, String name, float gLonSt, float gLatSt, 
            float fluxSt, String type) {
        this.idStar = idStar;
        this.name = name;
        this.gLonSt = gLonSt;
        this.gLatSt = gLatSt;
        this.fluxSt = fluxSt;
        this.type = type;
        
    }
    
    public boolean internoFilamento(List<Contorno> listaPunti) {
        float sum = 0;
        float STL = this.getgLonSt();
        float STB = this.getgLatSt();
        for (int i = 0; i < listaPunti.size() - 1; i++) {
            float CLi = listaPunti.get(i).getgLonCont();
            float CBip1 = listaPunti.get(i + 1).getgLatCont();
            float CBi = listaPunti.get(i).getgLatCont();
            float CLip1 = listaPunti.get(i + 1).getgLonCont();
                    
            float numeratore =  
                    (CLi - STL) * (CBip1 - STB) - (CBi - STB) * (CLip1 - STL);
            float denominatore = 
                    (CLi - STL) * (CLip1 - STL) + (CBi - STB) * (CBip1 - STB);
            float addendo = (float) atan(numeratore / denominatore);
            sum += addendo;
        }
        return abs(sum) >= 0.01;
    }

    public int getIdStar() {
        return idStar;
    }

    public void setIdStar(int idStar) {
        this.idStar = idStar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getgLonSt() {
        return gLonSt;
    }

    public void setgLonSt(float gLonSt) {
        this.gLonSt = gLonSt;
    }

    public float getgLatSt() {
        return gLatSt;
    }

    public void setgLatSt(float gLatSt) {
        this.gLatSt = gLatSt;
    }

    public float getFluxSt() {
        return fluxSt;
    }

    public void setFluxSt(float fluxSt) {
        this.fluxSt = fluxSt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
