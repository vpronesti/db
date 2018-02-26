package dao;

import entity.Segmento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
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
    
    public void inserisciSegmentoBatch(Connection conn, List<Segmento> ls) {
        String sql = "insert into segmento(idfil, idbranch, type, " + 
                "glon_br, glat_br, n, flux) values (?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idfil, idbranch, glon_br, glat_br) do update set " + 
                "type = excluded.type, " + 
                "n = excluded.n, " + 
                "flux = excluded.flux";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Segmento> i = ls.iterator();
            while (i.hasNext()) {
                Segmento s = i.next();
                ps.setInt(1, s.getIdFil());
                ps.setInt(2, s.getIdBranch());
                ps.setString(3, s.getType());
                ps.setDouble(4, s.getgLonBr());
                ps.setDouble(5, s.getgLatBr());
                ps.setInt(6, s.getN());
                ps.setDouble(6, s.getFlux());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
