package bean;

import entity.TipoFileCsv;
import java.io.File;

public class BeanRichiestaImport {
    private File fileSelezionato;
    private TipoFileCsv tipo;

    public BeanRichiestaImport(File fileSelezionato, TipoFileCsv tipo) {
        this.fileSelezionato = fileSelezionato;
        this.tipo = tipo;
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
    
}
