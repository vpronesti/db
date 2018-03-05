package gui;

import bean.BeanRispostaStellaFilamento;
import boundary.InterfacciaRicercaDistanzaStellaFilamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaDistanzaStellaFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    Text text;
    
    private Integer readInput() {
        String idFilamentoString = this.idFilamentoText.getText();
        if (idFilamentoString.isEmpty()) {
            text.setText("Inserire l'id del filamento"); 
            return null;
        }
        int idFil;
        try {
            idFil = Integer.parseInt(idFilamentoString);
        } catch (NumberFormatException e) {
            text.setText("L'id filamento inserito non e' un numero");
            return null;
        }
        return idFil;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {        
        Integer idFil = this.readInput();
        if (idFil != null) {
            String res = "";
            InterfacciaRicercaDistanzaStellaFilamento boundaryStellaFilamento = 
                    new  InterfacciaRicercaDistanzaStellaFilamento(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaStellaFilamento beanRisposta = boundaryStellaFilamento.ricercaDistanzaStellaFilamento(idFil);
            if (beanRisposta.isFilamentoEsiste()) {
                RisultatiRicercaDistanzaStellaFilamentoController risultatiController = new RisultatiRicercaDistanzaStellaFilamentoController(beanRisposta, true); 
                ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCADISTANZASTELLAFILAMENTO, risultatiController);
            } else {
                res = "Il filamento inserito non esiste";
            }
            text.setText(res);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
