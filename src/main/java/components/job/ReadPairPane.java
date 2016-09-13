package components.job;

import components.WidgetPane;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import xmlbinds.ReadPair;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class ReadPairPane extends WidgetPane {

    private Label read_pair_label = new Label( "Read Pair" );
    private Label sample_name_label = new Label( "Sample" );
    private Label read_file_a_label = new Label( "File A" );
    private Label read_file_b_label = new Label( "File B" );

    private Tooltip sample_name_tip = new Tooltip( "The name given to this sample" );
    private Tooltip read_file_a_tip = new Tooltip( "The name of the file for read A for the read pair" );
    private Tooltip read_file_b_tip = new Tooltip( "The name of the file for read B for the read pair" );

    private TextField sample_name = new TextField();
    private TextField read_file_a = new TextField();
    private TextField read_file_b = new TextField();


    private ReadPair READPAIR;

    ReadPairPane( ReadPair read_in){
        READPAIR = read_in;
        if(READPAIR == null)
            READPAIR = new ReadPair();
        else{
            sample_name.setText( READPAIR.getSample() );
            read_file_a.setText( READPAIR.getRead1Filename() );
            read_file_b.setText( READPAIR.getRead2Filename() );
        }

        this.getStyleClass().add("folderpane2");

        sample_name_label.setTooltip(sample_name_tip);
        read_file_a_label.setTooltip(read_file_a_tip);
        read_file_b_label.setTooltip(read_file_b_tip);
        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 50, 150 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 250, 350 );
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
        this.add(read_pair_label, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add(sample_name_label, 1, 1, 3, 1 );
        this.add(read_file_a_label, 1, 2, 3, 1 );
        this.add(read_file_b_label, 1, 3, 3, 1 );

        this.add( sample_name, 2, 1, 3, 1 );
        this.add( read_file_a, 2, 2, 3, 1 );
        this.add( read_file_b, 2, 3, 3, 1 );

        sample_name.textProperty().addListener(
                observable -> {
                    READPAIR.setSample( sample_name.getText() );
                }
        );

        read_file_a.textProperty().addListener(
                observable -> {
                    READPAIR.setRead1Filename( read_file_a.getText());
                }
        );

        read_file_b.textProperty().addListener(
                observable -> {
                    READPAIR.setRead2Filename( read_file_b.getText());
                }
        );
    }


    @Override
    void setTitle(String title) {

    }

    void clear(){
        sample_name.setText("");
        read_file_a.setText("");
        read_file_b.setText("");
    }

    void setReadPair( ReadPair input_pair ){
        READPAIR = input_pair;
    }

    ReadPair getReadPair(){
        return READPAIR;
    }
}
