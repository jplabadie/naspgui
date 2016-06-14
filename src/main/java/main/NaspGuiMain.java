package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.LogManager;
import utils.UserSettingsManager;

/**
 * Main method which defines the root of the JavaFX application
 */
public class NaspGuiMain extends Application {
    /**
     *
     * @param primaryStage the root stage of the GUI
     * @throws Exception multitude of exceptions due to resource loading issues
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("NASPGuiMainLayout.fxml"));
        primaryStage.setTitle("NASP GUI Beta");
        Scene scene = new Scene(root, 1024, 800);

        // Loading in this method seems to fail here
        //scene.getStylesheets().add(getClass().getResource("css/default.css").toExternalForm());

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        LogManager lm = LogManager.getInstance();
        UserSettingsManager usm = UserSettingsManager.getInstance();

        Application.launch(args);
    }
}