package gui;

import bean.BeanRichiestaNumeroSegmenti;
import bean.BeanRispostaFilamenti;
import boundary.InterfacciaRicercaNumeroSegmenti;
import entity.Filamento;
import java.util.ArrayList;
import java.util.List;
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
            if (beanRisposta.isAzioneConsentita()) {
                if (beanRisposta.isInputValido()) {
                    if (beanRisposta.getListaFilamenti().size() > 0) {
                        RisultatiRicercaNumeroSegmentiController risultatiController = new RisultatiRicercaNumeroSegmentiController(beanRisposta);
                        ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCANUMEROSEGMENTI, risultatiController);
                    } else {
                        text.setText("Non ci sono filamenti con numero di segmenti nell'intervallo specificato");
                    }
                } else {
                    text.setText("La dimensione minima dell'intervallo deve essere strettamente maggiore di 2");
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
}
