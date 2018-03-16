package dao;

import bean.BeanIdFilamento;
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

public class FilamentoDao {
    private static FilamentoDao instance;

    public static synchronized FilamentoDao getInstance() {
        if(instance == null)
            instance = new FilamentoDao();
        return instance;
    }
    
    /**
     * utilizzato per la ricerca dei filamenti in base al numero di segmenti SOSTITUITO
     * utilizzato per la ricerca di filamenti all'interno di una regione (cerchio o quadrato)
     * @param conn
     * @param idFil
     * @return 
     */
    public Filamento queryCampiFilamento(Connection conn, BeanIdFilamento idFil) {
        String sql = "select name, total_flux, mean_dens, mean_temp, ellipticity, contrast, satellite, instrument " + 
                "from filamento " + 
                "where idFil = " + idFil.getIdFil() + " and satellite = '" + idFil.getSatellite() + "'";
        Filamento fil = null;
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                double totalFlux = rs.getDouble("total_flux");
                double meanDens = rs.getDouble("mean_dens");
                double meanTemp = rs.getDouble("mean_temp");
                double ellipticity = rs.getDouble("ellipticity");
                double contrast = rs.getDouble("contrast");
                String satellite = rs.getString("satellite");
                String instrument = rs.getString("instrument");
                fil = new Filamento(idFil.getIdFil(), name, totalFlux, 
                        meanDens, meanTemp, ellipticity, contrast, satellite, 
                        instrument);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fil;
    }
    
    /**
     * utilizzato per la ricerca di filamenti in base a contrasto e range di ellitticita
     * @param conn
     * @return 
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
     * utilizzato nella ricerca dei filamenti in base a contrasto e range di ellitticita
     * @param beanRichiesta
     * @return 
     */
    public List<Filamento> queryFilamentoContrastoEllitticita(Connection conn, BeanRichiestaContrastoEllitticita beanRichiesta) {
        String sql = "select idfil, name, total_flux, mean_dens, mean_temp, ellipticity, contrast, satellite, instrument " + 
                "from filamento " + 
                "where contrast > " + beanRichiesta.getContrasto() + " and " + 
                beanRichiesta.getInizioIntervalloEllitticita() + " <= ellipticity and " + 
                beanRichiesta.getFineIntervalloEllitticita() + ">= ellipticity";
        List<Filamento> listaFilamenti = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idFil = rs.getInt("idfil");
                String name = rs.getString("name");
                double totalFlux = rs.getDouble("total_flux");
                double meanDens = rs.getDouble("mean_dens");
                double meanTemp = rs.getDouble("mean_temp");
                double ellipticity = rs.getDouble("ellipticity");
                double contrast = rs.getDouble("contrast");
                String satellite = rs.getString("satellite");
                String instrument = rs.getString("instrument");
                Filamento f = new Filamento(idFil, name, totalFlux, 
                        meanDens, meanTemp, ellipticity, contrast, satellite, 
                        instrument);
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
     * utilizzato nell'import dei contorni per controllare il reference
     * utilizzato nell'import dei segmenti per controllare il reference
     * utilizzato per recupero informazioni su filamento
     * utilizzato per la distanza delle stelle interne ad un filamento
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
     * @param conn
     * @param fil 
     */
    public void inserisciFilamento(Connection conn, Filamento fil) {
        Statement stmt = null;
        String sql = "insert into filamento(idfil, name, total_flux, " + 
                "mean_dens, mean_temp, ellipticity, contrast, satellite, " + 
                "instrument) values (" + 
                "" + fil.getIdFil() + ", " + 
                "'" + fil.getName() + "', " + 
                "" + fil.getTotalFlux() + ", " + 
                "" + fil.getMeanDens() + ", " + 
                "" + fil.getMeanTemp() + ", " + 
                "" + fil.getEllipticity() + ", " + 
                "" + fil.getContrast() + ", " + 
                "'" + fil.getSatellite() + "', " + 
                "'" + fil.getInstrument() + "'" + 
                ") on conflict (idfil, satellite) do update set " + 
                "name = excluded.name, " + 
                "total_flux = excluded.total_flux, " + 
                "mean_dens = excluded.mean_dens, " + 
                "mean_temp = excluded.mean_temp, " + 
                "ellipticity = excluded.ellipticity, " + 
                "contrast = excluded.contrast, " +
                "instrument = excluded.instrument;";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("");
            e.printStackTrace();
        }
    }
    
    /**
     * utilizzato per l'import
     * @param conn
     * @param lf 
     */
    public void inserisciFilamentoBatch(Connection conn, List<Filamento> lf) {
        String sql = "insert into filamento(idfil, name, total_flux, " + 
                "mean_dens, mean_temp, ellipticity, contrast, satellite, " + 
                "instrument) values (?, ?, ?, ?, ?, ?, ?, ?, ?) " + 
                "on conflict (idfil, satellite) do update set " + 
                "name = excluded.name, " + 
                "total_flux = excluded.total_flux, " + 
                "mean_dens = excluded.mean_dens, " + 
                "mean_temp = excluded.mean_temp, " + 
                "ellipticity = excluded.ellipticity, " + 
                "contrast = excluded.contrast, " +
                "instrument = excluded.instrument;";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            Iterator<Filamento> i = lf.iterator();
            while (i.hasNext()) {
                Filamento f = i.next();
                ps.setInt(1, f.getIdFil());
                ps.setString(2, f.getName());
                ps.setDouble(3, f.getTotalFlux());
                ps.setDouble(4, f.getMeanDens());
                ps.setDouble(5, f.getMeanTemp());
                ps.setDouble(6, f.getEllipticity());
                ps.setDouble(7, f.getContrast());
                ps.setString(8, f.getSatellite());
                ps.setString(9, f.getInstrument());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            conn.commit();
        } catch (SQLException e) {
            System.out.println("");
            e.printStackTrace();
        }
    }
}
