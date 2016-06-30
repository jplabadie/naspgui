package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.ReadPair;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/16/16.
 *
 * @author jlabadie
 */
class ReadPairPane extends WidgetGridPane {

    private Label READ_PAIR = new Label( "Read Pair" );
    private Label SAMPLE_NAME = new Label( "Sample Name" );
    private Label READ_FILE_A = new Label( "Read File A" );
    private Label READ_FILE_B = new Label( "Read File B" );

    private Tooltip SAMPLE_NAME_TIP = new Tooltip( "The name given to this sample" );
    private Tooltip READ_FILE_A_TIP = new Tooltip( "The name of the file for read A for the read pair" );
    private Tooltip READ_FILE_B_TIP = new Tooltip( "The name of the file for read B for the read pair" );

    private TextField sample_name = new TextField();
    private TextField read_file_a = new TextField();
    private TextField read_file_b = new TextField();

    private ReadPair rp;

    private ObservableList<TextField> elements;

    ReadPairPane(String name, String read_a, String read_b ){
        sample_name.setText( name );
        read_file_a.setText( read_a );
        read_file_b.setText( read_b );
    }

    ReadPairPane(){

        READ_PAIR.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        SAMPLE_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        READ_FILE_A.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        READ_FILE_B.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );

        SAMPLE_NAME.setTooltip(SAMPLE_NAME_TIP);
        READ_FILE_A.setTooltip(READ_FILE_A_TIP);
        READ_FILE_B.setTooltip(READ_FILE_B_TIP);
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
        this.add( READ_PAIR, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( SAMPLE_NAME, 1, 1, 3, 1 );
        this.add( READ_FILE_A, 1, 2, 3, 1 );
        this.add( READ_FILE_B, 1, 3, 3, 1 );

        this.add( sample_name, 2, 1, 3, 1 );
        this.add( read_file_a, 2, 2, 3, 1 );
        this.add( read_file_b, 2, 3, 3, 1 );

        ArrayList<TextField> temp = new ArrayList<>();
        elements = FXCollections.observableArrayList(temp);

        elements.addAll(sample_name, read_file_a, read_file_b);

        elements.addListener(new ListChangeListener<TextField>() {
            @Override
            public void onChanged(Change<? extends TextField> c) {
                if(rp == null){
                    rp = new ReadPair();
                }
                while( c.next() ){
                    //Hacky, any change will save all fields to the xmlbind
                    rp.setSample( sample_name.getText() );
                    rp.setRead1Filename( read_file_a.getText() );
                    rp.setRead2Filename( read_file_b.getText() );
                }
            }
        });
    }

    String getSampleName(){
        return sample_name.getText();
    }
    void setSampleName( String name ){
        sample_name.setText( name );
    }

    String getReadFileA(){
        return read_file_a.getText();
    }
    void setReadFileA( String file ){
        read_file_a.setText( file );
    }

    String getReadFileB(){
        return read_file_b.getText();
    }
    void setReadFileB( String file ){
        read_file_b.setText( file );
    }

    @Override
    void setTitle(String title) {

    }

    void clear() {
        setReadFileA( "" );
        setReadFileB( "" );
        setSampleName( "" );
    }

    void setXMLBind( ReadPair readpair ){
        rp = readpair;
        sample_name.setText( readpair.getSample() );
        read_file_a.setText( readpair.getRead1Filename() );
        read_file_b.setText( readpair.getRead2Filename() );
    }
}
