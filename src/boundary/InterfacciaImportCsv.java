package boundary;

import bean.BeanRichiestaImport;
import control.GestoreImportCsv;
import exception.FormatoFileNonSupportatoException;

public class InterfacciaImportCsv {
    private GestoreImportCsv controllerImportCsv;
    private String userId;
    
    public InterfacciaImportCsv(String userId) {
        this.userId = userId;
    }
    
    public boolean importaCsv(BeanRichiestaImport beanRichiesta) throws FormatoFileNonSupportatoException {
        controllerImportCsv = new GestoreImportCsv(this);
//        try {
         return controllerImportCsv.importCsv(beanRichiesta);
//        } catch ()
    }
}
