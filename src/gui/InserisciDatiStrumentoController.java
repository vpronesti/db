package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InserisciDatiStrumentoController {
    @FXML
    Text text;
    
//    @FXML
//    protected void inserisci(ActionEvent event) throws Exception {
//        BeanSatellite beanSatellite = this.wrapSatellite();
//        if (beanSatellite != null) {
//            InterfacciaInserimentoSatellite boundaryInserimentoSatellite = new
//                    InterfacciaInserimentoSatellite(LogInController.interfacciaUtenteLogin.getUserId());
//            if (boundaryInserimentoSatellite.inserisciSatellite(beanSatellite)) {
//                text.setText("Satellite inserito correttamente");
//            } else {
//                text.setText("Impossibilile inserire il satellite");
//            }
//        }
//    }

    @FXML
    protected void inserisciNome(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.INSERISCINOMESTRUMENTO);
    }
    
    @FXML
    protected void inserisciBanda(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.INSERISCIBANDASTRUMENTO);
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
}
