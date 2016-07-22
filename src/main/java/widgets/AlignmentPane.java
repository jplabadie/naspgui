package widgets;


import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.Alignment;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class AlignmentPane extends WidgetPane {

    private Label alignment_label = new Label( "Alignment" );
    private Label alignment_name_label = new Label( "Name" );
    private Label alignment_path_label = new Label( "Path" );

    private Tooltip alignment_name_tip = new Tooltip( "The name given to this Alignment" );
    private Tooltip alignment_path_tip = new Tooltip( "The path (file name) of this Alignment" );

    private TextField alignmentName = new TextField();
    private TextField alignmentPath = new TextField();

    private Alignment ALIGNMENT;

    AlignmentPane(Alignment alignment ){

        ALIGNMENT = alignment;
        alignmentName.setText( ALIGNMENT.getSample() );
        alignmentPath.setText( ALIGNMENT.getValue() );

        alignment_label.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        alignment_name_label.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        alignment_path_label.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );

        alignment_name_label.setTooltip(alignment_name_tip);
        alignment_path_label.setTooltip(alignment_path_tip);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 100, 150 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 100, 150 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 50 );
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

        // Add the title to row 0 column 0
        this.add(alignment_label, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add(alignment_name_label, 1, 1, 3, 1 );
        this.add(alignment_path_label, 1, 2, 3, 1 );

        this.add(alignmentName, 2, 1, 3, 1 );
        this.add(alignmentPath, 2, 2, 3, 1 );

        alignmentName.textProperty().addListener(
                observable -> ALIGNMENT.setSample( alignmentName.getText() )
        );

        alignmentPath.textProperty().addListener(
                observable -> ALIGNMENT.setValue( alignmentPath.getText() )
        );
    }

    Alignment getAlign(){
        return ALIGNMENT;
    }
    void setAlignment(Alignment alignment){
        ALIGNMENT = alignment;
    }

    @Override
    void setTitle(String title) {

    }

    void clear() {
        alignmentName.setText( "" );
        alignmentPath.setText( "" );
    }

}