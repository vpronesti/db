package entity;

public class Posizione {
    private double longitudine;
    private double latitudine;

    public Posizione(double longitudine, double latitudine) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }
    
    @Override
    public boolean equals(Object o) {
        boolean res = false;
        if (o instanceof Posizione) {
            Posizione p = (Posizione) o;
            if (p.getLongitudine() == this.longitudine && 
                    p.getLatitudine() == this.latitudine)
                res = true;
        }
        return res;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }
}
