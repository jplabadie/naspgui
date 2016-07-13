package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

    private Label external_apps_label = new Label("External Applications");

    private Image add = new Image(getClass().getResourceAsStream("/icons/add-1.png"));
    private Image remove = new Image(getClass().getResourceAsStream("/icons/garbage-2.png"));

    private ObservableList <ApplicationPane> coreApps;
    private ObservableList <ApplicationPane> alignerApps;
    private ObservableList <ApplicationPane> snpApps;

    private TitledPane coreAppsPane = new TitledPane();
    private TitledPane alignerAppsPane = new TitledPane();
    private TitledPane snpAppsPane = new TitledPane();

    private Accordion accordion = new Accordion();

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

        accordion.getPanes().addAll( coreAppsPane, alignerAppsPane, snpAppsPane );

        /**
         * Define the look and feel of static label elements
         */
        external_apps_label.setFont( Font.font( "Helvetica", FontWeight.EXTRA_BOLD, 24 ));
        external_apps_label.setPrefSize( 100, 20 );
        external_apps_label.setAlignment( Pos.CENTER );
        external_apps_label.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        external_apps_label.setAlignment( Pos.CENTER );

        ColumnConstraints c0 = new ColumnConstraints( 50,50,50 );
        ColumnConstraints c1 = new ColumnConstraints( 150,400,900 );
        c1.setHgrow( Priority.ALWAYS );
        this.getColumnConstraints().addAll( c0, c1 );

        this.add( external_apps_label, 0, 0, 2, 1 );
        this.add( accordion, 1, 1, 2, 1 );

        ImageView image_view = new ImageView( add );
        image_view.setFitHeight( 20 );
        image_view.setFitWidth( 20 );
    }

    private void initCoreAppsPane() {
        VBox coreBox = new VBox();
        coreBox.setSpacing( 10 );
        coreAppsPane.setContent( coreBox );

        if (EXTERNALAPPS == null) {
            EXTERNALAPPS = new ExternalApplications();
        } else {

            /** <<< BEGIN INDEX PANE INIT >>>   */
            HBox indexBox = new HBox();
            indexBox.setAlignment(Pos.CENTER_RIGHT);
            indexBox.setSpacing(5);
            CheckBox indexCheck = new CheckBox();
            if (EXTERNALAPPS.getIndex() == null) {
                EXTERNALAPPS.setIndex( new Index() );
                indexCheck.setSelected( false );
            }
            else indexCheck.setSelected( true );

            Index thisindex = EXTERNALAPPS.getIndex();
            ApplicationPane<Index> ipane = new ApplicationPane<>( EXTERNALAPPS.getIndex() );
            indexCheck.setOnAction( event -> {
                if (indexCheck.isSelected()) {
                    ipane.setDisable(false);
                    EXTERNALAPPS.setIndex( thisindex );
                }
                else {
                    ipane.setDisable(true);
                    EXTERNALAPPS.setIndex( null );
                }
            });

            indexBox.getChildren().addAll(indexCheck, ipane);
            coreApps.add(ipane);
            coreBox.getChildren().add(indexBox);
            /** <<< END INDEX PANE INIT >>> */

            /** <<< BEGIN MATRIXGENERATOR PANE INIT >>>   */
            HBox matrixGenBox = new HBox();
            matrixGenBox.setAlignment(Pos.CENTER_RIGHT);
            matrixGenBox.setSpacing(5);
            CheckBox mgenCheck = new CheckBox();
            if (EXTERNALAPPS.getMatrixGenerator() == null) {
                EXTERNALAPPS.setMatrixGenerator(new MatrixGenerator());
                mgenCheck.setSelected( false );
            }
            else mgenCheck.setSelected( true );

            MatrixGenerator thismatrixgen = EXTERNALAPPS.getMatrixGenerator();
            ApplicationPane<MatrixGenerator> mpane = new ApplicationPane<>( EXTERNALAPPS.getMatrixGenerator() );
            mgenCheck.setOnAction( event -> {
                if (mgenCheck.isSelected()) {
                    mpane.setDisable(false);
                    EXTERNALAPPS.setMatrixGenerator( thismatrixgen);
                }
                else{
                    mpane.setDisable(true);
                    EXTERNALAPPS.setMatrixGenerator( null );
                }
            });
            coreApps.add(mpane);
            matrixGenBox.getChildren().addAll(mgenCheck, mpane);
            coreBox.getChildren().add(matrixGenBox);
            /** <<< END MATRIXGENERATOR PANE INIT >>> */

            // Picard
            if (EXTERNALAPPS.getPicard() == null) {
                EXTERNALAPPS.setPicard(new Picard());
            }
            HBox picardBox = new HBox();
            picardBox.setAlignment(Pos.CENTER_RIGHT);
            picardBox.setSpacing(5);
            CheckBox picardCheck = new CheckBox();
            picardCheck.setSelected(false);
            ApplicationPane<Picard> ppane = new ApplicationPane<>(EXTERNALAPPS.getPicard());
            picardCheck.setOnAction(event -> {
                if (picardCheck.isSelected())
                    ppane.setDisable(false);
                else ppane.setDisable(true);
            });
            picardBox.getChildren().addAll(picardCheck, ppane);
            coreApps.add(ppane);
            coreBox.getChildren().add(picardBox);

            // SamTools
            if (EXTERNALAPPS.getSamtools() == null) {
                EXTERNALAPPS.setSamtools(new Samtools());
            }
            HBox samtoolsBox = new HBox();
            samtoolsBox.setAlignment(Pos.CENTER_RIGHT);
            samtoolsBox.setSpacing(5);
            CheckBox samCheck = new CheckBox();
            samCheck.setSelected(false);
            ApplicationPane<Samtools> spane = new ApplicationPane<>(EXTERNALAPPS.getSamtools());
            samCheck.setOnAction(event -> {
                if (samCheck.isSelected())
                    spane.setDisable(false);
                else spane.setDisable(true);
            });

            samtoolsBox.getChildren().addAll(samCheck, spane);
            coreApps.add(spane);
            coreBox.getChildren().add(samtoolsBox);

            // DupFinder
            if (EXTERNALAPPS.getDupFinder() == null) {
                EXTERNALAPPS.setDupFinder(new DupFinder());
            }
            HBox dupFindBox = new HBox();
            dupFindBox.setAlignment(Pos.CENTER_RIGHT);
            dupFindBox.setSpacing(5);
            CheckBox dupfindCheck = new CheckBox();
            dupfindCheck.setSelected(false);
            ApplicationPane<DupFinder> dpane = new ApplicationPane<>(EXTERNALAPPS.getDupFinder());
            dupfindCheck.setOnAction(event -> {
                if (dupfindCheck.isSelected())
                    dpane.setDisable(false);
                else dpane.setDisable(true);
            });

            dupFindBox.getChildren().addAll(dupfindCheck, dpane);
            coreApps.add(dpane);
            coreBox.getChildren().add(dupFindBox);

            // AssemblyImporter
            if (EXTERNALAPPS.getAssemblyImporter() == null) {
                EXTERNALAPPS.setAssemblyImporter( new AssemblyImporter());
            }
            HBox assemblyImporterBox = new HBox();
            assemblyImporterBox.setAlignment( Pos.CENTER_RIGHT );
            assemblyImporterBox.setSpacing( 5 );
            CheckBox assemblyImportCheck = new CheckBox();
            ApplicationPane<AssemblyImporter> apane = new ApplicationPane<>(EXTERNALAPPS.getAssemblyImporter());
            assemblyImportCheck.setOnAction( event -> {
                if( assemblyImportCheck.isSelected() )
                    assemblyImporterBox.setDisable( false );
                else assemblyImporterBox.setDisable( true );
            });

            assemblyImporterBox.getChildren().addAll( assemblyImportCheck, apane );
            coreApps.add( apane );
            coreBox.getChildren().add( assemblyImporterBox );
        }
    }

    private void initAlignerAppsPane(){

        VBox alignersBox = new VBox();
        alignersBox.setSpacing( 10 );
        alignerAppsPane.setContent( alignersBox );

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
                                Aligner new_app = new Aligner(); // TODO: Answer: is this unwrapping bad?
                                ApplicationPane<Aligner> new_pane = new ApplicationPane<>( new_app );
                                alignerApps.add( new_pane );
                            } );

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_app.setGraphic( image_view2 );

                            HBox new_ap_box = new HBox();
                            new_ap_box.getChildren().addAll( gp, add_app, remove_app );
                            new_ap_box.setAlignment( Pos.BOTTOM_CENTER );
                            alignersBox.getChildren().add( new_ap_box );

                            //new_app.setButtons(add_app, remove_app);
                            //appbox.getChildren().add( new_ap_box );

                            remove_app.setOnAction(
                                    event -> {
                                        //appbox.getChildren().remove( new_ap_box );
                                        alignerApps.remove( gp );
                                        alignersBox.getChildren().remove( new_ap_box );
                                    }
                            );

                            Application app = gp.getApplication();
                            EXTERNALAPPS.getAligner().add( (Aligner) app );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( ApplicationPane gp : c.getRemoved() ) {
                            EXTERNALAPPS.getAligner().remove( (Aligner) gp.getApplication());

                        }
                    }
                }
            }
        });

        ArrayList< ApplicationPane<Aligner>> temp = new ArrayList<>();
        for (Aligner aligner : EXTERNALAPPS.getAligner()) {
            ApplicationPane<Aligner> app = new ApplicationPane<>( aligner );
            temp.add( app );
        }
        alignerApps.addAll( temp );


    }

    private void initSnpAppsPane(){
        VBox snpcallersBox = new VBox();
        snpcallersBox.setSpacing( 10 );
        snpAppsPane.setContent( snpcallersBox );

        snpApps.addListener(new ListChangeListener<ApplicationPane>() {
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
                                SNPCaller new_app = new SNPCaller(); // TODO: Answer: is this unwrapping bad?
                                ApplicationPane<SNPCaller> new_pane = new ApplicationPane<>( new_app );
                                snpApps.add( new_pane );
                            } );

                            ImageView image_view2 = new ImageView( remove );
                            image_view2.setFitHeight( 20 );
                            image_view2.setFitWidth( 20 );
                            remove_app.setGraphic( image_view2 );

                            HBox new_ap_box = new HBox();
                            new_ap_box.getChildren().addAll( gp, add_app, remove_app );
                            new_ap_box.setAlignment( Pos.BOTTOM_CENTER );
                            snpcallersBox.getChildren().add( new_ap_box );

                            //new_app.setButtons(add_app, remove_app);
                            //appbox.getChildren().add( new_ap_box );

                            remove_app.setOnAction(
                                    event -> {
                                        snpApps.remove( gp );
                                        snpcallersBox.getChildren().remove( new_ap_box );
                                    }
                            );

                            Application app = gp.getApplication();
                            EXTERNALAPPS.getSNPCaller().add( (SNPCaller) app );
                        }
                    }
                    if ( c.wasRemoved() ) {
                        for ( ApplicationPane gp : c.getRemoved() ) {
                            EXTERNALAPPS.getSNPCaller().remove( (SNPCaller) gp.getApplication());

                        }
                    }
                }
            }
        });

        ArrayList< ApplicationPane <SNPCaller> > temp = new ArrayList<>();
        for (SNPCaller snpcaller : EXTERNALAPPS.getSNPCaller()) {
            ApplicationPane<SNPCaller> app = new ApplicationPane<>(snpcaller);
            temp.add(app);
        }
        snpApps.addAll( temp );

    }
}