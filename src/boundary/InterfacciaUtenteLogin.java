package boundary;

import control.GestoreLogin;

public class InterfacciaUtenteLogin {
    private String userId;
    private String password;
    private String tipo;
    private boolean logged;

    public InterfacciaUtenteLogin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
    
    public InterfacciaUtenteLogin() {
        
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
//    //TODO ANDYSNAKE UC [ID10]
//    public InterfVerificaDisponibilita getInterfacciaVerificaDisponibilita() throws Exception {
//        //TODO CI CONFORMIAMO TUTTI A DARE ALLA MASTER BOUNDARY 1! dipendenza alle boundary personali..?
//        if (this.isLog == false || !type.equals("Segretario")) {
//            System.err.println("errore di credenziali...");
//            throw new Exception();
//        }
//        return new InterfVerificaDisponibilita();
//    }
//    //TODO ANDYSNAKE UC [ID11]
//    public InterfacciaVisualizzaPrenAttTot getInterfacciaVisPrenAttTot() throws Exception {
//        if (this.isLog == false || !type.equals("Segretario")) {
//            System.err.println("errore di credenziali...");
//            throw new Exception();
//        }
//        return new InterfacciaVisualizzaPrenAttTot();
//    }
//    public InterfacciaPrenotazioneConferenzeProfessore getInterfacciaProf() throws Exception {
//        //todo controllo che non dovrebbe essere necessario
//        if (this.isLog == false || !type.equals("Professore")) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaPrenotazioneConferenzeProfessore(this.username);
//    }
//    public InterfacciaUtenteVisualizzaPrenotazioniAttive getInterfacciaUtenteViusualizzaPrenotazioniAttive() throws Exception {
//        if (this.isLog == false  ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaUtenteVisualizzaPrenotazioniAttive(this.username);
//    }
//    public InterfacciaSegreteriaAperturaAnnoAccademico getInterfacciaSegreteriaAperturaAnnoAccademico() throws Exception {
//        if (this.isLog == false || !type.equals("Segretario") ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaSegreteriaAperturaAnnoAccademico();
//    }
//    public InterfacciaSegreteriaModificaAnnoAccademico getInterfacciaSegreteriaModificaAnnoAccademico() throws Exception {
//        if (this.isLog == false || !type.equals("Segretario") ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaSegreteriaModificaAnnoAccademico();
//    }
//    
//    public InterfacciaProfessoreVisualizzazioneStorico getInterfacciaProfessoreVisualizzazioneStorico() throws Exception {
//        if (this.isLog == false || !type.equals("Professore") ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaProfessoreVisualizzazioneStorico(this.username);
//    }
//    
//    public InterfacciaSegretarioVisualizzazioneStorico getInterfacciaSegretarioVisualizzazioneStorico() throws Exception {
//        if (this.isLog == false || !type.equals("Segretario") ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaSegretarioVisualizzazioneStorico(this.username);
//    }
//    
//    public InterfacciaSegretarioDefinizioneSessione getInterfacciaSegretarioDefinizioneSessione() throws Exception {
//        if (this.isLog == false || !type.equals("Segretario") ) {
//            System.err.println("error");
//            throw new Exception();
//        }
//        return new InterfacciaSegretarioDefinizioneSessione(this.username);
//    }

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