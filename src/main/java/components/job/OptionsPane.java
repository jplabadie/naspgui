package components.job;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import xmlbinds.Options;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
public class OptionsPane extends GridPane {
    private Label core_settings = new Label("Core Settings");
    private Label RUN_NAME = new Label("Run Name");
    private Label OUTPUT_PATH = new Label("Remote Output Path");
    private Label REFERENCE = new Label( "Reference" );
    private Label REFERENCE_NAME = new Label("Reference Name");
    private Label REFERENCE_PATH = new Label("Reference Path");
    private Label trimReadsLabel = new Label( "Trim Reads (Adaptor)" );
    private Label FIND_DUPLICATES = new Label("Find Duplicates");
    private Label FILTERS = new Label( "Filters" );
    private Label PROPORTION_FILTER = new Label("Proportion Filter");
    private Label COVERAGE_FILTER = new Label("Coverage Filter");
    private Label JOB_SUBMITTER = new Label("Job Submitter");

    private Tooltip RUN_NAME_TIP = new Tooltip("The name you would like to call this NASP job run");
    private Tooltip OUTPUT_PATH_TIP = new Tooltip("The remote path where you would like to save output from this run");
    private Tooltip REFERENCE_NAME_TIP = new Tooltip("The name you would like to give this reference");
    private Tooltip REFERENCE_PATH_TIP = new Tooltip("The remote path where this reference can be found");
    private Tooltip trimReadsTip = new Tooltip(( "Enable trimming of read adaptors (recommended)"));
    private Tooltip FIND_DUPLICATES_TIP = new Tooltip("Set to true if you do want duplicate regions to be recorded");
    private Tooltip PROPORTION_FILTER_TIP = new Tooltip("The setting for the proportion filter");
    private Tooltip COVERAGE_FILTER_TIP = new Tooltip("The setting for the coverage filter");
    private Tooltip JOB_SUBMITTER_TIP = new Tooltip("The Job manager/submitter you would like to use on this run");

    private TextField runName = new TextField();
    private TextField outputPath = new TextField();
    private TextField refName = new TextField();
    private TextArea refPath = new TextArea();
    private CheckBox trimReads = new CheckBox();
    private CheckBox findDups = new CheckBox();
    private TextField propFilter = new TextField();
    private TextField covFilter = new TextField();
    private ChoiceBox<String> jobSubmitter = new ChoiceBox<>();

    private Options OPTIONS;

    public OptionsPane(Options input_options){
        /**
         * Initialize links to binding objects
         */
        OPTIONS = input_options;

        // Add OPTIONS to job-submitter choicebox
        ArrayList<String> job_sub_chocies = new ArrayList<>();
        ObservableList<String> choice_list = FXCollections.observableArrayList( job_sub_chocies );
        choice_list.addAll("PBS/Torque", "SLURM");
        jobSubmitter.setItems( choice_list );
        jobSubmitter.setValue( "PBS/Torque" ); //Default to PBS

        /**
         * Set Listeners on elements to auto-update xml_bind data
         */runName.textProperty().addListener(
                (observable -> {
                    OPTIONS.setRunName( runName.getText());
                })
        );

        outputPath.textProperty().addListener(
                (observable -> {
                    OPTIONS.setOutputFolder( outputPath.getText() );
                })
        );

        refName.textProperty().addListener(
                (observable -> OPTIONS.getReference().setName( refName.getText()))
        );

        refPath.textProperty().addListener(
                (observable -> OPTIONS.getReference().setPath( refPath.getText()))
        );

        trimReads.setAllowIndeterminate( false );
        trimReads.setOnAction( event -> {
            if( findDups.isSelected() )
                OPTIONS.setTrimReads( "True" );
            else
                OPTIONS.setTrimReads( "False" );
        });

        findDups.setAllowIndeterminate( false );
        findDups.setOnAction(event -> {
            if( findDups.isSelected() )
                OPTIONS.getReference().setFindDups( "True" );
            else
                OPTIONS.getReference().setFindDups( "False" );
        });

        propFilter.textProperty().addListener(
                observable -> OPTIONS.getFilters().setProportionFilter( propFilter.getText() )
        );

        covFilter.textProperty().addListener(
                observable -> OPTIONS.getFilters().setCoverageFilter( covFilter.getText() )
        );

        jobSubmitter.setOnAction(event -> OPTIONS.setJobSubmitter( jobSubmitter.getValue() ));

        /**
         * Define the look and feel of static label elements
         */
        core_settings.setId( "header1" );


        RUN_NAME.setId( "label2" );
        OUTPUT_PATH.setId( "label2" );
        REFERENCE.setId( "label2" );
        trimReadsLabel.setId( "label2" );
        FIND_DUPLICATES.setId( "label2" );
        FILTERS.setId( "label2" );
        JOB_SUBMITTER.setId( "label2" );

        REFERENCE_NAME.setId( "label3" );
        REFERENCE_PATH.setId( "label3" );
        PROPORTION_FILTER.setId( "label3" );
        COVERAGE_FILTER.setId( "label3" );

        /**
         * Add tooltips to the static label elements
         */
        RUN_NAME.setTooltip(RUN_NAME_TIP);
        OUTPUT_PATH.setTooltip(OUTPUT_PATH_TIP);
        REFERENCE_NAME.setTooltip(REFERENCE_NAME_TIP);
        REFERENCE_PATH.setTooltip(REFERENCE_PATH_TIP);
        FIND_DUPLICATES.setTooltip(FIND_DUPLICATES_TIP);
        PROPORTION_FILTER.setTooltip(PROPORTION_FILTER_TIP);
        COVERAGE_FILTER.setTooltip(COVERAGE_FILTER_TIP);
        JOB_SUBMITTER.setTooltip(JOB_SUBMITTER_TIP);

        /**
         * Define the look and behavior of the GridPane
         */
        // Set Horizontal and Vertical gap size (spacing between column areas)
        this.setHgap( 2 );
        this.setVgap( 2 );
        //Define column behavior (min_size, preferred_size, max_size)
        ColumnConstraints c0 = new ColumnConstraints( 25, 60, 120 );
        ColumnConstraints c1 = new ColumnConstraints( 25, 60, 120 );
        ColumnConstraints c2 = new ColumnConstraints( 50, 70, 120 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 150, 300 );
        //Define column auto-resizing behavior
        c1.setHgrow( Priority.NEVER );
        c2.setHgrow( Priority.ALWAYS );
        c3.setHgrow( Priority.SOMETIMES );
        c3.setHalignment( HPos.LEFT);
        // Add column behavior to the GridPane (order matters!)
        this.getColumnConstraints().addAll( c0, c1, c2, c3  );

        /**
         * Define the look and behavior of the non-static TextField and Label elements
         */

        // Add the title to row 0 column 0
        this.add(core_settings, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( RUN_NAME, 1, 1, 3, 1 );
        this.add( OUTPUT_PATH,1,2,3,1 );
        this.add( REFERENCE,1,3,3,1 );
        this.add( trimReadsLabel,1,6,3,1 );
        this.add( FIND_DUPLICATES,1,7,3,1 );
        this.add( FILTERS,1,8,3,1 );
        this.add( JOB_SUBMITTER,1,11,3,1 );

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( REFERENCE_NAME,1,4,2,1 );
        this.add( REFERENCE_PATH,1,5,2,1 );
        this.add( PROPORTION_FILTER,1,9,2,1 );
        this.add( COVERAGE_FILTER,1,10,2,1 );

        // Add text fields,checkboxes,choiceboxes to column 3 of the GridPane
        this.add(runName,3,1,4,1 );
        this.add(outputPath,3,2,4,1 );
        this.add(refName,3,4,4,1 );
        this.add(refPath,3,5,4,1 );
        this.add( trimReads,3,6,4,1 );
        this.add(findDups,3,7,4,1 );
        this.add(propFilter,3,9,4,1 );
        this.add(covFilter,3,10,4,1 );
        this.add(jobSubmitter,3,11,4,1 );

        runName.setText( OPTIONS.getRunName() );
        outputPath.setText( OPTIONS.getOutputFolder() );
        refName.setText( OPTIONS.getReference().getName() );
        refPath.setText( OPTIONS.getReference().getPath() );
        if( OPTIONS.getTrimReads().equalsIgnoreCase( "true" )){
            trimReads.setSelected( true );
        }
        if(OPTIONS.getReference().getFindDups().equalsIgnoreCase( "true" )){
            findDups.setSelected( true );
        }
        propFilter.setText( OPTIONS.getFilters().getProportionFilter() );
        covFilter.setText( OPTIONS.getFilters().getCoverageFilter() );
        jobSubmitter.setValue( OPTIONS.getJobSubmitter() );
    }

    TextField getRunName(){
        return runName;
    }

    void setReference( String path, String name){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format( cal.getTime() );
        refName.setText( name );
        refPath.setText( path );

        String folder = path.substring( 0, path.lastIndexOf('/') );
        runName.setText( name + "_" + date );
        outputPath.setText( folder +"/NASP_Output/");
    }
}