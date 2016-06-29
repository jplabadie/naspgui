package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.Files;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class FilesGridPane extends GridPane {

    private GridPane F = this;
    private Label FILES = new Label( "Files" );

    private Image add = new Image( getClass().getResourceAsStream( "/icons/add-3.png" ) );
    private Image remove = new Image( getClass().getResourceAsStream( "/icons/stop.png" ) );

    private ObservableList<AssemblyFolderGridPane> assemblies;
    private ObservableList<ReadFolderGridPane> reads;

    private VBox assbox = new VBox();
    private VBox readbox = new VBox();

    private Files files = new Files();

    FilesGridPane(){
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<AssemblyFolderGridPane> asss =  new ArrayList<>();
        assemblies = FXCollections.observableList( asss );

        ArrayList<ReadFolderGridPane> readfolders =  new ArrayList<>();
        reads = FXCollections.observableList( readfolders );
        /**
         * Define the look and feel of static label elements
         */

        FILES.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24 ) );
        FILES.setPrefSize( 100, 20 );
        FILES.setAlignment( Pos.CENTER );
        FILES.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        FILES.setAlignment( Pos.CENTER );

        /**
         * Add tooltips to the static label elements
         */

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 30, 60, 90 );
        ColumnConstraints c1 = new ColumnConstraints( 30, 60, 600 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.ALWAYS);
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1 );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(FILES, 0, 0, 3, 1);

        // Add the button to the widget with an event handler

        this.add( assbox, 1, 1, 3, 1 );
        this.add( readbox, 1, 2, 3, 1 );

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );

        assemblies.addListener(new ListChangeListener<GridPane>() {

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

                            add_assembly.setOnAction( event -> addAssemblyFolder() );

                            ImageView image_view2 = new ImageView( remove);
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_assembly.setGraphic( image_view2 );

                            AssemblyFolderGridPane new_folder = new AssemblyFolderGridPane();
                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( new_folder, remove_assembly, add_assembly );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            assbox.getChildren().add ( hbox );

                            remove_assembly.setOnAction(
                                    event -> {
                                        if(assemblies.size() > 1) {
                                            assemblies.remove( gp );
                                            assbox.getChildren().remove( hbox );
                                        }
                                        else if( assemblies.size() == 1 ){
                                            AssemblyFolderGridPane af = assemblies.get( 0 );
                                            af.clear();
                                        }
                                    }
                            );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( GridPane gp : c.getRemoved() ) {
                            assbox.getChildren().remove(gp);
                        }
                    }
                }
            }
        });

        reads.addListener(new ListChangeListener<GridPane>() {

            @Override
            public void onChanged( Change<? extends GridPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( GridPane gp : c.getAddedSubList() ) {
                            // Add the remove button to the widget
                            Button remove_rf = new Button();

                            Button add_rf = new Button();
                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_rf.setGraphic( image_view1 );
                            add_rf.setGraphic( image_view1 );
                            add_rf.setAlignment( Pos.BOTTOM_RIGHT );

                            add_rf.setOnAction( event -> addAssemblyFolder() );

                            ImageView image_view2 = new ImageView( remove);
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_rf.setGraphic( image_view2 );

                            ReadFolderGridPane temp_rf = new ReadFolderGridPane();
                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( temp_rf, remove_rf, add_rf );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            readbox.getChildren().add( hbox );

                            remove_rf.setOnAction(
                                    event -> {
                                        if(reads.size() > 1) {
                                            reads.remove( gp );
                                            F.getChildren().remove( hbox );
                                        }
                                        else if( reads.size() == 1 ){
                                            ReadFolderGridPane rf = reads.get( 0 );
                                            rf.clear();
                                        }
                                    }
                            );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( GridPane gp : c.getRemoved() ) {
                            readbox.getChildren().remove( gp );
                        }
                    }
                }
            }
        });

        this.addAssemblyFolder();
        this.addReadFolder();
    }

    void addAssemblyFolder(String path){
        AssemblyFolderGridPane af = new AssemblyFolderGridPane();
        af.setFolderPath( path );
        assemblies.add( af );
    }

    void addAssemblyFolder(){
        AssemblyFolderGridPane af = new AssemblyFolderGridPane();
        assemblies.add( af );
    }

    void addReadFolder(String path){
        ReadFolderGridPane rf = new ReadFolderGridPane();
        reads.add( rf );
    }

    void addReadFolder(){
        ReadFolderGridPane rf = new ReadFolderGridPane();
        reads.add( rf );
    }
}