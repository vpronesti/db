package util;

import bean.BeanIdFilamento;

public class CoppiaStellaFilamento {
    private int idStar;
    private BeanIdFilamento idFil;

    public CoppiaStellaFilamento(int idStar, BeanIdFilamento idFil) {
        this.idStar = idStar;
        this.idFil = idFil;
    }

    public int getIdStar() {
        return idStar;
    }

    public void setIdStar(int idStar) {
        this.idStar = idStar;
    }

    public BeanIdFilamento getIdFil() {
        return idFil;
    }

    public void setIdFil(BeanIdFilamento idFil) {
        this.idFil = idFil;
    }
    
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof CoppiaStellaFilamento) {
            CoppiaStellaFilamento ptr = (CoppiaStellaFilamento) v;
            retVal = ptr.getIdStar() == this.idStar && 
                    ptr.getIdFil().equals(this.idFil);
    }
        return false;
    }
}
