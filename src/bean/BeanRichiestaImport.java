package bean;

import entity.TipoFileCsv;
import java.io.File;

public class BeanRichiestaImport {
    private File fileSelezionato;
    private TipoFileCsv tipo;
    private String satellite;

    public BeanRichiestaImport(File fileSelezionato, TipoFileCsv tipo) {
        this.fileSelezionato = fileSelezionato;
        this.tipo = tipo;
    }
    
    public BeanRichiestaImport(File fileSelezionato, TipoFileCsv tipo, 
            String satellite) {
        this.fileSelezionato = fileSelezionato;
        this.tipo = tipo;
        this.satellite = satellite;
    }
    
    public File getFileSelezionato() {
        return fileSelezionato;
    }

    public void setFileSelezionato(File fileSelezionato) {
        this.fileSelezionato = fileSelezionato;
    }

    public TipoFileCsv getTipo() {
        return tipo;
    }

    public void setTipo(TipoFileCsv tipo) {
        this.tipo = tipo;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }
    
}
