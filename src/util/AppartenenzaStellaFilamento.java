package util;

import bean.BeanIdFilamento;
import dao.ContornoDao;
import entity.Contorno;
import entity.Stella;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppartenenzaStellaFilamento implements Runnable {
    private List<Stella> listaStelle;
    private List<BeanIdFilamento> listaIdFilamenti;
    
    private List<CoppiaStellaFilamento> listaAppartenenza = new ArrayList<>();

    public AppartenenzaStellaFilamento(List<Stella> listaStelle, 
            List<BeanIdFilamento> listaIdFilamenti) {
        this.listaStelle = listaStelle;
        this.listaIdFilamenti = listaIdFilamenti;
    }    
    
    @Override
    public void run() {
        Connection conn = DBAccess.getInstance().getConnection();
        ContornoDao contornoDao = ContornoDao.getInstance();
        Iterator<BeanIdFilamento> i = getListaIdFilamenti().iterator();
        while (i.hasNext()) {
            BeanIdFilamento idFil = i.next();
            List<Contorno> listaContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
            Iterator<Stella> iS = getListaStelle().iterator();
            while (iS.hasNext()) {
                Stella s = iS.next();
                if (s.internoFilamento(listaContorno)) {
                    CoppiaStellaFilamento c = new CoppiaStellaFilamento(s.getIdStar(), idFil);
                    getListaAppartenenza().add(c);
                }
            }
        }
        DBAccess.getInstance().closeConnection(conn);
    }

    public List<Stella> getListaStelle() {
        return listaStelle;
    }

    public void setListaStelle(List<Stella> listaStelle) {
        this.listaStelle = listaStelle;
    }

    public List<BeanIdFilamento> getListaIdFilamenti() {
        return listaIdFilamenti;
    }

    public void setListaIdFilamenti(List<BeanIdFilamento> listaIdFilamenti) {
        this.listaIdFilamenti = listaIdFilamenti;
    }

    public List<CoppiaStellaFilamento> getListaAppartenenza() {
        return listaAppartenenza;
    }

    public void setListaAppartenenza(List<CoppiaStellaFilamento> listaAppartenenza) {
        this.listaAppartenenza = listaAppartenenza;
    }
}
