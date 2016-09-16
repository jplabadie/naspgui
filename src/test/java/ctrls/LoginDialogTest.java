package ctrls;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Jean-Paul Labadie
 */
public class LoginDialogTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/NASPGuiMainLayout.fxml"));
        primaryStage.setTitle("NASP GUI Prototype");
        primaryStage.setScene(new Scene(root, 1024, 800));
        primaryStage.show();

        LoginDialog temp = new LoginDialog();
        temp.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}