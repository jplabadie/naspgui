package widgets;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import xmlbinds.JobParameters;

/**
 * Project naspgui.
 * Created by jlabadie on 7/8/16.
 *
 * @author jlabadie
 */
class JobParametersPane extends TitledPane {

    private Label MEMORY_REQUESTED = new Label( "Memory Requested" );
    private Label CPUS_REQUESTED = new Label( "CPUs Requested" );
    private Label QUEUE_REQUESTED = new Label( "Queue Requested" );
    private Label WALL_TIME_REQUESTED = new Label( "Maximum Walltime" );
    private Label JOB_SUBMITTER_ARGS = new Label( "Job Submitter Arguments") ;

    private Tooltip JOB_PARAM_TIP = new Tooltip("Parameters to pass the Job manager when running this app");
    private Tooltip MEM_REQ_TIP = new Tooltip("The amount of memory, in Gigabytes of RAM, to allocate for the app");
    private Tooltip CPU_REQ_TIP = new Tooltip("The number of CPUs to allocate for the app");
    private Tooltip WALL_TIME_TIP = new Tooltip("The allocated maximum completion time for the app, in hours");
    private Tooltip JOB_SUB_ARGS = new Tooltip("Additional arguments or options to pass to the job submitter");

    private GridPane gridPane = new GridPane();

    private TextField memReq = new TextField();
    private TextField cpusReq = new TextField ();
    private TextField queueReq = new TextField ();
    private TextField wallTimeReq = new TextField ();
    private TextField jobSubArgs = new TextField ();

    private JobParameters JOBPARAMETERS;

    JobParametersPane ( JobParameters jobparams ){

        JOBPARAMETERS = jobparams;

        this.getStyleClass().addAll( "tabpane2");
        this.setContent( gridPane );
        this.setText( "Job Parameters" );
        this.setExpanded( false );

        gridPane.setHgap( 2 );
        gridPane.setVgap( 2 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 25, 50 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 150, 200 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 150 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        gridPane.getColumnConstraints().addAll( c0, c1, c2, c3  );


        MEMORY_REQUESTED.setTooltip( MEM_REQ_TIP );
        CPUS_REQUESTED.setTooltip( CPU_REQ_TIP );
        WALL_TIME_REQUESTED.setTooltip( WALL_TIME_TIP );
        JOB_SUBMITTER_ARGS.setTooltip( JOB_SUB_ARGS );

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        gridPane.add( MEMORY_REQUESTED,1,1,3,1 );
        gridPane.add( CPUS_REQUESTED,1,2,3,1 );
        gridPane.add( QUEUE_REQUESTED,1,3,3,1 );
        gridPane.add( WALL_TIME_REQUESTED,1,4,3,1 );
        gridPane.add( JOB_SUBMITTER_ARGS,1,5,3,1 );

        gridPane.add( memReq, 3, 1, 3, 1);
        gridPane.add( cpusReq, 3, 2, 3, 1);
        gridPane.add( queueReq, 3, 3, 3, 1);
        gridPane.add( wallTimeReq, 3, 4, 3, 1);
        gridPane.add( jobSubArgs, 3, 5, 3, 1);


        memReq.textProperty().addListener( observable -> JOBPARAMETERS.setMemRequested( memReq.getText() ));
        cpusReq.textProperty().addListener( observable -> JOBPARAMETERS.setNumCPUs( cpusReq.getText() ));
        queueReq.textProperty().addListener( observable -> JOBPARAMETERS.setQueue( queueReq.getText() ));
        wallTimeReq.textProperty().addListener( observable -> JOBPARAMETERS.setWalltime( wallTimeReq.getText() ));
        jobSubArgs.textProperty().addListener( observable ->  JOBPARAMETERS.setJobSubmitterArgs( jobSubArgs.getText() ));

        memReq.setText( JOBPARAMETERS.getMemRequested() );
        cpusReq.setText( JOBPARAMETERS.getNumCPUs() );
        queueReq.setText( JOBPARAMETERS.getQueue() );
        wallTimeReq.setText( JOBPARAMETERS.getWalltime() );
        jobSubArgs.setText( JOBPARAMETERS.getJobSubmitterArgs() );
    }
}
