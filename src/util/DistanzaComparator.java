package util;

import gui.RisultatiRicercaDistanzaStellaFilamentoController.StellaGr;
import java.util.Comparator;

public class DistanzaComparator implements Comparator<StellaGr> {
    /**
     * utilizzato nel req.12 dove si richiede che la gui permetta di ordinare
     * le sorgenti in modo crescente in	base alla distanza
     * 
     * @param s1
     * @param s2
     * @return 
     */
    @Override
    public int compare(StellaGr s1, StellaGr s2) {
        Double d1 = s1.getDistanza();
        Double d2 = s2.getDistanza();
        return d1.compareTo(d2);
    }
}
