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

    public static synchronized SatelliteDao getInstance() {
        if(instance == null)
            instance = new SatelliteDao();
        return instance;
    }
    
    
    /**
     * utilizzato per l'inserimento di un sattelite nel db 
     * utilizzate per l'inserimento di un filamento 
     * @param satellite
     * @return 
     */
    public boolean queryEsistenzaSatellite(Connection conn, BeanSatellite satellite) {
        boolean res = false;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "select * from satellite where nome = '" + 
                    satellite.getNome() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                res = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * utilizzato nel per rqfn 3.3
     * @param satellite
     * @return 
     */
    public boolean inserisciSatellite(Connection conn, BeanSatellite satellite) {
        boolean res = true;
        Statement stmt = null;
        try {
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
            stmt.close();
        } catch (SQLException e) {
            res = false;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
