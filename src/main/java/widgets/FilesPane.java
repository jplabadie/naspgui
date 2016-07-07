package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class FilesPane extends GridPane {

    private Label files_label = new Label( "Files" );

    private Image add = new Image( getClass().getResourceAsStream( "/icons/add-1.png" ) );
    private Image remove = new Image( getClass().getResourceAsStream( "/icons/garbage-2.png" ) );

    private ObservableList<AssemblyFolderPane> assemblyFolderPanes;
    private ObservableList<ReadFolderPane> readFolderPanes;

    private VBox assembly_box = new VBox();
    private VBox read_box = new VBox();

    private Files FILES;
    private List<AssemblyFolder> ASSEMBLYFOLDERS;
    private List<ReadFolder> READFOLDERS;

    /**
     *
     * @param input_files a Files Object from the root NaspInputData Object
     */
    FilesPane( Files input_files ){
        FILES = input_files; // initialize File object ( an XML bind object )
        ASSEMBLYFOLDERS = FILES.getAssemblyFolder();
        READFOLDERS = FILES.getReadFolder();

        /**
         * Initialize observable lists for sub-elements which can change
         */
        ArrayList<AssemblyFolderPane> asss =  new ArrayList<>();
        assemblyFolderPanes = FXCollections.observableList( asss );

        ArrayList<ReadFolderPane> readfolders =  new ArrayList<>();
        readFolderPanes = FXCollections.observableList( readfolders );

        /**
         * Define the look and feel of static label elements
         */
        files_label.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24 ) );
        files_label.setPrefSize( 100, 20 );
        files_label.setAlignment( Pos.CENTER );
        files_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        files_label.setAlignment( Pos.CENTER );

        /**
         * Add tooltips to the static label elements
         */

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 2 );
        this.setVgap( 2 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c1 = new ColumnConstraints( 30, 60, 600 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.ALWAYS );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1 );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */
        // Add the title to row 0 column 0
        this.add(files_label, 0, 0, 3, 1);
        // Add the button to the widget with an event handler
        this.add(assembly_box, 1, 1, 3, 1 );
        this.add(read_box, 1, 2, 3, 1 );

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );


        /**
         * Add a listener to watch the AssemblyFolderPane list, and update ASSEMBLY_FOLDERS and the view as needed
         */
        assemblyFolderPanes.addListener( new ListChangeListener<AssemblyFolderPane>() {
            @Override
            public void onChanged( Change<? extends AssemblyFolderPane > c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( AssemblyFolderPane gp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_assembly = new Button();
                            Button add_assembly = new Button();
                            add_assembly.setTooltip(new Tooltip( "Add a new assembly folder" ));
                            remove_assembly.setTooltip(new Tooltip("Remove this assembly folder"));

                            ImageView add_icon_view = new ImageView( add );
                            add_icon_view.setFitHeight( 20 );
                            add_icon_view.setFitWidth( 20 );
                            remove_assembly.setGraphic( add_icon_view );
                            add_assembly.setGraphic( add_icon_view );
                            add_assembly.setAlignment( Pos.BOTTOM_RIGHT );

                            ImageView remove_icon_view = new ImageView( remove );
                            remove_icon_view.setFitHeight( 20 );
                            remove_icon_view.setFitWidth( 20 );
                            remove_assembly.setGraphic( remove_icon_view );

                            /**
                             * When the new_folder button is pressed, add a new assembly folder to the FILES list
                             * with an Assembly
                             */
                            add_assembly.setOnAction( event -> {
                                AssemblyFolder new_folder = new AssemblyFolder();
                                Assembly new_assembly = new Assembly();
                                new_folder.getAssembly().add( new_assembly );
                                ASSEMBLYFOLDERS.add( new_folder );
                                AssemblyFolderPane new_pane = new AssemblyFolderPane( new_folder );
                                assemblyFolderPanes.add( new_pane );
                            } );

                            HBox assmblybox = new HBox();
                            assmblybox.getChildren().addAll( gp );
                            assmblybox.setAlignment( Pos.BOTTOM_CENTER );

                            gp.setButtons( add_assembly, remove_assembly );
                            assembly_box.getChildren().addAll( assmblybox );

                            remove_assembly.setOnAction(
                                    event -> {
                                        if( assemblyFolderPanes.size() > 1 ) {
                                            assemblyFolderPanes.remove( gp );
                                            assembly_box.getChildren().remove( assmblybox );

                                        }
                                        else if( assemblyFolderPanes.size() == 1 ){
                                            AssemblyFolderPane af = assemblyFolderPanes.get( 0 );
                                            af.clear();
                                        }
                                    }
                            );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( AssemblyFolderPane gp : c.getRemoved() ) {
                            assembly_box.getChildren().remove( gp );
                            ASSEMBLYFOLDERS.remove( gp.getAssemblyFolder() );
                        }
                    }
                }
            }
        });

        readFolderPanes.addListener( new ListChangeListener<ReadFolderPane>() {
            @Override
            public void onChanged( Change<? extends ReadFolderPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( ReadFolderPane gp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_rf = new Button();

                            Button add_rf = new Button();
                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_rf.setGraphic( image_view1 );
                            add_rf.setGraphic( image_view1 );
                            add_rf.setAlignment( Pos.BOTTOM_RIGHT );

                            add_rf.setOnAction( event -> {
                                ReadFolder new_folder = new ReadFolder();
                                ReadPair new_pair = new ReadPair();
                                new_folder.getReadPair().add( new_pair );
                                READFOLDERS.add( new_folder );
                                ReadFolderPane new_pane = new ReadFolderPane( new_folder );
                                readFolderPanes.add( new_pane );
                            } );

                            ImageView image_view2 = new ImageView( remove);
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_rf.setGraphic( image_view2 );

                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( gp, remove_rf, add_rf );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            gp.setButtons( add_rf, remove_rf );
                            read_box.getChildren().add( hbox );

                            remove_rf.setOnAction(
                                    event -> {
                                        if(readFolderPanes.size() > 1) {
                                            readFolderPanes.remove( gp );
                                            read_box.getChildren().remove( hbox );
                                        }
                                        else if( readFolderPanes.size() == 1 ){
                                            ReadFolderPane rf = readFolderPanes.get( 0 );
                                            rf.clear();
                                        }
                                    }
                            );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( ReadFolderPane gp : c.getRemoved() ) {
                            read_box.getChildren().remove( gp );
                            READFOLDERS.remove( gp.getReadFolder() );
                        }
                    }
                }
            }
        });

         for( ReadFolder rf : READFOLDERS ){
             ReadFolderPane rfp = new ReadFolderPane( rf );
             readFolderPanes.add( rfp );
         }
        for( AssemblyFolder af: ASSEMBLYFOLDERS ){
            AssemblyFolderPane afp = new AssemblyFolderPane( af );
            assemblyFolderPanes.add( afp );
        }
    }
}