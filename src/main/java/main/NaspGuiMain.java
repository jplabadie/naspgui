package main;

import ctrls.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.LogManager;
import utilities.UserSettingsManager;

/**
 * Main method which defines the root of the JavaFX application
 */
public class NaspGuiMain extends Application {

    /**
     * @param primaryStage the root stage of the GUI
     * @throws Exception multitude of exceptions due to resource loading issues
     */
    @Override
    public void start( Stage primaryStage ) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("NASPGuiMainLayout.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(root);
        MainController controller = loader.getController(); //TODO: Modify MainController and pass in utilities and settings

        primaryStage.setTitle("NASP GUI Beta");
        Scene scene = new Scene(root, 1024, 800);
        //scene.getStylesheets().add( "styles/darc.styles" );
        //scene.getStylesheets().add("styles/default.styles");

        // Loading in this method seems to fail here
        //scene.getStylesheets().add(getClass().getResource("styles/default.styles").toExternalForm());

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
        System.out.println( "Closing" );
        Platform.exit();
        System.exit( 1 ); //hacky. This line shouldn't execute, but if it does, force the program/children into closing
    }

    public static void main(String[] args) {
        LogManager lm = LogManager.getInstance();
        UserSettingsManager usm = UserSettingsManager.getInstance();
        Application.launch(args);
    }
}