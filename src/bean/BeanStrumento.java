package bean;

public class BeanStrumento {
    private String nome;
    private double banda;
    
    public BeanStrumento(String nome, double banda) {
        this.nome = nome;
        this.banda = banda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getBanda() {
        return banda;
    }

    public void setBanda(double banda) {
        this.banda = banda;
    }

    
}
