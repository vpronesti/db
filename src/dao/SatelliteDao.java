package dao;

import bean.BeanSatellite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBAccess;

public class SatelliteDao {
    private static SatelliteDao instance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized SatelliteDao getInstance() {
        if(instance == null)
            instance = new SatelliteDao();
        return instance;
    }
    
    
    /**
     * utilizzato per l'inserimento di un sattelite nel db e per 
     * controllare esistenza quando viene inserito un filamento
     * @param satellite
     * @return 
     */
    public boolean queryEsistenzaSatellite(Connection conn, BeanSatellite satellite) {
        boolean res = false;
//        Connection conn = null;
        Statement stmt = null;
        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "select * from satellite where nome = '" + 
                    satellite.getNome() + "'";
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
     * utilizzato nel per rqfn 3.3
     * @param satellite
     * @return 
     */
    public boolean inserisciSatellite(Connection conn, BeanSatellite satellite) {
        boolean res = true;
//        Connection conn = null;
        Statement stmt = null;
        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "";
            if (satellite.getTermineOperazione() != null) {
            sql = "insert into satellite(nome, primaosservazione, " + 
                    "termineoperazione, agenzia) values('" + 
                    satellite.getNome() + "', '" +  
                    satellite.getPrimaOsservazione() + "', '" + 
                    satellite.getTermineOperazione() + "', '" + 
                    satellite.getAgenzia() + "')";
            } else {
                sql = "insert into satellite(nome, primaosservazione, " + 
                    "termineoperazione, agenzia) values('" + 
                    satellite.getNome() + "', '" +  
                    satellite.getPrimaOsservazione() + "', NULL , '" + 
                    satellite.getAgenzia() + "')";
            
            }
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            res = false; // non si puo inserire il satellite
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
//                if (conn != null)
//                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return res;
        }
        
    } 
}
