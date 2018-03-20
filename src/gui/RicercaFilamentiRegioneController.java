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
        double longitudineCentroide;
        try {
            longitudineCentroide = Double.parseDouble(longitudineCentroideString);
        } catch (NumberFormatException e) {
            text.setText("La longitudine inserita non e' un numero");
            return null;
        }
        
        String latitudineCentroideString = this.latitudineCentroideText.getText();
        if (latitudineCentroideString.isEmpty()) {
            text.setText("Inserire la latitudine del centroide"); 
            return null;
        }
        double latitudineCentroide;
        try {
            latitudineCentroide = Double.parseDouble(latitudineCentroideString);
        } catch (NumberFormatException e) {
            text.setText("La latitudine inserita non e' un numero");
            return null;
        }
        
        String dimensioneString = this.dimensioneText.getText();
        if (dimensioneString.isEmpty()) {
            text.setText("Inserire la dimensione della figura"); 
            return null;
        }
        double dimensione;
        try {
            dimensione = Double.parseDouble(dimensioneString);
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
            if (beanRisposta.isAzioneConsentita()) {
                if (beanRisposta.isInputValido()) {
                    if (beanRisposta.getListaFilamenti().size() > 0) {
                        RisultatiRicercaFilamentiRegioneController risultatiController = new RisultatiRicercaFilamentiRegioneController(beanRisposta);
                        ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCAFILAMENTIREGIONE, risultatiController);
                    } else {
                        text.setText("Non ci sono filamenti nella regione specificata");
                    }
                } else {
                    text.setText("La dimensione deve essere positiva");
                }
            } else {
                text.setText("Azione non consentita");
            }
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
