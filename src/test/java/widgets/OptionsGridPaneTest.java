package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @Author jlabadie
 */

public class OptionsGridPaneTest extends Application {
    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        primaryStage.setTitle( "Options GridPane Test!" );
        OptionsPane ogp = new OptionsPane(NASP_DATA.getOptions());

        StackPane root = new StackPane();
        root.getChildren().add( ogp );
        primaryStage.setScene( new Scene(root,400,300) );
        primaryStage.show();
    }
}

