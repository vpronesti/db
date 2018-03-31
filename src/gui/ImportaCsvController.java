package gui;

import bean.BeanRichiestaImport;
import boundary.InterfacciaImportCsv;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImportaCsvController {
    @FXML
    Text textFile;
    @FXML
    Text text;
    @FXML
    ComboBox<TipoFileCsv> tipoFileComboBox;
    
    File selectedFile = null;
    
    @FXML
    protected void scegli(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            textFile.setText(selectedFile.toString());
        }
    }
    
    private BeanRichiestaImport wrapRichiesta() {
        if (selectedFile == null) {
            text.setText("Inserire il file csv");
            return null;
        }
        TipoFileCsv tipoFile = tipoFileComboBox.getValue();
        if (tipoFile == null) {
            text.setText("Inserire tipo di file csv da importare");
            return null;
        }
        BeanRichiestaImport beanRichiestaImport = 
                new BeanRichiestaImport(selectedFile, tipoFile);
        return beanRichiestaImport;
    }
    
    @FXML
    protected void importa(ActionEvent event) throws Exception {
        BeanRichiestaImport beanRichiestaImport = this.wrapRichiesta();
        boolean res;
        if (beanRichiestaImport != null) {
            if (beanRichiestaImport.getTipo() == TipoFileCsv.FILAMENTO) {
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
            } else {
            SatelliteImportaCsvController satelliteController = 
                        new SatelliteImportaCsvController(beanRichiestaImport); 
                ViewSwap.getInstance().swap(event, ViewSwap.SATELLITEIMPORTACSV, satelliteController);
            }
        } 
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    @FXML
    void initialize() {
        tipoFileComboBox.setItems(FXCollections.observableArrayList(TipoFileCsv.values()));
    }
}
