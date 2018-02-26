package entity;

import dao.StrumentoDao;
import java.sql.Connection;
import java.util.List;
import util.DBAccess;

public class Strumento {
    private String nome;
    private List<Double> bande;
    
    public Strumento(String nome) {
        this.nome = nome;   
    }
    
    public Strumento(String nome, List<Double> bande) {
        this.nome = nome;
        this.bande = bande;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Double> getBande() {
        return bande;
    }

    public void setBande(List<Double> bande) {
        this.bande = bande;
    }
    
    public boolean inserisciNomeStrumento() {
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res;
        if (!strumentoDao.queryEsistenzaStrumento(conn, this.nome)){
            strumentoDao.inserisciNomeStrumento(conn, this.nome);
            res = true;
        } else {
            res = false;
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }
    
    public boolean inserisciBandaStrumento() {
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res = true;
        if (strumentoDao.queryEsistenzaStrumento(conn, this.nome)) {
            strumentoDao.inserisciBandaStrumento(conn, this.nome, this.bande.get(0));
        } else {
            res = false;
        }      
        return res;
    }
}
