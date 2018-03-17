package gui;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
import boundary.InterfacciaRicercaStelleRegione;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RicercaStelleRegioneController {
    @FXML
    TextField longitudineCentroideText;
    @FXML
    TextField latitudineCentroideText;
    @FXML
    TextField latoAText;
    @FXML
    TextField latoBText;
    @FXML
    Text text;
    
    private BeanRichiestaStelleRegione wrapInput() {
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
        String latoAString = this.latoAText.getText();
        if (latoAString.isEmpty()) {
            text.setText("Inserire il lato del rettangolo"); 
            return null;
        }
        double latoA;
        try {
            latoA = Double.parseDouble(latoAString);
        } catch (NumberFormatException e) {
            text.setText("Il lato inserito non e' un numero");
            return null;
        }
        String latoBString = this.latoBText.getText();
        if (latoBString.isEmpty()) {
            text.setText("Inserire il lato del rettangolo"); 
            return null;
        }
        double latoB;
        try {
            latoB = Double.parseDouble(latoBString);
        } catch (NumberFormatException e) {
            text.setText("Il lato inserito non e' un numero");
            return null;
        }
        
        BeanRichiestaStelleRegione beanRichiesta = 
                new BeanRichiestaStelleRegione(longitudineCentroide, latitudineCentroide, latoA, latoB);
        return beanRichiesta;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanRichiestaStelleRegione beanRichiesta = this.wrapInput();
        if (beanRichiesta != null) {
            InterfacciaRicercaStelleRegione boundaryStelleRegione = 
                    new  InterfacciaRicercaStelleRegione(LogInController.interfacciaUtenteLogin.getUserId());
            
            
//            Task<BeanRispostaStelleRegione> task = new Task<BeanRispostaStelleRegione>() {
//                @Override
//                protected BeanRispostaStelleRegione call() throws Exception {
//                    BeanRispostaStelleRegione beanRisposta = boundaryStelleRegione.ricercaStelleRegione(beanRichiesta);
//                    return beanRisposta;
//                }
//            };
//            Stage loadingDialog = new Stage(); // new stage
//                loadingDialog.initModality(Modality.APPLICATION_MODAL);
//                // Defines a modal window that blocks events from being
//                // delivered to any other application window.
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                loadingDialog.initOwner(stage);
//                VBox vb = new VBox(20);
//                Scene dialogScene = new Scene(vb, 300, 200);
//                loadingDialog.setScene(dialogScene);
//                loadingDialog.show();
//            task.setOnRunning((e) -> loadingDialog.show());
//            task.setOnSucceeded((e) -> {
//                loadingDialog.hide();
//                try {
//                    BeanRispostaStelleRegione beanRisposta = task.get();
//                    // process return value again in JavaFX thread
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(RicercaStelleRegioneController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ExecutionException ex) {
//                    Logger.getLogger(RicercaStelleRegioneController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            task.setOnFailed((e) -> {
//            // eventual error handling by catching exceptions from task.get()  
//             });
//            new Thread(task).start();
//            BeanRispostaStelleRegione beanRisposta = task.get();
            


            BeanRispostaStelleRegione beanRisposta = boundaryStelleRegione.ricercaStelleRegione(beanRichiesta);
            String res = "";
            if (beanRisposta.isAzioneConsentita()) {
            
                res = "Percentuale stelle interne ai filamenti: " + beanRisposta.getPercentualeStelleInterne() + "%\n";
                Set<String> tipiStelleInterne = beanRisposta.getTipiStellePercentualeInterne().keySet();
                for (String s : tipiStelleInterne) {
                    res += "\tPercentuale stelle " + s + ": " + beanRisposta.getTipiStellePercentualeInterne().get(s) + "%\n";
                }                            
                res += "Percentuale stelle esterne ai filamenti: " + beanRisposta.getPercentualeStelleEsterne() + "%\n";
                Set<String> tipiStelleEsterne = beanRisposta.getTipiStellePercentualeEsterne().keySet();
                for (String s : tipiStelleEsterne) {
                    res += "\tPercentuale stelle " + s + ": " + beanRisposta.getTipiStellePercentualeEsterne().get(s) + "%\n";
                }
            } else {
                res = "Azione non consentita";
            }
            text.setText(res);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    
}
