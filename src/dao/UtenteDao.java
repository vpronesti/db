package dao;

import bean.BeanUtente;
import util.DBAccess;
import entity.*;

import java.sql.*;

public class UtenteDao {
    private static UtenteDao instance;
    
    public static synchronized UtenteDao getInstance() {
        if(instance == null)
            instance = new UtenteDao();
        return instance;
    }
    
    public boolean queryEsistenzaUtente(Connection conn, BeanUtente beanUtente) {
        boolean res = false;
        String sql = "select * from utente where userid = '" 
                + beanUtente.getUserId() + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                res = true;
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public Utente findUtente(Connection conn, String userId, String password) {
        Statement stmt = null;
        Utente utente = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT  nome, cognome, userid, password, email, tipo FROM utente where userid = '"
                    + userId + "' AND password = '" + password + "' ;";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) { // rs is empty

                return null;
            }

            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String user = rs.getString("userid");
            String pword = rs.getString("password");
            String email = rs.getString("email");
            String tipo = rs.getString("tipo");
            if (rs.next()) {
                System.out.println("Exception"); //si deve lanciare un eccezione perchè non possono esserci due con lo stesso username e password
            }

            if (tipo.equals("Registrato")) {
                utente = new UtenteRegistrato(nome, cognome, user, 
                        pword, email);
            } else if (tipo.equals("Amministratore")) {
                utente = new UtenteAmministratore(nome, cognome, user, 
                        pword, email);
            }
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return utente;
    }
    
    /**
     * controllare inserimento dello stesso utente
     * @param conn
     * @param utente
     * @return 
     */
    public boolean inserisciUtente(Connection conn, BeanUtente utente) { 
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
           
            String sql = "insert into utente(nome, cognome, userid, " + 
                    "password, email, tipo) values ('" +  utente.getNome() + 
                    "', '" + utente.getCognome() + 
                    "', '" + utente.getUserId() + 
                    "', '" + utente.getPassword() +
                    "', '" + utente.getEmail() + 
                    "', '" + utente.getTipo() + "')";
            
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return true;
    }
}
