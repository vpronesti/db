package gui;

import bean.BeanInformazioniFilamento;
import boundary.InterfacciaRecuperoInformazioniDerivateFilamento;
import dao.SatelliteDao;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DBAccess;

public class RecuperoInformazioniFilamentoController {
    @FXML
    TextField idFilamentoText;
    @FXML
    ComboBox<String> tipoRicercaComboBox;
    @FXML
    ComboBox<String> satelliteComboBox;
    @FXML
    Button cerca;
    @FXML
    Text text;
    @FXML
    Button indietro;
    
    private BeanInformazioniFilamento wrapInput() {
        String tipoRicerca = tipoRicercaComboBox.getValue();
        if (tipoRicerca == null) {
            text.setText("Specificare il tipo di ricerca");
            return null;
        }
        
        Integer idFil = null;
        if (tipoRicerca.equals("Id filamento")) {
            String idFilString = this.idFilamentoText.getText();
            if (idFilString.isEmpty()) {
                text.setText("Inserire un id"); 
                return null;
            }
            try {
                idFil = Integer.parseInt(idFilString);
            } catch (NumberFormatException e) {
                text.setText("L'id inserito non e' un numero");
                return null;
            }
        }
        
        String idFilString = null;
        if (tipoRicerca.equals("Nome filamento")) {
            idFilString = this.idFilamentoText.getText();
            if (idFilString.isEmpty()) {
                text.setText("Inserire un nome"); 
                return null;
            }
        }
        
        String satellite = satelliteComboBox.getValue();
        if (satellite == null) {
            text.setText("Scegliere un satellite");
            return null;
        }
        BeanInformazioniFilamento beanFil;
        if (tipoRicerca.equals("Id filamento"))
            beanFil = new BeanInformazioniFilamento(idFil, satellite);
        else
            beanFil = new BeanInformazioniFilamento(idFilString, satellite);
        return beanFil;
    }
    
    @FXML
    protected void cerca(ActionEvent event) throws Exception {
        BeanInformazioniFilamento beanInfoFil = this.wrapInput();
        if (beanInfoFil != null) {
            InterfacciaRecuperoInformazioniDerivateFilamento boundaryInfoFilamento = 
                    new InterfacciaRecuperoInformazioniDerivateFilamento(
                            LogInController.interfacciaUtenteLogin.getUserId());

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
        
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Id filamento");
        items.add("Nome filamento");
        this.tipoRicercaComboBox.setItems(items);
    }
}
