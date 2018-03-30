package dao;

import bean.BeanSatellite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static util.DBAccess.UNIQUE_VIOLATION;

public class SatelliteDao {
    private static SatelliteDao instance;

    public static synchronized SatelliteDao getInstance() {
        if(instance == null)
            instance = new SatelliteDao();
        return instance;
    }
    
    /**
     * utilizzato nel req 3.3
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
            sql = "insert into satellite(nome, prima_osservazione, " + 
                    "termine_operazione, agenzia) values('" + 
                    satellite.getNome() + "', '" +  
                    satellite.getPrimaOsservazione() + "', '" + 
                    satellite.getTermineOperazione() + "', '" + 
                    satellite.getAgenzia() + "')";
            } else {
                sql = "insert into satellite(nome, prima_osservazione, " + 
                    "termine_operazione, agenzia) values('" + 
                    satellite.getNome() + "', '" +  
                    satellite.getPrimaOsservazione() + "', NULL , '" + 
                    satellite.getAgenzia() + "')";
            }
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                res = false;
                System.out.println("doppia chiave primaria in inserimento satellite");
            } else {
                e.printStackTrace();
            }
        }
        return res;
    }
    
    /**
     * utilizzato per eliminare delle modifiche fatte nei test
     * @param conn
     * @param beanS 
     */
    public void rimuoviSatellite(Connection conn, BeanSatellite beanS) {
        String sql = "delete from satellite where nome = '" + 
                beanS.getNome() + "'";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato per popolare le combo box nella grafica
     * @param conn
     * @return 
     */
    public List<String> querySatelliti(Connection conn) {
        String sql = "select nome from satellite";
        List<String> listaSatelliti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String sat = rs.getString("nome");
                listaSatelliti.add(sat);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSatelliti;
    }
}
