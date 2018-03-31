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
import static util.DBAccess.FOREIGN_KEY_VIOLATION;
import static util.DBAccess.UNIQUE_VIOLATION;

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
     * utilizzato per la ricerca delle stelle in una regione rettangolare
     * 
     * interroga la tabella stella_filamento del DB per 
     * vedere se una stella fa parte di un filamento
     * 
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
        return res;
    }
    
    /**
     * utilizzato per la ricerca delle stelle all'interno di un filamento
     * utilizzato per la distanza delle stelle interne ad un filamento
     * 
     * interroga la tabella stella_filamento per cercare quali sono le 
     * stelle che appartengono ad un determinato filamento
     * 
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
                String tipoSt = rs.getString("tipo_st");
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
     * utilizzato per la ricerca delle stelle in una regione rettangolare
     * 
     * restituisce la lista di stelle che si trovano all'interno di una 
     * determinata regione
     * 
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

    /**
     * utilizzato per l'aggiornamento della tabella stella-filamento invocata quando si fa l'import di file di contorni oppure stelle
     * 
     * questo metodo viene invocato per ogni filamento, scandisce tutte le 
     * stelle presenti nel DB per vedere se sono interne o esterne
     * 
     * @param conn
     * @param listaPunti insieme di punti che costituiscono il contorno di un singolo filamento
     * @return lista di id delle stelle interne la filamento
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
     * @param ls 
     */
    public boolean inserisciStellaBatch(Connection conn, List<Stella> ls) {
        boolean res = true;
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
//            conn.setAutoCommit(false);
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
//            conn.commit();
        } catch (SQLException e) {
            if (e.getSQLState().equals(FOREIGN_KEY_VIOLATION)) {
                System.out.println("fk violation in stellaDao batch");
                res = false;
            } else if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                System.out.println("violazione vincolo unique in stellaDao batch");
                res = false;
            } else {
                e.printStackTrace();
            }
        }
        return res;
    }
}
