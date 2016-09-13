package components.job;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import xmlbinds.Assembly;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class AssemblyPane extends WidgetPane {

    private Label assembly_label = new Label( "Assembly" );
    private Label ASSEMBLY_NAME = new Label( "Name" );
    private Label ASSEMBLY_PATH = new Label( "Path" );

    private Tooltip ASSEMBLY_NAME_TIP = new Tooltip( "The name given to this ASSEMBLY" );
    private Tooltip ASSEMBLY_PATH_TIP = new Tooltip( "The path (file name) of this ASSEMBLY" );

    private TextField assemblyName = new TextField();
    private TextField assemblyPath = new TextField();

    private Assembly ASSEMBLY;

    AssemblyPane( Assembly assembly ){

        ASSEMBLY = assembly;
        assemblyName.setText( ASSEMBLY.getSample() );
        assemblyName.setAlignment(Pos.CENTER_LEFT);
        assemblyPath.setText( ASSEMBLY.getValue() );
        assemblyPath.setAlignment( Pos.CENTER_LEFT );

        this.getStyleClass().add("folderpane2");

        ASSEMBLY_NAME.setTooltip(ASSEMBLY_NAME_TIP);
        ASSEMBLY_PATH.setTooltip(ASSEMBLY_PATH_TIP);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 50, 100 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 250, 350 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 50 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c2.setHalignment( HPos.LEFT);
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(assembly_label, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add(ASSEMBLY_NAME, 1, 1, 3, 1 );
        this.add(ASSEMBLY_PATH, 1, 2, 3, 1 );

        this.add(assemblyName, 2, 1, 3, 1 );
        this.add(assemblyPath, 2, 2, 3, 1 );

        assemblyName.textProperty().addListener(
                observable -> ASSEMBLY.setSample( assemblyName.getText() )
        );

        assemblyPath.textProperty().addListener(
                observable -> ASSEMBLY.setValue( assemblyPath.getText() )
        );
    }

    Assembly getAssembly(){
        return ASSEMBLY;
    }
    void setAssembly(Assembly assembly){
        ASSEMBLY = assembly;
    }

    @Override
    void setTitle(String title) {

    }

    void clear() {
        assemblyName.setText( "" );
        assemblyPath.setText( "" );
    }

}
