package widgets;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.*;

import java.lang.reflect.Type;

/**
 * A GridPane wrapper which is pre-configured to represent optional application input fields.
 *
 * Also provides getters and setters for these important variables.
 *
 * Project naspgui.
 * Created by jlabadie on 6/14/16.
 *
 * @author jlabadie
 */
class ApplicationPane<V extends Application> extends GridPane {

    private Label APPLICATION_PATH = new Label( "Application Path" );
    private Label ADDITIONAL_ARGS = new Label( "Additional Arguments" );
    private Label JOB_PARAMETERS = new Label( "Job Parameters" );
    private Label MEMORY_REQUESTED = new Label( "Memory Requested" );
    private Label CPUS_REQUESTED = new Label( "CPUs Requested" );
    private Label QUEUE_REQUESTED = new Label( "Queue Requested" );
    private Label WALL_TIME_REQUESTED = new Label( "Maximum Walltime" );
    private Label JOB_SUBMITTER_ARGS = new Label( "Job Submitter Arguments") ;

    private Tooltip APP_PATH_TIP = new Tooltip("A path to the application on the remote service");
    private Tooltip ADD_ARGS_TIP = new Tooltip("Additional arguments or options to pass to the app");
    private Tooltip JOB_PARAM_TIP = new Tooltip("Parameters to pass the Job manager when running this app");
    private Tooltip MEM_REQ_TIP = new Tooltip("The amount of memory, in Gigabytes of RAM, to allocate for the app");
    private Tooltip CPU_REQ_TIP = new Tooltip("The number of CPUs to allocate for the app");
    private Tooltip WALL_TIME_TIP = new Tooltip("The allocated maximum completion time for the app, in hours");
    private Tooltip JOB_SUB_ARGS = new Tooltip("Additional arguments or options to pass to the job submitter");

    private Label app_title = new Label();
    private TextField app_path = new TextField();
    private TextField app_args = new TextField();

    private TextField mem_req = new TextField();
    private TextField cpus_req = new TextField ();
    private TextField queue_req = new TextField ();
    private TextField wall_time_req = new TextField ();
    private TextField job_sub_args = new TextField ();

    private Type app_type;
    private V application_bind;

    /**
     *
     * @param type
     * @param binding
     */
    ApplicationPane(){

        /**
         * Define the look and feel of static label elements
         */
        APPLICATION_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        ADDITIONAL_ARGS.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        JOB_PARAMETERS.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        /**
         * Add tooltips to the static label elements
         */
        APPLICATION_PATH.setTooltip( APP_PATH_TIP );
        ADDITIONAL_ARGS.setTooltip( ADD_ARGS_TIP );
        JOB_PARAMETERS.setTooltip( JOB_PARAM_TIP );
        MEMORY_REQUESTED.setTooltip( MEM_REQ_TIP );
        CPUS_REQUESTED.setTooltip( CPU_REQ_TIP );
        WALL_TIME_REQUESTED.setTooltip( WALL_TIME_TIP );
        JOB_SUBMITTER_ARGS.setTooltip( JOB_SUB_ARGS );

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 4 );
        this.setVgap( 4 );
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
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */
        app_title.setPrefSize( 100,20 );
        app_title.setAlignment( Pos.CENTER );
        // Set up the look and feel of the title
        app_title.setFont( Font.font( "Helvetica", FontWeight.EXTRA_BOLD, 24 ) );
        app_title.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        app_title.setAlignment( Pos.CENTER );

        // Set default values
        app_title.setText( app_type.getTypeName() );
        app_path.setText( "todo-path" );

        // Add the title to row 0 column 0
        this.add( app_title, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( APPLICATION_PATH, 1, 1, 3, 1 );
        this.add( ADDITIONAL_ARGS,1,2,3,1 );
        this.add( JOB_PARAMETERS,1,3,3,1 );

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( MEMORY_REQUESTED,2,4,3,1 );
        this.add( CPUS_REQUESTED,2,5,3,1 );
        this.add( QUEUE_REQUESTED,2,6,3,1 );
        this.add( WALL_TIME_REQUESTED,2,7,3,1 );
        this.add( JOB_SUBMITTER_ARGS,2,8,3,1 );

        // Add text fields to column 3 of the GridPane
        this.add( app_path,3,1,4,1 );
        this.add( app_args,3,2,4,1 );
        this.add( mem_req,3,4,4,1 );
        this.add( cpus_req,3,5,4,1 );
        this.add( queue_req,3,6,4,1 );
        this.add( wall_time_req,3,7,4,1 );
        this.add( job_sub_args,3,8,4,1 );
    }

    /**
     *
     * @return a String array with all app-settings text as {title, path, args, mem, cpus, queue, time, job_args}
     */
    String[] getAppSettings(){
        return new String[]{
          app_title.getText(), app_path.getText(), app_args.getText(), mem_req.getText(), cpus_req.getText(),
                queue_req.getText(),wall_time_req.getText(),job_sub_args.getText()
        };
    }

    /**
     *
     * @param title the title of the app
     * @param path the path of the app runtime on the remote service
     * @param args arguments to pass to the app
     * @param mem requested amount of RAM for the app
     * @param cpus requested number of CPUs for the app
     * @param queue requested queue to place the app in during execution
     * @param time maximum allowable runtime
     * @param job_args arguments to pass to the job submitter
     */
    void setAppSettings(String title, String path, String args, String mem, String cpus,
                        String queue, String time, String job_args){
        app_title.setText(title);
        app_path.setText(path);
        app_args.setText(args);
        mem_req.setText(mem);
        cpus_req.setText(cpus);
        queue_req.setText(queue);
        wall_time_req.setText(time);
        job_sub_args.setText(job_args);
    }

    String getTitle(){
        return app_title.getText();
    }
    String getAppPath(){
        return app_path.getText();
    }
    String getAppArgs(){
        return app_args.getText();
    }
    String getMemRequested(){
        return mem_req.getText();
    }
    String getCpusRequested(){
        return cpus_req.getText();
    }
    String getQueueRequested(){
        return queue_req.getText();
    }
    String getWallTimeRequested(){
        return wall_time_req.getText();
    }
    String getJobSubmitterArgs(){
        return job_sub_args.getText();
    }

    void setTitle(String text){
        app_title.setText(text);
    }
    void setAppPath(String text){
        app_path.setText(text);
    }
    void setAppArgs(String text){
        app_args.setText(text);
    }
    void setMemoryRequested(String text){
        mem_req.setText(text);
    }
    void setCpusRequested(String text){
        cpus_req.setText(text);
    }
    void setQueueRequested(String text){
        queue_req.setText(text);
    }
    void setWallTimeRequested(String text){
        wall_time_req.setText(text);
    }
    void setJobSubmitterArgs(String text){
        job_sub_args.setText(text);
    }

    void setAppType( Type application_type ){
        app_type = application_type;
    }

    Type getAppType(){
        return  app_type;
    }

    void setAppBind( V application){
        application_bind = application;
    }

    V getAppBind(){
        return application_bind;
    }
}
