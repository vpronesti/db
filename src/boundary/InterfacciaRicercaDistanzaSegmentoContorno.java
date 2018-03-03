package boundary;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import control.GestoreRicercaDistanzaSegmentoContorno;

public class InterfacciaRicercaDistanzaSegmentoContorno {
    private GestoreRicercaDistanzaSegmentoContorno controllerFilamento;
    private String userId;
    
    public InterfacciaRicercaDistanzaSegmentoContorno(String userId) {
        this.userId = userId;
    }
    
    public BeanRispostaSegmentoContorno ricercaDistanzaSegmentoContorno(BeanRichiestaSegmentoContorno beanRichiesta) {
            controllerFilamento = new GestoreRicercaDistanzaSegmentoContorno(this);
            return controllerFilamento.ricercaDistanzaSegmentoContorno(beanRichiesta);
//        } else {
//            System.out.println("Input non valido"); // aggiungere eccezione
//            return null;
//        }
    }      
}
