package boundary;

import bean.BeanRichiestaImport;
import bean.BeanUtente;
import dao.UtenteDao;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;
import static util.UserId.AMMINISTRATORE;

/**
 * test per il requisito funzionale n 3.1 e 4
 */
@RunWith(value = Parameterized.class)
public class InterfacciaImportCsvTest {
    private final boolean expected;
    private final String userId;
    private File fileSelezionato;
    private TipoFileCsv tipo;
    private String satellite;

    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
//            {true, AMMINISTRATORE, new File("filamenti_Herschel.csv"), TipoFileCsv.FILAMENTO, null},
//            {true, AMMINISTRATORE, new File("filamenti_Spitzer.csv"), TipoFileCsv.FILAMENTO, null},
//            
//            {true, AMMINISTRATORE, new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO, "Herschel"},
//            {true, AMMINISTRATORE, new File("contorni_filamenti_Spitzer.csv"), TipoFileCsv.CONTORNO, "Spitzer"},
            
            {true, AMMINISTRATORE, new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO, "Herschel"},
//            {true, AMMINISTRATORE, new File("scheletro_filamenti_Spitzer.csv"), TipoFileCsv.SEGMENTO, "Spitzer"},
            
            {true, AMMINISTRATORE, new File("stelle_Herschel.csv"), TipoFileCsv.STELLA, null},
//            {false, AMMINISTRATORE, new File("stelle_Spitzer.csv"), TipoFileCsv.STELLA, null} // gestire meglio il caso di file non esistente
        });
    }
    
    public InterfacciaImportCsvTest(boolean expected, 
            String userId, File fileSelezionato, TipoFileCsv tipo, String satellite) {
        this.expected = expected; 
        this.userId = userId;
        this.fileSelezionato = fileSelezionato;
        this.tipo = tipo;
        this.satellite = satellite;
    }
    
    @Test
    public void testImport() {
        InterfacciaImportCsv interfacciaImportCsv = 
                new InterfacciaImportCsv(userId);
        BeanRichiestaImport beanRichiesta = 
                new BeanRichiestaImport(this.fileSelezionato, this.tipo, this.satellite);
        boolean res = true;
        Connection conn = DBAccess.getInstance().getConnection();
        boolean azioneConsentita = UtenteDao.getInstance().queryEsistenzaAmministratore(conn, new BeanUtente(this.userId));
        DBAccess.getInstance().closeConnection(conn);
        try {
            res = interfacciaImportCsv.importaCsv(beanRichiesta);
        } catch (FormatoFileNonSupportatoException e) {
            res = false;
        }
        assertEquals("errore", res && azioneConsentita, expected);
    }
}
