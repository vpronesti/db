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
        String sql = "select id_segmento, glon_se, glat_se, n, flusso " + 
                "from segmento " + 
                "where idfil = " + idFil.getIdFil() + "and satellite = '" + idFil.getSatellite() + 
                "' and tipo = 'S'";
        List<Segmento> listaSegmenti = new ArrayList<>();
        try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int idSegmento = rs.getInt("id_segmento");
            double gLonSe = rs.getDouble("glon_se");
            double gLatSe = rs.getDouble("glat_se");
            int n = rs.getInt("n");
            double flusso = rs.getDouble("flusso");
            Segmento s = new Segmento(idFil.getIdFil(), idFil.getSatellite(),
                    idSegmento, "S", gLonSe, gLatSe, n, flusso);
            listaSegmenti.add(s);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSegmenti;
    }
    
    /**
     * utilizzato per gli import
     * controlla che il punto di un segmento non faccia parte di altri segmenti
     * @param conn
     * @param s
     * @return 
     */
    public boolean querySegmentoAppartenteAltroSegmento(Connection conn, Segmento s) {
        boolean res = false;
        String sql = "select * from segmento where (idfil <> " + 
                s.getIdFil() + " or satellite <> '" + s.getSatellite() + "') and " + 
                "glon_se = " + s.getgLonSe() + " and glat_se = " + s.getgLatSe();
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
     * utilizzato per l'import dei file
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Segmento> queryPuntiSegmento(Connection conn, BeanIdFilamento idFil) {
        String sql = "select  id_segmento, tipo, glon_se, glat_se, n, flusso " + 
                "from segmento where idfil = " + idFil.getIdFil() + 
                " and satellite = '" + idFil.getSatellite() + "'";
        List<Segmento> listaSegmenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idSegmento = rs.getInt("id_segmento");
                String tipo = rs.getString("tipo");
                double gLonSe = rs.getDouble("glon_se");
                double gLatSe = rs.getDouble("glat_se");
                int n = rs.getInt("n");
                double flusso = rs.getDouble("flux");
                Segmento seg = new Segmento(idFil.getIdFil(), idFil.getSatellite(), 
                        idSegmento, tipo, gLonSe, gLatSe, n, flusso);
                listaSegmenti.add(seg);
            }
            rs.close();
            stmt.close();
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
                idFil + " and satellite = '" + satellite + "' and id_segmento = " + idSeg;
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
        String sql = "select tipo, glon_se, glat_se, n, flusso " + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and id_segmento = " + beanRichiesta.getIdSeg() + 
                " and n = (select max(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and id_segmento = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                double gLonSe = rs.getDouble("glon_se");
                double gLatSe = rs.getDouble("glat_se");
                int n = rs.getInt("n");
                double flusso = rs.getDouble("flusso");
                s = new Segmento(beanRichiesta.getIdFil(), beanRichiesta.getSatellite(), 
                        beanRichiesta.getIdSeg(), tipo, gLonSe, gLatSe, n, flusso);
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
        String sql = "select tipo, glon_se, glat_se, n, flusso " + 
                "from segmento " + 
                "where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and id_segmento = " + beanRichiesta.getIdSeg() + 
                " and n = (select min(n) from segmento where idfil = " + beanRichiesta.getIdFil() + 
                " and satellite = '" + beanRichiesta.getSatellite() + "'" + 
                " and id_segmento = " + beanRichiesta.getIdSeg() + ")";
        Segmento s = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);        
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                double gLonSe = rs.getDouble("glon_se");
                double gLatSe = rs.getDouble("glat_se");
                int n = rs.getInt("n");
                double flusso = rs.getDouble("flusso");
                s = new Segmento(beanRichiesta.getIdFil(), beanRichiesta.getSatellite(), 
                        beanRichiesta.getIdSeg(), tipo, gLonSe, gLatSe, n, flusso);
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
        String sql = "select filamento.idfil, filamento.satellite, nome, flusso_totale, dens_media, temp_media, ellitticita, contrasto, strumento " + 
                "from filamento_numero_segmenti join filamento on (filamento.idfil = filamento_numero_segmenti.idfil and filamento.satellite = filamento_numero_segmenti.satellite) " + 
                "where numsegmenti >= " + beanRichiesta.getInizioIntervallo() + 
                " and numsegmenti <= " + beanRichiesta.getFineIntervallo();
        List<Filamento> filamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idFil = rs.getInt("idfil");
                String nome = rs.getString("nome");
                double flussoTotale = rs.getDouble("flusso_totale");
                double densMedia = rs.getDouble("dens_media");
                double tempMedia = rs.getDouble("temp_media");
                double ellitticita = rs.getDouble("ellitticita");
                double contrasto = rs.getDouble("contrasto");
                String satellite = rs.getString("satellite");
                String strumento = rs.getString("strumento");
                Filamento f = new Filamento(idFil, nome, flussoTotale, 
                        densMedia, tempMedia, ellitticita, contrasto, 
                        satellite, strumento);
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
        String sql = "select count(distinct id_segmento) " + 
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
        String sql = "insert into segmento(idfil, satellite, id_segmento, tipo, " + 
                "glon_se, glat_se, n, flusso) values (" + 
                seg.getIdFil() + ", " + 
                "'" + seg.getSatellite() + "', " + 
                seg.getIdSegmento()+ ", " + 
                "'" + seg.getTipo()+ "', " + 
                seg.getgLonSe() + ", " +
                seg.getgLatSe() + ", " + 
                seg.getN() + ", " + 
                seg.getFlusso()+ 
                ") on conflict (idfil, satellite, id_segmento, glon_se, glat_se) do update set " + 
                "tipo = excluded.tipo, " + 
                "n = excluded.n, " + 
                "flusso = excluded.flusso";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserisciSegmentoBatch(Connection conn, List<Segmento> ls) {
        String sql = "insert into segmento(idfil, satellite, id_segmento, tipo, " + 
                "glon_se, glat_se, n, flusso) values (?, ?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idfil, satellite, id_segmento, glon_se, glat_se) do update set " + 
                "tipo = excluded.tipo, " + 
                "n = excluded.n, " + 
                "flusso = excluded.flusso";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Segmento> i = ls.iterator();
            while (i.hasNext()) {
                Segmento s = i.next();
                ps.setInt(1, s.getIdFil());
                ps.setString(2, s.getSatellite());
                ps.setInt(3, s.getIdSegmento());
                ps.setString(4, s.getTipo());
                ps.setDouble(5, s.getgLonSe());
                ps.setDouble(6, s.getgLatSe());
                ps.setInt(7, s.getN());
                ps.setDouble(8, s.getFlusso());
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
