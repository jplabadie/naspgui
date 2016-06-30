package widgets;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class AssemblyFolderPane extends GridPane {

    private GridPane AF = this;
    private Label ASSEMBLY_FOLDER = new Label( "Assembly Folder" );
    private Label ASSEMBLY_FOLDER_PATH = new Label( "Folder Path" );

    private TextField assembly_folder_path = new TextField();

    private Tooltip ASSEMBLY_FOLDER_PATH_TIP
            = new Tooltip( "The remote path containing the assemblies you are interested in" );

    private Image add = new Image( getClass().getResourceAsStream( "/icons/add-3.png" ) );
    private Image remove = new Image( getClass().getResourceAsStream( "/icons/stop.png" ) );

    private ObservableList<AssemblyPane> assembly_gridpanes;

    private int grid_row_position = 2;


    AssemblyFolderPane(){
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<AssemblyPane> ass_pairings =  new ArrayList<>();
        assembly_gridpanes = FXCollections.observableList( ass_pairings );

        /**
         * Define the look and feel of static label elements
         */
        ASSEMBLY_FOLDER.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 18 ) );
        ASSEMBLY_FOLDER.setPrefSize( 100, 20 );
        ASSEMBLY_FOLDER.setAlignment( Pos.CENTER );
        ASSEMBLY_FOLDER.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        ASSEMBLY_FOLDER.setAlignment( Pos.CENTER );
        ASSEMBLY_FOLDER_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );

        /**
         * Add tooltips to the static label elements
         */
        ASSEMBLY_FOLDER_PATH.setTooltip(ASSEMBLY_FOLDER_PATH_TIP);

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c1 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c2 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c3 = new ColumnConstraints( 30, 60, 90 );
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
        this.add( ASSEMBLY_FOLDER, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( ASSEMBLY_FOLDER_PATH, 1, 1, 3, 1 );
        this.add( assembly_folder_path, 3, 1, 4, 1 );

        // Add the button to the widget with an event handler

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );

        assembly_gridpanes.addListener(new ListChangeListener<GridPane>() {

            @Override
            public void onChanged( Change<? extends GridPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( GridPane gp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_assembly = new Button();

                            Button add_assembly = new Button();
                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_assembly.setGraphic( image_view1 );
                            add_assembly.setGraphic( image_view1 );
                            add_assembly.setAlignment( Pos.BOTTOM_RIGHT );

                            add_assembly.setOnAction( event -> addAssembly() );

                            ImageView image_view2 = new ImageView( remove);
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_assembly.setGraphic( image_view2 );

                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( remove_assembly, add_assembly );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            AF.add( hbox, 5, grid_row_position, 3, 1 );

                            remove_assembly.setOnAction(
                                    event -> {
                                        if(assembly_gridpanes.size() > 1) {
                                            assembly_gridpanes.remove( gp );
                                            AF.getChildren().remove( hbox );
                                        }
                                        else if( assembly_gridpanes.size() == 1 ){
                                            AssemblyPane ap = assembly_gridpanes.get( 0 );
                                            ap.clear();
                                        }
                                    }
                            );
                            AF.add( gp, 2, grid_row_position++, 3, 1 );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( GridPane gp : c.getRemoved() ) {
                            AF.getChildren().remove( gp );
                            grid_row_position--;
                        }
                    }
                }
            }
        });
        this.addAssembly();
    }

    void addAssembly(String sample_name, String sample_path ){
        AssemblyPane ap = new AssemblyPane( sample_name, sample_path );
        assembly_gridpanes.add( ap );
    }

    void addAssembly( ){
        AssemblyPane ap = new AssemblyPane();
        assembly_gridpanes.add( ap );
    }

    void setFolderPath( String text ){
        assembly_folder_path.setText( text );
    }

    String getFolderPath(){
        return  assembly_folder_path.getText();
    }

    void clear(){
        assembly_gridpanes.clear();
    }

    /**
     * Accepts buttons from the parent Node (FilesPane)
     * These buttons are controlled  by the parent, but visually fit in the child's pane
     * @param add_assembly
     * @param remove_assembly
     */
    public void setButtons( Button add_assembly, Button remove_assembly) {

        HBox button_box = new HBox();
        button_box.getChildren().addAll(add_assembly, remove_assembly);
        this.add( button_box, 3, 0, 3, 1);
    }
}