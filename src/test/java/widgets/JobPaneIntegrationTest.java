package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xmlbinds.Aligner;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

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

        ObjectFactory of = new ObjectFactory();
        NaspInputData ni = of.createNaspInputDataType();

        OptionsPane op  = new OptionsPane(NASP_DATA.getOptions());

        FilesPane fgp = new FilesPane(NASP_DATA.getFiles());

        ExternalApplicationsPane aps = new ExternalApplicationsPane(ni.getExternalApplications());
        aps.addApplication( );

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
