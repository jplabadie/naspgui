package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private GridPane RF = this;
    private Label READ_FOLDER = new Label( "Read Folder" );
    private Label READ_FOLDER_PATH = new Label( "Folder Path" );

    private Tooltip READ_FOLDER_PATH_TIP
            = new Tooltip( "The remote path containing the reads you are interested in" );

    private Button add_readpair = new Button();

    private ObservableList<GridPane> read_pairs_gridpanes;

    private int grid_row_position = 2;


    ReadFolderGridPane(){
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<GridPane> read_pairings =  new ArrayList<>();
        read_pairs_gridpanes = FXCollections.observableList( read_pairings );
        read_pairs_gridpanes.addListener( new ListChangeListener<GridPane>() {

            @Override
            public void onChanged( Change<? extends GridPane> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (GridPane gp : c.getAddedSubList()) {
                            // Add the remove button to the widget
                            Button remove_readpair = new Button();
                            Image remove = new Image( getClass().getResourceAsStream("/icons/minus.png") );
                            ImageView image_view = new ImageView(remove);
                            image_view.setFitHeight( 20 );
                            image_view.setFitWidth( 20 );
                            remove_readpair.setGraphic(image_view);

                            RF.add( remove_readpair, 3, grid_row_position, 3, 1 );
                            add_readpair.setOnAction( event -> read_pairs_gridpanes.remove( gp ) );
                            RF.add(gp, 2, grid_row_position++, 3, 1);
                        }
                    }
                    if (c.wasRemoved()) {
                        for (GridPane gp : c.getRemoved()) {
                            RF.getChildren().remove(gp);
                            grid_row_position--;
                        }
                    }

                }
            }

        });
        /**
         * Define the look and feel of static label elements
         */
        READ_FOLDER.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 18 ) );
        READ_FOLDER.setPrefSize( 100,20 );
        READ_FOLDER.setAlignment( Pos.CENTER );
        READ_FOLDER.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        READ_FOLDER.setAlignment( Pos.CENTER );
        READ_FOLDER_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );

        /**
         * Add tooltips to the static label elements
         */
        READ_FOLDER_PATH.setTooltip(READ_FOLDER_PATH_TIP);

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 50, 150 );
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
        this.add( READ_FOLDER, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( READ_FOLDER_PATH, 1, 1, 3, 1 );

        // Add the button to the widget with an event handler
        Image add = new Image( getClass().getResourceAsStream( "/icons/add.png" ) );
        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );
        add_readpair.setGraphic( image_view );

        this.add( add_readpair, 3 , 2, 3, 1 );
        add_readpair.setOnAction( event -> addReadPair() );
    }



    void addReadPair( String sample_name, String read_a, String read_b ){
        ReadPairGridPane rp = new ReadPairGridPane( sample_name, read_a, read_b );
        read_pairs_gridpanes.add( rp );
    }

    void addReadPair( ){
        ReadPairGridPane rp = new ReadPairGridPane();
        read_pairs_gridpanes.add( rp );
    }

}