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
import static util.DBAccess.FOREIGN_KEY_VIOLATION;
import static util.DBAccess.UNIQUE_VIOLATION;

public class SegmentoDao {
    private static SegmentoDao istance;

    public static synchronized SegmentoDao getInstance() {
        if(istance == null)
            istance = new SegmentoDao();
        return istance;
    }
    
    /**
     * utilizzato per la distanza delle stelle interne ad un filamento
     * 
     * @param conn
     * @param idFil
     * @return lista di punti che compongono l'asse principale del segmento
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
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaSegmenti;
    }    
    
    /**
     * utilizzato per la distanza tra segmento e contorno 
     * 
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
     * 
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
     * 
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
     * utilizzato per la ricerca dei filamenti in base al numero di segmenti
     * 
     * utilizza la vista che associa i filamenti al numero di segmenti e fa 
     * il join con la tabella dei filamenti per ottenere 
     * le relative informazioni
     * 
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
     * utilizzato per recupero informazioni su filamento	
     * 
     * la ricerca viene effettuata su una vista
     * 
     * @param conn
     * @param idFil
     * @return 
     */
    public int queryNumeroSegmentiFilamento(Connection conn, BeanIdFilamento idFil) {
        String sql = "select numsegmenti " + 
                "from filamento_numero_segmenti " + 
                "where idfil = " + idFil.getIdFil() + " and satellite = '" + 
                idFil.getSatellite() + "'";
        Integer res = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                res = rs.getInt(1);
            else 
                res = 0;
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean inserisciSegmentoBatch(Connection conn, List<Segmento> ls) {
        boolean res = true;
        String sql = "insert into segmento(idfil, satellite, id_segmento, tipo, " + 
                "glon_se, glat_se, n, flusso) values (?, ?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (glon_se, glat_se) do update set " +
                "idfil = excluded.idfil, " + 
                "satellite = excluded.satellite, " + 
                "id_segmento = excluded.id_segmento, " + 
                "tipo = excluded.tipo, " + 
                "n = excluded.n, " + 
                "flusso = excluded.flusso";
//                "on conflict (idfil, satellite, id_segmento, glon_se, glat_se) do update set " + 
//                "tipo = excluded.tipo, " + 
//                "n = excluded.n, " + 
//                "flusso = excluded.flusso";
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
            if (e.getSQLState().equals(FOREIGN_KEY_VIOLATION)) {
                res = false;
                System.out.println("fk violation in segmento dao batch");
            } else {
                e.printStackTrace();
            }
        }
        return res;
    }
}
