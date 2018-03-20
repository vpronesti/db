package gui;

import bean.BeanIdFilamento;
import bean.BeanRispostaStellaFilamento;
import boundary.InterfacciaRicercaDistanzaStellaFilamento;
import dao.SatelliteDao;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DBAccess;

public class RicercaDistanzaStellaFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    ComboBox<String> satelliteComboBox;
    @FXML
    Text text;
    
    private BeanIdFilamento readInput() {
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
        return new BeanIdFilamento(idFil, satellite);
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {        
        BeanIdFilamento idFil = this.readInput();
        if (idFil != null) {
            String res = "";
            InterfacciaRicercaDistanzaStellaFilamento boundaryStellaFilamento = 
                    new  InterfacciaRicercaDistanzaStellaFilamento(LogInController.interfacciaUtenteLogin.getUserId());
            BeanRispostaStellaFilamento beanRisposta = boundaryStellaFilamento.ricercaDistanzaStellaFilamento(idFil);
            if (beanRisposta.isAzioneConsentita()) {
                if (beanRisposta.isFilamentoEsiste()) {
                    if (beanRisposta.getListaStelle().size() == 0) {
                        res = "Non ci sono stelle all'interno del filamento specificato";
                    } else {
                        RisultatiRicercaDistanzaStellaFilamentoController risultatiController = new RisultatiRicercaDistanzaStellaFilamentoController(beanRisposta, true); 
                        ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCADISTANZASTELLAFILAMENTO, risultatiController);
                    }
                } else {
                    res = "Il filamento inserito non esiste";
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
