package dao;

import bean.BeanIdFilamento;
import bean.BeanIdStella;
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
import static util.DBAccess.FOREIGN_KEY_VIOLATION;

public class ContornoDao {
    private static ContornoDao instance;

    public static synchronized ContornoDao getInstance() {
        if(instance == null)
            instance = new ContornoDao();
        return instance;
    }
    
    /**
     * questo metodo serve per aggiornare la tabella che rappresenta la 
     * relazione di appartenenza tra le stelle e i filamenti 
     * 
     * prima cerca quali sono gli id dei filamenti nel DB
     * poi per ciascuno di questi filamenti prende i punti del contorno e 
     * itera sulle stelle del DB per decidere se sono interne o esterne
     * 
     * il commit della transazione viene fatto dal metodo che ne ha 
     * invocato l'esecuzione (importContorni o importStelle) che comunque 
     * devono ancora fare il commit per gli inserimenti sulla stessa tranzazione 
     * 
     * @param conn 
     */
    public void aggiornamentoStellaFilamento(Connection conn) {
        List<BeanIdFilamento> listaId = this.queryIdFilamentiContorno(conn);
//System.out.println("numId: " + listaId.size());
        String sql = "insert into stella_filamento(idstella, satellite_stella, idfil, satellite_filamento) values (?, ?, ?, ?) " + 
                "on conflict (idstella, satellite_stella, idfil, satellite_filamento) do update set " + 
                "idstella = excluded.idstella, " + 
                "satellite_stella = excluded.satellite_stella, " + 
                "idfil = excluded.idfil, " + 
                "satellite_filamento = excluded.satellite_filamento";
        Iterator<BeanIdFilamento> i = listaId.iterator();
        try {
//            conn.setAutoCommit(false);
//            Connection conn2 = DBAccess.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            while (i.hasNext()) {
                BeanIdFilamento idFil = i.next();
                List<Contorno> puntiContorno = this.queryPuntiContornoFilamento(conn, idFil);
//System.out.println("listCon: " + puntiContorno.size());
                StellaDao stellaDao = StellaDao.getInstance();
                List<BeanIdStella> listaIdStelle = stellaDao.queryIdStelleContornoFilamento(conn, puntiContorno);
//System.out.println("idStelle: " + listaIdStelle.size());
                Iterator<BeanIdStella> iS = listaIdStelle.iterator();
                while (iS.hasNext()) {
                    BeanIdStella idS = iS.next();
                    ps.setInt(1, idS.getIdStella());
                    ps.setString(2, idS.getSatellite());
                    ps.setInt(3, idFil.getIdFil());
                    ps.setString(4, idFil.getSatellite());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
            ps.close();
//            conn.commit();
//            DBAccess.getInstance().closeConnection(conn2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @return 
     */
    public List<Filamento> queryFilamentiInterniCerchio(Connection conn, 
            BeanRichiestaFilamentiRegione beanRichiesta) {
        double longCentr = beanRichiesta.getLongCentroide();
        double latiCentr = beanRichiesta.getLatiCentroide();
        double dim = beanRichiesta.getDimensione();
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
                double total_flux = rs.getDouble("total_flux");
                double mean_dens = rs.getDouble("mean_dens");
                double mean_temp = rs.getDouble("mean_temp");
                double ellipticity = rs.getDouble("ellipticity");
                double contrast = rs.getDouble("contrast");
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
        double dist = beanRichiesta.getDimensione() / 2;
        double longMax = beanRichiesta.getLongCentroide() + dist;
        double longMin = beanRichiesta.getLongCentroide() - dist;
        double latiMax = beanRichiesta.getLatiCentroide() + dist;
        double latiMin = beanRichiesta.getLatiCentroide() - dist;
        
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
                double total_flux = rs.getDouble("total_flux");
                double mean_dens = rs.getDouble("mean_dens");
                double mean_temp = rs.getDouble("mean_temp");
                double ellipticity = rs.getDouble("ellipticity");
                double contrast = rs.getDouble("contrast");
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
     * utilizzato per l'aggiornamento della tabella stella-filamento invocata quando si fa l'import di file di contorni oppure stelle
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * 
     * ricerca quali sono gli id dei filamenti nel DB che hanno dei contorni 
     * gli id vengono ricercati nella tabella dei contorni piuttosto che in quella dei filamenti perche' 
     * potrebbero esistere dei filamenti per i quali non e' ancora stato inserito nel DB il corrispettivo contorno e
     * per i filamenti di cui non si conosce il contorno non ha senso cercare quali siano le stelle interne 
     * 
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
     * 
     * ricerca quali sono i punti che formano il contorno di un filamento
     * 
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
                double gLonCont = rs.getDouble("glog_cont");
                double gLatCont = rs.getDouble("glat_cont");
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
        Double maxGLogCont = null;
        Double minGLogCont = null;
        Double maxGLatCont = null;
        Double minGLatCont = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                maxGLogCont = rs.getDouble(1);
                maxGLatCont = rs.getDouble(2);
                minGLogCont = rs.getDouble(3);
                minGLatCont = rs.getDouble(4);
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
        Double gLogCont = null;
        Double gLatCont = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                gLogCont = rs.getDouble(1);
                gLatCont = rs.getDouble(2);
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
    
    /**
     * utilizzato per l'import
     * questo metodo viene chiamato piu' volte dal controller perche' i 
     * dati letti dal file potrebbero eccedere la memoria assegnata 
     * al programma
     * 
     * sara' il controller a fare il commit dell'inserimento 
     * dopo l'ultimo blocco
     * 
     * @param conn
     * @param lc 
     */
    public boolean inserisciContornoBatch(Connection conn, List<Contorno> lc) {
        boolean res = true;
        String sql = "insert into contorno(idfil, satellite, glog_cont, glat_cont) " + 
                "values(?, ?, ?, ?)" +
                " on conflict (idfil, satellite, glog_cont, glat_cont) do update set " + 
                "idfil = excluded.idfil, " + 
                "satellite = excluded.satellite, " + 
                "glog_cont = excluded.glog_cont, " + 
                "glat_cont = excluded.glat_cont;";
        try {
//            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Contorno> i = lc.iterator();
            while (i.hasNext()) {
                Contorno c = i.next();
                ps.setInt(1, c.getIdFil());
                ps.setString(2, c.getSatellite());
                ps.setDouble(3, c.getgLonCont());
                ps.setDouble(4, c.getgLatCont());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
//            conn.commit();
        } catch (SQLException e) {
            if (e.getSQLState().equals(FOREIGN_KEY_VIOLATION)) {
                System.out.println("fk violation in contornoDao batch");
                res = false;
            } else {
                e.printStackTrace();
            }
        }
        return res;
    }
}
