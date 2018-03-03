package gui;

import bean.BeanRichiestaFilamentiRegione;
import bean.BeanRispostaFilamenti;
import boundary.InterfacciaRicercaFilamentiRegione;
import entity.TipoFigura;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaFilamentiRegioneController {
    @FXML
    TextField longitudineCentroideText;
    @FXML
    TextField latitudineCentroideText;
    @FXML
    TextField dimensioneText;
    @FXML
    ComboBox<TipoFigura> tipoFiguraComboBox;
    @FXML
    Text text;
    
    private BeanRichiestaFilamentiRegione wrapInput() {
        String longitudineCentroideString = this.longitudineCentroideText.getText();
        if (longitudineCentroideString.isEmpty()) {
            text.setText("Inserire la longitudine del centroide"); 
            return null;
        }
        float longitudineCentroide;
        try {
            longitudineCentroide = Float.parseFloat(longitudineCentroideString);
        } catch (NumberFormatException e) {
            text.setText("La longitudine inserita non e' un numero");
            return null;
        }
        
        String latitudineCentroideString = this.latitudineCentroideText.getText();
        if (latitudineCentroideString.isEmpty()) {
            text.setText("Inserire la latitudine del centroide"); 
            return null;
        }
        float latitudineCentroide;
        try {
            latitudineCentroide = Float.parseFloat(latitudineCentroideString);
        } catch (NumberFormatException e) {
            text.setText("La latitudine inserita non e' un numero");
            return null;
        }
        
        String dimensioneString = this.dimensioneText.getText();
        if (dimensioneString.isEmpty()) {
            text.setText("Inserire la dimensione della figura"); 
            return null;
        }
        float dimensione;
        try {
            dimensione = Float.parseFloat(dimensioneString);
        } catch (NumberFormatException e) {
            text.setText("La dimensione inserita non e' un numero");
            return null;
        }
        
        TipoFigura tipoFigura = tipoFiguraComboBox.getValue();
        if (tipoFigura == null) {
            text.setText("Inserire il tipo di figura");
            return null;
        }
        BeanRichiestaFilamentiRegione beanRichiesta = 
                new BeanRichiestaFilamentiRegione(longitudineCentroide, 
                        latitudineCentroide, dimensione, tipoFigura);
        return beanRichiesta;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanRichiestaFilamentiRegione beanRichiesta = this.wrapInput();
        if (beanRichiesta != null) {
            InterfacciaRicercaFilamentiRegione boundaryFilamentiRegione = 
                    new  InterfacciaRicercaFilamentiRegione(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaFilamenti beanRisposta = boundaryFilamentiRegione.ricercaFilamentiRegione(beanRichiesta);
//            // effettuare controlli sulla risposta e passare bean al secondo controller
//            RisultatiRicercaContrastoEllitticitaController risultatiController = 
//                    new RisultatiRicercaContrastoEllitticitaController(); 
//            ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCACONTRASTOELLITTICITA, risultatiController);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
        @FXML
    void initialize() {
        tipoFiguraComboBox.setItems(FXCollections.observableArrayList(TipoFigura.values()));
    }
}
