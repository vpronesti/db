package boundary;

import bean.BeanRichiestaImport;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * test per il requisito funzionale n 3.1 e 4
 */
@RunWith(value = Parameterized.class)
public class InterfacciaImportCsvTest {
    private final boolean expected;
    private final String userId;
    private File fileSelezionato;
    private TipoFileCsv tipo;

    
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
//            ok ma mancano dei filamenti controllare che non ci siano duplicati
//            {true, "a", new File("filamenti_Herschel.csv"), TipoFileCsv.FILAMENTO},
//            {true, "a", new File("filamenti_Spitzer.csv"), TipoFileCsv.FILAMENTO},
            
//            {true, "a", new File("contorni_filamenti_Herschel.csv"), TipoFileCsv.CONTORNO},
            {true, "a", new File("contorni_filamenti_Spitzer.csv"), TipoFileCsv.CONTORNO},
            
//            Ok pg non fa vedere la tab
//            {true, "a", new File("scheletro_filamenti_Herschel.csv"), TipoFileCsv.SEGMENTO},
//            {true, "a", new File("scheletro_filamenti_Spitzer.csv"), TipoFileCsv.SEGMENTO},
            
//            ok
//            {true, "a", new File("stelle_Herschel.csv"), TipoFileCsv.STELLA},
//            {false, "a", new File("stelle_Spitzer.csv"), TipoFileCsv.STELLA} // gestire meglio il caso di file non esistente
        });
    }
    
    public InterfacciaImportCsvTest(boolean expected, 
            String userId, File fileSelezionato, TipoFileCsv tipo) {
        this.expected = expected; 
        this.userId = userId;
        this.fileSelezionato = fileSelezionato;
        this.tipo = tipo;
    }
    
    @Test
    public void testImport() {
        InterfacciaImportCsv interfacciaImportCsv = 
                new InterfacciaImportCsv("a");
        BeanRichiestaImport beanRichiesta = 
                new BeanRichiestaImport(this.fileSelezionato, this.tipo);
        boolean res = true;
        try {
            res = interfacciaImportCsv.importaCsv(beanRichiesta);
        } catch (FormatoFileNonSupportatoException e) {
            res = false;
        }
        assertEquals("errore nella definizione", res, expected);
    }
}
