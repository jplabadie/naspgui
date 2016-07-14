package widgets;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.DefaultRemoteNetUtil;
import utils.JobRecord;
import utils.JobSaveLoadManager;
import utils.RemoteNetUtil;
import xmlbinds.ExternalApplications;
import xmlbinds.MatrixGenerator;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

import java.io.File;

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

        borderPane.setPrefHeight( 900);
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
            System.out.println( NASP_DATA.getFiles().getReadFolder().get( 0 ).getPath() );
            JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, output );
        });

        /**
         * Define start-job button actions
         */
        start_job.setOnAction( event -> {
            String xml_name = NASP_DATA.getOptions().getRunName();
            String remotepath = NASP_DATA.getOptions().getOutputFolder();
            if ( xml_name == null )
                xml_name = "/temp";
            System.out.println( NASP_DATA.getFiles().getReadFolder().get( 0 ).getPath() );
            File outfile = JobSaveLoadManager.jaxbObjectToXML( NASP_DATA, xml_name );

            net.upload( outfile, remotepath+ ".xml");
            String jobid = net.runNaspJob( remotepath +".xml" );
            System.out.println( jobid );
            System.out.println( remotepath );
        });

        preview_job.setOnAction( event -> {


        });
    }

    public void setRemoteNet( RemoteNetUtil network){
        net = network;
    }
}