package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import utils.JobSaveLoadManager;
import xmlbinds.Aligner;
import xmlbinds.NaspInputData;

import java.io.File;
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

        File input = new File(getClass().getResource("/xml/defaultjob.xml").getFile());
        NaspInputData nid = JobSaveLoadManager.jaxbXMLToObject( input ) ;
        JobTab tab = new JobTab( nid );
        TabPane root = new TabPane();

        root.getTabs().add( tab );
        Scene scene = new Scene( root, 800, 600 );
        scene.getStylesheets().add("css/darc.css");
        primaryStage.setScene( scene );
        primaryStage.show();

        for( Method t : Aligner.class.getDeclaredMethods() ){
            System.out.println( t.getName() );
        }
    }
}
