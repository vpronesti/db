package bean;

public class BeanIdFilamento {
    private int idFil;
    private String satellite;

    public BeanIdFilamento(int idFil, String satellite) {
        this.idFil = idFil;
        this.satellite = satellite;
    }

    public int getIdFil() {
        return idFil;
    }

    public void setIdFil(int idFil) {
        this.idFil = idFil;
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

        if (v instanceof BeanIdFilamento){
            BeanIdFilamento ptr = (BeanIdFilamento) v;
            retVal = ptr.getIdFil() == this.idFil && ptr.getSatellite().equals(this.satellite);
        }

        return retVal;
    }
    
}
