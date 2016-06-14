package widgets;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @author jlabadie
 */
class ApplicationGridPaneFactory {

    private static Label APPLICATION_PATH = new Label( "Application Path" );
    private static Label ADDITIONAL_ARGS = new Label( "Additional Arguments" );
    private static Label JOB_PARAMETERS = new Label( "Job Parameters" );
    private static Label MEMORY_REQUESTED = new Label( "Memory Requested" );
    private static Label CPUS_REQUESTED = new Label( "CPUs Requested" );
    private static Label QUEUE_REQUESTED = new Label( "Queue Requested" );
    private static Label WALL_TIME_REQUESTED = new Label( "Maximum Walltime" );
    private static Label JOB_SUBMITTER_ARGS = new Label( "Job Submitter Arguments") ;


    ApplicationGridPaneFactory(){

        APPLICATION_PATH.setFont(Font.font("Courier", FontWeight.BOLD,14));
        ADDITIONAL_ARGS.setFont(Font.font("Courier", FontWeight.BOLD,14));
        JOB_PARAMETERS.setFont(Font.font("Courier", FontWeight.BOLD,14));
    }

    GridPane getNewGridPane( String application_title ){

        Label app_title = new Label();
            app_title.setPrefSize( 100,20 );
            app_title.setAlignment(Pos.CENTER);

        TextField alt_path = new TextField();
            alt_path.setAccessibleText( " an optional path to an alternative application version " );
        alt_path.setVisible(true);
        TextField app_args = new TextField();

        TextField mem_req = new TextField();
        TextField cpus_req = new TextField ();
        TextField queue_req = new TextField ();
        TextField wall_time_req = new TextField ();
        TextField job_sub_args = new TextField ();



        GridPane gp = new GridPane();
        // gp.setGridLinesVisible(true); // for debug/style checking

        //Define the look and behavior of the GridPane
        gp.setHgap(4);
        gp.setVgap(4);
            ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
            ColumnConstraints c1 = new ColumnConstraints( 25, 25, 50 );
                c1.setHgrow(Priority.NEVER);
            ColumnConstraints c2 = new ColumnConstraints( 25, 150, 200 );
                c2.setHgrow(Priority.ALWAYS);
            ColumnConstraints c3 = new ColumnConstraints( 25, 50, 150 );
                c3.setHgrow(Priority.SOMETIMES);
                c3.setHalignment(HPos.RIGHT);
        gp.getColumnConstraints().addAll( c0, c1, c2, c3  );

        // Set up the title
        app_title.setText( application_title );
        app_title.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24) );
        app_title.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );

        // Add row headings
        gp.add( app_title, 0, 0, 3, 1 );
        gp.add( APPLICATION_PATH, 1, 1, 3, 1 );
        gp.add( ADDITIONAL_ARGS,1,2,3,1 );
        gp.add( JOB_PARAMETERS,1,3,3,1 );
        gp.add( MEMORY_REQUESTED,2,4,3,1 );
        gp.add( CPUS_REQUESTED,2,5,3,1 );
        gp.add( QUEUE_REQUESTED,2,6,3,1 );
        gp.add( WALL_TIME_REQUESTED,2,7,3,1 );
        gp.add( JOB_SUBMITTER_ARGS,2,8,3,1 );

        // Add text fields
        gp.add( alt_path,3,1,4,1 );
        gp.add( app_args,3,2,4,1 );
        gp.add( mem_req,3,4,4,1 );
        gp.add( cpus_req,3,5,4,1 );
        gp.add( queue_req,3,6,4,1 );
        gp.add( wall_time_req,3,7,4,1 );
        gp.add( job_sub_args,3,8,4,1 );

        return gp;
    }


}
