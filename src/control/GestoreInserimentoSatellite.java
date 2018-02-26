package control;

import bean.BeanSatellite;
import boundary.InterfacciaInserimentoSatellite;
import entity.Satellite;

public class GestoreInserimentoSatellite {
    private InterfacciaInserimentoSatellite amministratore;
    
    public GestoreInserimentoSatellite(InterfacciaInserimentoSatellite amministratore) {
        this.amministratore = amministratore;
    }

    public boolean inserisciSatellite(BeanSatellite beanSatellite) {
        Satellite satellite = new Satellite(beanSatellite.getNome(), 
                beanSatellite.getPrimaOsservazione(), 
                beanSatellite.getTermineOperazione(), 
                beanSatellite.getAgenzia());
        return satellite.inserisciSatellite();
    }    
}
