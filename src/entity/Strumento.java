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
}
