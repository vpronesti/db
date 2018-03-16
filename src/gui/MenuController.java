package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController {
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
    protected void recuperoInformazioniFilamento(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RECUPEROINFORMAZIONIFILAMENTO);
    }
    
    @FXML
    protected void ricercaContrastoElliticita(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCACONTRASTOELLITTICITA);
    }
    
    @FXML
    protected void ricercaFilamentoNumeroSegmenti(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCANUMEROSEGMENTI);
    }
    
    @FXML
    protected void ricercaFilamentiRegione(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCAFILAMENTIREGIONE);
    }
    
    @FXML
    protected void ricercaStelleFilamento(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCASTELLEFILAMENTO);
    }
    
    @FXML
    protected void ricercaStelleRegione(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCASTELLEREGIONE);
    }
    
    @FXML
    protected void distanzaSegmentoContorno(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCADISTANZASEGMENTOCONTORNO);
    }
    
    @FXML
    protected void distanzaStellaFilamento(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCADISTANZASTELLAFILAMENTO);
    }
    
    @FXML
    protected void logOut(ActionEvent event) throws Exception {
        LogInController.interfacciaUtenteLogin.logOut();
        ViewSwap.getInstance().swap(event, ViewSwap.LOGIN);
    }
}
