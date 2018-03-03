package dao;

import bean.BeanInformazioniFilamento;
import bean.BeanRichiestaFilamentiRegione;
import entity.Contorno;
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
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @return 
     */
    public List<Integer> queryIdFilamentiContorno(Connection conn) {
        String sql = "select distinct idfil from contorno";
        List<Integer> listaId = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                listaId.add(id);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaId;
    }
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * utilizzato per la distanza tra segmento e contorno 
     * @param conn
     * @param idFil
     * @return 
     */
    public List<Contorno> queryPuntiContornoFilamento(Connection conn, int idFil) {
        String sql = "select idfil, glog_cont, glat_cont " + 
                "from contorno " + 
                "where idfil = " + idFil;
        List<Contorno> listaContorni = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                float gLonCont = rs.getFloat("glog_cont");
                float gLatCont = rs.getFloat("glat_cont");
                Contorno c = new Contorno(idFil, gLonCont, gLatCont);
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
     * utilizzato per la ricerca dei filamenti che si trovano all'interno di una regione
     * @param conn
     * @param beanRichiesta 
     */
    public void queryFilamentiInCerchio(Connection conn, BeanRichiestaFilamentiRegione beanRichiesta) {
        String puntiContornoCerchio = "select idfil, glog_cont, glat_cont " + 
                "from contorno" + 
                "where (" + 
                "((glog_cont - " + beanRichiesta.getLongCentroide() + ")^2 + "  + 
                "(glat_cont - " + beanRichiesta.getLatiCentroide() + ")^2 ) < " + 
                beanRichiesta.getDimensione() + ")";
        String sql = "select idfil " + 
                "from contorno " + 
                "where ";
    }
    
    /**
     * utilizzato per recupero informazioni filamento
     * @param conn
     * @param beanFil 
     */
    public void queryEstensioneContorno(Connection conn, BeanInformazioniFilamento beanFil) {
        String sql = "select max(glog_cont), max(glat_cont), min(glog_cont), min(glat_cont)" + 
                "from contorno " + 
                "where idfil = " + beanFil.getIdFil();
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
                "where idfil = " + beanFil.getIdFil();
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
        String sql = "insert into contorno(idfil, glog_cont, glat_cont) " + 
                "values(" + cont.getIdFil() + 
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
        String sql = "insert into contorno(idfil, glog_cont, glat_cont) " + 
                "values(?, ?, ?)" +
                " on conflict (idfil, glog_cont, glat_cont) do update set " + 
                "idfil = excluded.idfil, " + 
                "glog_cont = excluded.glog_cont, " + 
                "glat_cont = excluded.glat_cont;";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Contorno> i = lc.iterator();
            while (i.hasNext()) {
                Contorno c = i.next();
                ps.setInt(1, c.getIdFil());
                ps.setFloat(2, c.getgLonCont());
                ps.setFloat(3, c.getgLatCont());
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
