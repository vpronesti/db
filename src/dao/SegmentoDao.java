package dao;

import bean.BeanIdFilamento;
import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRichiestaSegmentoContorno;
import entity.Filamento;
import entity.Segmento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SegmentoDao {
    private static SegmentoDao istance;

    public static synchronized SegmentoDao getInstance() {
        if(istance == null)
            istance = new SegmentoDao();
        return istance;
    }
    
    /**
     * utilizzato per la distanza delle stelle interne ad un filamento
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Segmento> queryPuntiSegmentoPrincipaleFilamento(Connection conn, BeanIdFilamento idFil) {
        String sql = "select idbranch, glon_br, glat_br, n, flux " + 
                "from segmento " + 
                "where idfil = " + idFil.getIdFil() + "and satellite = '" + idFil.getSatellite() + 
                "' and type = 'S'";
        List<Segmento> listaSegmenti = new ArrayList<>();
        try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int idBranch = rs.getInt("idbranch");
            double gLonBr = rs.getDouble("glon_br");
            double gLatBr = rs.getDouble("glat_br");
            int n = rs.getInt("n");
            double flux = rs.getDouble("flux");
            Segmento s = new Segmento(idFil.getIdFil(), idFil.getSatellite(),
                    idBranch, "S", gLonBr, gLatBr, n, flux);
            listaSegmenti.add(s);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSegmenti;
    }
    
    /**
     * utilizzato per la distanza tra segmento e contorno 
     * @param conn
     * @param idFil
     * @param idSeg
     * @return 
     */
    public boolean queryEsistenzaSegmento(Connection conn, 
            int idFil, String satellite, int idSeg) {
        String sql = "select *  from segmento where idfil = " + 
                idFil + " and satellite = '" + satellite + "' and idbranch = " + idSeg;
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
        String sql = "select type, glon_br, glat_br, n, flux " + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and idbranch = " + beanRichiesta.getIdSeg() + 
                " and n = (select max(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and idbranch = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String type = rs.getString("type");
                double gLonBr = rs.getDouble("glon_br");
                double gLatBr = rs.getDouble("glat_br");
                int n = rs.getInt("n");
                double flux = rs.getDouble("flux");
                s = new Segmento(beanRichiesta.getIdFil(), beanRichiesta.getSatellite(), 
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
        String sql = "select type, glon_br, glat_br, n, flux " + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and idbranch = " + beanRichiesta.getIdSeg() + 
                " and n = (select min(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and idbranch = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String type = rs.getString("type");
                double gLonBr = rs.getDouble("glon_br");
                double gLatBr = rs.getDouble("glat_br");
                int n = rs.getInt("n");
                double flux = rs.getDouble("flux");
                s = new Segmento(beanRichiesta.getIdFil(), beanRichiesta.getSatellite(), 
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
     * utilizzato per la ricerca dei filamenti in base al numero di segmenti SOSTITUITO
     * @param conn
     * @param beanRichiesta 
     */
    public List<BeanIdFilamento> queryIdFilamentiConNSegmenti(Connection conn, BeanRichiestaNumeroSegmenti beanRichiesta) {
        String sql = "select idfil, satellite " + 
                "from filamento_numero_segmenti " + 
                "where numsegmenti >= " + beanRichiesta.getInizioIntervallo() + 
                " and numsegmenti <= " + beanRichiesta.getFineIntervallo();
        List<BeanIdFilamento> idFilamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idfil");
                String satellite = rs.getString("satellite");
                BeanIdFilamento idFil = new BeanIdFilamento(id, satellite);
                idFilamenti.add(idFil);   
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idFilamenti;
    }
    
    /**
     * utilizzato per la ricerca dei filamenti in base al numero di segmenti
     * @param conn
     * @param beanRichiesta 
     */
    public List<Filamento> queryFilamentiConNSegmenti(Connection conn, BeanRichiestaNumeroSegmenti beanRichiesta) {
        String sql = "select filamento.idfil, filamento.satellite, name, total_flux, mean_dens, mean_temp, ellipticity, contrast, instrument " + 
                "from filamento_numero_segmenti join filamento on (filamento.idfil = filamento_numero_segmenti.idfil and filamento.satellite = filamento_numero_segmenti.satellite) " + 
                "where numsegmenti >= " + beanRichiesta.getInizioIntervallo() + 
                " and numsegmenti <= " + beanRichiesta.getFineIntervallo();
        List<Filamento> filamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idFil = rs.getInt("idfil");
                String name = rs.getString("name");
                double total_flux = rs.getDouble("total_flux");
                double mean_dens = rs.getDouble("mean_dens");
                double mean_temp = rs.getDouble("mean_temp");
                double ellipticity = rs.getDouble("ellipticity");
                double contrast = rs.getDouble("contrast");
                String satellite = rs.getString("satellite");
                String instrument = rs.getString("instrument");
                Filamento f = new Filamento(idFil, name, total_flux, mean_dens, mean_temp, ellipticity, contrast, satellite, instrument);
                filamenti.add(f);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filamenti;
    }
    
    /**
     * utilizzato per il recupero di informazioni derivate di un filamento	
     * si potrebbe modificare e utilizzare la vista
     * @param conn
     * @param idFil
     * @return 
     */
    public int queryNumeroSegmentiFilamento(Connection conn, BeanIdFilamento idFil) {
        String sql = "select count(distinct idBranch) " + 
                "from segmento " + 
                "where idFil = " + idFil.getIdFil() + " and satellite = '" + 
                idFil.getSatellite() + "'";
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
        String sql = "insert into segmento(idfil, satellite, idbranch, type, " + 
                "glon_br, glat_br, n, flux) values (" + 
                seg.getIdFil() + ", " + 
                "'" + seg.getSatellite() + "', " + 
                seg.getIdBranch() + ", " + 
                "'" + seg.getType() + "', " + 
                seg.getgLonBr() + ", " +
                seg.getgLatBr() + ", " + 
                seg.getN() + ", " + 
                seg.getFlux() + 
                ") on conflict (idfil, satellite, idbranch, glon_br, glat_br) do update set " + 
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
        String sql = "insert into segmento(idfil, satellite, idbranch, type, " + 
                "glon_br, glat_br, n, flux) values (?, ?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idfil, satellite, idbranch, glon_br, glat_br) do update set " + 
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
                ps.setString(2, s.getSatellite());
                ps.setInt(3, s.getIdBranch());
                ps.setString(4, s.getType());
                ps.setDouble(5, s.getgLonBr());
                ps.setDouble(6, s.getgLatBr());
                ps.setInt(7, s.getN());
                ps.setDouble(8, s.getFlux());
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
