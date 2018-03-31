package gui;

import bean.BeanRichiestaImport;
import boundary.InterfacciaImportCsv;
import dao.SatelliteDao;
import exception.FormatoFileNonSupportatoException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import util.DBAccess;

public class SatelliteImportaCsvController implements Initializable {
    @FXML
    Text text;
    @FXML
    ComboBox<String> satelliteComboBox;
    
    private BeanRichiestaImport beanRichiestaImport;

    public SatelliteImportaCsvController(BeanRichiestaImport beanRichiestaImport) {
        this.beanRichiestaImport = beanRichiestaImport;
    }
    
    private BeanRichiestaImport wrapRichiesta() {
        String satellite = satelliteComboBox.getValue();
        if (satellite == null) {
            text.setText("Scegliere un satellite");
            return null;
        }
        beanRichiestaImport.setSatellite(satellite);
        return this.beanRichiestaImport;
    }
    
    @FXML
    protected void importa(ActionEvent event) throws Exception {
        BeanRichiestaImport beanRichiestaImport = this.wrapRichiesta();
        if (beanRichiestaImport != null) {
            InterfacciaImportCsv boundaryImportCsv = new
                    InterfacciaImportCsv(LogInController.interfacciaUtenteLogin.getUserId());
            try {
                if (boundaryImportCsv.importaCsv(beanRichiestaImport)) {
                    text.setText("Import effettuato correttamente");
                } else {
                    text.setText("Impossibilile importare il file");
                }
            } catch (FormatoFileNonSupportatoException e) {
                text.setText("Il formato del file scelto non e' supportato");
            } catch (FileNotFoundException e) {
                text.setText("Il file specificato non esiste");
            }
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    @FXML
    @Override
    public void initialize() {
        Connection conn = DBAccess.getInstance().getConnection();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        List<String> satelliti = satelliteDao.querySatelliti(conn);
        satelliteComboBox.setItems(FXCollections.observableArrayList(satelliti));
        DBAccess.getInstance().closeConnection(conn);
    }
}
