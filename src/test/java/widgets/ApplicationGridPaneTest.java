package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @Author jlabadie
 */

public class ApplicationGridPaneTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Applications GridPane Test!");

        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
    }
}

