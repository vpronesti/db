package gui;

import bean.BeanRichiestaImport;
import boundary.InterfacciaImportCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.FileNotFoundException;
import javafx.concurrent.Task;

public class TaskImport extends Task<Boolean> {
    InterfacciaImportCsv boundaryImportCsv;
    BeanRichiestaImport beanRichiestaImport;

    public TaskImport(InterfacciaImportCsv boundaryImportCsv, 
            BeanRichiestaImport beanRichiestaImport) {
        this.boundaryImportCsv = boundaryImportCsv;
        this.beanRichiestaImport = beanRichiestaImport;
    }
    
    @Override
    protected Boolean call() throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        boolean res = boundaryImportCsv.importaCsv(beanRichiestaImport);
        return res;
    }
}
