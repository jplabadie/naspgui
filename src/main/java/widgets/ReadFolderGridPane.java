package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @Author jlabadie
 */
public class ReadFolderGridPane extends GridPane {

    private static Label READ_FOLDER = new Label( "Read Folder" );
    private static Label READ_FOLDER_PATH = new Label( "Folder Path" );
    private static Label READ_PAIR = new Label( "Read Pair" );
    private static Label SAMPLE_NAME = new Label( "Sample Name" );
    private static Label READ_FILE_A = new Label( "Read File A" );
    private static Label READ_FILE_B = new Label( "Read File B" );

    private static Tooltip READ_FOLDER_PATH_TIP
            = new Tooltip( "The remote path containing the reads you are interested in" );
    private static Tooltip SAMPLE_NAME_TIP = new Tooltip( "The name given to this sample" );
    private static Tooltip READ_FILE_A_TIP = new Tooltip( "The name of the file for read A for the read pair" );
    private static Tooltip READ_FILE_B_TIP = new Tooltip( "The name of the file for read B for the read pair" );

    private TextField read_folder_path = new TextField();
    private TextField sample_name = new TextField();
    private TextField file_a_path = new TextField();
    private TextField file_b_path = new TextField();

    private ObservableList<GridPane> read_pairs_gridpanes;
    private ArrayList<String[]> read_pairs;

    private int grid_row_position = 2;


    ReadFolderGridPane(){

        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<GridPane> read_pairings =  new ArrayList<>();
        read_pairs_gridpanes = FXCollections.observableList(read_pairings);

        /**
         * Define the look and feel of static label elements
         */
        READ_FOLDER.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 18 ) );
        READ_FOLDER.setPrefSize( 100,20 );
        READ_FOLDER.setAlignment( Pos.CENTER );
        READ_FOLDER.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        READ_FOLDER.setAlignment( Pos.CENTER );
        READ_FOLDER_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        READ_PAIR.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        SAMPLE_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        READ_FILE_A.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        READ_FILE_B.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        /**
         * Add tooltips to the static label elements
         */
        READ_FOLDER_PATH.setTooltip(READ_FOLDER_PATH_TIP);
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

        // Add the title to row 0 column 0
        this.add( READ_FOLDER, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( READ_FOLDER_PATH, 1, 1, 3, 1 );

    }

    void addReadPair(){

        TextField sample_name = new TextField();
        TextField file_a_path = new TextField();
        TextField file_b_path = new TextField();

        // Add row headings for app-path and app-args to column 1
        this.add( READ_PAIR, grid_row_position, 1 , 3, 1 );
        grid_row_position++;

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( SAMPLE_NAME, grid_row_position, 2, 3, 1 );
        this.add( sample_name, grid_row_position, 3, 3, 1 );
        grid_row_position++; //move down

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( READ_FILE_A, grid_row_position, 2, 3, 1 );
        this.add( file_a_path, grid_row_position, 3, 3, 1 );
        grid_row_position++; //move down

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( READ_FILE_B, grid_row_position, 2, 3, 1 );
        this.add( file_b_path, grid_row_position, 3, 3, 1 );
        grid_row_position++; //move down

        //read_pairs.add( new String[]{ sample_name.getText(), file_a, file_b } );
        //TODO: Make this implementation less frustrating

    }




}