package dao;

import bean.BeanStrumento;
import bean.BeanStrumentoSatellite;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StrumentoDao {
    private static StrumentoDao instance;

    public static synchronized StrumentoDao getInstance() {
        if(instance == null)
            instance = new StrumentoDao();
        return instance;
    }
    
    public void rimuoviBandaStrumento(Connection conn, BeanStrumento beanS) {
        String sql = "delete from strumento_banda where strumento = '" + 
                beanS.getNome() + "' and banda = " + beanS.getBanda();
        String sql2 = "delete from banda where banda not in " + 
                "(select distinct banda from strumento_banda)";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato solo nei test
     * @param conn
     * @param beanS 
     */
    public void rimuoviSatelliteStrumento(Connection conn, BeanStrumentoSatellite beanS) {
        String sql = "delete from strumento_satellite where strumento = '" + 
                beanS.getNome() + "' and satellite = '" + beanS.getSatellite() + "'";
        String sql2 = "delete from strumento where nome not in " + 
                "(select distinct strumento from strumento_satellite)";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserisciSatelliteStrumento(Connection conn, BeanStrumentoSatellite beanS) {
        String sql = "insert into strumento_satellite(strumento, satellite) values ('" + 
                beanS.getNome() + "', '" + beanS.getSatellite() + "')";
        try {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean queryEsistenzaSatelliteStrumento(Connection conn, String sat, String str) {
        String sql = "select * from strumento_satellite where satellite = '"+ 
                sat + "' and strumento = '" + str + "'";
        boolean res = false;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                res = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public void inserisciBanda(Connection conn, double banda) {
        String sql = "insert into banda(banda) values(" + banda + ")";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean queryEsistenzaStrumentoBanda(Connection conn, BeanStrumento beanS) {
        String sql = "select * from strumento_banda where strumento = '" + 
                beanS.getNome() + "' and banda = " + beanS.getBanda();
        boolean res = false;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                res = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean queryEsistenzaBanda(Connection conn, double banda) {
        String sql = "select * from banda where banda = " + banda;
        boolean res = false;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
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
     * utilizzato per l'import del file dei filamenti, bisogna controllare che 
     * lo strumento esista
     * @param strumento
     * @return 
     */    
    public boolean queryEsistenzaStrumento(Connection conn, String strumento) {
        boolean res = false;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "select * from strumento where nome = '" + 
                    strumento + "'";
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
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaStrumenti;
    }
    
    /**
     * utilizzato per l'assegnamento di bande ad uno strumento
     * @param banda 
     */
    public void inserisciStrumentoBanda(Connection conn, String strumento, 
            double banda) {
        String sql = "insert into strumento_banda(strumento, banda) values('" 
                + strumento + "', " + banda 
                + ")";
        try {
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
