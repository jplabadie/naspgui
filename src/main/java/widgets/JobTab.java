package widgets;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utils.JobRecord;
import utils.JobSaveLoadManager;
import xmlbinds.*;

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

    /**
     * Creates a job tab window from existing NaspData
     * @param input NaspInputData initialized from XML
     */
    JobTab( NaspInputData input ) {
        // TODO: Display NaspInputData correctly in the View
        NASP_DATA = input;
    }

    /**
     *  Creates a blank job and initializes new NASP data
     */
    JobTab() {
        /**
         * Create new (blank) NaspInputData root and populate with Children
         */
        NASP_DATA = OF.createNaspInputData(); // Create NaspInputData

        // Create and populate Options
        Options options = OF.createOptions(); // create Options
        options.setFilters( OF.createFilters() ); // create Filters
        options.setReference( OF.createReference() ); // create Reference
        NASP_DATA.setOptions( options ); // set NaspInputData Options to newly created objects

        //Create and populate Files
        Files files = new Files(); // create Files
        AssemblyFolder assembly_folder = new AssemblyFolder(); // create first empty AssemblyFolder
        Assembly assembly = new Assembly(); // create first empty Assembly
        assembly_folder.getAssembly().add(assembly); // add first Assembly to AssemblyFolder
        ReadFolder read_folder = new ReadFolder(); // create first empty ReadFolder
        ReadPair read_pair = new ReadPair(); // create first empty ReadPair
        read_folder.getReadPair().add( read_pair ); // add first ReadPair to ReadFolder
        files.getAssemblyFolder().add( assembly_folder ); // add AssemblyFolder to Files
        files.getReadFolder().add( read_folder ); // add ReadFolder to Files
        NASP_DATA.setFiles( files ); // add Files to NaspInputData

        //Create and populate ExternalApplications
        ExternalApplications temp_xa = new ExternalApplications(); // Create ExternalApplications
        MatrixGenerator mg = new MatrixGenerator(); // Create MatrixGenerator
        mg.setName( "MatrixGenerator" ); // set MatrixGenerator name to default (MatrixGenerator)
        Index indx = new Index(); // Create Index
        indx.setName( "Index" ); // set Index name to default (Index)
        temp_xa.setMatrixGenerator( mg ); // add MatrixGenerator to ExternalApplications
        temp_xa.setIndex( indx ); // add Index to ExternalApplications
        NASP_DATA.setExternalApplications( temp_xa ); // Add ExternalApplications to NaspInputData

        optspane = new OptionsPane( NASP_DATA.getOptions() ); // init OptionsPane
        filespane = new FilesPane( NASP_DATA.getFiles() ); // init FilesPane
        xappspane = new ExternalApplicationsPane( NASP_DATA.getExternalApplications() ); //Init ExApps Pane

        this.setContent( borderPane ); // set a BorderPane as the root container for this Tab
        scrollPane.setContent( vBox ); // add a VBox to the scroll pane ( the VBox will hold our GridPanes )
        borderPane.setCenter( scrollPane ); // add the scroll pane to the Center region of the BorderPane

        vBox.getChildren().addAll( optspane, filespane, xappspane); // add our GridPanes to the VBox ( order matters )

        /**
         * Define 3 buttons for Start/Save/Load, and add them to a ToolBar at the bottom of the view
         */
        Button start_job = new Button( "Start Job" );
        Button save_job = new Button( "Save Job" );
        Button load_job = new Button( "Load Job" );
        bottom_toolbar.getItems().addAll( save_job, load_job, start_job );
        vBox.getChildren().add( bottom_toolbar );
        borderPane.setBottom( bottom_toolbar );

        /**
         * Define save button actions
         */
        save_job.setOnAction( event -> {
            String output = options.getRunName();
            if (output == null)
                output = "/temp";
            System.out.println(NASP_DATA.getOptions().getFilters().getCoverageFilter());
            JobSaveLoadManager.jaxbObjectToXML(NASP_DATA, output );
        });

        /**
         * Define load button actions
         */
        load_job.setOnAction( event -> {
            //TODO: Finish this func, include error handling and alerts
            String output = options.getRunName();
            if (output == null)
                output = "/temp";
            JobSaveLoadManager.jaxbObjectToXML(NASP_DATA, output );
        });

        start_job.setOnAction( event -> {

        });
    }
}