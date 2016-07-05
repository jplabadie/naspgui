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
    private ToolBar menu = new ToolBar();


    private JobRecord job_rec;

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
        NASP_DATA = input;
    }

    /**
     *  Creates a blank job and initializes new NASP data
     */
    JobTab() {
        NASP_DATA = OF.createNaspInputData();
        Options temp_opts = new Options();
        Filters temp_filters = new Filters();
        Reference temp_ref = new Reference();
        temp_opts.setFilters(temp_filters);
        temp_opts.setReference(temp_ref);
        NASP_DATA.setOptions(temp_opts);

        Files temp_files = new Files();
        AssemblyFolder temp_af = new AssemblyFolder();
        Assembly temp_assembly = new Assembly();
        temp_af.getAssembly().add(temp_assembly);
        ReadFolder temp_rf = new ReadFolder();
        ReadPair temp_rp = new ReadPair();
        temp_rf.getReadPair().add(temp_rp);
        temp_files.getAssemblyFolder().add(temp_af);
        temp_files.getReadFolder().add(temp_rf);
        NASP_DATA.setFiles( temp_files );

        ExternalApplications temp_xa = new ExternalApplications();

        MatrixGenerator mg = new MatrixGenerator();
        mg.setName( "MatrixGenerator" );
        Index indx = new Index();
        indx.setName( "Index" );
        temp_xa.setMatrixGenerator( mg );
        temp_xa.setIndex( indx );
        NASP_DATA.setExternalApplications( temp_xa );

        optspane = new OptionsPane( NASP_DATA.getOptions() );
        filespane = new FilesPane( NASP_DATA.getFiles() );
        xappspane = new ExternalApplicationsPane( NASP_DATA.getExternalApplications() );


        this.setContent( borderPane );
        scrollPane.setContent( vBox );
        borderPane.setCenter( scrollPane );

        vBox.getChildren().addAll( optspane, filespane, xappspane);

        Button start_job = new Button( "Start Job" );
        Button save_job = new Button( "Save Job" );
        Button load_job = new Button( "Load Job" );
        menu.getItems().addAll( save_job, load_job, start_job );
        vBox.getChildren().add( menu );
        borderPane.setBottom( menu );

        save_job.setOnAction( event -> {
            String output = optspane.getRunName();
            if (output == null)
                output = "/temp";
            JobSaveLoadManager.jaxbObjectToXML(NASP_DATA, output );
        });

        load_job.setOnAction( event -> { //TODO: Finish this func
            String output = optspane.getRunName();
            if (output == null)
                output = "/temp";
            JobSaveLoadManager.jaxbObjectToXML(NASP_DATA, output );
        });
    }
}