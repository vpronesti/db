package util;

import dao.FilamentoDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess {
    public static final String DBNAME = "progDB";
    public static final String DBOWNER = "postgres";
    public static final String DBPASSW = "admin";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/progDB";
    private static DBAccess instance;
    
    // nei test, per verificare che un'azione si consentita solo 
    // ad un determinato tipo di utente vengono utilizzati 
    // gli user id degli utenti qui specificati
    public static final String AMMINISTRATORE = "a";
    public static final String REGISTRATO = "z";
    public static final String NONREGISTRATO = "b"; 
    
    public static final String UNIQUE_VIOLATION = "23505";
    public static final String FOREIGN_KEY_VIOLATION = "23503";
    
    public static synchronized DBAccess getInstance() {
        if(instance == null)
            instance = new DBAccess();
        return instance;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, DBOWNER, DBPASSW);
        } catch (ClassNotFoundException e) {
            System.err.println("Impossibile caricare i driver per il db");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Impossibile stabilire la connessione");
            e.printStackTrace();
        }
        return conn;
    }
    
    public void disableAutoCommit(Connection conn) {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Impossibile disabilitare l'autocommit");
            e.printStackTrace();
        }
    }
    
    public void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Impossibilie eseguire il commit");
            e.printStackTrace();
        }
    }
    
    public void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            System.err.println("Errore in rollback");
            e.printStackTrace();
        }
    }
    
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore nella chiusura della connessione");
            e.printStackTrace();
        }
    }
}
