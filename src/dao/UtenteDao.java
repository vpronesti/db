package dao;

import bean.BeanUtente;
import entity.*;

import java.sql.*;
import static util.DBAccess.CHECK_VIOLATION;
import static util.DBAccess.UNIQUE_VIOLATION;

public class UtenteDao {
    private static UtenteDao instance;
    
    public static synchronized UtenteDao getInstance() {
        if(instance == null)
            instance = new UtenteDao();
        return instance;
    }
    
    public boolean queryEsistenzaAmministratore(Connection conn, BeanUtente beanUtente) {
        boolean res = false;
        String sql = "select * from utente where userid = '" + 
                beanUtente.getUserId() + "' and tipo = 'Amministratore'";
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
    
    /**
     * controlla se l'utente con userId e password dati in input esiste, 
     * se esiste restituisce il tipo di utente
     * @param conn 
     * @param userId
     * @param password
     * @return Utente che puo' essere registrato o 
     * amministratore, null se non esiste
     */
    public Utente queryUtente(Connection conn, String userId, String password) {
        Statement stmt = null;
        Utente utente = null;
        try {
            stmt = conn.createStatement();
            String sql = "select  nome, cognome, userid, password, email, tipo from utente where userid = '"
                    + userId + "' and password = '" + password + "' ;";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                return null;
            }

            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String user = rs.getString("userid");
            String pword = rs.getString("password");
            String email = rs.getString("email");
            String tipo = rs.getString("tipo");

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
     * utilizzato nel REQ-2
     * @param conn
     * @param utente
     * @return 
     */
    public boolean inserisciUtente(Connection conn, BeanUtente utente) { 
        Statement stmt = null;
        boolean res = true;
        try {
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }   
            String sql = "insert into utente(nome, cognome, userid, " + 
                    "password, email, tipo) values ('" +  utente.getNome() + 
                    "', '" + utente.getCognome() + 
                    "', '" + utente.getUserId() + 
                    "', '" + utente.getPassword() +
                    "', '" + utente.getEmail() + 
                    "', '" + utente.getTipo() + "')";
            
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                    System.out.println("violazione unique in inserimento utente");
                    res = false;
                } else if (e.getSQLState().equals(CHECK_VIOLATION)) {
                    System.out.println("violazione tipo utente in inserimento utente");
                    res = false;
                } else {
                    e.printStackTrace();
                }
            }
        try {
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return res;
    }
}
