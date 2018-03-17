package gui;

import bean.BeanRichiestaSegmentoContorno;
import bean.BeanRispostaSegmentoContorno;
import boundary.InterfacciaRicercaDistanzaSegmentoContorno;
import dao.SatelliteDao;
import static java.lang.Double.max;
import static java.lang.Double.min;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DBAccess;

public class RicercaDistanzaSegmentoContornoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    ComboBox<String> satelliteComboBox;
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
        String satellite = satelliteComboBox.getValue();
        if (satellite == null) {
            text.setText("Scegliere un satellite");
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
                new BeanRichiestaSegmentoContorno(idFil, satellite, idSeg);
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
            if (beanRisposta.isAzioneConsentita()) {
                if (beanRisposta.isSegmentoEsiste()) {
                    double verticeVicino = min(beanRisposta.getDistanzaA(), beanRisposta.getDistanzaB());
                    double verticeLontano = max(beanRisposta.getDistanzaA(), beanRisposta.getDistanzaB());
                    res = "Il vertice piu' vicino e' distante " + verticeVicino + " dal contorno\n" + 
                            "Il vertice piu' lontano e' distante " + verticeLontano + " dal contorno";
                } else {
                    res = "Il filamento o il segmento inserito non esiste";
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
    
    @FXML
    void initialize() {
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        List<String> satelliti = satelliteDao.querySatelliti(conn);
        satelliteComboBox.setItems(FXCollections.observableArrayList(satelliti));
        DBAccess.getInstance().closeConnection(conn);
    }
}
