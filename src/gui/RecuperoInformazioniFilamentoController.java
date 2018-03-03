package gui;

import bean.BeanInformazioniFilamento;
import boundary.InterfacciaRecuperoInformazioniDerivateFilamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RecuperoInformazioniFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    Button cerca;
    @FXML
    Text text;
    @FXML
    Button indietro;
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        String idFilString = this.idFilamentoText.getText();
        if (idFilString.isEmpty()) {
            text.setText("Inserire un id"); 
            return;
        }
        int idFil;
        try {
            idFil = Integer.parseInt(idFilString);
        } catch (NumberFormatException e) {
            text.setText("L'id inserito non e' un numero");
            return;
        }
        InterfacciaRecuperoInformazioniDerivateFilamento boundaryInfoFilamento = 
                new InterfacciaRecuperoInformazioniDerivateFilamento(
                        LogInController.interfacciaUtenteLogin.getUserId());
        BeanInformazioniFilamento beanInfoFil = 
                new BeanInformazioniFilamento(idFil);
        if (boundaryInfoFilamento.recuperaInfoFilamento(beanInfoFil)) {
            text.setText("Posizione del centroide:\n" + 
                    "\tG.Lon.: " + beanInfoFil.getgLonCentroide() + "\n" + 
                    "\tG.Lat.: " + beanInfoFil.getgLatCentroide() + "\n" +
                    "Estensione del contorno: " + 
                    (beanInfoFil.getMaxGLatContorno() - beanInfoFil.getMinGLatContorno())+ 
                    "x" + 
                    (beanInfoFil.getMaxGLonContorno() - beanInfoFil.getMinGLonContorno()) + "\n" + 
                    "Numero di segmenti: " + beanInfoFil.getNumSegmenti());
        } else {
            text.setText("L'id inserito non e' presente nel db");
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
