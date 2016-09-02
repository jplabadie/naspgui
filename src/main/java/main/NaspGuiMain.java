package main;

import javafx.application.Application;
import javafx.application.Platform;
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
    public void start( Stage primaryStage ) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("NASPGuiMainLayout.fxml"));
        primaryStage.setTitle("NASP GUI Beta");
        Scene scene = new Scene(root, 1024, 800);
        //scene.getStylesheets().add( "css/darc.css" );
        //scene.getStylesheets().add("css/default.css");

        // Loading in this method seems to fail here
        //scene.getStylesheets().add(getClass().getResource("css/default.css").toExternalForm());

        // add height and width listeners to allow resizing
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

        primaryStage.setScene( scene );
        primaryStage.show();
    }

    /**
     * This method will be called whenever the application receives a stop or other kill request from the user
     * Here I'm using it to get the program to close (daemon processes are keeping the program alive otherwise)
     * It can/should be used to save state/write logs/etc before the application exits
     */
    @Override
    public void stop(){
        System.out.println("Closing");
        Platform.exit();
        System.exit( 0 );

    }

    public static void main(String[] args) {
        LogManager lm = LogManager.getInstance();
        UserSettingsManager usm = UserSettingsManager.getInstance();

        Application.launch(args);
    }
}