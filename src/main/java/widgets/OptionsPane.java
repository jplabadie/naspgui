package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import xmlbinds.Options;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @author jlabadie
 */
public class OptionsPane extends GridPane {
    private Label CORE_SETTINGS = new Label("Core Settings");
    private Label RUN_NAME = new Label("Run Name");
    private Label OUTPUT_PATH = new Label("Remote Output Path");
    private Label REFERENCE = new Label( "Reference" );
    private Label REFERENCE_NAME = new Label("Reference Name");
    private Label REFERENCE_PATH = new Label("Reference Path");
    private Label FIND_DUPLICATES = new Label("Find Duplicates");
    private Label FILTERS = new Label( "Filters" );
    private Label PROPORTION_FILTER = new Label("Proportion Filter");
    private Label COVERAGE_FILTER = new Label("Coverage Filter");
    private Label JOB_SUBMITTER = new Label("Job Submitter");

    private Tooltip RUN_NAME_TIP = new Tooltip("The name you would like to call this NASP job run");
    private Tooltip OUTPUT_PATH_TIP = new Tooltip("The remote path where you would like to save output from this run");
    private Tooltip REFERENCE_NAME_TIP = new Tooltip("The name you would like to give this reference");
    private Tooltip REFERENCE_PATH_TIP = new Tooltip("The remote path where this reference can be found");
    private Tooltip FIND_DUPLICATES_TIP = new Tooltip("Set to true if you do want duplicate regions to be recorded");
    private Tooltip PROPORTION_FILTER_TIP = new Tooltip("The setting for the proportion filter");
    private Tooltip COVERAGE_FILTER_TIP = new Tooltip("The setting for the coverage filter");
    private Tooltip JOB_SUBMITTER_TIP = new Tooltip("The Job manager/submitter you would like to use on this run");

    private TextField run_name = new TextField();
    private TextField output_path = new TextField();
    private TextField ref_name = new TextField();
    private TextField ref_path = new TextField();
    private CheckBox find_dups = new CheckBox();
    private TextField prop_filter = new TextField();
    private TextField cov_filter = new TextField();
    private ChoiceBox<String> job_submitter = new ChoiceBox<>();

    private Options OPTIONS;

    OptionsPane( Options input_options ){
        /**
         * Initialize links to binding objects
         */
        OPTIONS = input_options;

        // Add OPTIONS to job-submitter choicebox
        ArrayList<String> job_sub_chocies = new ArrayList<>();
        ObservableList<String> choice_list = FXCollections.observableArrayList( job_sub_chocies );
        choice_list.addAll("PBS/Torque", "SLURM");
        job_submitter.setItems( choice_list );
        job_submitter.setValue( "PBS/Torque" ); //Default to PBS

        /**
         * Set Listeners on elements to auto-update xml_bind data
         */run_name.textProperty().addListener(
                (observable -> {
                    OPTIONS.setRunName( run_name.getText());
                })
        );

        output_path.textProperty().addListener(
                (observable -> {
                    OPTIONS.setOutputFolder( output_path.getText() );

                })
        );

        ref_name.textProperty().addListener(
                (observable -> OPTIONS.getReference().setName( run_name.getText()))
        );

        ref_path.textProperty().addListener(
                (observable -> OPTIONS.getReference().setPath( ref_path.getText()))
        );

        find_dups.setAllowIndeterminate( false );
        find_dups.setOnAction( event -> {
            if( find_dups.isSelected() )
                OPTIONS.getReference().setFindDups( "true" );
            else
                OPTIONS.getReference().setFindDups( "false" );
        });

        prop_filter.textProperty().addListener(
                observable -> OPTIONS.getFilters().setProportionFilter( prop_filter.getText() )
        );

        cov_filter.textProperty().addListener(
                observable -> OPTIONS.getFilters().setCoverageFilter( cov_filter.getText() )
        );

        job_submitter.setOnAction( event -> OPTIONS.setJobSubmitter( job_submitter.getValue() ));

        /**
         * Define the look and feel of static label elements
         */
        CORE_SETTINGS.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24 ) );
        CORE_SETTINGS.setPrefSize( 100,20 );
        CORE_SETTINGS.setAlignment( Pos.CENTER );
        CORE_SETTINGS.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        CORE_SETTINGS.setAlignment( Pos.CENTER );
        RUN_NAME.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        OUTPUT_PATH.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        REFERENCE.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        FIND_DUPLICATES.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        FILTERS.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );
        JOB_SUBMITTER.setFont( Font.font( "Helvetica", FontWeight.BOLD, 14 ) );

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
        ColumnConstraints c2 = new ColumnConstraints( 25, 60, 120 );
        ColumnConstraints c3 = new ColumnConstraints( 25, 60, 120 );
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

        // Add the title to row 0 column 0
        this.add( CORE_SETTINGS, 0, 0, 3, 1 );

        // Add row headings for app-path and app-args to column 1
        this.add( RUN_NAME, 1, 1, 3, 1 );
        this.add( OUTPUT_PATH,1,2,3,1 );
        this.add( REFERENCE,1,3,3,1 );
        this.add( FIND_DUPLICATES,1,6,3,1 );
        this.add( FILTERS,1,7,3,1 );
        this.add( JOB_SUBMITTER,1,10,3,1 );

        // Add row headings for the job-manager text fields to column 2 of the GridPane
        this.add( REFERENCE_NAME,2,4,3,1 );
        this.add( REFERENCE_PATH,2,5,3,1 );
        this.add( PROPORTION_FILTER,2,8,3,1 );
        this.add( COVERAGE_FILTER,2,9,3,1 );

        // Add text fields,checkboxes,choiceboxes to column 3 of the GridPane
        this.add( run_name,3,1,4,1 );
        this.add( output_path,3,2,4,1 );
        this.add( ref_name,3,4,4,1 );
        this.add( ref_path,3,5,4,1 );
        this.add( find_dups,3,6,4,1 );
        this.add( prop_filter,3,8,4,1 );
        this.add( cov_filter,3,9,4,1 );
        this.add( job_submitter,3,10,4,1 );

        run_name.setText( OPTIONS.getRunName() );
        output_path.setText( OPTIONS.getOutputFolder() );
        ref_name.setText( OPTIONS.getReference().getName() );
        ref_path.setText( OPTIONS.getReference().getPath() );
        if(OPTIONS.getReference().getFindDups().equalsIgnoreCase("true")){
            find_dups.setSelected( true );
        }
        prop_filter.setText( OPTIONS.getFilters().getProportionFilter() );
        cov_filter.setText( OPTIONS.getFilters().getCoverageFilter() );
        job_submitter.setValue( OPTIONS.getJobSubmitter() );
    }

    TextField getRunName(){
        return run_name;
    }
}