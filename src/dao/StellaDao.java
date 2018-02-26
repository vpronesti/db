package dao;

import entity.Stella;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;

public class StellaDao {
    private static StellaDao instance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized StellaDao getInstance() {
        if(instance == null)
            instance = new StellaDao();
        return instance;
    }
    
    public void inserisciStellaBatch(Connection conn, List<Stella> ls) {
        
        String sql = "insert into stella(idstar, namestar, " + 
                "glon_st, glat_st, flux_st, type_st) values(?, ?, ?, ?, ?, ?) " + 
                "on conflict (idstar) do update set " + 
                "namestar = excluded.namestar, " + 
                "glon_st = excluded.glon_st, " + 
                "glat_st = excluded.glat_st, " + 
                "flux_st = excluded.flux_st, " + 
                "type_st = excluded.type_st " + 
                ";";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Stella> i = ls.iterator();
            while (i.hasNext()) {
                Stella s = i.next();
                ps.setInt(1, s.getIdStar());
                ps.setString(2, s.getName());
                ps.setDouble(3, s.getgLonSt());
                ps.setDouble(4, s.getgLatSt());
                ps.setDouble(5, s.getFluxSt());
                ps.setString(6, s.getType());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserisciStella(Connection conn, Stella st) {
        Statement stmt = null;
        String sql = "insert into stella(idstar, namestar, " + 
                "glon_st, glat_st, flux_st, type_st) values(" + 
                st.getIdStar() + ", " + 
                "'" + st.getName() + "', " + 
                st.getgLonSt() + ", " +
                st.getgLatSt() + ", " + 
                st.getFluxSt() + ", " + 
                "'" + st.getType() + "'" + 
                ") on conflict (idstar) do update set " + 
                "namestar = excluded.namestar, " + 
                "glon_st = excluded.glon_st, " + 
                "glat_st = excluded.glat_st, " + 
                "flux_st = excluded.flux_st, " + 
                "type_st = excluded.type_st " + 
                ";";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
