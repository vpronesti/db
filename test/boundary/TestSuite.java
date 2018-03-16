package boundary;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(value = Suite.class)
@SuiteClasses(value = {
    InterfacciaUtenteLoginTest.class, // REQ-1
        // REQ-2
//    InterfacciaImportCsvTest.class, // REQ-3.1 4
    InterfacciaRegistrazioneUtenteTest.class, // REQ-3.2
    InterfacciaInserimentoSatelliteTest.class, // REQ-3.3
    InterfacciaInserimentoNomeStrumentoTest.class, // REQ-3.4
    InterfacciaInserimentoBandaStrumentoTest.class, // REQ-3.4
    InterfacciaRecuperoInformazioniDerivateFilamentoTest.class, // REQ-5
    InterfacciaRicercaContrastoEllitticitaInputTest.class, // REQ-6
    InterfacciaRicercaContrastoEllitticitaOutputTest.class, // REQ-6
    InterfacciaRicercaNumeroSegmentiInputTest.class, // REQ-7
    InterfacciaRicercaNumeroSegmentiOutputTest.class, // REQ-7
    InterfacciaRicercaFilamentiRegioneInputTest.class, // REQ-8
    InterfacciaRicercaFilamentiRegioneOutputTest.class, // REQ-8
    InterfacciaRicercaStelleFilamentoTest.class, // REQ-9
    InterfacciaRicercaStelleRegioneTest.class, // REQ-10
    InterfacciaRicercaDistanzaSegmentoContornoTest.class, // REQ-11
    InterfacciaRicercaDistanzaStellaFilamentoTest.class // REQ-12
})
public class TestSuite {
    
}
