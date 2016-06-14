package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @Author jlabadie
 */

public class ApplicationGridPaneFactoryTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        ApplicationGridPaneFactory agpf = new ApplicationGridPaneFactory();

        GridPane gp = agpf.getNewGridPane("GATK");

        StackPane root = new StackPane();
        root.getChildren().add(gp);
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
    }
}

