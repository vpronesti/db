package control;

import boundary.InterfacciaInserimentoNomeStrumento;
import entity.Strumento;

public class GestoreInserimentoNomeStrumento {
        private InterfacciaInserimentoNomeStrumento amministratore;
    
    public GestoreInserimentoNomeStrumento(InterfacciaInserimentoNomeStrumento amministratore) {
        this.amministratore = amministratore;
    }
    
    public boolean inserisciNomeStrumento(String nomeStrumento) {
        Strumento strumento = new Strumento(nomeStrumento);
        return strumento.inserisciNomeStrumento();
    }
}
