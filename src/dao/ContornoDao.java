package dao;

import entity.Contorno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
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
    
    public void inserisciContornoBatch(Connection conn, List<Contorno> lc) {
        String sql = "insert into contorno(idfil, glog_cont, glat_cont) " + 
                "values(?, ?, ?)" + 
                " on conflict (idfil, glon_cont, glat_cont) do update set " + 
                "idfil = excluded.idfil, " + 
                "glon_cont = excluded.glon_cont, " + 
                "glat_cont = excluded.glat_cont";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Contorno> i = lc.iterator();
            while (i.hasNext()) {
                Contorno c = i.next();
                ps.setInt(1, c.getIdFil());
                ps.setDouble(2, c.getgLonCont());
                ps.setDouble(3, c.getgLatCont());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
