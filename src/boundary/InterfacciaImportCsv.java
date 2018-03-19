package boundary;

import bean.BeanRichiestaImport;
import bean.BeanUtente;
import control.GestoreImportCsv;
import dao.UtenteDao;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import exception.ImpossibileAprireFileException;
import java.sql.Connection;
import util.DBAccess;

/**
 * REQ-FN-3.1
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
                beanRichiesta.getTipo() == TipoFileCsv.CONTORNO || 
                beanRichiesta.getTipo() == TipoFileCsv.STELLA) && 
                (beanRichiesta.getSatellite() == null || beanRichiesta.getSatellite().isEmpty()))
            res = false;
        return res;
    }
    
    public boolean importaCsv(BeanRichiestaImport beanRichiesta) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        if (azioneConsentita) {
            if (this.controllaBean(beanRichiesta)) {
                controllerImportCsv = new GestoreImportCsv(this);
                return controllerImportCsv.importCsv(beanRichiesta);    
            } 
        }
        return false;
    }
}
