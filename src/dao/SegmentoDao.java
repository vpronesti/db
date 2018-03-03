package dao;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRichiestaSegmentoContorno;
import entity.Segmento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;

public class SegmentoDao {
    private static SegmentoDao istance;

    public static synchronized SegmentoDao getInstance() {
        if(istance == null)
            istance = new SegmentoDao();
        return istance;
    }
    
    /**
     * utilizzato per la distanza tra segmento e contorno 
     * @param conn
     * @param idFil
     * @param idSeg
     * @return 
     */
    public boolean queryEsistenzaSegmento(Connection conn, int idFil, int idSeg) {
        String sql = "select *  from segmento where idfil = " + 
                idFil + " and idseg = " + idSeg;
        boolean res = false;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                res = true;
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * utilizzato per la distanza tra segmento e contorno 
     * @param conn
     * @param beanRichiesta
     * @return 
     */
    public Segmento queryMaxSegmento(Connection conn, BeanRichiestaSegmentoContorno beanRichiesta) {
        String sql = "select type, glon_br, glat_br, n, flux" + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and idseg = " + beanRichiesta.getIdSeg() + 
                " and n = (select max(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and idseg = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String type = rs.getString("type");
                float gLonBr = rs.getFloat("glon_br");
                float gLatBr = rs.getFloat("glat_br");
                int n = rs.getInt("n");
                float flux = rs.getFloat("flux");
                s = new Segmento(beanRichiesta.getIdFil(), 
                        beanRichiesta.getIdSeg(), type, gLonBr, gLatBr, n, flux);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    /**
     * utilizzato per la distanza tra segmento e contorno 
     * il minimo dovrebbe essere sempre 1
     * @param conn
     * @param beanRichiesta
     * @return 
     */
    public Segmento queryMinSegmento(Connection conn, BeanRichiestaSegmentoContorno beanRichiesta) {
        String sql = "select type, glon_br, glat_br, n, flux" + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and idseg = " + beanRichiesta.getIdSeg() + 
                " and n = (select min(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and idseg = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String type = rs.getString("type");
                float gLonBr = rs.getFloat("glon_br");
                float gLatBr = rs.getFloat("glat_br");
                int n = rs.getInt("n");
                float flux = rs.getFloat("flux");
                s = new Segmento(beanRichiesta.getIdFil(), 
                        beanRichiesta.getIdSeg(), type, gLonBr, gLatBr, n, flux);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    /**
     * utilizzato per la ricerca dei filamenti in base al numero di segmenti
     * @param conn
     * @param beanRichiesta 
     */
    public List<Integer> queryIdFilamentiConNSegmenti(Connection conn, BeanRichiestaNumeroSegmenti beanRichiesta) {
        String sql = "select idfil " + 
                "from filamento_numero_segmenti " + 
                "where numsegmenti >= " + beanRichiesta.getInizioIntervallo() + 
                " and numsegmenti <= " + beanRichiesta.getFineIntervallo();
        List<Integer> idFilamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idFilamenti.add(rs.getInt("idfil"));   
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFilamenti;
    }
    
    /**
     * utilizzato per il recupero di informazioni derivate di un filamento		
     * @param conn
     * @param idFil
     * @return 
     */
    public int queryNumeroSegmentiFilamento(Connection conn, int idFil) {
        String sql = "select count(distinct idBranch) " + 
                "from segmento " + 
                "where idFil = " + idFil;
        Integer res = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                res = rs.getInt(1);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
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
                ") on conflict (idfil, idbranch, glon_br, glat_br) do update set " + 
                "type = excluded.type, " + 
                "n = excluded.n, " + 
                "flux = excluded.flux";
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
                ps.setFloat(4, s.getgLonBr());
                ps.setFloat(5, s.getgLatBr());
                ps.setInt(6, s.getN());
                ps.setFloat(7, s.getFlux());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
