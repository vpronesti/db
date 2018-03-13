package bean;

public class BeanStrumento {
    private String nome;
    private float banda;
    
    public BeanStrumento(String nome, float banda) {
        this.nome = nome;
        this.banda = banda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getBanda() {
        return banda;
    }

    public void setBanda(float banda) {
        this.banda = banda;
    }

    
}
