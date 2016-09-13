package components.job;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import xmlbinds.ReadFolder;
import xmlbinds.ReadPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class ReadFolderPane extends GridPane {

    private GridPane RF = this;
    private Label read_folder_label = new Label( "Read Folder" );
    private Label read_folder_path_label = new Label( "Folder Path" );

    private TextField read_folder_path = new TextField();

    private Tooltip read_folder_path_tip
            = new Tooltip( "The remote path containing the reads you are interested in" );

    private Image add = new Image( getClass().getResourceAsStream( "/icons/add-3.png" ) );
    private Image remove = new Image( getClass().getResourceAsStream( "/icons/stop.png" ) );

    private ObservableList<ReadPairPane> readPairPanes;

    private int grid_row_position = 2;

    ReadFolder READFOLDER;
    List<ReadPair> READPAIRS;

    ReadFolderPane( ReadFolder readfolder, Button removeButton ){

        this.setId( "folderpane1" );
        READFOLDER = readfolder;
        READPAIRS = READFOLDER.getReadPair();

        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<ReadPairPane> read_pairings =  new ArrayList<>();
        readPairPanes = FXCollections.observableList( read_pairings );

        /**
         * Define the look and feel of static label elements
         */
        read_folder_label.setId( "header5" );
        read_folder_label.setPrefSize( 100, 20 );
        read_folder_label.setAlignment( Pos.CENTER );
        read_folder_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        read_folder_label.setAlignment( Pos.CENTER );

        read_folder_path.setId( "textfield1" );
        /**
         * Add tooltips to the static label elements
         */
        read_folder_path_label.setTooltip(read_folder_path_tip);
        read_folder_path_label.setId( "label1" );

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 2 );
        this.setVgap( 2 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c1 = new ColumnConstraints( 30, 90, 100 );
        ColumnConstraints c2 = new ColumnConstraints( 30, 300, 500 );
        ColumnConstraints c3 = new ColumnConstraints( 30, 60, 90 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.SOMETIMES );
        c1.setHalignment( HPos.LEFT);
        c2.setHgrow( Priority.ALWAYS );
        c2.setHalignment( HPos.LEFT );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.LEFT );

        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(read_folder_label, 0, 0, 3, 1 );
        Button delAssemblyFolder = removeButton;
        this.add( delAssemblyFolder, 2, 0, 3, 1 );
        delAssemblyFolder.setAlignment( Pos.CENTER_LEFT);

        // Add row headings for app-path and app-args to column 1
        this.add(read_folder_path_label, 1, 1, 3, 1 );
        this.add( read_folder_path, 2, 1, 4, 1 );

        read_folder_path.setText( READFOLDER.getPath() );
        read_folder_path.textProperty().addListener(
                observable -> {
                    READFOLDER.setPath( read_folder_path.getText());
                }
        );

        // Add the button to the widget with an event handler

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );

        readPairPanes.addListener(new ListChangeListener<ReadPairPane>() {
            @Override
            public void onChanged( Change<? extends ReadPairPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( ReadPairPane rpp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_readpair = new Button();

                            Button add_readpair = new Button();
                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_readpair.setGraphic( image_view1 );
                            add_readpair.setGraphic( image_view1 );
                            add_readpair.setAlignment( Pos.BOTTOM_RIGHT );

                            /**
                             * When the add-new-pair button is pressed, create and add a new ReadPair (bind)
                             */
                            add_readpair.setOnAction( event -> {
                                ReadPair new_pair = new ReadPair();
                                ReadPairPane new_pane = new ReadPairPane( new_pair );
                                readPairPanes.add( new_pane );
                                READPAIRS.add( new_pair );
                            });

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_readpair.setGraphic( image_view2 );

                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( remove_readpair, add_readpair );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            RF.add( hbox, 5, grid_row_position, 3, 1 );

                            remove_readpair.setOnAction(
                                    event -> {
                                        if(readPairPanes.size() > 1) {
                                            readPairPanes.remove(rpp);
                                            RF.getChildren().remove(hbox);
                                        }
                                        else if( readPairPanes.size() == 1){
                                            ReadPairPane rp = readPairPanes.get(0);
                                            rp.clear();
                                        }
                                    }
                            );
                            RF.add( rpp, 1, grid_row_position++, 3, 1 );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( ReadPairPane gp : c.getRemoved() ) {
                            RF.getChildren().remove( gp );
                            READPAIRS.remove( gp.getReadPair() );
                            grid_row_position--;
                        }
                    }
                }
            }
        });

        if( READPAIRS.isEmpty() )
            READPAIRS.add( new ReadPair() );

        for( ReadPair read : READPAIRS ){
            ReadPairPane new_pair_pane = new ReadPairPane ( read );
            readPairPanes.add( new_pair_pane );
        }
    }


    void clear(){
        readPairPanes.clear();
    }

    /**
     * Accepts buttons from the parent Node (FilesPane)
     * These buttons are controlled  by the parent, but visually fit in the child's pane
     * @param add_readfolder
     * @param remove_readfolder
     */
    public void setButtons( Button add_readfolder, Button remove_readfolder ) {

        HBox button_box = new HBox();
        button_box.getChildren().addAll(add_readfolder, remove_readfolder);
        this.add( button_box, 3, 0, 3, 1);
    }

    ReadFolder getReadFolder(){
        return READFOLDER;
    }

    void setReadFolder( ReadFolder input ){
        READFOLDER = input;
    }
}