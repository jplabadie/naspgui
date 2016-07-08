package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
class ExternalApplicationsPane extends Accordion {

    private Label external_apps_label = new Label("External Applications");

    private Image add = new Image(getClass().getResourceAsStream("/icons/add-1.png"));
    private Image remove = new Image(getClass().getResourceAsStream("/icons/garbage-2.png"));

    private ObservableList <ApplicationPane> coreApps;
    private ObservableList <ApplicationPane> alignerApps;
    private ObservableList <ApplicationPane> snpApps;

    private TitledPane coreAppsPane = new TitledPane();
    private TitledPane alignerAppsPane = new TitledPane();
    private TitledPane snpAppsPane = new TitledPane();

    private ExternalApplications EXTERNALAPPS;

    ExternalApplicationsPane( ExternalApplications binding ) {

        EXTERNALAPPS = binding;

        /**
         * Initialize the observable list which will hold the read pairs for this widget
         */
        coreApps = FXCollections.observableList( new ArrayList<ApplicationPane>() );
        alignerApps = FXCollections.observableList( new ArrayList<ApplicationPane>() );
        snpApps = FXCollections.observableList( new ArrayList<ApplicationPane>() );

        coreAppsPane.setText( "Tools" );
        alignerAppsPane.setText( "Aligners" );
        snpAppsPane.setText( "SNP Callers" );

        initCoreAppsPane();
        initAlignerAppsPane();
        initSnpAppsPane();

        this.getChildren().addAll( coreAppsPane, alignerAppsPane, snpAppsPane );

        /**
         * Define the look and feel of static label elements
         */
        external_apps_label.setFont( Font.font( "Helvetica", FontWeight.EXTRA_BOLD, 24 ));
        external_apps_label.setPrefSize( 100, 20 );
        external_apps_label.setAlignment( Pos.CENTER );
        external_apps_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        external_apps_label.setAlignment( Pos.CENTER );


        this.getChildren().add( external_apps_label );

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );



        if(EXTERNALAPPS == null){
            EXTERNALAPPS = new ExternalApplications();
        }
        else{
            if( EXTERNALAPPS.getIndex() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getIndex() );
                coreApps.add( app );
            }
            if( EXTERNALAPPS.getMatrixGenerator() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getMatrixGenerator() );
                coreApps.add( app );
            }
            if( EXTERNALAPPS.getPicard() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getPicard() );
                coreApps.add( app );
            }
            if( EXTERNALAPPS.getSamtools() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getSamtools() );
                coreApps.add( app );
            }
            if( EXTERNALAPPS.getDupFinder() != null ){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getDupFinder() );
                coreApps.add( app );
            }
            if( EXTERNALAPPS.getAssemblyImporter() != null){
                ApplicationPane app = new ApplicationPane( EXTERNALAPPS.getAssemblyImporter() );
                coreApps.add( app );
            }
            for( SNPCaller snpcaller : EXTERNALAPPS.getSNPCaller()){
                ApplicationPane app = new ApplicationPane( snpcaller );
                coreApps.add( app );
            }
            for( Aligner aligner : EXTERNALAPPS.getAligner()){
                ApplicationPane app = new ApplicationPane( aligner );
                coreApps.add( app );
            }
        }
    }

    private void initCoreAppsPane() {

        HBox matrixGenBox = new HBox();
        HBox indexBox = new HBox();
        HBox picardBox = new HBox();
        HBox samtoolsBox = new HBox();
        HBox dupFindBox = new HBox();
        HBox assemblyImporterBox = new HBox();

        CheckBox mgenCheck = new CheckBox();
        CheckBox indexCheck = new CheckBox();
        CheckBox picardCheck = new CheckBox();
        CheckBox samtoolsCheck = new CheckBox();
        CheckBox dupfindCheck = new CheckBox();
        CheckBox assemblyImportCheck = new CheckBox();

        ApplicationPane<MatrixGenerator> mpane = new ApplicationPane<>( EXTERNALAPPS.getMatrixGenerator() );
        ApplicationPane<Index> ipane = new ApplicationPane<>( EXTERNALAPPS.getIndex() );
        ApplicationPane<Picard> ppane = new ApplicationPane<>( EXTERNALAPPS.getPicard() );
        ApplicationPane<Samtools> spane = new ApplicationPane<>( EXTERNALAPPS.getSamtools() );
        ApplicationPane<DupFinder> dpane = new ApplicationPane<>( EXTERNALAPPS.getDupFinder() );
        ApplicationPane<AssemblyImporter> apane = new ApplicationPane<>( EXTERNALAPPS.getAssemblyImporter() );

        matrixGenBox.getChildren().addAll( mgenCheck, mpane );
        indexBox.getChildren().addAll( indexCheck, ipane );
        picardBox.getChildren().addAll( picardCheck, ppane );
        samtoolsBox.getChildren().addAll( samtoolsCheck, spane );
        dupFindBox.getChildren().addAll( dupfindCheck, dpane );
        assemblyImporterBox.getChildren().addAll( assemblyImportCheck, apane );

        VBox coreBox = new VBox();
        coreBox.getChildren().addAll( matrixGenBox, indexBox, picardBox, samtoolsBox, dupFindBox, assemblyImporterBox);


        coreApps.addListener(new ListChangeListener<ApplicationPane>() {
            @Override
            public void onChanged( Change<? extends ApplicationPane> c ) {
                while ( c.next() ) {


                }
            }
        });
    }

    private void initAlignerAppsPane(){

        VBox alignersBox = new VBox();

        alignerApps.addListener(new ListChangeListener<ApplicationPane>() {
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
                                coreApps.add( new_pane );
                            } );

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_app.setGraphic( image_view2 );

                            HBox new_ap_box = new HBox();
                            new_ap_box.getChildren().addAll( gp, add_app, remove_app );
                            new_ap_box.setAlignment( Pos.BOTTOM_CENTER );

                            //new_app.setButtons(add_app, remove_app);
                            //appbox.getChildren().add( new_ap_box );

                            remove_app.setOnAction(
                                    event -> {
                                        //appbox.getChildren().remove( new_ap_box );
                                        coreApps.remove( gp );
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


                        }
                    }
                }
            }
        });
    }

    private void initSnpAppsPane(){

    }
}