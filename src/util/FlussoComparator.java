package util;

import gui.RisultatiRicercaDistanzaStellaFilamentoController.StellaGr;
import java.util.Comparator;

public class FlussoComparator implements Comparator<StellaGr> {
    /**
     * utilizzato nel req.12 dove si richiede che la gui permetta di ordinare
     * le sorgenti in modo crescente in	base al flusso
     * 
     * @param s1
     * @param s2
     * @return 
     */
    @Override
    public int compare(StellaGr s1, StellaGr s2) {
        Double f1 = s1.getFlux_st();
        Double f2 = s2.getFlux_st();
        return f1.compareTo(f2);
    }
}
