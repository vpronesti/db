package gui;

import bean.BeanRichiestaImport;
import boundary.InterfacciaImportCsv;
import dao.SatelliteDao;
import exception.FormatoFileNonSupportatoException;
import exception.ImpossibileAprireFileException;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
//                Service<Boolean> service = new Service<Boolean>(){
//                    @Override
//                    protected Task<Boolean> createTask() {
//                    return new Task<Boolean>() {
//                       @Override
//                       protected Boolean call() throws Exception {
//                            return boundaryImportCsv.importaCsv(beanRichiestaImport);                      
//                       }
//                       };
//                   }
//                 };
//                 service.setOnSucceeded(e -> {
//                     text.setText("Import effettuato correttamente");
//                 });
//                 service.setOnFailed(e -> {
//                     text.setText("Impossibilile importare il file");
//                 });              
//                 ProgressDialog pd = new ProgressDialog(service);
//                 pd.setContentText("Please wait while the window loads...");
//                 pd.initModality(Modality.WINDOW_MODAL);
//                 pd.initOwner(stage);
//                 service.start();
                
                
//                TaskImport task = new TaskImport(boundaryImportCsv, beanRichiestaImport);
//                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//                    @Override
//                    public void handle(WorkerStateEvent t)
//                    {
//                        // Code to run once runFactory() is completed **successfully**
//                        text.setText("Import effettuato correttamente");
//                    }
//                });
//                task.setOnFailed(new EventHandler<WorkerStateEvent>() {
//                    @Override
//                    public void handle(WorkerStateEvent t)
//                    {
//                        text.setText("Impossibilile importare il file");
//                    }
//                });
//                new Thread(task).start();

                if (boundaryImportCsv.importaCsv(beanRichiestaImport)) {
                    text.setText("Import effettuato correttamente");
                } else {
                    text.setText("Impossibilile importare il file");
                }
            } catch (FormatoFileNonSupportatoException e) {
                text.setText("Il formato del file scelto non e' supportato");
            } catch (ImpossibileAprireFileException e) {
                text.setText("Il percorso specificato o non esiste o e' una directory");
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
