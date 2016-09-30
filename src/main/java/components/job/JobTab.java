package components.job;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import utilities.DefaultRemoteNetUtil;
import utilities.JobRecord;
import utilities.RemoteNetUtil;
import utilities.XMLSaveLoad;
import xmlbinds.nasp_xmlbinds.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the form and associated xmlbinding objects for creating a new NASP run
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
     *
     */
    void initialize() {
        borderPane.setPrefHeight( 900 );
        /*
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
        vBox.setPadding( new Insets( 50,20,20,50 ) );

        /*
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

        /*
         * Define save button actions
         */
        save_job.setOnAction( event -> {
            String output = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();
            if ( output == null )
                output = "/temp";
            File outfile = XMLSaveLoad.jaxbObjectToXML(NASP_DATA, output);
            //TODO: upload saved job?
            //remotepath = remotepath + "/" + outfile.getName();
            //net.upload( outfile, remotepath );
        });

        /*
         * Define start-job button actions
         */
        start_job.setOnAction( event -> {
            String xml_name = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();
            remotepath = remotepath.substring(0, remotepath.lastIndexOf("/")) + "/";
            File outfile = XMLSaveLoad.jaxbObjectToXML(NASP_DATA, xml_name);

            remotepath = remotepath +"/"+ outfile.getName();
            net.upload( outfile, remotepath);
            List<String> before_run = net.getJobs();

            net.runNaspJob( remotepath );
            List<String> after_run = net.getJobs();
        });

        // Define NASP XML preview action
        preview_job.setOnAction( event -> {
            TextArea ta = new TextArea();
            try {
                File f = XMLSaveLoad.jaxbObjectToXML( NASP_DATA, "~temp_job.xml");
                String content = new Scanner(f).useDelimiter("\\Z").next();
                ta.setText( content );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Stage xml_preview = new Stage();
            xml_preview.setTitle("NASP XML Preview");
            xml_preview.setScene(new Scene(ta, 800, 800));

            xml_preview.show();
        });

        /*
         * Enable Drag-and-Drop Drag-Over events
         */
        this.getContent().setOnDragOver( new EventHandler<DragEvent>() {
            @Override
            public void handle( DragEvent event ) {
                if( event.getDragboard().hasString() ){
                    event.acceptTransferModes( TransferMode.ANY );
                }
                event.consume();
            }
        });

        /*
         * Enable visual feedback to indicate Drag-Event has passed checks
         */
        this.getContent().setOnDragEntered( new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if ( event.getDragboard().hasString() ) {
                    borderPane.setBackground(
                            new Background( new BackgroundFill( Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY ))
                    );
                }
                event.consume();
            }
        });

        /*
         * Enable Drag-and-Drop Drop event handling
         * Currently, we only support recursive adding of all files (rather than selective D+D)
         */
        this.getContent().setOnDragDropped( new EventHandler<DragEvent>() {
            public void handle( DragEvent event ) {

                Dragboard db = event.getDragboard();
                System.out.println( "dragboard: "+db.getString() );
                boolean success = false;
                if ( db.hasString() ) {

                List<String> files = new ArrayList<>(); //create underlying list (ArrayList generic for Strings)
                files.addAll( net.getAllFiles( db.getString() )); // populate list with all files at given destination

                List<Pair<String,String>> fasta_list = new ArrayList<>();// create underlying list for only fasta files
                ObservableList<Pair<String,String>> fastas = FXCollections.observableList( fasta_list ); // create observable list

                Pattern fasta = Pattern.compile( "(?:fa|fna|fas|fasta)(?=[?.|.qz]*$)" );
                for( String x : files ) {
                    Matcher m = fasta.matcher( x );
                    if ( m.find() ) {
                        String fasta_path = x ;
                        String fasta_name = x.substring( x.lastIndexOf('/') + 1, x.lastIndexOf('.') );
                        fastas.add( new Pair<String,String>(fasta_name, fasta_path) );
                    }
                }
                VBox refsPane = new VBox();
                    refsPane.setAlignment(Pos.CENTER_LEFT);
                    refsPane.setSpacing(10.0);
                    refsPane.getChildren().add(
                            new Text("Select your reference FASTA\nNote: Mouse-over a selection to see its acutal path")
                    );

                final ToggleGroup tgroup = new ToggleGroup();


                   for( Pair<String,String> item : fastas ){
                       RadioButton rb = new RadioButton();
                       rb.setText( item.getKey() );
                       rb.setUserData( item );
                       rb.setTooltip( new Tooltip( item.getValue() ));
                       rb.setToggleGroup( tgroup );
                       refsPane.getChildren().add( rb );
                   }

                    Dialog<String> selectRef = new Dialog<>();
                    selectRef.getDialogPane().setContent( refsPane );
                    ButtonType confirmButton = new ButtonType( "Confirm", ButtonBar.ButtonData.OK_DONE );
                    ButtonType cancelButton = new ButtonType( "Cancel", ButtonBar.ButtonData.CANCEL_CLOSE );
                    selectRef.getDialogPane().getButtonTypes().addAll( confirmButton, cancelButton );

                    selectRef.showAndWait();

                    @SuppressWarnings("unchecked")
                    Pair<String,String> reference = (Pair<String, String>) tgroup.getSelectedToggle().getUserData();

                    if( reference == null ){

                        Alert alert = new Alert( Alert.AlertType.ERROR );
                        alert.setHeaderText( "Error" );
                        alert.setContentText( "You must select a reference FASTA for the operation to continue." );
                        alert.showAndWait();
                        return;
                    }

                    fastas.remove( reference );
                    System.out.println( "reference: " + reference );
                    String ref_name = reference.getKey();
                    String ref_folder = reference.getValue();
                    optspane.setReference( ref_folder, ref_name );

                    for( String x : files )
                            System.out.println( "File: " + x );
                    //TODO: Use the files in 'files' to build and populate the FilesPane UI and NASP xml
                    /*
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
                    ArrayList<String> singles = new ArrayList<String>();

                    //TODO: Unpaired reads should be supported too
                    for( String x : reads ){
                        Pattern pair1 = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                        Matcher m1 = pair1.matcher( x );
                        if( m1.find() ) {
                            String p1 = m1.group( 1 );
                            boolean found_pair = false;
                            for ( String y : reads ) {
                                Pattern pair2 = Pattern.compile( "(.*)(_[R]?)([2])(.*)$" );
                                Matcher m2 = pair2.matcher( y );
                                if ( m2.find() ){
                                    String p2 = m2.group( 1 );
                                    if( p2.equals( p1 ) ) {
                                        Pair<String, String> pair = new Pair<>( x, y );
                                        System.out.println( "Pairs: " + x + " \n\t " + y );
                                        rps.add( pair );
                                        found_pair = true;
                                        break;
                                    }
                                }
                            }
                            if( ! found_pair ){
                                singles.add( x );
                            }
                        }
                    }

                    /*
                     * Handle Assemblies in Drag-and-Drop
                     */
                    List<AssemblyFolder> assf =  NASP_DATA.getFiles().getAssemblyFolder();
                    ArrayList<String> ass_folders = new ArrayList<>();
                    for( AssemblyFolder x : assf ) {
                        System.out.println("folder:" +  x.getPath() );
                        ass_folders.add( x.getPath() );
                    }

                    for( Pair<String,String> x : fastas ){

                        String path = x.getValue();
                        String folder = path.substring( 0, path.lastIndexOf("/") + 1);
                        String name = x.getKey();

                        System.out.println( "Stuff: " + folder + " : " + name + " : " + db.getString());


                        AssemblyFolder af;
                        if ( ass_folders.contains( folder )) {
                            af = assf.get( ass_folders.indexOf( folder ));

                        } else {
                            af = new AssemblyFolder();
                            af.setPath( folder );
                            ass_folders.add( folder );
                            assf.add( af );
                        }
                        Assembly temp = new Assembly();
                        temp.setValue( path );
                        temp.setSample( name );
                        af.getAssembly().add( temp );
                        System.out.println( "Assemblies: " + x );

                    }

                    /*
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

                    /*
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

                    /*
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
                            System.out.println( "$$$" + mm.group(1) );
                            new_pair.setSample( mm.group(1) );

                            readpairings.add( new_pair );
                            rf.add( new_folder );
                        }
                    }

                    for( String read : singles ){
                        String folder = read.substring( 0, read.lastIndexOf("/") + 1 );
                        String file1 = read.substring( read.lastIndexOf("/")
                                + 1, read.length() );

                        System.out.println( "Folder: " + folder+ ": "+ file1 );

                        if( read_folders.contains( folder )) {
                            ReadFolder fold = rf.get( read_folders.indexOf( folder ) );
                            List<ReadPair> readpairings = fold.getReadPair();
                            ReadPair new_pair = new ReadPair();
                            new_pair.setRead1Filename( file1 );
                            Pattern pp = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                            Matcher mm = pp.matcher( file1 );
                            mm.find();
                            new_pair.setSample( mm.group(1) );

                            readpairings.add( new_pair );
                        }
                        else {
                            read_folders.add( folder );
                            ReadFolder new_folder = new ReadFolder();
                            new_folder.setPath( folder );

                            List<ReadPair> readpairings = new_folder.getReadPair();
                            ReadPair new_pair = new ReadPair();
                            new_pair.setRead1Filename( file1 );
                            Pattern pp = Pattern.compile( "(.*)(_[R]?)([1])(.*)$" );
                            Matcher mm = pp.matcher( file1 );
                            mm.find();
                            System.out.println( mm.toString() );
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