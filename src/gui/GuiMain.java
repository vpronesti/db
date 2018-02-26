package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application implements Runnable {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewSwap.LOGIN));
        primaryStage.setTitle("DB");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void run() {
        launch(null);
    }
}
