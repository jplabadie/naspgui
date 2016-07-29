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
import xmlbinds.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
        vBox.setPadding( new Insets(50,20,20,50) );

        /**
         * Define 3 buttons for Start/Save/Load, and add them to a ToolBar at the bottom of the view
         */
        Button start_job = new Button( "Start Job" );
        Button save_job = new Button( "Save Job" );
        Button preview_job = new Button( "Preview XML" );

        bottom_toolbar.getItems().addAll( save_job, start_job, preview_job );
        vBox.getChildren().add( bottom_toolbar );
        borderPane.setBottom( bottom_toolbar );

        this.setText( optspane.getRunName().getText() );
        optspane.getRunName().setOnAction( event -> {
            this.setText( optspane.getRunName().getText() );
        });

        /**
         * Define save button actions
         */
        save_job.setOnAction( event -> {
            String output = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();
            if ( output == null )
                output = "/temp";
            File outfile = JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, output );
            //TODO: upload saved job?
            //remotepath = remotepath + "/" + outfile.getName();
            //net.upload( outfile, remotepath );
        });

        /**
         * Define start-job button actions
         */
        start_job.setOnAction( event -> {
            String xml_name = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();
            File outfile = JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, xml_name );

            remotepath = remotepath +"/"+ outfile.getName();
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

                    for( String x : files)
                            System.out.println( "File: " + x);
                    //TODO: Use the files in 'files' to build and populate the FilesPane UI and NASP xml
                    /**
                     * Find and pair read files from Drag-and-Drop remote path
                     */
                    ArrayList<String> reads = new ArrayList<>();
                    Pattern fastq = Pattern.compile( "(?:fastq|f?q)(?:.gz)?$" );
                    for( String x : files ){
                        Matcher m = fastq.matcher( x );
                        if ( m.find() ){
                            reads.add( x );
                            System.out.println( "Reads: " + x );
                        }
                    }
                    ArrayList<Pair<String, String>> rps = new ArrayList<>();

                    for( String x : reads ){
                        Pattern pair1 = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                        Matcher m1 = pair1.matcher( x );
                        if( m1.find() ) {
                            String p1 = m1.group( 1 );
                            for ( String y : reads ) {
                                Pattern pair2 = Pattern.compile( "(.*)(_[R]?)([2])(.*)$" );
                                Matcher m2 = pair2.matcher( y );
                                if ( m2.find() ){
                                    String p2 = m2.group( 1 );
                                    if( p2.equals( p1 ) ) {
                                        Pair<String, String> pair = new Pair<>( x, y );
                                        System.out.println( "Pairs: " + x + " \n\t " + y );
                                        rps.add( pair );
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * Handle Assemblies in Drag-and-Drop
                     */
                    List<AssemblyFolder> assf =  NASP_DATA.getFiles().getAssemblyFolder();
                    ArrayList<String> ass_folders = new ArrayList<>();
                    for( AssemblyFolder x : assf ) {
                        System.out.println("folder:" +  x.getPath() );
                        ass_folders.add(x.getPath());
                    }
                    boolean firstFastaAlreadyFound = false;
                    Pattern ass = Pattern.compile( "(?:fa|fna|fas|fasta)(?=[?.|.qz]*$)" );
                    for( String x : files ){
                        Matcher m = ass.matcher( x );
                        if ( m.find() ){
                            String folder = x.substring( 0, x.lastIndexOf("/") + 1 );
                            String name = x.substring( x.lastIndexOf('/') + 1, x.lastIndexOf('.') );

                            System.out.println( "Stuff: " + folder + " : " + name + " : " + db.getString());
                            if( ! firstFastaAlreadyFound )
                            {
                                firstFastaAlreadyFound = true;
                                optspane.setReference(x, name);
                            }
                            else {
                                AssemblyFolder af;
                                if ( ass_folders.contains(folder) ) {
                                    af = assf.get(ass_folders.indexOf(folder));
                                } else {
                                    af = new AssemblyFolder();
                                    af.setPath(folder);
                                    ass_folders.add(folder);
                                    assf.add(af);
                                }
                                Assembly temp = new Assembly();
                                temp.setValue(x);
                                temp.setSample(x.substring(x.lastIndexOf('/') + 1, x.lastIndexOf('.')));
                                af.getAssembly().add(temp);
                                System.out.println("Assemblies: " + x);
                            }
                        }
                    }

                    /**
                     * Handle VCF files in Drag-and-Drop
                     */
                    List<VCFFolder> vcfz =  NASP_DATA.getFiles().getVCFFolder();
                    ArrayList<String> vcf_folders = new ArrayList<>();
                    if( vcfz != null ){
                        for ( VCFFolder x : vcfz ) {
                            vcf_folders.add( "" + x.getPath() );
                        }
                    }
                    Pattern vcf = Pattern.compile( "\\.vcf(?=[?.]*$)" );
                    for( String x : files ){
                        Matcher m = vcf.matcher( x );
                        if ( m.find() ){
                            String folder = x.substring( 0, x.lastIndexOf("/") + 1 );
                            VCFFolder vf;

                            if( vcf_folders.contains( folder ))
                                vf = vcfz.get( vcf_folders.indexOf( folder ));
                            else{
                                vf = new VCFFolder();
                                vf.setPath( folder );
                                vcf_folders.add( folder );
                                vcfz.add( vf );
                            }
                            VCFFile temp = new VCFFile();
                            temp.setValue( x );
                            x = x.substring( x.lastIndexOf('/')+1, x.lastIndexOf('.'));
                            temp.setSample( x );
                            vf.getVCFFile().add( temp );
                            System.out.println( "VCFs: " + x );
                        }
                    }

                    /**
                     * Handle Alignment files in Drag-and-Drop
                     */
                    List<AlignmentFolder> algn =  NASP_DATA.getFiles().getAlignmentFolder();
                    ArrayList<String> algn_folders = new ArrayList<>();
                    for( AlignmentFolder x : algn )
                        algn_folders.add( x.getPath() );

                    Pattern sam = Pattern.compile( "\\.sam(?=[?.]*$)" );
                    Pattern bam = Pattern.compile( "\\.bam(?=[?.]*$)" );
                    for( String x : files ){
                        Matcher m1 = sam.matcher( x );
                        Matcher m2 = bam.matcher( x );

                        if ( m1.find() || m2.find() ){
                            String folder = x.substring( 0, x.lastIndexOf( "/" ) + 1 );
                            AlignmentFolder af;
                            if( algn_folders.contains( folder ))
                                af = algn.get( algn_folders.indexOf( folder ) );
                            else {
                                af = new AlignmentFolder();
                                algn_folders.add( folder );
                                af.setPath( folder );
                                algn.add( af );
                            }

                            Alignment temp = new Alignment();
                            temp.setValue( x );
                            x = x.substring( x.lastIndexOf('/') + 1, x.lastIndexOf('.') );
                            temp.setSample( x );

                            af.getAlignment().add( temp );
                            System.out.println( "BAMs/SAMs: " + x );
                        }
                    }

                    /**
                     * Handle Read files in Drag-and-Drop
                     */
                    List<ReadFolder> rf = NASP_DATA.getFiles().getReadFolder();
                    ArrayList<String> read_folders = new ArrayList<>();
                    for( ReadFolder x : rf )
                            read_folders.add( x.getPath() );

                    //TODO: Initialize read_folders with those already in NASPDATA

                    for( Pair<String, String> pair : rps ){
                        System.out.println( "!" + pair.getKey() );
                        String folder = pair.getKey().substring( 0, pair.getKey().lastIndexOf("/") + 1 );
                        String file1 = pair.getKey().substring( pair.getKey().lastIndexOf("/")
                                + 1, pair.getKey().length() );
                        String file2 = pair.getValue().substring( pair.getValue().lastIndexOf("/")
                                + 1, pair.getValue().length() );

                        System.out.println( "Folder: " + folder+ ": "+ file1 + ", " + file2 );

                        if( read_folders.contains( folder )) {
                            System.out.println( "%%" );
                            ReadFolder fold = rf.get( read_folders.indexOf( folder ) );
                            List<ReadPair> readpairings = fold.getReadPair();
                            ReadPair new_pair = new ReadPair();
                            new_pair.setRead1Filename( file1 );
                            new_pair.setRead2Filename( file2 );
                            Pattern pp = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                            Matcher mm = pp.matcher( file1 );
                            mm.find();
                            System.out.println( "$$$" + mm.group(1) );
                            new_pair.setSample( mm.group(1) );

                            readpairings.add( new_pair );
                        }
                        else {
                            System.out.println( "***" );
                            read_folders.add( folder );
                            ReadFolder new_folder = new ReadFolder();
                            new_folder.setPath( folder );

                            List<ReadPair> readpairings = new_folder.getReadPair();
                            ReadPair new_pair = new ReadPair();
                            new_pair.setRead1Filename( file1 );
                            new_pair.setRead2Filename( file2 );
                            Pattern pp = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                            Matcher mm = pp.matcher( file1 );
                            mm.find();
                            System.out.println( mm.toString() );
                            System.out.println( "$$$"+mm.group(1) );
                            new_pair.setSample( mm.group(1) );

                            readpairings.add( new_pair );
                            rf.add( new_folder );
                        }
                    }
                    vBox.getChildren().remove( filespane );
                    filespane = new FilesPane( NASP_DATA.getFiles() );
                    vBox.getChildren().add( 1, filespane );

                    success = true;
                }
                event.setDropCompleted( success );
                event.consume();
            }
        });
    }

    public void setRemoteNet( RemoteNetUtil network){
        net = network;
    }
}