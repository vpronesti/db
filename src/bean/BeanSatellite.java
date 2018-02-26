package bean;

import java.time.LocalDate;

public class BeanSatellite {
    private String nome;
    private LocalDate primaOsservazione;
    private LocalDate termineOperazione;
    private String agenzia;
    
    public BeanSatellite(String nome) {
        this.nome = nome;
    }
    
    public BeanSatellite(String nome, LocalDate primaOsservazione, 
            LocalDate termineOperazione, String agenzia) {
        this.nome = nome;
        this.primaOsservazione = primaOsservazione;
        this.termineOperazione = termineOperazione;
        this.agenzia = agenzia;
        
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
