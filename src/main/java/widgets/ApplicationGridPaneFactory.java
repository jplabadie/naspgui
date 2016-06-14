package widgets;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @author jlabadie
 */
public class ApplicationGridPaneFactory {

    private static Label APPLICATION_PATH = new Label( "Application Path" );
    private static Label ADDITIONAL_ARGS = new Label( "Additional Arguments" );
    private static Label JOB_PARAMETERS = new Label( "Job Parameters" );
    private static Label MEMORY_REQUESTED = new Label( "Memory Requested" );
    private static Label CPUS_REQUESTED = new Label( "CPUs Requested" );
    private static Label QUEUE_REQUESTED = new Label( "Queue Requested" );
    private static Label WALL_TIME_REQUESTED = new Label( "Maximum Walltime" );
    private static Label JOB_SUBMITTER_ARGS = new Label( "Job Submitter Arguments") ;

    private Label app_title = new Label();
    private Text alt_path = new Text();
    private Text app_args = new Text();
    private Text mem_req = new Text();
    private Text cpus_req = new Text();
    private Text queue_req = new Text();
    private Text wall_time_req = new Text();
    private Text job_sub_args = new Text();


    protected GridPane getNewGridPane( String application_title ){

        app_title.setText( application_title );
        app_title.setPrefSize(USE_COMPUTED_SIZE,USE_COMPUTED_SIZE);

        GridPane gp = new GridPane();

        gp.setGridLinesVisible(true); //primarily for debugging

        gp.add( app_title,1,0,3,1);
        gp.add( APPLICATION_PATH,1,1,2,1);
        gp.add( ADDITIONAL_ARGS,1,2,2,1);
        gp.add( JOB_PARAMETERS,1,3,2,1);
        gp.add( MEMORY_REQUESTED,1,4,2,1);
        gp.add( CPUS_REQUESTED,1,5,2,1);
        gp.add( QUEUE_REQUESTED,1,6,2,1);
        gp.add( WALL_TIME_REQUESTED,1,7,2,1);
        gp.add( JOB_SUBMITTER_ARGS,1,8,2,1);

        gp.add( alt_path,2,1,4,1);
        gp.add( app_args,2,2,4,1);
        gp.add( mem_req,2,3,4,1);
        gp.add( cpus_req,2,4,4,1);
        gp.add( queue_req,2,5,4,1);
        gp.add( wall_time_req,2,6,4,1);
        gp.add( job_sub_args,2,7,4,1);

        return gp;
    }


}
