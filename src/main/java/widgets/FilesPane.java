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
    private ObservableList<AlignmentFolderPane> alignmentFolderPanes;
    private ObservableList<VcfFolderPane> vcfFolderPanes;

    private VBox assembly_box = new VBox();
    private GridPane assemblyFolderOuter = new GridPane();

    private VBox read_box = new VBox();
    private GridPane readFolderOuter = new GridPane();

    private VBox align_box = new VBox();
    private GridPane alignFolderOuter = new GridPane();

    private VBox vcfFolderBox = new VBox();
    private GridPane vcfFolderOuter = new GridPane();

    private Files FILES;
    private List<AssemblyFolder> ASSEMBLYFOLDERS;
    private List<ReadFolder> READFOLDERS;
    private List<VCFFolder> VCFFOLDERS;
    private List<AlignmentFolder> ALIGNFOLDERS;

    /**
     *
     * @param input_files a Files Object from the root NaspInputData Object
     */
    FilesPane( Files input_files ){
        FILES = input_files; // initialize File object ( an XML bind object )
        ASSEMBLYFOLDERS = FILES.getAssemblyFolder();
        READFOLDERS = FILES.getReadFolder();
        VCFFOLDERS = FILES.getVCFFolder();
        ALIGNFOLDERS = FILES.getAlignmentFolder();

        /**
         * Initialize observable lists for sub-elements which can change
         */
        ArrayList<AssemblyFolderPane> asss =  new ArrayList<>();
        assemblyFolderPanes = FXCollections.observableList( asss );

        ArrayList<ReadFolderPane> readfolders =  new ArrayList<>();
        readFolderPanes = FXCollections.observableList( readfolders );

        ArrayList<VcfFolderPane> vcffolders =  new ArrayList<>();
        vcfFolderPanes = FXCollections.observableList( vcffolders );

        ArrayList<AlignmentFolderPane> alignfolders =  new ArrayList<>();
        alignmentFolderPanes = FXCollections.observableList( alignfolders );

        /**
         * Define the look and feel of static label elements
         */
        files_label.setId( "header1" );

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
        this.add( files_label, 0, 0, 3, 1 );
        // Add the button to the widget with an event handler

        this.add( assemblyFolderOuter, 1, 1, 3, 1 );
        assemblyFolderOuter.add( assembly_box, 1, 1, 1, 1 );
        ImageView a_add_img = new ImageView( add );
        a_add_img.setFitHeight( 25 );
        a_add_img.setFitWidth( 25 );
        Button addAssemblyFolder = new Button( "", a_add_img );

        Label af_label = new Label( "Assembly Folders" );
        af_label.setId( "header4");
        assemblyFolderOuter.add( af_label, 0, 0, 2, 1 );
        assemblyFolderOuter.add( addAssemblyFolder, 2, 0 );
        addAssemblyFolder.setOnAction( event -> {
            assemblyFolderPanes.add( new AssemblyFolderPane( new AssemblyFolder() ));
        });

        this.add( readFolderOuter, 1, 2, 3, 1 );
        readFolderOuter.add( read_box, 1, 0 );
        ImageView r_add_img = new ImageView( add );
        r_add_img.setFitHeight( 25 );
        r_add_img.setFitWidth( 25 );
        Button addReadFolder = new Button( "", r_add_img );
        readFolderOuter.add( addReadFolder, 1, 1 );
        Label rf_label = new Label( "Read Folders" );
        readFolderOuter.add( rf_label, 0, 0 );
        addReadFolder.setOnAction( event -> {
            readFolderPanes.add( new ReadFolderPane( new ReadFolder() ));
        });

        this.add( vcfFolderOuter, 1, 3, 3, 1 );
        vcfFolderOuter.add( vcfFolderBox, 1, 0 );
        ImageView v_add_img = new ImageView( add );
        v_add_img.setFitHeight( 25 );
        v_add_img.setFitWidth( 25 );
        Button addVcfFolder = new Button( "", v_add_img );
        vcfFolderOuter.add( addVcfFolder, 1, 1 );
        Label vf_label = new Label( "VCF Folders" );
        vcfFolderOuter.add( vf_label, 0, 0 );
        addVcfFolder.setOnAction( event -> {
            vcfFolderPanes.add( new VcfFolderPane( new VCFFolder() ));
        });

        this.add( alignFolderOuter, 1, 4, 3, 1 );
        alignFolderOuter.add( align_box, 1, 0 );
        ImageView al_add_img = new ImageView( add );
        al_add_img.setFitHeight( 25 );
        al_add_img.setFitWidth( 25 );
        Button addAlignFolder = new Button("", al_add_img );
        alignFolderOuter.add( addAlignFolder, 1, 1 );
        Label al_label = new Label( "Alignment Folders" );
        alignFolderOuter.add( al_label, 0, 0 );
        addAlignFolder.setOnAction( event -> {
            alignmentFolderPanes.add( new AlignmentFolderPane( new AlignmentFolder()));
        });

        /**
         * Add a listener to watch the AssemblyFolderPane list, and update ASSEMBLY_FOLDERS and the view as needed
         */
        assemblyFolderPanes.addListener( new ListChangeListener<AssemblyFolderPane>() {
            @Override
            public void onChanged( Change<? extends AssemblyFolderPane > c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( AssemblyFolderPane gp : c.getAddedSubList() ) {

                            ASSEMBLYFOLDERS.add( gp.getAssemblyFolder() );

                            HBox assmblybox = new HBox();
                            assmblybox.getChildren().addAll( gp );
                            assmblybox.setAlignment( Pos.BOTTOM_CENTER );

                            assembly_box.getChildren().addAll( assmblybox );

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

                            READFOLDERS.add( gp.getReadFolder() );

                            HBox hbox = new HBox();
                            hbox.getChildren().addAll( gp );
                            hbox.setAlignment( Pos.BOTTOM_CENTER );
                            read_box.getChildren().add( hbox );

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

        /**
         * Add a listener to watch the VcfFolderPane list, and update ASSEMBLY_FOLDERS and the view as needed
         */
        vcfFolderPanes.addListener( new ListChangeListener<VcfFolderPane>() {
            @Override
            public void onChanged( Change<? extends VcfFolderPane > c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( VcfFolderPane gp : c.getAddedSubList() ) {

                            VCFFOLDERS.add( gp.getVcfFolder() );

                            HBox vcfBox = new HBox();
                            vcfBox.getChildren().addAll( gp );
                            vcfBox.setAlignment( Pos.BOTTOM_CENTER );
                            vcfFolderBox.getChildren().addAll( vcfBox );

                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( VcfFolderPane gp : c.getRemoved() ) {
                            vcfFolderBox.getChildren().remove( gp );
                            VCFFOLDERS.remove( gp.getVcfFolder() );
                        }
                    }
                }
            }
        });
        /**
         * Add a listener to watch the VcfFolderPane list, and update ASSEMBLY_FOLDERS and the view as needed
         */
        alignmentFolderPanes.addListener( new ListChangeListener<AlignmentFolderPane>() {
            @Override
            public void onChanged( Change<? extends AlignmentFolderPane > c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for ( AlignmentFolderPane gp : c.getAddedSubList() ) {

                            ALIGNFOLDERS.add( gp.getAssemblyFolder() );
                            HBox alignBox = new HBox();
                            alignBox.getChildren().addAll( gp );
                            alignBox.setAlignment( Pos.BOTTOM_CENTER );
                            align_box.getChildren().addAll( alignBox );

                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( AlignmentFolderPane gp : c.getRemoved() ) {
                            align_box.getChildren().remove( gp );
                            ALIGNFOLDERS.remove( gp.getAssemblyFolder() );
                        }
                    }
                }
            }
        });


        ArrayList list = new ArrayList<ReadFolderPane>();
        for( ReadFolder rf : READFOLDERS ){
             ReadFolderPane rfp = new ReadFolderPane( rf );
             list.add( rfp );
         }
        readFolderPanes.addAll( list );

        list = new ArrayList<AssemblyFolderPane>();
        for( AssemblyFolder af: ASSEMBLYFOLDERS ){
            AssemblyFolderPane afp = new AssemblyFolderPane( af );
            list.add( afp );
        }
        assemblyFolderPanes.addAll( list );

        list = new ArrayList<VcfFolderPane>();
        for( VCFFolder vf: VCFFOLDERS ){
            VcfFolderPane vfp = new VcfFolderPane( vf );
        }
        vcfFolderPanes.addAll( list );

        list = new ArrayList<AlignmentFolderPane>();
        for( AlignmentFolder alignf: ALIGNFOLDERS ){
            AlignmentFolderPane afp = new AlignmentFolderPane( alignf );
            alignmentFolderPanes.add( afp );
        }
        alignmentFolderPanes.addAll( list );


    }
}