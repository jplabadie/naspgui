package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
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

        VBox vb = new VBox();
        vb.setPrefSize( 620, 480);

        OptionsPane op  = new OptionsPane();

        FilesPane fgp = new FilesPane();

        ExternalApplicationsPane aps = new ExternalApplicationsPane();
        ApplicationPane ap1 = new ApplicationPane("App1", "/home/ap1");
        aps.addApplication(ap1);

        vb.getChildren().addAll( op, fgp, aps );

        ScrollPane root = new ScrollPane();
        root.setContent( vb );
        primaryStage.setScene( new Scene( root, 800, 600 ) );
        primaryStage.show();

        for( Method t : Aligner.class.getDeclaredMethods() ){
            System.out.println( t.getName() );
        }
    }
}
