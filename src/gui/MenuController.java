package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController {
//    @FXML
//    Button PrenotazioneConId;
    @FXML
    Text text;
    
    @FXML
    protected void registraUtente(ActionEvent event) throws Exception {
        if (!LogInController.interfacciaUtenteLogin.getTipo().equals("Amministratore")) {
            text.setText("Azione consentita solo agli amministratori");
        } else {
            ViewSwap.getInstance().swap(event, ViewSwap.REGISTRAUTENTE);

        }
    }
    
    @FXML
    protected void inserisciSatellite(ActionEvent event) throws Exception {
        if (!LogInController.interfacciaUtenteLogin.getTipo().equals("Amministratore")) {
            text.setText("Azione consentita solo agli amministratori");
        } else {
            ViewSwap.getInstance().swap(event, ViewSwap.INSERISCISATELLITE);

        }
    }
    
    @FXML
    protected void importaCsv(ActionEvent event) throws Exception {
        if (!LogInController.interfacciaUtenteLogin.getTipo().equals("Amministratore")) {
            text.setText("Azione consentita solo agli amministratori");
        } else {
            ViewSwap.getInstance().swap(event, ViewSwap.IMPORTACSV);
        }
    }
    
    @FXML
    protected void inserisciDatiStrumento(ActionEvent event) throws Exception {
        if (!LogInController.interfacciaUtenteLogin.getTipo().equals("Amministratore")) {
            text.setText("Azione consentita solo agli amministratori");
        } else {
            ViewSwap.getInstance().swap(event, ViewSwap.INSERISCISTRUMENTO);

        }
    }
    
    @FXML
    protected void logOut(ActionEvent event) throws Exception {
        LogInController.interfacciaUtenteLogin.logOut();
        ViewSwap.getInstance().swap(event, ViewSwap.LOGIN);
    }
}
