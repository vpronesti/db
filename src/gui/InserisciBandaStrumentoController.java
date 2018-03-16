package gui;

import bean.BeanStrumento;
import boundary.InterfacciaInserimentoBandaStrumento;
import dao.StrumentoDao;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DBAccess;

public class InserisciBandaStrumentoController {
    @FXML
    ComboBox<String> nomeStrumentoComboBox;
    @FXML
    TextField bandaStrumentoText;
    @FXML
    Text text;
    
    private BeanStrumento wrapInput() {
        String nomeStrumento = nomeStrumentoComboBox.getValue();
        if (nomeStrumento == null) {
            text.setText("Scegliere uno strumento");
            return null;
        }
        String bandaString = this.bandaStrumentoText.getText();
        if (bandaString.isEmpty()) {
            text.setText("Inserire una banda"); 
            return null;
        }
        double banda;
        try {  
            banda = Double.parseDouble(bandaString);  
        } catch (NumberFormatException nfe) {  
            return null;  
        }
        BeanStrumento beanStrumento = new BeanStrumento(nomeStrumento, banda);
        return beanStrumento;
        
    }
    
    @FXML
    protected void inserisci(ActionEvent event) throws Exception {
        BeanStrumento beanStrumento = this.wrapInput();
        if (beanStrumento != null) {
            InterfacciaInserimentoBandaStrumento boundaryInserimentoBandaStrumento = new
                        InterfacciaInserimentoBandaStrumento(LogInController.interfacciaUtenteLogin.getUserId());
                if (boundaryInserimentoBandaStrumento.inserisciBandaStrumento(beanStrumento)) {
                    text.setText("Banda inserita");
                } else {
                    text.setText("Impossibilile inserire la banda per lo strumeto specificato");
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
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        List<String> strumenti = strumentoDao.queryStrumenti(conn);
        DBAccess.getInstance().closeConnection(conn);
        
        this.nomeStrumentoComboBox.setItems(FXCollections.observableArrayList(strumenti));

    }
}
