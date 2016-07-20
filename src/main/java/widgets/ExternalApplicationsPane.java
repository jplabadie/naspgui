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

            ApplicationPane<Index> ipane = new ApplicationPane<>( EXTERNALAPPS.getIndex() );
            indexCheck.setOnAction( event -> {
                if (indexCheck.isSelected()) {
                    ipane.setDisable(false);
                    EXTERNALAPPS.setIndex( (Index) ipane.getApplication() );
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

            /** <<< BEGIN BAMINDEX PANE INIT >>>   */
            HBox bamIndexBox = new HBox();
            Boolean bamIndexEmpty = false;
            bamIndexBox.setAlignment(Pos.CENTER_RIGHT);
            bamIndexBox.setSpacing(5);
            CheckBox bamIndexCheck = new CheckBox();

            if (EXTERNALAPPS.getBamIndex() == null) {
                EXTERNALAPPS.setBamIndex( new BamIndex() );
                bamIndexEmpty = true;
            }
            ApplicationPane<BamIndex> bampane = new ApplicationPane<>( EXTERNALAPPS.getBamIndex() );
            bamIndexCheck.setOnAction( event -> {
                if (bamIndexCheck.isSelected()) {
                    bampane.setDisable(false);
                    EXTERNALAPPS.setBamIndex( (BamIndex) bampane.getApplication() );
                }
                else {
                    bampane.setDisable(true);
                    EXTERNALAPPS.setBamIndex( null );
                }
            });

            if( bamIndexEmpty ) {
                bamIndexCheck.setSelected( false );
                bampane.setDisable( true );
            }
            else bamIndexCheck.setSelected( true );

            bamIndexBox.getChildren().addAll( bamIndexCheck, bampane );
            coreApps.add( bampane );
            coreBox.getChildren().add( bamIndexBox );
            /** <<< END BAMINDEX PANE INIT >>> */

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
            ApplicationPane<MatrixGenerator> mpane = new ApplicationPane<>( EXTERNALAPPS.getMatrixGenerator() );
            mgenCheck.setOnAction( event -> {
                if (mgenCheck.isSelected()) {
                    mpane.setDisable(false);
                    EXTERNALAPPS.setMatrixGenerator((MatrixGenerator) mpane.getApplication());
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

            /** <<< BEGIN PICARD PANE INIT >>> */
            HBox picardBox = new HBox();
            picardBox.setAlignment( Pos.CENTER_RIGHT );
            picardBox.setSpacing( 5 );
            CheckBox picardCheck = new CheckBox();
            if ( EXTERNALAPPS.getPicard() == null ) {
                EXTERNALAPPS.setPicard( new Picard() );
                picardCheck.setSelected( false );
            }
            else picardCheck.setSelected( true );
            ApplicationPane<Picard> ppane = new ApplicationPane<>( EXTERNALAPPS.getPicard() );
            picardCheck.setOnAction(event -> {
                if (picardCheck.isSelected()){
                    ppane.setDisable( false );
                    EXTERNALAPPS.setPicard( (Picard) ppane.getApplication() );
                }
                else {
                    ppane.setDisable(true);
                    EXTERNALAPPS.setPicard( null );
                }
            });
            picardBox.getChildren().addAll(picardCheck, ppane);
            coreApps.add(ppane);
            coreBox.getChildren().add(picardBox);
            /** <<< END PICARD PANE INIT >>> */

            /** <<< BEGIN SAMTOOLS PANE INIT >>> */
            HBox samtoolsBox = new HBox();
            samtoolsBox.setAlignment(Pos.CENTER_RIGHT);
            samtoolsBox.setSpacing(5);
            CheckBox samCheck = new CheckBox();
            if (EXTERNALAPPS.getSamtools() == null) {
                EXTERNALAPPS.setSamtools(new Samtools());
                samCheck.setSelected( false );
            }
            else samCheck.setSelected( true );
            ApplicationPane<Samtools> spane = new ApplicationPane<>( EXTERNALAPPS.getSamtools() );
            samCheck.setOnAction(event -> {
                if (samCheck.isSelected()){
                    spane.setDisable(false);
                    EXTERNALAPPS.setSamtools( (Samtools) spane.getApplication() );
                }
                else{
                    spane.setDisable(true);
                    EXTERNALAPPS.setSamtools( null );
                }
            });

            samtoolsBox.getChildren().addAll(samCheck, spane);
            coreApps.add(spane);
            coreBox.getChildren().add(samtoolsBox);
            /** <<< END SAMTOOLS PANE INIT >>> */

            /** <<< BEGIN DUPFINDER PANE INIT >>> */
            HBox dupFindBox = new HBox();
            dupFindBox.setAlignment(Pos.CENTER_RIGHT);
            dupFindBox.setSpacing(5);
            CheckBox dupfindCheck = new CheckBox();
            if (EXTERNALAPPS.getDupFinder() == null) {
                EXTERNALAPPS.setDupFinder(new DupFinder());
                dupfindCheck.setSelected( false );
            }
            else dupfindCheck.setSelected( true );
            ApplicationPane<DupFinder> dpane = new ApplicationPane<>( EXTERNALAPPS.getDupFinder() );
            dupfindCheck.setOnAction(event -> {
                if (dupfindCheck.isSelected()) {
                    dpane.setDisable(false);
                    EXTERNALAPPS.setDupFinder( (DupFinder) dpane.getApplication());
                }
                else {
                    dpane.setDisable(true);
                    EXTERNALAPPS.setDupFinder( null );
                }
            });

            dupFindBox.getChildren().addAll(dupfindCheck, dpane);
            coreApps.add(dpane);
            coreBox.getChildren().add(dupFindBox);
            /** <<< END DUPFINDER PANE INIT >>> */

            /** <<< BEGIN ASSEMBLYIMPORTER PANE INIT >>> */
            HBox assemblyImporterBox = new HBox();
            assemblyImporterBox.setAlignment( Pos.CENTER_RIGHT );
            assemblyImporterBox.setSpacing( 5 );
            CheckBox assemblyImportCheck = new CheckBox();
            if (EXTERNALAPPS.getAssemblyImporter() == null) {
                EXTERNALAPPS.setAssemblyImporter( new AssemblyImporter());
                assemblyImportCheck.setSelected( false );
            }
            else assemblyImportCheck.setSelected( true );

            ApplicationPane<AssemblyImporter> apane = new ApplicationPane<>(EXTERNALAPPS.getAssemblyImporter());
            assemblyImportCheck.setOnAction( event -> {
                if( assemblyImportCheck.isSelected() ){
                    apane.setDisable( false );
                    EXTERNALAPPS.setAssemblyImporter( (AssemblyImporter) apane.getApplication());
                }
                else {
                    apane.setDisable( true );
                    EXTERNALAPPS.setAssemblyImporter( null );
                }
            });

            assemblyImporterBox.getChildren().addAll( assemblyImportCheck, apane );
            coreApps.add( apane );
            coreBox.getChildren().add( assemblyImporterBox );
            /** <<< END ASSEMBLYIMPORTER PANE INIT >>> */

            /** <<< BEGIN READTRIMMER PANE INIT >>> */
            HBox readTrimmerBox = new HBox();
            readTrimmerBox.setAlignment( Pos.CENTER_RIGHT );
            readTrimmerBox.setSpacing( 5 );
            CheckBox readTrimmerCheck = new CheckBox();
            if (EXTERNALAPPS.getReadTrimmer() == null) {
                EXTERNALAPPS.setReadTrimmer( new ReadTrimmer());
                readTrimmerCheck.setSelected( false );
            }
            else readTrimmerCheck.setSelected( true );

            ApplicationPane<ReadTrimmer> tpane = new ApplicationPane<>(EXTERNALAPPS.getReadTrimmer());
            readTrimmerCheck.setOnAction( event -> {
                if( readTrimmerCheck.isSelected() ){
                    tpane.setDisable( false );
                    EXTERNALAPPS.setReadTrimmer( (ReadTrimmer) tpane.getApplication());
                }
                else {
                    tpane.setDisable( true );
                    EXTERNALAPPS.setReadTrimmer( null );
                }
            });

            readTrimmerBox.getChildren().addAll( readTrimmerCheck, tpane );
            coreApps.add( tpane );
            coreBox.getChildren().add( readTrimmerBox );
            /** <<< END READTRIMMER PANE INIT >>> */
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
                            CheckBox alignerCheck = new CheckBox();
                            alignerCheck.setTooltip( new Tooltip( "enable/disable this application") );

                            HBox new_ap_box = new HBox();
                            new_ap_box.setAlignment( Pos.TOP_CENTER);
                            new_ap_box.setSpacing( 10.0 );
                            new_ap_box.getChildren().addAll( alignerCheck, gp );
                            alignersBox.getChildren().add( new_ap_box );

                            alignerCheck.setOnAction( event -> {
                                if(alignerCheck.isSelected()){
                                    gp.setDisable( true );
                                    EXTERNALAPPS.getAligner().remove( (Aligner) gp.getApplication());
                                }
                                else{
                                    gp.setDisable( false );
                                    EXTERNALAPPS.getAligner().add((Aligner) gp.getApplication());
                                }
                            } );


                            // Don't add aligners twice, especially on-load
                            Application app = gp.getApplication();
                            if( ! EXTERNALAPPS.getAligner().contains( app ))
                                EXTERNALAPPS.getAligner().add( (Aligner) app );
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
                            CheckBox snpCheck = new CheckBox();
                            snpCheck.setTooltip( new Tooltip( "enable/disable this application") );

                            HBox new_snp_box = new HBox();
                            new_snp_box.setAlignment( Pos.TOP_CENTER);
                            new_snp_box.setSpacing( 10.0 );
                            new_snp_box.getChildren().addAll( snpCheck, gp );
                            snpcallersBox.getChildren().add( new_snp_box );

                            snpCheck.setOnAction( event -> {
                                if(snpCheck.isSelected()){
                                    gp.setDisable( true );
                                    EXTERNALAPPS.getSNPCaller().remove( (SNPCaller) gp.getApplication());
                                }
                                else{
                                    gp.setDisable( false );
                                    EXTERNALAPPS.getSNPCaller().add( (SNPCaller) gp.getApplication());
                                }
                            } );


                            // Don't add SNPCallers twice, especially on-load
                            Application app = gp.getApplication();
                            if( ! EXTERNALAPPS.getSNPCaller().contains( app ))
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