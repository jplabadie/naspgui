package widgets;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import utils.DefaultRemoteNetUtil;
import utils.JobRecord;
import utils.JobSaveLoadManager;
import utils.RemoteNetUtil;
import xmlbinds.ExternalApplications;
import xmlbinds.MatrixGenerator;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project naspgui.
 * Created by jlabadie on 6/29/16.
 *
 * @author jlabadie
 */
public class JobTab extends Tab {

    private BorderPane borderPane = new BorderPane();
    private ScrollPane scrollPane = new ScrollPane();
    private VBox vBox = new VBox();
    private ToolBar bottom_toolbar = new ToolBar();

    private JobRecord job_rec; //TODO: Integrate jobRecord/logging

    private OptionsPane optspane;
    private FilesPane filespane;
    private ExternalApplicationsPane xappspane;
    private NaspInputData NASP_DATA;

    private ObjectFactory OF = new ObjectFactory();
    private RemoteNetUtil net = new DefaultRemoteNetUtil();

    /**
     * Creates a job tab window from existing NaspData
     * @param input NaspInputData initialized from XML
     */
    public JobTab( NaspInputData input ) {
        // TODO: Display NaspInputData correctly in the View
        NASP_DATA = input;
        initialize();
    }

    public JobTab(){
        initialize();
    }

    /**
     *  Creates a blank job and initializes new NASP data
     */
    void initialize() {

        borderPane.setPrefHeight( 900 );
        /**
         * Create new (blank) NaspInputData root and populate with Children
         */
        if(NASP_DATA == null) {
            NASP_DATA = OF.createNaspInputData(); // Create NaspInputData
        }
        //Create and populate ExternalApplications
        ExternalApplications externalApplications = NASP_DATA.getExternalApplications(); // Create ExternalApplications
        MatrixGenerator mg = externalApplications.getMatrixGenerator(); // Create MatrixGenerator
        if( mg == null){
            mg = OF.createMatrixGenerator();
            externalApplications.setMatrixGenerator( mg );
        }
        mg.setName( "MatrixGenerator" ); // set MatrixGenerator name to default (MatrixGenerator)

        optspane = new OptionsPane( NASP_DATA.getOptions() ); // init OptionsPane
        filespane = new FilesPane( NASP_DATA.getFiles() ); // init FilesPane
        xappspane = new ExternalApplicationsPane( NASP_DATA.getExternalApplications() ); //Init ExApps Pane

        this.setContent( borderPane ); // set a BorderPane as the root container for this Tab
        scrollPane.setContent( vBox ); // add a VBox to the scroll pane ( the VBox will hold our GridPanes )
        borderPane.setCenter( scrollPane ); // add the scroll pane to the Center region of the BorderPane

        vBox.getChildren().addAll( optspane, filespane, xappspane ); // add our GridPanes to the VBox ( order matters )
        vBox.setPadding(new Insets(50,20,20,50));

        /**
         * Define 3 buttons for Start/Save/Load, and add them to a ToolBar at the bottom of the view
         */
        Button start_job = new Button( "Start Job" );
        Button save_job = new Button( "Save Job" );
        Button preview_job = new Button( "Preview XML" );

        bottom_toolbar.getItems().addAll( save_job, start_job, preview_job );
        vBox.getChildren().add( bottom_toolbar );
        borderPane.setBottom( bottom_toolbar );

        this.setText( optspane.getRunName().getText());
        optspane.getRunName().setOnAction( event -> {
            this.setText( optspane.getRunName().getText());
        });

        /**
         * Define save button actions
         */
        save_job.setOnAction( event -> {
            String output = NASP_DATA.getOptions().getRunName();
            if ( output == null )
                output = "/temp";
            JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, output );
        });

        /**
         * Define start-job button actions
         */
        start_job.setOnAction( event -> {
            String xml_name = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();


            File outfile = JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, xml_name );

           remotepath = remotepath +"/"+ outfile.getName();


            //TODO: The remote path needs to include the desired filename, and must be platform agnostic
            net.upload( outfile, remotepath);
            String jobid = net.runNaspJob( remotepath );
            System.out.println( jobid );
            System.out.println( remotepath );
        });

        preview_job.setOnAction( event -> {


        });

        /**
         * Enable Drag-and-Drop Drag-Over events
         */
        this.getContent().setOnDragOver( new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if( event.getDragboard().hasString() ){
                    event.acceptTransferModes( TransferMode.ANY );
                }
                event.consume();
            }
        });

        /**
         * Enable visual feedback to indicate Drag-Event has passed checks
         */
        this.getContent().setOnDragEntered( new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if ( event.getDragboard().hasString() ) {
                    borderPane.setBackground(
                            new Background( new BackgroundFill( Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY))
                    );
                }
                event.consume();
            }
        });

        /**
         * Enable Drag-and-Drop Drop event handling
         */
        this.getContent().setOnDragDropped( new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                System.out.println(db.getString());
                boolean success = false;
                if (db.hasString()) {
                    ArrayList<String> files = net.getAllFiles( db.getString() );
                    for( String x : files ) {

                        System.out.println(x);
                    }
                    //TODO: Use the files in 'files' to build and populate the FilesPane UI and NASP xml

                    ArrayList<String> reads = new ArrayList<>();
                    Pattern fasta = Pattern.compile( "(.*)(.f(ast)?q(?:.gz)?)$");
                    for( String x : files ){

                        Matcher m = fasta.matcher( x );
                        if ( m.find() ){
                            reads.add( x );
                        }
                    }

                    ArrayList<Pair<String, String>> rps = new ArrayList<>();
                    Pattern pair1 = Pattern.compile( "^(.*)(_[R]?)([1])(.*)$" );
                    Pattern pair2 = Pattern.compile( "^(.*)(_[R]?)([2])(.*)$" );
                    for( String x : reads ){
                        Matcher m1 = pair1.matcher( x );
                        if( m1.find()) {
                            for (String y : reads) {
                                Matcher m2 = pair2.matcher( y );
                                if ( m2.find() ){
                                    Pair<String, String> pair = new Pair<>( x, y );
                                    rps.add( pair );
                                    break;
                                }
                            }
                        }
                    }

                    for( Pair<String, String> x : rps ){
                        System.out.println( x.getKey() + " : " + x.getValue() );
                    }

                    ArrayList<String> assemblies = new ArrayList<>();
                    Pattern ass = Pattern.compile( "(?:fa|fna|fas|fasta)$" );
                    for( String x : files ){
                        Matcher m = ass.matcher( x );
                        if ( m.find() ){
                            assemblies.add( x );
                        }
                    }

                    ArrayList<String> vcfs = new ArrayList<>();
                    Pattern vcf = Pattern.compile( "(?:.gz)" );
                    for( String x : files ){
                        Matcher m = vcf.matcher( x );
                        if ( m.find() ){
                            vcfs.add( x );
                        }
                    }

                    ArrayList<String> sambam = new ArrayList<>();
                    Pattern sam = Pattern.compile( "(?:.sam)" );
                    Pattern bam = Pattern.compile( "(?:.bam)" );
                    for( String x : files ){
                        Matcher m1 = sam.matcher( x );
                        Matcher m2 = bam.matcher( x );
                        if ( m1.find() ){
                            sambam.add( x );
                        }
                        else if ( m2.find() ){
                            sambam.add( x );
                        }
                    }

                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }

    public void setRemoteNet( RemoteNetUtil network){
        net = network;
    }
}