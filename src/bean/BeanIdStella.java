package bean;

public class BeanIdStella {
    private int idStella;
    private String satellite;
    
    public String toString() {
        return "idStella: " + idStella + ", satellite = " + satellite;
    }

    public BeanIdStella(int idStella, String satellite) {
        this.idStella = idStella;
        this.satellite = satellite;
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
    
    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof BeanIdStella){
            BeanIdStella ptr = (BeanIdStella) v;
            retVal = ptr.getIdStella() == this.idStella && ptr.getSatellite().equals(this.satellite);
        }

        return retVal;
    }
    
}
