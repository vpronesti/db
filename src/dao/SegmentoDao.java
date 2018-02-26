package dao;

import entity.Segmento;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBAccess;

public class SegmentoDao {
    private static SegmentoDao istance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized SegmentoDao getInstance() {
        if(istance == null)
            istance = new SegmentoDao();
        return istance;
    }
    
    public void inserisciSegmento(Connection conn, Segmento seg) {
        Statement stmt = null;
        String sql = "insert into segmento(idfil, idbranch, type, " + 
                "glon_br, glat_br, n, flux) values (" + 
                seg.getIdFil() + ", " + 
                seg.getIdBranch() + ", " + 
                "'" + seg.getType() + "', " + 
                seg.getgLonBr() + ", " +
                seg.getgLatBr() + ", " + 
                seg.getN() + ", " + 
                seg.getFlux() + 
                ") on conflict do update";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
