package control;

import bean.BeanStrumento;
import boundary.InterfacciaInserimentoBandaStrumento;
import entity.Strumento;
import java.util.ArrayList;
import java.util.List;

public class GestoreInserimentoBandaStrumento {
    private InterfacciaInserimentoBandaStrumento amministratore;
    
    public GestoreInserimentoBandaStrumento(InterfacciaInserimentoBandaStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean inserisciBandaStrumento(BeanStrumento beanStrumento) {
        double banda;
        try {  
            banda = Double.parseDouble(beanStrumento.getBanda());  
        } catch (NumberFormatException nfe) {  
            return false;  
        }
        // forse serve la lista per il recupero di uno strumento con piu 
        // bande ma all'inserimento e' inutile
        List<Double> bande = new ArrayList<>();
        bande.add(banda);
        Strumento strumento = new Strumento(beanStrumento.getNome(), bande);
        return strumento.inserisciBandaStrumento();
    }
}
