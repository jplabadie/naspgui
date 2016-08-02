package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Project naspgui.
 * Created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class JobManagerPaneTest extends Application {

    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {

        primaryStage.setTitle( "Job Manager Visual Test" );

        JobManagerPane jmp = new JobManagerPane();

        Scene scene = new Scene( jmp, 800, 600 );
        //scene.getStylesheets().add( "css/darc.css" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
}