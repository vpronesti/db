package gui;

import bean.BeanUtente;
import boundary.InterfacciaRegistrazioneUtente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegistraUtenteController {
    @FXML
    TextField nomeText;
    @FXML
    TextField cognomeText;
    @FXML
    TextField userIdText;
    @FXML
    PasswordField passText;
    @FXML
    TextField emailText;
    @FXML
    ComboBox<String> tipoComboBox;
    @FXML
    Text text;
    
    private BeanUtente wrapUtente() {
        String nome = nomeText.getText();
        if (nome.isEmpty()) {
            text.setText("Inserire il nome dell'utente");
            return null;
        }
        String cognome = cognomeText.getText();
        if (cognome.isEmpty()) {
            text.setText("Inserire il cognome dell'utente");
            return null;
        }
        String userId = userIdText.getText();
        if (userId.isEmpty()) {
            text.setText("Inserire l'user id dell'utente");
            return null;
        }
        if (userId.length() < 6) {
            text.setText("L'user id deve essere di almeno 6 caratteri");
            return null;
        }
        String password = passText.getText();
        if (password.isEmpty()) {
            text.setText("Inserire la password dell'utente");
            return null;
        }
        if (password.length() < 6) {
            text.setText("La password deve essere di almeno 6 caratteri");
            return null;
        }
        String email = emailText.getText();
        if (email.isEmpty()) {
            text.setText("Inserire l'email dell'utente");
            return null;
        }
        String tipo = "";
        if (tipoComboBox.getValue() == null) {
            text.setText("Inserire il tipo dell'utente");
            return null;
        } else {
            tipo = tipoComboBox.getValue();
        }
        BeanUtente utente = new BeanUtente(nome, cognome, 
                userId, password, email, tipo);
        return utente;
    }
    
    @FXML
    protected void inserisciUtente(ActionEvent event) throws Exception {
        BeanUtente beanUtente = this.wrapUtente();
        if (beanUtente != null) {
            InterfacciaRegistrazioneUtente boundaryRegistrazioneUtente = new
                    InterfacciaRegistrazioneUtente(LogInController.interfacciaUtenteLogin.getUserId());
            if (boundaryRegistrazioneUtente.definizioneUtente(beanUtente)) {
                text.setText("Utente definito correttamente");
            } else {
                text.setText("Impossibilile inserire l'utente");
            }
        }
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    @FXML
    void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Amministratore");
        items.add("Registrato");
        this.tipoComboBox.setItems(items);
    }
}
