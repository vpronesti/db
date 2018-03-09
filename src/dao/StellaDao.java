package dao;

import bean.BeanRichiestaStelleRegione;
import entity.Contorno;
import entity.Stella;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StellaDao {
    private static StellaDao instance;

    public static synchronized StellaDao getInstance() {
        if(instance == null)
            instance = new StellaDao();
        return instance;
    }
    
    /**
     * utilizzato per la ricerca delle stelle in una regione rettangolare
     * @param conn
     * @param beanRichiesta
     * @return 
     */
    public List<Stella> queryStelleRegione(Connection conn, BeanRichiestaStelleRegione beanRichiesta) {
        float maxLon = beanRichiesta.getLongCentr() + beanRichiesta.getLatoA()/2;
        float maxLat = beanRichiesta.getLatiCentr() + beanRichiesta.getLatoB()/2;
        float minLon = beanRichiesta.getLongCentr() - beanRichiesta.getLatoA()/2;
        float minLat = beanRichiesta.getLatiCentr() - beanRichiesta.getLatoB()/2;
        String sql = "select idstar, namestar, glon_st, glat_st, flux_st, type_st " + 
                "from stella " + 
                "where glon_st > " + minLon + " and glon_st < " + maxLon + " and " + 
                "glat_st > " + minLat + " and glat_st < " + maxLat;
        List<Stella> listaStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt("idstar");
                String nameStar = rs.getString("namestar");
                float gLonSt = rs.getFloat("glon_st");
                float gLatSt = rs.getFloat("glat_st");
                float fluxSt = rs.getFloat("flux_st");
                String typeSt = rs.getString("type_st");
                Stella s = new Stella(idStar, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
                listaStelle.add(s);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaStelle;
    }
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * @param conn
     * @param con
     * @return 
     */
    public List<Stella> queryStelleFilamento(Connection conn, List<Contorno> listaPunti) {
        String sql = "select * from stella";
        List<Stella> listaStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt("idstar");
                String nameStar = rs.getString("namestar");
                float gLonSt = rs.getFloat("glon_st");
                float gLatSt = rs.getFloat("glat_st");
                float fluxSt = rs.getFloat("flux_st");
                String typeSt = rs.getString("type_st");
                Stella s = new Stella(idStar, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
                if (s.internoFilamento(listaPunti))
                    listaStelle.add(s);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaStelle;
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
                ps.setFloat(3, s.getgLonSt());
                ps.setFloat(4, s.getgLatSt());
                ps.setFloat(5, s.getFluxSt());
                ps.setString(6, s.getType().toString());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
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
