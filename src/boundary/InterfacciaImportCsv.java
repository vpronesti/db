package boundary;

import bean.BeanRichiestaImport;
import control.GestoreImportCsv;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;

/**
 * REQ-FN-3
 * REQ-FN-4
 */
public class InterfacciaImportCsv {
    private GestoreImportCsv controllerImportCsv;
    private String userId;
    
    public InterfacciaImportCsv(String userId) {
        this.userId = userId;
    }
    
    private boolean controllaBean(BeanRichiestaImport beanRichiesta) {
        boolean res = true;
        if (beanRichiesta.getFileSelezionato() == null || beanRichiesta.getFileSelezionato().toString().isEmpty())
            res = false;
        if (beanRichiesta.getTipo() == null)
            res = false;
        if ((beanRichiesta.getTipo() == TipoFileCsv.SEGMENTO || 
                beanRichiesta.getTipo() == TipoFileCsv.CONTORNO) && 
                (beanRichiesta.getSatellite() == null || beanRichiesta.getSatellite().isEmpty()))
            res = false;
        return res;
    }
    
    public boolean importaCsv(BeanRichiestaImport beanRichiesta) throws FormatoFileNonSupportatoException {
        if (this.controllaBean(beanRichiesta)) {
            controllerImportCsv = new GestoreImportCsv(this);
            return controllerImportCsv.importCsv(beanRichiesta);    
        } else {
            return false;
        }
    }
}
