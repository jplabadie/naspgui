package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @Author jlabadie
 */

public class ReadPairGridPaneTest extends Application {
    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        primaryStage.setTitle( "ReadPair GridPane Test!" );
        ReadPairPane rpgp = new ReadPairPane();

        StackPane root = new StackPane();
        root.getChildren().add( rpgp );
        primaryStage.setScene( new Scene(root,400,300) );
        primaryStage.show();
    }
}

