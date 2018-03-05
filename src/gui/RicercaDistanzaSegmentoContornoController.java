package gui;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import boundary.InterfacciaRicercaDistanzaSegmentoContorno;
import static java.lang.Float.max;
import static java.lang.Float.min;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RicercaDistanzaSegmentoContornoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    TextField idSegmentoText;
    @FXML
    Text text;
    
    private BeanRichiestaSegmentoContorno wrapInput() {
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
        String idSegmentoString = this.idSegmentoText.getText();
        if (idSegmentoString.isEmpty()) {
            text.setText("Inserire l'id del segmento"); 
            return null;
        }
        int idSeg;
        try {
            idSeg = Integer.parseInt(idSegmentoString);
        } catch (NumberFormatException e) {
            text.setText("L'id segmento inserito non e' un numero");
            return null;
        }

        BeanRichiestaSegmentoContorno beanRichiesta = 
                new BeanRichiestaSegmentoContorno(idFil, idSeg);
        return beanRichiesta;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanRichiestaSegmentoContorno beanRichiesta = this.wrapInput();
        if (beanRichiesta != null) {
            String res = "";
            InterfacciaRicercaDistanzaSegmentoContorno boundarySegmentoContorno = 
                    new  InterfacciaRicercaDistanzaSegmentoContorno(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaSegmentoContorno beanRisposta = boundarySegmentoContorno.ricercaDistanzaSegmentoContorno(beanRichiesta);
            if (beanRisposta.isSegmentoEsiste()) {
                float verticeVicino = min(beanRisposta.getDistanzaA(), beanRisposta.getDistanzaB());
                float verticeLontano = max(beanRisposta.getDistanzaA(), beanRisposta.getDistanzaB());
                res = "Il vertice piu' vicino e' distante " + verticeVicino + " dal contorno\n" + 
                        "Il vertice piu' lontano e' distante " + verticeLontano + " dal contorno";
            } else {
                res = "Il filamento o il segmento inserito non esiste";
            }
            text.setText(res);
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
