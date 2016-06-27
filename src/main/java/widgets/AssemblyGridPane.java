package widgets;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Project naspgui.
 * Created by jlabadie on 6/24/16.
 *
 * @author jlabadie
 */
public class AssemblyGridPane extends GridPane {

    private static Label SAMPLE_NAME = new Label( "Sample Name" );
    private static Label FILE_NAME = new Label( "File Name" );

    private static Tooltip SAMPLE_NAME_TIP = new Tooltip( "The name given to this sample" );
    private static Tooltip FILE_NAME_TIP = new Tooltip( "The name of the sample file in the folder" );

    private TextField sample_name = new TextField();
    private TextField file_name = new TextField();

    AssemblyGridPane(){

        SAMPLE_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        FILE_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );

        SAMPLE_NAME.setTooltip(SAMPLE_NAME_TIP);
        FILE_NAME.setTooltip(FILE_NAME_TIP);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 150, 200 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 150 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the sample nodes to to row 0 column 0 and 2
        this.add( SAMPLE_NAME, 0, 0, 3, 1 );
        this.add( sample_name, 0, 2, 3, 1);

        // Add file nodes to row 2 column 0 and 2
        this.add( FILE_NAME, 1, 0, 3, 1 );
        this.add( file_name, 1, 2, 3, 1 );
    }

    String getSampleName(){
        return sample_name.getText();
    }
    void setSampleName( String name ){
        sample_name.setText( name );
    }

    String getFileName(){
        return file_name.getText();
    }
    void setFileName( String name ){
        file_name.setText( name );
    }
}