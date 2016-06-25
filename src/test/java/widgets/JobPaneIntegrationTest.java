package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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

        ApplicationGridPane ap = new ApplicationGridPane( "Test", "Testing" );
        OptionsGridPane op  = new OptionsGridPane();

        ReadFolderGridPane rf = new ReadFolderGridPane();

        rf.addReadPair();
        rf.addReadPair();
        rf.addReadPair();

        vb.getChildren().addAll( op, rf, ap );

        StackPane root = new StackPane();
        root.getChildren().add( vb );
        primaryStage.setScene( new Scene( root,400,600 ) );
        primaryStage.show();


        for(Method t :Aligner.class.getDeclaredMethods()){
            System.out.println(t.getName());
        }
    }


}
