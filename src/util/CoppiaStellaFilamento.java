package util;

import bean.BeanIdFilamento;
import bean.BeanIdStella;

public class CoppiaStellaFilamento {
    private BeanIdStella idStella;
    private BeanIdFilamento idFil;

    public CoppiaStellaFilamento(BeanIdStella idStella, BeanIdFilamento idFil) {
        this.idStella = idStella;
        this.idFil = idFil;
    }
    
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof CoppiaStellaFilamento) {
            CoppiaStellaFilamento ptr = (CoppiaStellaFilamento) v;
            retVal = ptr.getIdStella().equals(this.getIdStella()) && 
                    ptr.getIdFil().equals(this.getIdFil());
    }
        return false;
    }

    public BeanIdStella getIdStella() {
        return idStella;
    }

    public void setIdStar(BeanIdStella idStella) {
        this.idStella = idStella;
    }

    public BeanIdFilamento getIdFil() {
        return idFil;
    }

    public void setIdFil(BeanIdFilamento idFil) {
        this.idFil = idFil;
    }
}
