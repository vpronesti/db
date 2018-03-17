package bean;

public class BeanUtente {
    private String nome;
    private String cognome;
    private String userId;
    private String password;
    private String email;
    private String tipo;

    public BeanUtente(String userId) {
        this.userId = userId;
    }
    
    public BeanUtente(String nome, String cognome, String userId, 
            String password, String email, String tipo) {
        this.nome = nome;
        this.cognome = cognome;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.tipo = tipo;
        
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
