package gui;

import bean.BeanStrumentoSatellite;
import boundary.InterfacciaInserimentoNomeStrumento;
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

public class InserisciNomeStrumentoController {
    @FXML
    TextField nomeStrumentoText;
    @FXML
    ComboBox<String> nomeSatelliteComboBox;
    @FXML
    Text text;
    
    private BeanStrumentoSatellite wrapInput() {
        String nomeSatellite = nomeSatelliteComboBox.getValue();
        if (nomeSatellite == null) {
            text.setText("Scegliere un satellite");
            return null;
        }
        String nomeStrumento = this.nomeStrumentoText.getText();
        if (nomeStrumento.isEmpty()) {
            text.setText("Inserire il nome dello strumento"); 
            return null;
        }
        BeanStrumentoSatellite beanStrumento = new BeanStrumentoSatellite(nomeStrumento, nomeSatellite);
        return beanStrumento;
        
    }
    
    @FXML
    protected void inserisci(ActionEvent event) throws Exception {
        BeanStrumentoSatellite beanStrumento = this.wrapInput();
        if (beanStrumento != null) {
            InterfacciaInserimentoNomeStrumento boundaryInserimentoNomeStrumento = new
            InterfacciaInserimentoNomeStrumento(LogInController.interfacciaUtenteLogin.getUserId());
            if (boundaryInserimentoNomeStrumento.inserisciNomeStrumento(beanStrumento)) {
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
    
    @FXML
    void initialize(){
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        List<String> satelliti = satelliteDao.querySatelliti(conn);
        DBAccess.getInstance().closeConnection(conn);
        
        this.nomeSatelliteComboBox.setItems(FXCollections.observableArrayList(satelliti));

    }
}
