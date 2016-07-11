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

        this.getPanes().addAll( coreAppsPane, alignerAppsPane, snpAppsPane );

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
    }

    private void initCoreAppsPane() {
        VBox coreBox = new VBox();
        coreAppsPane.setContent( coreBox );

        if(EXTERNALAPPS == null){
            EXTERNALAPPS = new ExternalApplications();
        }
        else {
            if (EXTERNALAPPS.getIndex() == null) {
                EXTERNALAPPS.setIndex(new Index());
            }
            HBox indexBox = new HBox();
            indexBox.setAlignment(Pos.CENTER_RIGHT);
            indexBox.setSpacing(5);
            CheckBox indexCheck = new CheckBox();
            indexCheck.setSelected(true);
            ApplicationPane<Index> ipane = new ApplicationPane<>(EXTERNALAPPS.getIndex());
            indexCheck.setOnAction(event -> {
                if (indexCheck.isSelected())
                    ipane.setDisable(false);
                else ipane.setDisable(true);
            });

            indexBox.getChildren().addAll(indexCheck, ipane);
            coreApps.add(ipane);
            coreBox.getChildren().add(indexBox);

            if (EXTERNALAPPS.getMatrixGenerator() == null) {
                EXTERNALAPPS.setMatrixGenerator(new MatrixGenerator());
            }
            HBox matrixGenBox = new HBox();
            matrixGenBox.setAlignment(Pos.CENTER_RIGHT);
            matrixGenBox.setSpacing(5);
            CheckBox mgenCheck = new CheckBox();
            mgenCheck.setSelected(true);
            ApplicationPane<MatrixGenerator> mpane = new ApplicationPane<>(EXTERNALAPPS.getMatrixGenerator());
            mgenCheck.setOnAction(event -> {
                if (mgenCheck.isSelected())
                    mpane.setDisable(false);
                else mpane.setDisable(true);
            });
            coreApps.add(mpane);
            matrixGenBox.getChildren().addAll(mgenCheck, mpane);
            coreBox.getChildren().add(matrixGenBox);

            if (EXTERNALAPPS.getPicard() == null) {
                EXTERNALAPPS.setPicard(new Picard());
            }
            HBox picardBox = new HBox();
            picardBox.setAlignment( Pos.CENTER_RIGHT );
            picardBox.setSpacing( 5 );
            CheckBox picardCheck = new CheckBox();
            picardCheck.setSelected(false);
            ApplicationPane<Picard> ppane = new ApplicationPane<>(EXTERNALAPPS.getPicard());
            picardCheck.setOnAction(event -> {
                if (picardCheck.isSelected())
                    ppane.setDisable(true);
                else ppane.setDisable(false);
            });
            picardBox.getChildren().addAll( picardCheck, ppane);
            coreApps.add(ppane);
            coreBox.getChildren().add(picardBox);

            if (EXTERNALAPPS.getSamtools() != null) {
                ApplicationPane<Samtools> spane = new ApplicationPane<>(EXTERNALAPPS.getSamtools());
                CheckBox samtoolsCheck = new CheckBox();
                HBox samtoolsBox = new HBox();
                samtoolsBox.getChildren().addAll(samtoolsCheck, spane);
                coreApps.add(spane);
            }
            if (EXTERNALAPPS.getDupFinder() != null) {
                ApplicationPane<DupFinder> dpane = new ApplicationPane<>(EXTERNALAPPS.getDupFinder());
                HBox dupFindBox = new HBox();
                CheckBox dupfindCheck = new CheckBox();
                dupFindBox.getChildren().addAll(dupfindCheck, dpane);
                coreApps.add(dpane);
            }
            if (EXTERNALAPPS.getAssemblyImporter() != null) {
                HBox assemblyImporterBox = new HBox();
                CheckBox assemblyImportCheck = new CheckBox();
                ApplicationPane<AssemblyImporter> apane = new ApplicationPane<>(EXTERNALAPPS.getAssemblyImporter());
                assemblyImporterBox.getChildren().addAll(assemblyImportCheck, apane);
                coreApps.add(apane);
            }
            for (SNPCaller snpcaller : EXTERNALAPPS.getSNPCaller()) {
                ApplicationPane<SNPCaller> app = new ApplicationPane<>(snpcaller);
                coreApps.add(app);
            }
            for (Aligner aligner : EXTERNALAPPS.getAligner()) {
                ApplicationPane<Aligner> app = new ApplicationPane<>(aligner);
                coreApps.add(app);
            }
        }
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