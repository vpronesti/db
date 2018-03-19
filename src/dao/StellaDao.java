package dao;

import bean.BeanIdFilamento;
import bean.BeanIdStella;
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
import util.CoppiaStellaFilamento;

public class StellaDao {
    private static StellaDao instance;
    private final int MAX_DIM_ST = 2000;
    private final int MAX_DIM_FIL = 5000;

    public static synchronized StellaDao getInstance() {
        if(instance == null)
            instance = new StellaDao();
        return instance;
    }
    
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Stella> queryStelleFilamento(Connection conn, BeanIdFilamento idFil) {
        List<Stella> listaStelle = new ArrayList<>();
        String sql = "select sf.idstar, sf.satellite_star, namestar, glon_st, glat_st, flux_st, type_st " + 
                "from stella_filamento sf join stella on sf.idstar = stella.idstar and sf.satellite_star = stella.satellite " + 
                "where sf.idfil = " + idFil.getIdFil() + 
                " and sf.satellite_filamento = '" + idFil.getSatellite() + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt("idstar");
                String satelliteStar = rs.getString("satellite_star");
                String nameStar = rs.getString("namestar");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                double fluxSt = rs.getDouble("flux_st");
                String typeSt = rs.getString("type_st");
                Stella s = new Stella(idStar, satelliteStar, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
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
     * utilizzato per la ricerca delle stelle in una regione rettangolare
     * @param conn
     * @param beanRichiesta
     * @return 
     */
    public List<Stella> queryStelleRegione(Connection conn, BeanRichiestaStelleRegione beanRichiesta) {
        double maxLon = beanRichiesta.getLongCentr() + beanRichiesta.getLatoA()/2;
        double maxLat = beanRichiesta.getLatiCentr() + beanRichiesta.getLatoB()/2;
        double minLon = beanRichiesta.getLongCentr() - beanRichiesta.getLatoA()/2;
        double minLat = beanRichiesta.getLatiCentr() - beanRichiesta.getLatoB()/2;
        String sql = "select idstar, satellite, namestar, glon_st, glat_st, flux_st, type_st " + 
                "from stella " + 
                "where glon_st > " + minLon + " and glon_st < " + maxLon + " and " + 
                "glat_st > " + minLat + " and glat_st < " + maxLat;
        List<Stella> listaStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt("idstar");
                String satellite = rs.getString("satellite");
                String nameStar = rs.getString("namestar");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                double fluxSt = rs.getDouble("flux_st");
                String typeSt = rs.getString("type_st");
                Stella s = new Stella(idStar, satellite, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
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
     * elimina
     * @param conn
     * @param listaPunti
     * @return 
     */
    public void aggiornaStelleFilamento(Connection conn) {
        String sql = "select idstar, satellite, glon_st, glat_st " + 
                "from stella";
        List<Stella> listaStelle = new ArrayList<>();
//        List<AppartenenzaStellaFilamento> listaAppartenenza = new ArrayList<>();
//        List<Thread> listaThread = new ArrayList<>();
        List<BeanIdFilamento> listaIdFil = ContornoDao.getInstance().queryIdFilamentiContorno(conn);
        List<CoppiaStellaFilamento> listaStFil = new ArrayList<>();
//    System.out.println("lista id fil:" + listaIdFil.size());
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt(1);
                String satellite = rs.getString("satellite");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                Stella s = new Stella(idStar, satellite, gLonSt, gLatSt);
                
                Iterator<BeanIdFilamento> i = listaIdFil.iterator();
                while (i.hasNext()) {
                    BeanIdFilamento idFil = i.next();
                    List<Contorno> listaPunti = ContornoDao.getInstance().queryPuntiContornoFilamento(conn, idFil);
                    if (s.internoFilamento(listaPunti)) {
                        BeanIdStella idStella = new BeanIdStella(s.getIdStar(), s.getSatellite());
                        CoppiaStellaFilamento csf = new CoppiaStellaFilamento(idStella, idFil);
                        listaStFil.add(csf);
                    }
                }
                
                
//                listaStelle.add(s);
//                if (listaStelle.size() >= MAX_DIM_ST) {
//                    int c = 0;
//                    for (c = 0; c + MAX_DIM_FIL < listaIdFil.size(); c += MAX_DIM_FIL) {
//                    
//                        AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c, c + MAX_DIM_FIL));
//                        listaAppartenenza.add(a);
//                        Thread t = new Thread(a);
//                        listaThread.add(t);
//System.out.println("adding thread: " + listaThread.size());
//                        t.start();
//
//                        listaStelle = new ArrayList<>();
//                    }
//                    if (listaIdFil.size() % MAX_DIM_FIL != 0) {
//                        AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c - MAX_DIM_FIL, listaIdFil.size()));
//                        listaAppartenenza.add(a);
//                        Thread t = new Thread(a);
//                        listaThread.add(t);
//System.out.println("adding thread: " + listaThread.size());
//                        t.start();
//
//                        listaStelle = new ArrayList<>();
//                    }
//                }
//            }
//            if (listaStelle.size() > 0) {
//                int c = 0;
//                for (c = 0; c + MAX_DIM_FIL < listaIdFil.size(); c += MAX_DIM_FIL) {
//
//                    AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c, c + MAX_DIM_FIL));
//                    listaAppartenenza.add(a);
//                    Thread t = new Thread(a);
//                    listaThread.add(t);
//System.out.println("adding thread: " + listaThread.size());
//                    t.start();
//
//                    listaStelle = new ArrayList<>();
//                }
//                if (listaIdFil.size() % MAX_DIM_FIL != 0) {
//                    AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c - MAX_DIM_FIL, listaIdFil.size()));
//                    listaAppartenenza.add(a);
//                    Thread t = new Thread(a);
//                    listaThread.add(t);
//System.out.println("adding thread: " + listaThread.size());
//                    t.start();
//
//                    listaStelle = new ArrayList<>();
//                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        
//        Iterator<Thread> iT = listaThread.iterator();
//        while (iT.hasNext()) {
//            Thread t = iT.next();
//            try {
//                t.join();
//System.out.println("thread finished ");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        
//        Iterator<AppartenenzaStellaFilamento> iSF = listaAppartenenza.iterator();
//        while (iSF.hasNext()) {
//            AppartenenzaStellaFilamento a = iSF.next();
//            List<CoppiaStellaFilamento> listaCSF = a.getListaAppartenenza();
//            this.inserisciStellaFilamentoBatch(conn, listaCSF);
//        }
        this.inserisciStellaFilamentoBatch(conn, listaStFil);
    }
    public void inserisciStellaFilamentoBatch(Connection conn, List<CoppiaStellaFilamento> lsf) {
        
        String sql = "insert into stella_filamento(idstar, satellite_star, idfil, satellite_filamento) " + 
                "values(?, ?, ?, ?) " + 
                "on conflict (idstar, satellite_star, idfil, satellite_filamento) do update set " + 
                "idstar = excluded.idstar, " + 
                "satellite_star = excluded.satellite_star, " + 
                "idfil = excluded.idfil, " + 
                "satellite_filamento = excluded.satellite_filamento" + 
                ";";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<CoppiaStellaFilamento> i = lsf.iterator();
            while (i.hasNext()) { 
                CoppiaStellaFilamento csf = i.next();
                ps.setInt(1, csf.getIdStella().getIdStella());
                ps.setString(2, csf.getIdStella().getSatellite());
                ps.setInt(3, csf.getIdFil().getIdFil());
                ps.setString(4, csf.getIdFil().getSatellite());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * ????
     * @param conn
     * @param con
     * @return 
     */
    public List<BeanIdStella> queryIdStelleContornoFilamento(Connection conn, List<Contorno> listaPunti) {
        String sql = "select idstar, satellite, glon_st, glat_st from stella";
        List<BeanIdStella> listaIdStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("idstar");
                String satellite = rs.getString("satellite");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                Stella s = new Stella(id, satellite, gLonSt, gLatSt);
                
                if (s.internoFilamento(listaPunti)) {
                    BeanIdStella idStella = new BeanIdStella(id, satellite);
                    listaIdStelle.add(idStella);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaIdStelle;
    }
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento (SOSTITUITO forse)
     * utilizzato per la distanza delle stelle interne ad un filamento
     * @param conn
     * @param con
     * @return 
     */
    public List<Stella> queryStelleContornoFilamento(Connection conn, List<Contorno> listaPunti) {
        String sql = "select * from stella";
        List<Stella> listaStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStar = rs.getInt("idstar");
                String satellite = rs.getString("satellite");
                String nameStar = rs.getString("namestar");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                double fluxSt = rs.getDouble("flux_st");
                String typeSt = rs.getString("type_st");
                Stella s = new Stella(idStar, satellite, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
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
        
        String sql = "insert into stella(idstar, satellite, namestar, " + 
                "glon_st, glat_st, flux_st, type_st) values(?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idstar, satellite) do update set " + 
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
                ps.setString(2, s.getSatellite());
                ps.setString(3, s.getName());
                ps.setDouble(4, s.getgLonSt());
                ps.setDouble(5, s.getgLatSt());
                ps.setDouble(6, s.getFluxSt());
                ps.setString(7, s.getType());
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
        String sql = "insert into stella(idstar, satellite, namestar, " + 
                "glon_st, glat_st, flux_st, type_st) values(" + 
                st.getIdStar() + ", " + 
                "'" + st.getSatellite()+ "', " + 
                "'" + st.getName() + "', " + 
                st.getgLonSt() + ", " +
                st.getgLatSt() + ", " + 
                st.getFluxSt() + ", " + 
                "'" + st.getType() + "'" + 
                ") on conflict (idstar, satellite) do update set " + 
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
