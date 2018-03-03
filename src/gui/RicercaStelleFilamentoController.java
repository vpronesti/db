package gui;

import bean.BeanRispostaStelleFilamento;
import boundary.InterfacciaRicercaStelleFilamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaStelleFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    Text text;
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        String idFilamentoString = this.idFilamentoText.getText();
        if (idFilamentoString.isEmpty()) {
            text.setText("Inserire l'id del filamento"); 
            return;
        }
        int idFilamento;
        try {
            idFilamento = Integer.parseInt(idFilamentoString);
        } catch (NumberFormatException e) {
            text.setText("L'id inserito non e' un numero");
            return;
        }
        InterfacciaRicercaStelleFilamento boundaryStelleFilamento = 
                    new  InterfacciaRicercaStelleFilamento(LogInController.interfacciaUtenteLogin.getUserId());
        BeanRispostaStelleFilamento beanRisposta = boundaryStelleFilamento.ricercaStelleFilamento(idFilamento);
        String risp;
        if (beanRisposta.isContornoFilamento()) {
            risp = "Numero totale stelle trovate: " + beanRisposta.getTotaleStelleTrovate() + "\n" + 
                    "Percentuale stelle di tipo unbound: " + beanRisposta.getPercentualeUnbound() + "%\n" +
                    "Percentuale stelle di tipo prestellar: " + beanRisposta.getPercentualePrestellar() + "%\n" +
                    "Percentuale stelle di tipo protostellar: " + beanRisposta.getPercentualeProtostellar() + "%\n";
        } else {
            risp = "Nel db non sono definiti i punti di confine per l'id specificato oppure non esiste l'id";
        }
        text.setText(risp);
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
