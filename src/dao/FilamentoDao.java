package dao;

import entity.Filamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import util.DBAccess;

public class FilamentoDao {
    private static FilamentoDao instance;
    private final static String PASS = DBAccess.DBPASSW;
    private final static String USER = DBAccess.DBOWNER;
    private final static String DB_URL = DBAccess.DB_URL;

    public static synchronized FilamentoDao getInstance() {
        if(instance == null)
            instance = new FilamentoDao();
        return instance;
    }
    
    /**
     * utilizzato nell'import dei contorni per controllare il reference
     * utilizzato nell'import dei segmenti per controllare il reference
     * @param conn
     * @param idFil
     * @return 
     */
    public boolean queryEsistenzaFilamento(Connection conn, int idFil) {
        Statement stmt = null;
        boolean res = false;
        String sql = "select * from filamento where idfil = " + idFil;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
                res = true;
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
                ") on conflict (idfil) do update set " + 
                "name = excluded.name, " + 
                "total_flux = excluded.total_flux, " + 
                "mean_dens = excluded.mean_dens, " + 
                "mean_temp = excluded.mean_temp, " + 
                "ellipticity = excluded.ellipticity, " + 
                "contrast = excluded.contrast, " + 
                "satellite = excluded.satellite, " + 
                "instrument = excluded.instrument;";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
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
                "on conflict (idfil) do update set " + 
                "name = excluded.name, " + 
                "total_flux = excluded.total_flux, " + 
                "mean_dens = excluded.mean_dens, " + 
                "mean_temp = excluded.mean_temp, " + 
                "ellipticity = excluded.ellipticity, " + 
                "contrast = excluded.contrast, " + 
                "satellite = excluded.satellite, " + 
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
            conn.commit();
        } catch (SQLException e) {
            System.out.println("");
            e.printStackTrace();
        }
    }
}
