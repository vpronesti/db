package gui;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import boundary.InterfacciaRicercaContrastoEllitticita;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaContrastoEllitticitaController {
    @FXML
    TextField brillanzaText;
    @FXML
    TextField inizioIntervalloEllitticitaText;
    @FXML
    TextField fineIntervalloEllitticitaText;
    @FXML
    Button cerca;
    @FXML
    Text text;
    @FXML
    Button indietro;
    
    private BeanRichiestaContrastoEllitticita wrapInput() {
        String brillanzaString = this.brillanzaText.getText();
        if (brillanzaString.isEmpty()) {
            text.setText("Inserire la brillanza"); 
            return null;
        }
        double brillanza;
        try {
            brillanza = Double.parseDouble(brillanzaString);
        } catch (NumberFormatException e) {
            text.setText("La brillanza inserita non e' un numero");
            return null;
        }
        
        String inizioIntervalloEllitticitaString = this.inizioIntervalloEllitticitaText.getText();
        if (inizioIntervalloEllitticitaString.isEmpty()) {
            text.setText("Inserire l'inizio dell'intervallo"); 
            return null;
        }
        double inizioIntervalloEllitticita;
        try {
            inizioIntervalloEllitticita = Double.parseDouble(inizioIntervalloEllitticitaString);
        } catch (NumberFormatException e) {
            text.setText("L'inizio dell'intervallo di ellitticita' inserito non e' un numero");
            return null;
        }
        
        String fineIntervalloEllitticitaString = this.fineIntervalloEllitticitaText.getText();
        if (fineIntervalloEllitticitaString.isEmpty()) {
            text.setText("Inserire la fine dell'intervallo"); 
            return null;
        }
        double fineIntervalloEllitticita;
        try {
            fineIntervalloEllitticita = Double.parseDouble(fineIntervalloEllitticitaString);
        } catch (NumberFormatException e) {
            text.setText("La fine dell'intervallo di ellitticita' inserito non e' un numero");
            return null;
        }
        
        BeanRichiestaContrastoEllitticita beanRichiesta = 
                new BeanRichiestaContrastoEllitticita(brillanza, 
                        inizioIntervalloEllitticita, fineIntervalloEllitticita);
        return beanRichiesta;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanRichiestaContrastoEllitticita beanRichiesta = this.wrapInput();
        if (beanRichiesta != null) {
            InterfacciaRicercaContrastoEllitticita boundaryContrastoEllitticita = 
                    new  InterfacciaRicercaContrastoEllitticita(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaContrastoEllitticita beanRisposta = boundaryContrastoEllitticita.ricercaContrastoEllitticita(beanRichiesta);
            if (beanRisposta.isAzioneConsentita()) {
                if (!beanRisposta.isInputValido()) {
                    text.setText("Le percentuali non possono essere minori di zero ed i valori di ellitticita' devono essere compresi tra 1 e 10 esclusi");
                } else {
                    if (beanRisposta.getListaFilamenti().size() == 0) {
                        text.setText("Non ci sono filamenti con le caratteristiche richieste");
                    } else {
                        RisultatiRicercaContrastoEllitticitaController risultatiController = 
                                new RisultatiRicercaContrastoEllitticitaController(beanRisposta);
                        ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCACONTRASTOELLITTICITA, risultatiController);
                    }
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
