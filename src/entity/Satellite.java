package entity;

import bean.BeanSatellite;
import dao.SatelliteDao;
import java.sql.Connection;
import java.time.LocalDate;
import util.DBAccess;

public class Satellite {
    private String nome;
    private LocalDate primaOsservazione;
    private LocalDate termineOperazione;
    private String agenzia;
    
    public Satellite(String nome, LocalDate primaOsservazione, 
            LocalDate termineOperazione, String agenzia) {
        this.nome = nome;
        this.primaOsservazione = primaOsservazione;
        this.termineOperazione = termineOperazione;
        this.agenzia = agenzia;
        
    }
    
    public boolean inserisciSatellite() {
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        BeanSatellite beanSatellite = new BeanSatellite(nome, 
                primaOsservazione, termineOperazione, agenzia);
        Connection conn = DBAccess.getInstance().getConnection();
        boolean res;
        if (!satelliteDao.queryEsistenzaSatellite(conn, beanSatellite)){
            satelliteDao.inserisciSatellite(conn, beanSatellite);
            res = true;
        } else {
            res = false;
        }
        DBAccess.getInstance().closeConnection(conn);
        return res;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getPrimaOsservazione() {
        return primaOsservazione;
    }

    public void setPrimaOsservazione(LocalDate primaOsservazione) {
        this.primaOsservazione = primaOsservazione;
    }

    public LocalDate getTermineOperazione() {
        return termineOperazione;
    }

    public void setTermineOperazione(LocalDate termineOperazione) {
        this.termineOperazione = termineOperazione;
    }

    public String getAgenzia() {
        return agenzia;
    }

    public void setAgenzia(String agenzia) {
        this.agenzia = agenzia;
    }
    

}
