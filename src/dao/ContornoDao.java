package dao;

import entity.Contorno;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBAccess;

public class ContornoDao {
    private static ContornoDao instance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized ContornoDao getInstance() {
        if(instance == null)
            instance = new ContornoDao();
        return instance;
    }
    
    public void inserisciContorno(Connection conn, Contorno cont) {
        Statement stmt = null;
        String sql = "insert into contorno(idfil, glog_cont, glat_cont) " + 
                "values(" + cont.getIdFil() + 
                ", " + cont.getgLonCont() + 
                ", " + cont.getgLatCont() + ") on conflict do update";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
