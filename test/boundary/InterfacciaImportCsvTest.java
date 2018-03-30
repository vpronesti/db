package boundary;

import bean.BeanRichiestaImport;
import bean.BeanUtente;
import dao.UtenteDao;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DBAccess;
import static util.DBAccess.AMMINISTRATORE;
import static util.DBAccess.NONREGISTRATO;
import static util.DBAccess.REGISTRATO;

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
            {true, AMMINISTRATORE, new File("filamenti_Herschel.csv"), TipoFileCsv.FILAMENTO, null},
            {true, AMMINISTRATORE, new File("filamenti_Spitzer.csv"), TipoFileCsv.FILAMENTO, null},
            // file non esistente
            {false, AMMINISTRATORE, new File("filamenti_Herschel!!!.csv"), TipoFileCsv.FILAMENTO, null},
            
            {true, AMMINISTRATORE, new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO, "Herschel"},
            {true, AMMINISTRATORE, new File("contorni_filamenti_Spitzer.csv"), TipoFileCsv.CONTORNO, "Spitzer"},
            // satellite non esistente
            {false, AMMINISTRATORE, new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO, "Herschel!!!"},
            // file non esistente
            {false, AMMINISTRATORE, new File("contorni_filamenti_Herschel!!!.csv"), TipoFileCsv.CONTORNO, "Spitzer"}, 
            
            {true, AMMINISTRATORE, new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO, "Herschel"},
            {true, AMMINISTRATORE, new File("scheletro_filamenti_Spitzer.csv"), TipoFileCsv.SEGMENTO, "Spitzer"},
            // satellite non esistente
            {false, AMMINISTRATORE, new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO, "Herschel!!!"},
            // file non esistente
            {false, AMMINISTRATORE, new File("scheletro_filamenti_Herschel!!!.csv"), TipoFileCsv.SEGMENTO, "Spitzer"}, 
            
            {true, AMMINISTRATORE, new File("stelle_Herschel.csv"), TipoFileCsv.STELLA, "Herschel"}, 
            // satellite non esistente
            {false, AMMINISTRATORE, new File("stelle_Herschel.csv"), TipoFileCsv.STELLA, "Herschel!!!"},
            // file non esistente
            {false, AMMINISTRATORE, new File("stelle_Spitzer.csv"), TipoFileCsv.STELLA, "Spitzer"},
                
            
            // i seguenti input vengono inseriti da utenti non amministratori quindi i test falliscono
            {false, REGISTRATO, new File("filamenti_Herschel.csv"), TipoFileCsv.FILAMENTO, null},
            {false, NONREGISTRATO, new File("filamenti_Herschel.csv"), TipoFileCsv.FILAMENTO, null},
            {false, REGISTRATO, new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO, "Herschel"},            
            {false, NONREGISTRATO, new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO, "Herschel"},
            {false, REGISTRATO, new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO, "Herschel"},
            {false, NONREGISTRATO, new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO, "Herschel"},
            {false, REGISTRATO, new File("stelle_Herschel.csv"), TipoFileCsv.STELLA, "Herschel"},            
            {false, NONREGISTRATO, new File("stelle_Herschel.csv"), TipoFileCsv.STELLA, "Herschel"}
            
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
    
    /**
     * questo test controlla: 
     *  che gli inserimenti siano consentiti solo agli amministratori, 
     *  che i satelliti a cui si fa riferimento esistano, 
     *  che i file che si prova ad importare esistano
     */
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
        } catch (FileNotFoundException e) {
            res = false;
        }
        assertEquals("errore", res && azioneConsentita, expected);
    }
}
