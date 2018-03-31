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
     * per ogni punto dei contorni controlla se esiste un punto dei segmenti che 
     * occupa la stessa posizione 
     * @param conn
     * @return 
     */
    public boolean controlloSovrapposizioneContornoSegmento(Connection conn) {
        boolean res = false;
        String sql = "select glog_cont, glat_cont " + 
                "from contorno c1 " + 
                "where exists " + 
                    "(select * " + 
                    "from segmento s1 " + 
                    "where c1.glog_cont = s1.glon_se and c1.glat_cont = s1.glat_se)";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
//                double lon = rs.getDouble(1);
//                double lat = rs.getDouble(2);
//System.out.println("lon " + lon + " lat " + lat);
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
        String sql = "insert into stella_filamento(idstella, satellite_stella, idfil, satellite_filamento) values (?, ?, ?, ?) " + 
                "on conflict (idstella, satellite_stella, idfil, satellite_filamento) do update set " + 
                "idstella = excluded.idstella, " + 
                "satellite_stella = excluded.satellite_stella, " + 
                "idfil = excluded.idfil, " + 
                "satellite_filamento = excluded.satellite_filamento";
        /**
         * per ciascuno dei filamenti di cui si conosce il contorno
         *      si selezionano i punti del contorno
         *      per ogni insieme di punti del contorno del filamento
         *          per ogni stella verifica se e' interna
         */
        Iterator<BeanIdFilamento> i = listaId.iterator();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            while (i.hasNext()) {
                BeanIdFilamento idFil = i.next();
                List<Contorno> puntiContorno = this.queryPuntiContornoFilamento(conn, idFil);
                StellaDao stellaDao = StellaDao.getInstance();
                List<BeanIdStella> listaIdStelle = stellaDao.queryIdStelleContornoFilamento(conn, puntiContorno);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * 
     * seleziona dalla tabella dei contorni gli id dei filamenti che hanno 
     * tutti i punti interni alla regione specificata e fa il join con la 
     * tabella dei filamenti per ottenere le informazioni da mostrare all'utente
     * 
     * @param conn
     * @return 
     */
    public List<Filamento> queryFilamentiInterniCerchio(Connection conn, 
            BeanRichiestaFilamentiRegione beanRichiesta) {
        double longCentr = beanRichiesta.getLongCentroide();
        double latiCentr = beanRichiesta.getLatiCentroide();
        double dim = beanRichiesta.getDimensione();
        String sql = "select filamento.idfil, nome, flusso_totale, dens_media, temp_media, ellitticita, contrasto, filamento.satellite, strumento " + 
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
     * 
     * seleziona dalla tabella dei contorni gli id dei filamenti che hanno 
     * tutti i punti interni alla regione specificata e fa il join con la 
     * tabella dei filamenti per ottenere le informazioni da mostrare all'utente
     * 
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
        
        String sql = "select filamento.idfil, nome, flusso_totale, dens_media, temp_media, ellitticita, contrasto, filamento.satellite, strumento " + 
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
     * utilizzato per recupero informazioni su filamento
     * 
     * la ricerca viene effettuata su una vista
     * 
     * @param conn
     * @param beanFil 
     */
    public void queryInfoContornoFilamento(Connection conn, BeanInformazioniFilamento beanFil) {
        String sql = "select min_glon_estensione, max_glon_estensione, " + 
                        "min_glat_estensione, max_glat_estensione, glon_centroide, glat_centroide " + 
                "from contorno_estensione_centroide " + 
                "where idfil = " + beanFil.getIdFil() + " and satellite = '" + beanFil.getSatellite() + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                double minLonEstensione = rs.getDouble("min_glon_estensione");
                double maxLonEstensione = rs.getDouble("max_glon_estensione");
                double minLatEstensione = rs.getDouble("min_glat_estensione");
                double maxLatEstensione = rs.getDouble("max_glat_estensione");
                double lonCentroide = rs.getDouble("glon_centroide");
                double latCentroide = rs.getDouble("glat_centroide");
                beanFil.setMinGLonContorno(minLonEstensione);
                beanFil.setMaxGLonContorno(maxLonEstensione);
                beanFil.setMinGLatContorno(minLatEstensione);
                beanFil.setMaxGLatContorno(maxLatEstensione);
                beanFil.setgLonCentroide(lonCentroide);
                beanFil.setgLatCentroide(latCentroide);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
