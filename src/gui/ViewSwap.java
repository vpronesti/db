package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ViewSwap {
    protected static String LOGIN = "logIn.fxml";
    protected static String MENU = "menu.fxml";
    protected static String REGISTRAUTENTE = "registraUtente.fxml";
    protected static String IMPORTACSV = "importaCsv.fxml";
    protected static String SATELLITEIMPORTACSV = "satelliteImportaCsv.fxml";
    protected static String INSERISCISATELLITE = "inserisciDatiSatellite.fxml";
    protected static String INSERISCISTRUMENTO = "inserisciDatiStrumento.fxml";
    protected static String INSERISCINOMESTRUMENTO = "inserisciNomeStrumento.fxml";
    protected static String INSERISCIBANDASTRUMENTO = "inserisciBandaStrumento.fxml";
    protected static String RECUPEROINFORMAZIONIFILAMENTO = "recuperoInformazioniFilamento.fxml";
    protected static String RICERCACONTRASTOELLITTICITA = "ricercaContrastoEllitticita.fxml";
    protected static String RISULTATIRICERCACONTRASTOELLITTICITA = "risultatiRicercaContrastoEllitticita.fxml";
    protected static String RICERCANUMEROSEGMENTI = "ricercaNumeroSegmenti.fxml";
    protected static String RISULTATIRICERCANUMEROSEGMENTI = "risultatiRicercaNumeroSegmenti.fxml";
    protected static String RICERCAFILAMENTIREGIONE = "ricercaFilamentiRegione.fxml";
    protected static String RISULTATIRICERCAFILAMENTIREGIONE = "risultatiRicercaFilamentiRegione.fxml";
    protected static String RICERCASTELLEFILAMENTO = "ricercaStelleFilamento.fxml";
    protected static String RICERCASTELLEREGIONE = "ricercaStelleRegione.fxml";
    protected static String RICERCADISTANZASEGMENTOCONTORNO = "ricercaDistanzaSegmentoContorno.fxml";
    protected static String RICERCADISTANZASTELLAFILAMENTO = "ricercaDistanzaStellaFilamento.fxml";
    protected static String RISULTATIRICERCADISTANZASTELLAFILAMENTO = "risultatiRicercaDistanzaStellaFilamento.fxml";
    
    protected static ViewSwap viewSwap;

    public static ViewSwap getInstance() {
        if (ViewSwap.viewSwap == null) {
            ViewSwap.viewSwap = new ViewSwap();
            return ViewSwap.viewSwap;
        } else {
            return ViewSwap.viewSwap;
        }
    }


    public void swap(ActionEvent event, String url) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Parent newRoot = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(newRoot));
    }

    public void swap(ActionEvent event, String url, Initializable controller) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        loader.setController(controller); //set controller to fxml from instance of controller passed
        Parent newRoot = loader.load();
        
        stage.setScene(new Scene(newRoot));
    }

}
