package util;

import gui.RisultatiRicercaDistanzaStellaFilamentoController.StellaGr;
import java.util.Comparator;

public class FlussoComparator implements Comparator<StellaGr> {
    @Override
    public int compare(StellaGr s1, StellaGr s2) {
        Float f1 = s1.getFlux_st();
        Float f2 = s2.getFlux_st();
        return f1.compareTo(f2);
    }
}
