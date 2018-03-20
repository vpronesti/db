package gui;

import bean.BeanIdFilamento;
import bean.BeanRispostaStelleFilamento;
import boundary.InterfacciaRicercaStelleFilamento;
import dao.SatelliteDao;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DBAccess;

public class RicercaStelleFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    ComboBox<String> satelliteComboBox;
    @FXML
    Text text;
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        String idFilamentoString = this.idFilamentoText.getText();
        if (idFilamentoString.isEmpty()) {
            text.setText("Inserire l'id del filamento"); 
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idFilamentoString);
        } catch (NumberFormatException e) {
            text.setText("L'id inserito non e' un numero");
            return;
        }
        String satellite = satelliteComboBox.getValue();
        if (satellite == null) {
            text.setText("Scegliere un satellite");
            return;
        }
        BeanIdFilamento idFil = new BeanIdFilamento(id, satellite);
        InterfacciaRicercaStelleFilamento boundaryStelleFilamento = 
                    new  InterfacciaRicercaStelleFilamento(LogInController.interfacciaUtenteLogin.getUserId());
        BeanRispostaStelleFilamento beanRisposta = boundaryStelleFilamento.ricercaStelleFilamento(idFil);
        String risp;
        if (beanRisposta.isAzioneConsentita()) {
            if (beanRisposta.isFilamentoEsiste()) {
                risp = "Numero totale stelle trovate: " + beanRisposta.getTotaleStelleTrovate() + "\n";
                Map<String, Double> tipiStellaPercentuale = beanRisposta.getTipiStellaPercentuale();
                Set<String> tipiStella = tipiStellaPercentuale.keySet();
                for (String s : tipiStella) {
                    risp += "Percentuale stelle di tipo " + s + ": " + tipiStellaPercentuale.get(s) + "%\n";
                }
            } else {
                risp = "Nel db non sono definiti i punti di confine per l'id specificato oppure non esiste l'id";
            }
        } else {
            risp = "Azione non consentita";
        }
        text.setText(risp);
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
        DBAccess.getInstance().closeConnection(conn);
        this.satelliteComboBox.setItems(FXCollections.observableArrayList(satelliti));
        
    }
}
