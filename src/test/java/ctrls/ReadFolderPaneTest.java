package ctrls;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.Test;

/**
 * @author Jean-Paul Labadie
 * @date 8/11/2015
 */
public class ReadFolderPaneTest extends Application {

    @Test
    public void testSetReadPairs() throws Exception {

    }

    @Test
    public void testGetReadPairs() throws Exception {

    }

    @Test
    public void testGetReadPane() throws Exception {

    }

    @Test
    public void testGetNode() throws Exception {
        Application.launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circ = new Circle(4, 4, 3);
        Group root = new Group(circ);

        ReadFolderPane test = new ReadFolderPane();
        root.getChildren().add(test.getReadPane());

        Scene scene = new Scene(root, 800, 600);

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

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();
    }
}