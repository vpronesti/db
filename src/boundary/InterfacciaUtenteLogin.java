package boundary;

import control.GestoreLogin;

/**
 * REQ-1
 */
public class InterfacciaUtenteLogin {
    private String userId;
    private String password;
    private String tipo;
    private boolean logged;

    public InterfacciaUtenteLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public boolean logIn() {
        if ((this.tipo = GestoreLogin.getInstance().login(this.userId, 
                this.password)) != null) {
            this.logged = true;
            return true;
        } else {
            return false;
        }
    }

    public void logOut() {
        this.logged = false;
        this.userId = null;
        this.password = null;
        this.tipo=null;
    }

    public String getUserId() {
        return userId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setUserId(String userId) {
    	this.userId = userId;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
}