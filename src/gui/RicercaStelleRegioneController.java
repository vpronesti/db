package gui;

import bean.BeanRichiestaStelleRegione;
import bean.BeanRispostaStelleRegione;
import boundary.InterfacciaRicercaStelleRegione;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
        String latoAString = this.latoAText.getText();
        if (latoAString.isEmpty()) {
            text.setText("Inserire il lato del rettangolo"); 
            return null;
        }
        float latoA;
        try {
            latoA = Float.parseFloat(latoAString);
        } catch (NumberFormatException e) {
            text.setText("Il lato inserito non e' un numero");
            return null;
        }
        String latoBString = this.latoBText.getText();
        if (latoBString.isEmpty()) {
            text.setText("Inserire il lato del rettangolo"); 
            return null;
        }
        float latoB;
        try {
            latoB = Float.parseFloat(latoBString);
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
            BeanRispostaStelleRegione beanRisposta = boundaryStelleRegione.ricercaStelleRegione(beanRichiesta);
            String res = "Percentuale stelle interne ai filamenti: " + beanRisposta.getPercentualeStelleInterne() + "%\n";
            Set<String> tipiStelleInterne = beanRisposta.getTipiStellePercentualeInterne().keySet();
            for (String s : tipiStelleInterne) {
                res += "\tPercentuale stelle " + s + ": " + beanRisposta.getTipiStellePercentualeInterne().get(s) + "%\n";
            }                            
            res += "Percentuale stelle esterne ai filamenti: " + beanRisposta.getPercentualeStelleEsterne() + "%\n";
            Set<String> tipiStelleEsterne = beanRisposta.getTipiStellePercentualeEsterne().keySet();
            for (String s : tipiStelleEsterne) {
                res += "\tPercentuale stelle " + s + ": " + beanRisposta.getTipiStellePercentualeEsterne().get(s) + "%\n";
            }
            text.setText(res);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
