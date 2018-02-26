package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.DBAccess;

public class StrumentoDao {
    private static StrumentoDao instance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized StrumentoDao getInstance() {
        if(instance == null)
            instance = new StrumentoDao();
        return instance;
    }
    
    /**
     * utilizzato per l'import del file dei filamenti, bisogna controllare che 
     * lo strumento esista
     * @param strumento
     * @return 
     */    
    public boolean queryEsistenzaStrumento(Connection conn, String strumento) {
        boolean res = false;
//        Connection conn = null;
        Statement stmt = null;
        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "select * from strumento where nome = '" + 
                    strumento + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                res = true;
            }
            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return res;
    }
    
    /**
     * utilizzato per l'inserimento dei dati di uno strumento
     * gestire meglio il caso di errore
     * @param conn
     * @param nomeStrumento 
     */
    public void inserisciNomeStrumento(Connection conn, String nomeStrumento) {
        String sql = "insert into strumento(nome) values('" + 
                nomeStrumento + "')";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato per scegliere lo strumento a cui assegnare una banda
     * @param conn
     * @return 
     */
    public List<String> queryStrumenti(Connection conn) {
        String sql = "select * from strumento";
        List<String> listaStrumenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
                listaStrumenti.add(rs.getString("nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaStrumenti;
    }
    
    /**
     * utilizzato per l'assegnamento di bande ad uno strumento
     * @param banda 
     */
    public void inserisciBandaStrumento(Connection conn, String strumento, 
            Double banda) {
        String sql1 = "insert into banda(banda) values(" + banda + ")"; // potrebbe esistere gia
        String sql2 = "insert into strumento_banda(strumento, banda) values('" 
                + strumento + "', " + banda 
                + ")";
        try {
            conn.setAutoCommit(false);
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            stmt1.executeUpdate(sql1);
            stmt2.executeUpdate(sql2);
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
