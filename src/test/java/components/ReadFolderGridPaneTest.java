package components;

import components.job.ReadFolderPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xmlbinds.nasp_xmlbinds.ReadFolder;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
public class ReadFolderGridPaneTest extends Application {
    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        primaryStage.setTitle( "ReadFolder GridPane Test!" );

        ReadFolder rf = new ReadFolder();
        Button button = new Button();
        ReadFolderPane rfgp = new ReadFolderPane(rf, button);

        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
        sp.setPrefSize( 500, 500 );
        sp.setContent( rfgp );

        VBox root = new VBox();

        root.getChildren().add( sp );
        primaryStage.setScene( new Scene(root,400,300) );
        primaryStage.show();
    }
}