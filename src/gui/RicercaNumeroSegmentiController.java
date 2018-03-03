package gui;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import boundary.InterfacciaRicercaNumeroSegmenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaNumeroSegmentiController {
    @FXML
    TextField inizioIntervalloText;
    @FXML
    TextField fineIntervalloText;
    @FXML
    Text text;
    
    private BeanRichiestaNumeroSegmenti wrapInput() {
        String inizioIntervalloString = this.inizioIntervalloText.getText();
        if (inizioIntervalloString.isEmpty()) {
            text.setText("Inserire l'inizio dell'intervallo di ricerca"); 
            return null;
        }
        int inizioIntervallo;
        try {
            inizioIntervallo = Integer.parseInt(inizioIntervalloString);
        } catch (NumberFormatException e) {
            text.setText("L'inizio dell'intervallo inserito non e' un numero");
            return null;
        }
        
        String fineIntervalloString = this.fineIntervalloText.getText();
        if (fineIntervalloString.isEmpty()) {
            text.setText("Inserire la fine dell'intervallo di ricerca"); 
            return null;
        }
        int fineIntervallo;
        try {
            fineIntervallo = Integer.parseInt(fineIntervalloString);
        } catch (NumberFormatException e) {
            text.setText("La fine dell'intervallo inserito non e' un numero");
            return null;
        }
        
        BeanRichiestaNumeroSegmenti beanRichiesta = 
                new BeanRichiestaNumeroSegmenti(inizioIntervallo, fineIntervallo);
        return beanRichiesta;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanRichiestaNumeroSegmenti beanRichiesta = this.wrapInput();
        if (beanRichiesta != null) {
            InterfacciaRicercaNumeroSegmenti boundaryNumeroSegmenti = 
                    new  InterfacciaRicercaNumeroSegmenti(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaFilamenti beanRisposta = boundaryNumeroSegmenti.ricercaNumeroSegmenti(beanRichiesta);
            // effettuare controlli sulla risposta e passare bean al secondo controller
//            RisultatiRicercaContrastoEllitticitaController risultatiController = 
//                    new RisultatiRicercaContrastoEllitticitaController(); 
//            ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCACONTRASTOELLITTICITA, risultatiController);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
