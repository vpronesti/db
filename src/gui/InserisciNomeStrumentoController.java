package gui;

import boundary.InterfacciaInserimentoNomeStrumento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InserisciNomeStrumentoController {
    @FXML
    TextField nomeStrumentoText;
    @FXML
    Text text;
    
    @FXML
    protected void inserisci(ActionEvent event) throws Exception {
        String nomeStrumento = this.nomeStrumentoText.getText();
        if (nomeStrumento.isEmpty()) {
            text.setText("Inserire il nome dello strumento"); 
        } else {
            InterfacciaInserimentoNomeStrumento boundaryInserimentoNomeStrumento = new
                        InterfacciaInserimentoNomeStrumento(LogInController.interfacciaUtenteLogin.getUserId());
                if (boundaryInserimentoNomeStrumento.inserisciNomeStrumento(nomeStrumento)) {
                    text.setText("Strumento registrato");
                } else {
                    text.setText("Impossibilile registrare lo strumento");
                }
        }
    }
            
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.INSERISCISTRUMENTO);
    }
}
