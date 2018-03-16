package util;

import gui.RisultatiRicercaDistanzaStellaFilamentoController.StellaGr;
import java.util.Comparator;

public class DistanzaComparator implements Comparator<StellaGr> {
    @Override
    public int compare(StellaGr s1, StellaGr s2) {
        Double d1 = s1.getDistanza();
        Double d2 = s2.getDistanza();
//        if (d1 > d2)
//            return 1;
//        if (d1 < d2)
//            return -1;
//        return 0;
        return d1.compareTo(d2);
    }
}
