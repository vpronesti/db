package gui;

import boundary.InterfacciaUtenteLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LogInController {
    protected static InterfacciaUtenteLogin interfacciaUtenteLogin;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Text textArea;

    @FXML
    public void logIn(ActionEvent event) throws Exception {
        String userIdEntered = (String) this.userId.getCharacters().toString();
        String passwordEntered = (String) this.password.getCharacters().toString();
        this.password.clear();
        interfacciaUtenteLogin = new InterfacciaUtenteLogin(userIdEntered, 
                passwordEntered);
        if (interfacciaUtenteLogin.logIn()) {
            ViewSwap.getInstance().swap(event, ViewSwap.MENU);
        } else {
            this.textArea.setText("Accesso non consentito");
        }
    }
}