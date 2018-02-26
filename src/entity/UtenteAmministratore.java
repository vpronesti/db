package entity;

/*
 * Nota: si tenga conto che un amministratore può aggiornare i dati della 
 * applicazione mentre altri utenti la stanno utilizzando.	
*/
public class UtenteAmministratore extends Utente {
    public UtenteAmministratore(String nome, String cognome, 
            String userId, String password, String email) {
        super(nome, cognome, userId, password, email, "Amministratore");
    }
    

    
    /*
     * Importare un nuovo file dei dati scientifici  un satellite
     */
    public void importaFileDatiSatellite() {
        
    }
    /*
     * Registrare un nuovo utente
     */
    public void registraUtente() {
        
    }
    
    /*
     * Inserire i dati di un satellite
     */
    public void inserisciDatiSatellite() {
        
    }
    
    /*
     * Inserire i dati degli strumenti con le relative bande
     */
    public void inserisciDatiStrumento() {
        
    }
}
