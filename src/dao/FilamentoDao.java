package dao;

import bean.BeanIdFilamento;
import bean.BeanInformazioniFilamento;
import bean.BeanRichiestaContrastoEllitticita;
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
import static util.DBAccess.UNIQUE_VIOLATION;

public class FilamentoDao {
    private static FilamentoDao instance;

    public static synchronized FilamentoDao getInstance() {
        if(instance == null)
            instance = new FilamentoDao();
        return instance;
    }
    
    /**
     * utilizzato per la ricerca di filamenti in base a contrasto e range di ellitticita'
     * 
     * @param conn
     * @return numero di filamenti totali nel DB
     */
    public int queryNumeroFilamenti(Connection conn) {
        String sql = "select count(*) " + 
                "from filamento ";
        Integer totaleFilamenti = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                totaleFilamenti = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totaleFilamenti;
    }
    
    /**
     * utilizzato nella ricerca dei filamenti in base a contrasto e range di ellitticita'
     * 
     * @param beanRichiesta
     * @return 
     */
    public List<Filamento> queryFilamentoContrastoEllitticita(Connection conn, BeanRichiestaContrastoEllitticita beanRichiesta) {
        String sql = "select idfil, nome, flusso_totale, dens_media, temp_media, ellitticita, contrasto, satellite, strumento " + 
                "from filamento " + 
                "where contrasto > " + beanRichiesta.getContrasto() + " and " + 
                beanRichiesta.getInizioIntervalloEllitticita() + " <= ellitticita and " + 
                beanRichiesta.getFineIntervalloEllitticita() + ">= ellitticita";
        List<Filamento> listaFilamenti = new ArrayList<>();
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
                        densMedia, tempMedia, ellitticita, contrasto, satellite, 
                        strumento);
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
     * utilizzato per recupero informazioni su filamento
     * 
     * un filamento puo' essere identificato tramite la coppia nome-satellite 
     * oppure id-satellite, questo metodo si occupa di convertire il nome di 
     * un filamento nel suo id
     * 
     * @param conn
     * @param beanFil
     * @return 
     */
    public boolean queryIdFilamento(Connection conn, BeanInformazioniFilamento beanFil) {
        String sql = "select idfil from filamento where nome = '" + beanFil.getNome() + 
                "' and satellite = '" + beanFil.getSatellite() + "'";
        boolean res = true;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt(1);
                beanFil.setIdFil(id);
            } else {
                res = false;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * utilizzato per recupero informazioni su filamento
     * utilizzato per la distanza delle stelle interne ad un filamento
     * 
     * @param conn
     * @param idFil
     * @return 
     */
    public boolean queryEsistenzaFilamento(Connection conn, BeanIdFilamento idFil) {
        int id = idFil.getIdFil();
        String satellite = idFil.getSatellite();
        Statement stmt = null;
        boolean res = false;
        String sql = "select * from filamento where idfil = " + 
                id + " and satellite = '" + satellite + "'";
        try {
            stmt = conn.createStatement();
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
     * utilizzato per l'import
     * questo metodo viene chiamato piu' volte dal controller perche' i 
     * dati letti dal file potrebbero eccedere la memoria assegnata 
     * al programma
     * 
     * sara' il controller a fare il commit dell'inserimento 
     * dopo l'ultimo blocco
     * 
     * @param conn
     * @param lf 
     */
    public boolean inserisciFilamentoBatch(Connection conn, List<Filamento> lf) {
        boolean res = true;
        String sql = "insert into filamento(idfil, nome, flusso_totale, " + 
                "dens_media, temp_media, ellitticita, contrasto, satellite, " + 
                "strumento) values (?, ?, ?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idfil, satellite) do update set " + 
                "nome = excluded.nome, " + 
                "flusso_totale = excluded.flusso_totale, " + 
                "dens_media = excluded.dens_media, " + 
                "temp_media = excluded.temp_media, " + 
                "ellitticita = excluded.ellitticita, " + 
                "contrasto = excluded.contrasto, " +
                "strumento = excluded.strumento;";
        try {
//            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Filamento> i = lf.iterator();
            while (i.hasNext()) {
                Filamento f = i.next();
                ps.setInt(1, f.getIdFil());
                ps.setString(2, f.getNome());
                ps.setDouble(3, f.getFlussoTotale());
                ps.setDouble(4, f.getDensMedia());
                ps.setDouble(5, f.getTempMedia());
                ps.setDouble(6, f.getEllitticita());
                ps.setDouble(7, f.getContrasto());
                ps.setString(8, f.getSatellite());
                ps.setString(9, f.getStrumento());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
//            conn.commit();
        } catch (SQLException e) {
            if (e.getSQLState().equals(FOREIGN_KEY_VIOLATION)) {
                res = false;
                System.out.println("violazione f.k. in inserimento filamenti");
            } else if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                res = false;
                System.out.println("violazione vincolo unique in inserimento filamenti");
            }
            else
                e.printStackTrace();
        }
        return res;
    }
}
