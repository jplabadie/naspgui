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
import xmlbinds.Application;
import xmlbinds.JobParameters;

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
class ApplicationPane< V extends Application> extends GridPane {

    private Label APPLICATION_PATH = new Label( "App Path" );
    private Label ADDITIONAL_ARGS = new Label( "Arguments" );

    private Tooltip APP_PATH_TIP = new Tooltip("A path to the application on the remote service");
    private Tooltip ADD_ARGS_TIP = new Tooltip("Additional arguments or options to pass to the app");

    private Label appTitle = new Label();
    private TextField appPath = new TextField();
    private TextField appArgs = new TextField();

    private JobParametersPane jobParametersPane;

    private V APPLICATION;

    ApplicationPane( V app ){

        APPLICATION = app;

        appTitle.setText( APPLICATION.getName() );

        JobParameters jobparams = APPLICATION.getJobParameters();

        if (jobparams == null) {
            jobparams = new JobParameters();
            APPLICATION.setJobParameters(jobparams);
        }

        jobParametersPane = new JobParametersPane( jobparams );

        /**
         * Define the look and feel of static label elements
         */
        APPLICATION_PATH.setFont( Font.font( "Helvetica", FontWeight.BOLD, 12 ) );
        ADDITIONAL_ARGS.setFont( Font.font( "Helvetica", FontWeight.BOLD, 12 ) );

        /**
         * Add tooltips to the static label elements
         */
        APPLICATION_PATH.setTooltip( APP_PATH_TIP );
        ADDITIONAL_ARGS.setTooltip( ADD_ARGS_TIP );

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 2 );
        this.setVgap( 2 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 25, 35 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 90, 125 );
        ColumnConstraints c2 = new ColumnConstraints( 25, 300, 400 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 50, 150 );
        //Define column auto-resizing behavior
        c0.setHgrow( Priority.SOMETIMES );
        c1.setHgrow( Priority.SOMETIMES );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.RIGHT );
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        appTitle.setPrefHeight( 20);
        appTitle.setAlignment( Pos.CENTER_LEFT );
        // Set up the look and feel of the title
        appTitle.setId("header2");

        // Set default values
        //appTitle.setText( APPLICATION.getName() );
        //appPath.setText( APPLICATION.getPath() );

        // Add the title to row 0 column 0
        this.add(appTitle, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( APPLICATION_PATH, 1, 1, 3, 1 );
        this.add( ADDITIONAL_ARGS,1,2,3,1 );

        // Add text fields to column 3 of the GridPane
        this.add( appPath,2,1,4,1 );
        this.add( appArgs,2,2,4,1 );

        this.add( jobParametersPane, 2, 3, 6, 4 );

        //TODO: ADD listeners to auto-update binds as input changes

        appPath.textProperty().addListener( observable -> {
            APPLICATION.setPath( appPath.getText() );

        });

        appArgs.textProperty().addListener( observable -> APPLICATION.setAdditionalArguments( appArgs.getText() ));

        appPath.setText( APPLICATION.getPath() );
        appArgs.setText( APPLICATION.getAdditionalArguments() );
    }

    String getTitle(){
        return appTitle.getText();
    }

    void setTitle(String text){
        appTitle.setText(text);
    }

    Application getApplication(){
        return APPLICATION;
    }

    void setApplication( V app ){
        APPLICATION = app;
    }
}