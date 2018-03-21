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
     * interroga la tabella stella_filamento del DB per 
     * vedere se una stella fa parte di un filamento
     * 
     * utilizzato per la ricerca delle stelle in una regione rettangolare
     * @param conn
     * @param idStella
     * @return 
     */
    public boolean queryStellaAppartieneFilamento(Connection conn, BeanIdStella idStella) {
        boolean res = false;
        String sql = "select * from stella_filamento where idstella = " + 
                idStella.getIdStella() + " and satellite_stella = '" + 
                idStella.getSatellite() + "'";
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
//        System.out.println("returning " + res + " for: " + idStella);
        return res;
    }
    
    /**
     * interroga la tabella stella_filamento per cercare quali sono le 
     * stelle che appartengono ad un determinato filamento
     * 
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * utilizzato per la distanza delle stelle interne ad un filamento
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Stella> queryStelleFilamento(Connection conn, BeanIdFilamento idFil) {
        List<Stella> listaStelle = new ArrayList<>();
        String sql = "select sf.idstella, sf.satellite_stella, nomestella, glon_st, glat_st, flusso_st, tipo_st " + 
                "from stella_filamento sf join stella on sf.idstella = stella.idstella and sf.satellite_stella = stella.satellite " + 
                "where sf.idfil = " + idFil.getIdFil() + 
                " and sf.satellite_filamento = '" + idFil.getSatellite() + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStella = rs.getInt("idstella");
                String satelliteStella = rs.getString("satellite_stella");
                String nomeStella = rs.getString("nomestella");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                double flussoSt = rs.getDouble("flusso_st");
                String tipoSt = rs.getString("tiop_st");
                Stella s = new Stella(idStella, satelliteStella, nomeStella, gLonSt, gLatSt, flussoSt, tipoSt);
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
     * restituisce la lista di stelle che si trovano all'interno di una 
     * determinata regione
     * 
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
        String sql = "select idstella, satellite, nomestella, glon_st, glat_st, flusso_st, tipo_st " + 
                "from stella " + 
                "where glon_st > " + minLon + " and glon_st < " + maxLon + " and " + 
                "glat_st > " + minLat + " and glat_st < " + maxLat;
        List<Stella> listaStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idStella = rs.getInt("idstella");
                String satellite = rs.getString("satellite");
                String nomeStella = rs.getString("nomestella");
                double gLonSt = rs.getDouble("glon_st");
                double gLatSt = rs.getDouble("glat_st");
                double flussoSt = rs.getDouble("flusso_st");
                String tipoSt = rs.getString("tipo_st");
                Stella s = new Stella(idStella, satellite, nomeStella, gLonSt, gLatSt, flussoSt, tipoSt);
                listaStelle.add(s);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaStelle;
    }
    
//    /**
//     * elimina
//     * @param conn
//     * @param listaPunti
//     * @return 
//     */
//    public void aggiornaStelleFilamento(Connection conn) {
//        String sql = "select idstar, satellite, glon_st, glat_st " + 
//                "from stella";
//        List<Stella> listaStelle = new ArrayList<>();
////        List<AppartenenzaStellaFilamento> listaAppartenenza = new ArrayList<>();
////        List<Thread> listaThread = new ArrayList<>();
//        List<BeanIdFilamento> listaIdFil = ContornoDao.getInstance().queryIdFilamentiContorno(conn);
//        List<CoppiaStellaFilamento> listaStFil = new ArrayList<>();
////    System.out.println("lista id fil:" + listaIdFil.size());
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                int idStar = rs.getInt(1);
//                String satellite = rs.getString("satellite");
//                double gLonSt = rs.getDouble("glon_st");
//                double gLatSt = rs.getDouble("glat_st");
//                Stella s = new Stella(idStar, satellite, gLonSt, gLatSt);
//                
//                Iterator<BeanIdFilamento> i = listaIdFil.iterator();
//                while (i.hasNext()) {
//                    BeanIdFilamento idFil = i.next();
//                    List<Contorno> listaPunti = ContornoDao.getInstance().queryPuntiContornoFilamento(conn, idFil);
//                    if (s.internoFilamento(listaPunti)) {
//                        BeanIdStella idStella = new BeanIdStella(s.getIdStar(), s.getSatellite());
//                        CoppiaStellaFilamento csf = new CoppiaStellaFilamento(idStella, idFil);
//                        listaStFil.add(csf);
//                    }
//                }
//                
//                
////                listaStelle.add(s);
////                if (listaStelle.size() >= MAX_DIM_ST) {
////                    int c = 0;
////                    for (c = 0; c + MAX_DIM_FIL < listaIdFil.size(); c += MAX_DIM_FIL) {
////                    
////                        AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c, c + MAX_DIM_FIL));
////                        listaAppartenenza.add(a);
////                        Thread t = new Thread(a);
////                        listaThread.add(t);
////System.out.println("adding thread: " + listaThread.size());
////                        t.start();
////
////                        listaStelle = new ArrayList<>();
////                    }
////                    if (listaIdFil.size() % MAX_DIM_FIL != 0) {
////                        AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c - MAX_DIM_FIL, listaIdFil.size()));
////                        listaAppartenenza.add(a);
////                        Thread t = new Thread(a);
////                        listaThread.add(t);
////System.out.println("adding thread: " + listaThread.size());
////                        t.start();
////
////                        listaStelle = new ArrayList<>();
////                    }
////                }
////            }
////            if (listaStelle.size() > 0) {
////                int c = 0;
////                for (c = 0; c + MAX_DIM_FIL < listaIdFil.size(); c += MAX_DIM_FIL) {
////
////                    AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c, c + MAX_DIM_FIL));
////                    listaAppartenenza.add(a);
////                    Thread t = new Thread(a);
////                    listaThread.add(t);
////System.out.println("adding thread: " + listaThread.size());
////                    t.start();
////
////                    listaStelle = new ArrayList<>();
////                }
////                if (listaIdFil.size() % MAX_DIM_FIL != 0) {
////                    AppartenenzaStellaFilamento a = new AppartenenzaStellaFilamento(listaStelle, listaIdFil.subList(c - MAX_DIM_FIL, listaIdFil.size()));
////                    listaAppartenenza.add(a);
////                    Thread t = new Thread(a);
////                    listaThread.add(t);
////System.out.println("adding thread: " + listaThread.size());
////                    t.start();
////
////                    listaStelle = new ArrayList<>();
////                }
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
////        
////        Iterator<Thread> iT = listaThread.iterator();
////        while (iT.hasNext()) {
////            Thread t = iT.next();
////            try {
////                t.join();
////System.out.println("thread finished ");
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
////        
////        Iterator<AppartenenzaStellaFilamento> iSF = listaAppartenenza.iterator();
////        while (iSF.hasNext()) {
////            AppartenenzaStellaFilamento a = iSF.next();
////            List<CoppiaStellaFilamento> listaCSF = a.getListaAppartenenza();
////            this.inserisciStellaFilamentoBatch(conn, listaCSF);
////        }
//        this.inserisciStellaFilamentoBatch(conn, listaStFil);
//    }
//    public void inserisciStellaFilamentoBatch(Connection conn, List<CoppiaStellaFilamento> lsf) {
//        
//        String sql = "insert into stella_filamento(idstar, satellite_star, idfil, satellite_filamento) " + 
//                "values(?, ?, ?, ?) " + 
//                "on conflict (idstar, satellite_star, idfil, satellite_filamento) do update set " + 
//                "idstar = excluded.idstar, " + 
//                "satellite_star = excluded.satellite_star, " + 
//                "idfil = excluded.idfil, " + 
//                "satellite_filamento = excluded.satellite_filamento" + 
//                ";";
//        try {
//            conn.setAutoCommit(false);
//            PreparedStatement ps = conn.prepareStatement(sql);
//            Iterator<CoppiaStellaFilamento> i = lsf.iterator();
//            while (i.hasNext()) { 
//                CoppiaStellaFilamento csf = i.next();
//                ps.setInt(1, csf.getIdStella().getIdStella());
//                ps.setString(2, csf.getIdStella().getSatellite());
//                ps.setInt(3, csf.getIdFil().getIdFil());
//                ps.setString(4, csf.getIdFil().getSatellite());
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            ps.close();
//            conn.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    

    /**
     * ????
     * @param conn
     * @param con
     * @return 
     */
    public List<BeanIdStella> queryIdStelleContornoFilamento(Connection conn, List<Contorno> listaPunti) {
        String sql = "select idstella, satellite, glon_st, glat_st from stella";
        List<BeanIdStella> listaIdStelle = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("idstella");
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
    
//    /**
//     * utilizzato per la ricerca delle stelle all'interno di un filamento (SOSTITUITO forse)
//     * @param conn
//     * @param con
//     * @return 
//     */
//    public List<Stella> queryStelleContornoFilamento(Connection conn, List<Contorno> listaPunti) {
//        String sql = "select * from stella";
//        List<Stella> listaStelle = new ArrayList<>();
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                int idStar = rs.getInt("idstar");
//                String satellite = rs.getString("satellite");
//                String nameStar = rs.getString("namestar");
//                double gLonSt = rs.getDouble("glon_st");
//                double gLatSt = rs.getDouble("glat_st");
//                double fluxSt = rs.getDouble("flux_st");
//                String typeSt = rs.getString("type_st");
//                Stella s = new Stella(idStar, satellite, nameStar, gLonSt, gLatSt, fluxSt, typeSt);
//                if (s.internoFilamento(listaPunti))
//                    listaStelle.add(s);
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return listaStelle;
//    }
    
    public void inserisciStellaBatch(Connection conn, List<Stella> ls) {
        
        String sql = "insert into stella(idstella, satellite, nomestella, " + 
                "glon_st, glat_st, flusso_st, tipo_st) values(?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idstella, satellite) do update set " + 
                "nomestella = excluded.nomestella, " + 
                "glon_st = excluded.glon_st, " + 
                "glat_st = excluded.glat_st, " + 
                "flusso_st = excluded.flusso_st, " + 
                "tipo_st = excluded.tipo_st " + 
                ";";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Stella> i = ls.iterator();
            while (i.hasNext()) { 
                Stella s = i.next();
                ps.setInt(1, s.getIdStella());
                ps.setString(2, s.getSatellite());
                ps.setString(3, s.getNome());
                ps.setDouble(4, s.getgLonSt());
                ps.setDouble(5, s.getgLatSt());
                ps.setDouble(6, s.getFlussoSt());
                ps.setString(7, s.getTipo());
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
        String sql = "insert into stella(idstella, satellite, nomestella, " + 
                "glon_st, glat_st, flusso_st, tipo_st) values(" + 
                st.getIdStella()+ ", " + 
                "'" + st.getSatellite()+ "', " + 
                "'" + st.getNome() + "', " + 
                st.getgLonSt() + ", " +
                st.getgLatSt() + ", " + 
                st.getFlussoSt()+ ", " + 
                "'" + st.getTipo()+ "'" + 
                ") on conflict (idstella, satellite) do update set " + 
                "nomestella = excluded.nomestella, " + 
                "glon_st = excluded.glon_st, " + 
                "glat_st = excluded.glat_st, " + 
                "flusso_st = excluded.flusso_st, " + 
                "tipo_st = excluded.tipo_st " + 
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
