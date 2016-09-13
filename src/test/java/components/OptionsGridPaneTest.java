package components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */

public class OptionsGridPaneTest extends Application {
    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {
        primaryStage.setTitle( "Options GridPane Test!" );

        ObjectFactory of = new ObjectFactory();
        NaspInputData NASP_DATA = of.createNaspInputData();
        OptionsPane ogp = new OptionsPane( NASP_DATA.getOptions() );

        StackPane root = new StackPane();
        root.getChildren().add( ogp );
        primaryStage.setScene( new Scene( root,400,300 ) );
        primaryStage.show();
    }
}
