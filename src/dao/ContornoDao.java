package dao;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import bean.BeanRichiestaFilamentiRegione;
import entity.Contorno;
import entity.Filamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;

public class ContornoDao {
    private static ContornoDao instance;

    public static synchronized ContornoDao getInstance() {
        if(instance == null)
            instance = new ContornoDao();
        return instance;
    }
    
//    public void aggiornamentoStellaFilamento(Connection conn) {
//        List<BeanIdFilamento> listaId = this.queryIdFilamentiContorno(conn);
//System.out.println("numId: " + listaId.size());
//        String sql = "insert into stella_filamento(idstar, idfil, satellite) values (?, ?, ?) " + 
//                "on conflict (idstar, idfil, satellite) do update set " + 
//                "idstar = excluded.idstar, " + 
//                "idfil = excluded.idfil, " + 
//                "satellite = excluded.satellite";
//        Iterator<Integer> i = listaId.iterator();
//        try {
//            conn.setAutoCommit(false);
//            Connection conn2 = DBAccess.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            while (i.hasNext()) {
//                int id = i.next();
//                BeanIdFilamento idFil = new BeanIdFilamento();
//                List<Contorno> puntiContorno = this.queryPuntiContornoFilamento(conn2, idFil);
//System.out.println("listCon: " + puntiContorno.size());
//                StellaDao stellaDao = StellaDao.getInstance();
//                List<Integer> listaIdStelle = stellaDao.queryIdStelleContornoFilamento(conn2, puntiContorno);
//System.out.println("idStelle: " + listaIdStelle.size());
//                Iterator<Integer> iS = listaIdStelle.iterator();
//                while (iS.hasNext()) {
//                    int idS = iS.next();
//                    ps.setInt(1, idS);
//                    ps.setInt(2, idFil);
//                    ps.addBatch();
//                }
//            }
//            ps.executeBatch();
//            ps.close();
//            conn.commit();
//            DBAccess.getInstance().closeConnection(conn2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @return 
     */
    public List<Filamento> queryFilamentiInterniCerchio(Connection conn, 
            BeanRichiestaFilamentiRegione beanRichiesta) {
        float longCentr = beanRichiesta.getLongCentroide();
        float latiCentr = beanRichiesta.getLatiCentroide();
        float dim = beanRichiesta.getDimensione();
        String sql = "select filamento.idfil, name, total_flux, mean_dens, mean_temp, ellipticity, contrast, filamento.satellite, instrument " + 
                "from ( " + 
                    "select distinct c1.idfil, c1.satellite " + 
                    "from contorno c1 " + 
                    "where exists " + 
                        "(select glog_cont, glat_cont " + 
                            "from contorno c2 where c2.idfil = c1.idfil and c2.satellite = c1.satellite and " + 
                                "(|/((glog_cont - " + longCentr + 
                                ")^2 + (glat_cont - " + latiCentr + 
                                ")^2) <= " + dim + ")" + 
                        ") " + 
                    "and not exists " + 
                        "(select glog_cont, glat_cont " + 
                        "from contorno c2 where c2.idfil = c1.idfil and c2.satellite = c1.satellite and " + 
                            "(|/((glog_cont - " + longCentr + 
                            ")^2 + (glat_cont - " + latiCentr + 
                            ")^2) > " + dim + ")" + 
                        ")" + 
                ") as subQ join filamento on (subQ.idfil = filamento.idfil and subQ.satellite = filamento.satellite)";
        List<Filamento> listaFilamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idFil = rs.getInt(1);
                String name = rs.getString("name");
                float total_flux = rs.getFloat("total_flux");
                float mean_dens = rs.getFloat("mean_dens");
                float mean_temp = rs.getFloat("mean_temp");
                float ellipticity = rs.getFloat("ellipticity");
                float contrast = rs.getFloat("contrast");
                String satellite = rs.getString("satellite");
                String instrument = rs.getString("instrument");
                Filamento f = new Filamento(idFil, name, total_flux, 
                        mean_dens, mean_temp, ellipticity, contrast, 
                        satellite, instrument);
                if (!listaFilamenti.contains(f))
                    listaFilamenti.add(f);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFilamenti;
    }
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @return 
     */
    public List<Filamento> queryFilamentiInterniQuadrato(Connection conn, 
            BeanRichiestaFilamentiRegione beanRichiesta) {
        float dist = beanRichiesta.getDimensione() / 2;
        float longMax = beanRichiesta.getLongCentroide() + dist;
        float longMin = beanRichiesta.getLongCentroide() - dist;
        float latiMax = beanRichiesta.getLatiCentroide() + dist;
        float latiMin = beanRichiesta.getLatiCentroide() - dist;
        
        String sql = "select filamento.idfil, name, total_flux, mean_dens, mean_temp, ellipticity, contrast, filamento.satellite, instrument " + 
                "from ( " + 
                    "select distinct c1.idfil, c1.satellite " + 
                    "from contorno c1 " + 
                    "where exists " + 
                    "(select glog_cont, glat_cont " + 
                            "from contorno c2 where c2.idfil = c1.idfil and c2.satellite = c1.satellite and " + 
                                "(glog_cont <= " + longMax + " and glog_cont >= " + longMin + " and " + 
                                "glat_cont <= " + latiMax + " and glat_cont >= " + latiMin + ")" + 
                    ") " + 
                    "and not exists " + 
                        "(select glog_cont, glat_cont " + 
                        "from contorno c2 where c2.idfil = c1.idfil and c2.satellite = c1.satellite  and " + 
                            "(glog_cont > " + longMax + " or glog_cont < " + longMin + " or " + 
                            "glat_cont > " + latiMax + " or glat_cont < " + latiMin + ")" + 
                        ")" + 
                ") as subQ join filamento on (subQ.idfil = filamento.idfil and subQ.satellite = filamento.satellite) ";
        List<Filamento> listaFilamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idFil = rs.getInt(1);
                String name = rs.getString("name");
                float total_flux = rs.getFloat("total_flux");
                float mean_dens = rs.getFloat("mean_dens");
                float mean_temp = rs.getFloat("mean_temp");
                float ellipticity = rs.getFloat("ellipticity");
                float contrast = rs.getFloat("contrast");
                String satellite = rs.getString("satellite");
                String instrument = rs.getString("instrument");
                Filamento f = new Filamento(idFil, name, total_flux, 
                        mean_dens, mean_temp, ellipticity, contrast, 
                        satellite, instrument);
                if (!listaFilamenti.contains(f))
                    listaFilamenti.add(f);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFilamenti;
    }
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @return 
     */
    public List<BeanIdFilamento> queryIdFilamentiContorno(Connection conn) {
        String sql = "select distinct idfil, satellite from contorno";
        List<BeanIdFilamento> listaIdFil = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String satellite = rs.getString(2);
                BeanIdFilamento idFil = new BeanIdFilamento(id, satellite);
                listaIdFil.add(idFil);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaIdFil;
    }
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * utilizzato per la distanza tra segmento e contorno 
     * utilizzato per la distanza delle stelle interne ad un filamento
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Contorno> queryPuntiContornoFilamento(Connection conn, 
            BeanIdFilamento idFil) {
        int id = idFil.getIdFil();
        String satellite = idFil.getSatellite();
        String sql = "select glog_cont, glat_cont " + 
                "from contorno " + 
                "where idfil = " + id + " and satellite = '" + satellite + "'";
        List<Contorno> listaContorni = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                float gLonCont = rs.getFloat("glog_cont");
                float gLatCont = rs.getFloat("glat_cont");
                Contorno c = new Contorno(id, satellite, gLonCont, gLatCont);
                listaContorni.add(c);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaContorni;
    }
    
    /**
     * utilizzato per recupero informazioni filamento
     * @param conn
     * @param beanFil 
     */
    public void queryEstensioneContorno(Connection conn, BeanInformazioniFilamento beanFil) {
        String sql = "select max(glog_cont), max(glat_cont), min(glog_cont), min(glat_cont)" + 
                "from contorno " + 
                "where idfil = " + beanFil.getIdFil() + " and satellite = '" + 
                beanFil.getSatellite() + "'";
        Float maxGLogCont = null;
        Float minGLogCont = null;
        Float maxGLatCont = null;
        Float minGLatCont = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                maxGLogCont = rs.getFloat(1);
                maxGLatCont = rs.getFloat(2);
                minGLogCont = rs.getFloat(3);
                minGLatCont = rs.getFloat(4);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        beanFil.setMaxGLonContorno(maxGLogCont);
        beanFil.setMaxGLatContorno(maxGLatCont);
        beanFil.setMinGLonContorno(minGLogCont);
        beanFil.setMinGLatContorno(minGLatCont);
    }
    
    /**
     * utilizzato per recupero informazioni filamento
     * @param conn
     * @param beanFil 
     */
    public void queryPosizioneCentroide(Connection conn, BeanInformazioniFilamento beanFil) {
        String sql = "select avg(glog_cont), avg(glat_cont)" + 
                "from contorno " + 
                "where idfil = " + beanFil.getIdFil() + " and satellite = '" + 
                beanFil.getSatellite() + "'";
        Float gLogCont = null;
        Float gLatCont = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                gLogCont = rs.getFloat(1);
                gLatCont = rs.getFloat(2);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        beanFil.setgLonCentroide(gLogCont);
        beanFil.setgLatCentroide(gLatCont);
    }
    
    public void inserisciContorno(Connection conn, Contorno cont) {
        Statement stmt = null;
        String sql = "insert into contorno(idfil, satellite, glog_cont, glat_cont) " + 
                "values(" + cont.getIdFil() + 
                ", '" + cont.getSatellite() + "'" + 
                ", " + cont.getgLonCont() + 
                ", " + cont.getgLatCont() + ") on conflict do update";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inserisciContornoBatch(Connection conn, List<Contorno> lc) {
        String sql = "insert into contorno(idfil, satellite, glog_cont, glat_cont) " + 
                "values(?, ?, ?, ?)" +
                " on conflict (idfil, satellite, glog_cont, glat_cont) do update set " + 
                "idfil = excluded.idfil, " + 
                "satellite = excluded.satellite, " + 
                "glog_cont = excluded.glog_cont, " + 
                "glat_cont = excluded.glat_cont;";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Contorno> i = lc.iterator();
            while (i.hasNext()) {
                Contorno c = i.next();
                ps.setInt(1, c.getIdFil());
                ps.setString(2, c.getSatellite());
                ps.setFloat(3, c.getgLonCont());
                ps.setFloat(4, c.getgLatCont());
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
