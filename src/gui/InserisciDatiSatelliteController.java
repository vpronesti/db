package gui;

import bean.BeanSatellite;
import boundary.InterfacciaInserimentoSatellite;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InserisciDatiSatelliteController {
    @FXML
    TextField nomeText;
    @FXML
    DatePicker primaOsservazioneDate;
    @FXML
    DatePicker termineOperazioneDate;
    @FXML
    TextField agenziaText;
    @FXML
    Text text;
    
    private BeanSatellite wrapSatellite() {
        String nome = this.nomeText.getText();
        if (nome.isEmpty()) {
            text.setText("Inserire il nome del satellite"); 
            return null;
        }
        LocalDate primaOsservazione = this.primaOsservazioneDate.getValue();
        if (primaOsservazione == null) {
            text.setText("Inserire la data della prima osservazione");
            return null;
        }
        LocalDate termineOperazione = this.termineOperazioneDate.getValue();
        String agenzia = this.agenziaText.getText();
        if (agenzia.isEmpty()) {
            text.setText("Inserire il nome dell'agenzia"); 
            return null;
        }
        BeanSatellite beanSatellite = new BeanSatellite(nome, 
                primaOsservazione, termineOperazione, agenzia);
        return beanSatellite;
    }
    
    @FXML
    protected void inserisci(ActionEvent event) throws Exception {
        BeanSatellite beanSatellite = this.wrapSatellite();
        if (beanSatellite != null) {
            InterfacciaInserimentoSatellite boundaryInserimentoSatellite = new
                    InterfacciaInserimentoSatellite(LogInController.interfacciaUtenteLogin.getUserId());
            if (boundaryInserimentoSatellite.inserisciSatellite(beanSatellite)) {
                text.setText("Satellite inserito correttamente");
            } else {
                text.setText("Impossibilile inserire il satellite");
            }
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
