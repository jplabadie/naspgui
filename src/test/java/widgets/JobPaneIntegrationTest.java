package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import xmlbinds.Aligner;

import java.lang.reflect.Method;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
public class JobPaneIntegrationTest extends Application {

    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        primaryStage.setTitle( "JobTab Integration Test" );

        JobTab tab = new JobTab();
        TabPane root = new TabPane();

        root.getTabs().add( tab );
        primaryStage.setScene( new Scene( root, 800, 600 ) );
        primaryStage.show();

        for( Method t : Aligner.class.getDeclaredMethods() ){
            System.out.println( t.getName() );
        }
    }
}
