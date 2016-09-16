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
import nasp_xmlbinds.Alignment;
import nasp_xmlbinds.AlignmentFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI component which binds the AlignmentFolder Object to its graphical presentation
 *
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class AlignmentFolderPane extends GridPane {

    private GridPane AF = this;
    private Label alignment_folder_label = new Label( "Alignment Folder" );
    private Label alignment_folder_path_label = new Label( "Folder Path" );

    private TextField alignmentFolderPath = new TextField();

    private Tooltip alignment_folder_path_tip
            = new Tooltip( "The remote path containing the alignments you are interested in" );

    private Image add = new Image( getClass().getResourceAsStream( "/icons/add-3.png" ) );
    private Image remove = new Image( getClass().getResourceAsStream( "/icons/stop.png" ) );

    private ObservableList<AlignmentPane> alignmentGridpanes;

    private int grid_row_position = 2;

    private AlignmentFolder ALIGNMENTFOLDER;
    private List<Alignment> ALIGNMENTS;

    AlignmentFolderPane( AlignmentFolder input_alignment_folder, Button removeButton ){
        this.setId( "folderpane1" );
        ALIGNMENTFOLDER = input_alignment_folder;
        ALIGNMENTS = ALIGNMENTFOLDER.getAlignment();

        alignmentFolderPath.setText( ALIGNMENTFOLDER.getPath() );
        alignmentFolderPath.setId( "textfield1" );
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<AlignmentPane> al_pairings =  new ArrayList<>();
        alignmentGridpanes = FXCollections.observableList( al_pairings );

        /**
         * Define the look and feel of static label elements
         */
        alignment_folder_label.setId( "header5" );
        alignment_folder_label.setPrefSize( 100, 20 );
        alignment_folder_label.setAlignment( Pos.CENTER );
        alignment_folder_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        alignment_folder_label.setAlignment( Pos.CENTER );
        /**
         * Add tooltips to the static label elements
         */
        alignment_folder_path_label.setTooltip( alignment_folder_path_tip );
        alignment_folder_path_label.setId( "label1" );

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
        Button delAlignmentFolder = removeButton;
        this.add( delAlignmentFolder, 2, 0, 3, 1 );
        delAlignmentFolder.setAlignment( Pos.CENTER_LEFT);

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add( alignment_folder_label, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( alignment_folder_path_label, 1, 1, 3, 1 );
        this.add( alignmentFolderPath, 2, 1, 4, 1 );

        alignmentFolderPath.textProperty().addListener(
                observable -> {
                    ALIGNMENTFOLDER.setPath( alignmentFolderPath.getText() );
                }
        );

        // Add the button to the widget with an event handler
        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );

        alignmentGridpanes.addListener( new ListChangeListener<AlignmentPane>() {
            @Override
            public void onChanged( Change<? extends AlignmentPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( AlignmentPane gp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_assembly = new Button();

                            Button add_assembly = new Button();
                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_assembly.setGraphic( image_view1 );
                            add_assembly.setGraphic( image_view1 );
                            add_assembly.setAlignment( Pos.BOTTOM_RIGHT );

                            add_assembly.setOnAction( event -> {
                                Alignment alignment = new Alignment();
                                AlignmentPane ap = new AlignmentPane( alignment );
                                ALIGNMENTS.add( alignment );
                                alignmentGridpanes.add ( ap );
                            } );

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_assembly.setGraphic( image_view2 );

                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( remove_assembly, add_assembly );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            AF.add( hbox, 5, grid_row_position, 3, 1 );

                            remove_assembly.setOnAction(
                                    event -> {
                                        if( alignmentGridpanes.size() > 1 ) {
                                            alignmentGridpanes.remove( gp );
                                            AF.getChildren().remove( hbox );
                                        }
                                        else if( alignmentGridpanes.size() == 1 ){
                                            AlignmentPane ap = alignmentGridpanes.get( 0 );
                                            ap.clear();
                                        }
                                    }
                            );
                            AF.add( gp, 1, grid_row_position++, 3, 1 );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( AlignmentPane gp : c.getRemoved() ) {
                            AF.getChildren().remove( gp );
                            ALIGNMENTS.remove( gp.getAlign() );
                            grid_row_position--;
                        }
                    }
                }
            }
        });
        if(ALIGNMENTS.isEmpty())
            ALIGNMENTS.add( new Alignment() );
        for( Alignment alignment: ALIGNMENTS){
            AlignmentPane ap = new AlignmentPane( alignment );
            alignmentGridpanes.add( ap );
        }
    }

    /**
     * Empties the contents of the AlignmentFolder object and the graphical pane
     */
    void clear(){
        alignmentFolderPath.setText("");
        for( int i= alignmentGridpanes.size() -1 ; i >=0 ; i--) {
            if (i > 1){
                alignmentGridpanes.remove( i);
            }
            else
                alignmentGridpanes.get(i).clear();
        }
    }

    /**
     * Accepts buttons from the parent Node (FilesPane)
     * These buttons are controlled  by the parent, but visually fit in the child's pane
     * @param add_assembly a button which will add a new Assembly object and graphical pane to this AssemblyFolder
     * @param remove_assembly a button which will remove an Assembly object/graphical pane from this AssemblyFolder
     */
    void setButtons( Button add_assembly, Button remove_assembly ) {

        HBox button_box = new HBox();
        button_box.getChildren().addAll(add_assembly, remove_assembly);
        this.add( button_box, 3, 0, 3, 1);
    }

    AlignmentFolder getAssemblyFolder() {
        return ALIGNMENTFOLDER;
    }

    public void setAssemblyFolder(AlignmentFolder input){
        ALIGNMENTFOLDER = input;
    }
}