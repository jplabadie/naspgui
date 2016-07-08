package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.*;

import java.util.ArrayList;

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
class ApplicationPane extends GridPane {

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
    private ChoiceBox< String > title_choice = new ChoiceBox<>();
    private TextField appPath = new TextField();
    private TextField appArgs = new TextField();

    private TextField memReq = new TextField();
    private TextField cpusReq = new TextField ();
    private TextField queueReq = new TextField ();
    private TextField wallTimeReq = new TextField ();
    private TextField jobSubArgs = new TextField ();

    private Application APPLICATION;
    private JobParameters JOBPARAMETERS;

    ApplicationPane( Application app ){
        APPLICATION = app;
        JOBPARAMETERS = APPLICATION.getJobParameters();
        if( JOBPARAMETERS == null ) {
            JOBPARAMETERS = new JobParameters();
            APPLICATION.setJobParameters( JOBPARAMETERS );
        }

        app_title.setText( APPLICATION.getName() );
        title_choice.setValue( app_title.getText() );

        /**
         * Define the look and feel of static label elements
         */
        APPLICATION_PATH.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        ADDITIONAL_ARGS.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        JOB_PARAMETERS.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
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
        this.setHgap( 2 );
        this.setVgap( 2 );
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
        app_title.setPrefSize( 100, 20 );
        app_title.setAlignment( Pos.CENTER_LEFT );
        // Set up the look and feel of the title
        app_title.setFont( Font.font( "Helvetica", FontWeight.EXTRA_BOLD, 18 ) );
        app_title.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        app_title.setAlignment( Pos.CENTER );

        // Set default values
        //app_title.setText( APPLICATION.getName() );
        //appPath.setText( APPLICATION.getPath() );

        ArrayList<String> temp = new ArrayList<String>();
        ObservableList<String> choices = FXCollections.observableList( temp );
        choices.addAll(
                "Index",
                "MatrixGenerator",
                "Picard",
                "Samtools",
                "DupFinder",
                "AssemblyImporter",
                "Aligner",
                "SnpCaller"
        );
        title_choice.setItems( choices );

        title_choice.setOnAction( event -> {
            if( APPLICATION != null){
               String title_text = choices.get( title_choice.getSelectionModel().getSelectedIndex() );
                app_title.setText( title_text );
                switch ( choices.get( title_choice.getSelectionModel().getSelectedIndex() ) ) {
                    case "Index": APPLICATION = new Index();
                        break;
                    case "MatrixGenerator" : APPLICATION = new MatrixGenerator();
                        break;
                    case "Picard" : APPLICATION = new Picard();
                        break;
                    case "Samtools" : APPLICATION = new Samtools();
                        break;
                    case "DupFinder" : APPLICATION = new DupFinder();
                        break;
                    case "AssemblyImporter" : APPLICATION = new AssemblyImporter();
                        break;
                    case "Aligner" : APPLICATION = new Aligner();
                        break;
                    case "SnpCaller" : APPLICATION = new SNPCaller();
                }
                APPLICATION.setName( title_text );
            }
        });

        // Add the title to row 0 column 0
        this.add( app_title, 0, 0, 3, 1 );
        this.add( title_choice, 3 , 0, 3, 1);

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
        this.add(appPath,3,1,4,1 );
        this.add(appArgs,3,2,4,1 );
        this.add(memReq,3,4,4,1 );
        this.add(cpusReq,3,5,4,1 );
        this.add(queueReq,3,6,4,1 );
        this.add(wallTimeReq,3,7,4,1 );
        this.add(jobSubArgs,3,8,4,1 );

        //TODO: ADD listeners to auto-update binds as input changes

        appPath.textProperty().addListener( observable -> APPLICATION.setPath( appPath.getText() ));
        appArgs.textProperty().addListener( observable -> APPLICATION.setAdditionalArguments( appArgs.getText() ));
        memReq.textProperty().addListener( observable -> JOBPARAMETERS.setMemRequested( memReq.getText() ));
        cpusReq.textProperty().addListener( observable -> JOBPARAMETERS.setNumCPUs( cpusReq.getText() ));
        queueReq.textProperty().addListener( observable -> JOBPARAMETERS.setQueue( queueReq.getText() ));
        wallTimeReq.textProperty().addListener( observable -> JOBPARAMETERS.setWalltime( wallTimeReq.getText() ));
        jobSubArgs.textProperty().addListener( observable ->  JOBPARAMETERS.setJobSubmitterArgs( jobSubArgs.getText() ));

        appPath.setText( APPLICATION.getPath() );
        appArgs.setText( APPLICATION.getAdditionalArguments() );
        memReq.setText( JOBPARAMETERS.getMemRequested() );
        cpusReq.setText( JOBPARAMETERS.getNumCPUs() );
        queueReq.setText( JOBPARAMETERS.getQueue() );
        wallTimeReq.setText( JOBPARAMETERS.getWalltime() );
        jobSubArgs.setText( JOBPARAMETERS.getJobSubmitterArgs() );
    }

    String getTitle(){
        return app_title.getText();
    }

    void setTitle(String text) {
        app_title.setText(text);
    }

    Application getApplication(){
        return APPLICATION;
    }

    void setApplication( Application app ){
        APPLICATION = app;
    }
}