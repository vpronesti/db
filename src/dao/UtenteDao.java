package dao;

import bean.BeanUtente;
import util.DBAccess;
import entity.*;

import java.sql.*;

public class UtenteDao {
    private static UtenteDao instance;
    
    private static final String PASS = DBAccess.DBPASSW;
    private static final String USER = DBAccess.DBOWNER;
    private static final String DB_URL = DBAccess.DB_URL;
    
    public static synchronized UtenteDao getInstance() {
        if(instance == null)
            instance = new UtenteDao();
        return instance;
    } 
    
    public Utente findUtente(String userId, String password) {
        Statement stmt = null;
        Connection conn = null;
        Utente utente = null;
        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
                System.out.println("Exception"); //si deve lanciare un eccezzione perchè non possono esserci due con lo stesso username e password
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
            conn.close();
        } catch (SQLException se) {
            // Errore durante l'apertura della connessione

            se.printStackTrace();
        } catch (Exception e) {
            // Errore nel loading del driver

            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return utente;
    }
    
    public boolean inserisciUtente(BeanUtente utente) { 
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            // Errore nel loading del driver

            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return true;
    }
}
