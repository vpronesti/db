package bean;

public class BeanStrumento {
    private String nome;
    private String banda;
    
    public BeanStrumento(String nome, String banda) {
        this.nome = nome;
        this.banda = banda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    
}
