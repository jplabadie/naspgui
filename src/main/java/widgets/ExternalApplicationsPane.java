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

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
class ExternalApplicationsPane extends GridPane {

    private GridPane F = this;
    private Label external_apps_label = new Label("External Applications");

    private Image add = new Image(getClass().getResourceAsStream("/icons/add-1.png"));
    private Image remove = new Image(getClass().getResourceAsStream("/icons/garbage-2.png"));

    private ObservableList<ApplicationPane> apps;

    private VBox appbox = new VBox();

    private ExternalApplications EXTERNALAPPS;


    ExternalApplicationsPane( ExternalApplications binding ) {
        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        ArrayList<ApplicationPane> app_panes = new ArrayList<>();
        apps = FXCollections.observableList( app_panes );

        EXTERNALAPPS = binding;

        /**
         * Define the look and feel of static label elements
         */
        external_apps_label.setFont( Font.font( "Helvetica", FontWeight.EXTRA_BOLD, 24 ));
        external_apps_label.setPrefSize( 100, 20 );
        external_apps_label.setAlignment( Pos.CENTER );
        external_apps_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        external_apps_label.setAlignment( Pos.CENTER );

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
        ColumnConstraints c1 = new ColumnConstraints( 30, 200, 600 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.ALWAYS );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1 );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add( external_apps_label, 0, 0, 3, 1 );

        // Add the application box to the widget with an event handler
        appbox.setSpacing( 5 );
        this.add( appbox, 1, 1, 3, 1 );

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );

        apps.addListener( new ListChangeListener<ApplicationPane>() {
            @Override
            public void onChanged( Change<? extends ApplicationPane> c ) {
                while ( c.next() ) {
                    if ( c.wasAdded() ) {
                        for (ApplicationPane gp : c.getAddedSubList()) {
                            // Add the remove button to the widget
                            Button remove_app = new Button();
                            Button add_app = new Button();
                            add_app.setTooltip( new Tooltip("Add a new application") );
                            remove_app.setTooltip( new Tooltip("Remove this application") );

                            ImageView image_view1 = new ImageView( add );
                            image_view1.setFitHeight( 20 );
                            image_view1.setFitWidth( 20 );
                            remove_app.setGraphic( image_view1 );
                            add_app.setGraphic( image_view1 );
                            add_app.setAlignment( Pos.BOTTOM_RIGHT );

                            add_app.setOnAction( event -> {
                                Application new_app = new MatrixGenerator(); // TODO: Answer: is this unwrapping bad?
                                ApplicationPane new_pane = new ApplicationPane( new_app );
                                apps.add( new_pane );
                            } );

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_app.setGraphic( image_view2 );

                            HBox new_ap_box = new HBox();
                            new_ap_box.getChildren().addAll( gp, add_app, remove_app );
                            new_ap_box.setAlignment( Pos.BOTTOM_CENTER );

                            //new_app.setButtons(add_app, remove_app);
                            appbox.getChildren().add( new_ap_box );

                            remove_app.setOnAction(
                                event -> {
                                    appbox.getChildren().remove( new_ap_box );
                                    apps.remove( gp );
                                }
                            );

                            Application app = gp.getApplication();

                            if( app.getClass() == Index.class )
                                EXTERNALAPPS.setIndex((Index) app);
                            else if( app.getClass() == MatrixGenerator.class )
                                EXTERNALAPPS.setMatrixGenerator((MatrixGenerator) app);
                            else if( app.getClass() == Picard.class )
                                EXTERNALAPPS.setPicard((Picard) app);
                            else if( app.getClass() == Samtools.class )
                                EXTERNALAPPS.setSamtools((Samtools) app);
                            else if( app.getClass() == DupFinder.class )
                                EXTERNALAPPS.setDupFinder((DupFinder) app);
                            else if( app.getClass() == AssemblyImporter.class )
                                EXTERNALAPPS.setAssemblyImporter((AssemblyImporter) app);
                            else if( app.getClass() == Aligner.class )
                                EXTERNALAPPS.getAligner().add((Aligner) app);
                            else if( app.getClass() == SNPCaller.class)
                                EXTERNALAPPS.getSNPCaller().add((SNPCaller) app);
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( ApplicationPane gp : c.getRemoved() ) {

                            appbox.getChildren().remove( gp );
                            Application app = gp.getApplication();

                            if( app.getClass() == Index.class )
                                EXTERNALAPPS.setIndex( null );
                            else if( app.getClass() == MatrixGenerator.class )
                                EXTERNALAPPS.setMatrixGenerator( null );
                            else if( app.getClass() == Picard.class )
                                EXTERNALAPPS.setPicard( null );
                            else if( app.getClass() == Samtools.class )
                                EXTERNALAPPS.setSamtools( null );
                            else if( app.getClass() == DupFinder.class )
                                EXTERNALAPPS.setDupFinder( null );
                            else if( app.getClass() == AssemblyImporter.class )
                                EXTERNALAPPS.setAssemblyImporter( null );
                            else if( app.getClass() == Aligner.class )
                                EXTERNALAPPS.getAligner().remove( app );
                            else if( app.getClass() == SNPCaller.class)
                                EXTERNALAPPS.getSNPCaller().remove( app );
                        }
                    }
                }
            }
        });

        if(EXTERNALAPPS == null){
            EXTERNALAPPS = new ExternalApplications();
        }
        else{
            if( EXTERNALAPPS.getIndex() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getIndex() );
                apps.add( app );
            }
            if( EXTERNALAPPS.getMatrixGenerator() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getMatrixGenerator() );
                apps.add( app );
            }
            if( EXTERNALAPPS.getPicard() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getPicard() );
                apps.add( app );
            }
            if( EXTERNALAPPS.getSamtools() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getSamtools() );
                apps.add( app );
            }
            if( EXTERNALAPPS.getDupFinder() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getDupFinder() );
                apps.add( app );
            }
            if( EXTERNALAPPS.getAssemblyImporter() != null){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getAssemblyImporter() );
                apps.add( app );
            }
            for( SNPCaller snpcaller : EXTERNALAPPS.getSNPCaller()){
                ApplicationPane app = new ApplicationPane( snpcaller );
                apps.add( app );
            }
            for( Aligner aligner : EXTERNALAPPS.getAligner()){
                ApplicationPane app = new ApplicationPane( aligner );
                apps.add( app );
            }
        }
    }
}