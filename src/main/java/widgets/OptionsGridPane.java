package widgets;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Project naspgui.
 * Created by jlabadie on 6/15/16.
 *
 * @Author jlabadie
 */
public class OptionsGridPane extends GridPane {

    private static Label CORE_SETTINGS = new Label("Core Settings");
    private static Label RUN_NAME = new Label("Run Name");
    private static Label OUTPUT_PATH = new Label("Remote Output Path");
    private static Label REFERENCE = new Label( "Reference" );
    private static Label REFERENCE_NAME = new Label("Reference Name");
    private static Label REFERENCE_PATH = new Label("Reference Path");
    private static Label FIND_DUPLICATES = new Label("Find Duplicates");
    private static Label FILTERS = new Label( "Filters" );
    private static Label PROPORTION_FILTER = new Label("Proportion Filter");
    private static Label COVERAGE_FILTER = new Label("Coverage Filter");
    private static Label JOB_SUBMITTER = new Label("Job Submitter");

    private static Tooltip RUN_NAME_TIP = new Tooltip("The name you would like to call this NASP job run");
    private static Tooltip OUTPUT_PATH_TIP = new Tooltip("The remote path where you would like to save output from this run");
    private static Tooltip REFERENCE_NAME_TIP = new Tooltip("The name you would like to give this reference");
    private static Tooltip REFERENCE_PATH_TIP = new Tooltip("The remote path where this reference can be found");
    private static Tooltip FIND_DUPLICATES_TIP = new Tooltip("Set to true if you do want duplicate regions to be recorded");
    private static Tooltip PROPORTION_FILTER_TIP = new Tooltip("The setting for the proportion filter");
    private static Tooltip COVERAGE_FILTER_TIP = new Tooltip("The setting for the coverage filter");
    private static Tooltip JOB_SUBMITTER_TIP = new Tooltip("The Job manager/submitter you would like to use on this run");

    private TextField run_name = new TextField();
    private TextField output_path = new TextField();
    private TextField ref_name = new TextField();
    private TextField ref_path = new TextField();
    private CheckBox find_dups = new CheckBox();
    private TextField prop_filter = new TextField();
    private TextField cov_filter = new TextField();
    private ChoiceBox<String> job_submitter = new ChoiceBox<>();

    OptionsGridPane(){
        /**
         * Define the look and feel of static label elements
         */
        CORE_SETTINGS.setFont( Font.font("Helvetica", FontWeight.EXTRA_BOLD, 24 ) );
        CORE_SETTINGS.setPrefSize( 100,20 );
        CORE_SETTINGS.setAlignment( Pos.CENTER );
        CORE_SETTINGS.setPrefSize( USE_COMPUTED_SIZE, USE_COMPUTED_SIZE );
        CORE_SETTINGS.setAlignment( Pos.CENTER );
        RUN_NAME.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        OUTPUT_PATH.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        REFERENCE.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        FIND_DUPLICATES.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        FILTERS.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
        JOB_SUBMITTER.setFont( Font.font( "Courier", FontWeight.BOLD, 14 ) );
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
        this.add( PROPORTION_FILTER,2,8,3,1);
        this.add( COVERAGE_FILTER,2,9,3,1);

        // Add text fields,checkboxes,choiceboxes to column 3 of the GridPane
        this.add( run_name,3,1,4,1 );
        this.add( output_path,3,2,4,1 );
        this.add( ref_name,3,4,4,1 );
        this.add( ref_path,3,5,4,1 );
        this.add( find_dups,3,6,4,1 );
        this.add( prop_filter,3,8,4,1 );
        this.add( cov_filter,3,9,4,1 );
        this.add( job_submitter,3,10,4,1);

    }

    String getRunName(){
        return run_name.getText();
    }
    String getOuputPath(){
        return output_path.getText();
    }
    String getReferenceName(){
        return ref_name.getText();
    }
    String getReferencePath(){
        return ref_path.getText();
    }
    Boolean getFindDuplicates(){
        return find_dups.isSelected();
    }
    String getProportionFilter(){
        return prop_filter.getText();
    }
    String getCoverageFilter(){
        return cov_filter.getText();
    }
    String getJobSubmitter(){
        return job_submitter.getValue();
    }
    ObservableList<String> getJobSubmitterOptions(){
        return job_submitter.getItems();
    }

    void setRunName(String text){
        run_name.setText(text);
    }
    void setOutputPath(String text){
        output_path.setText(text);
    }
    void setReferenceName(String text){
        ref_name.setText(text);
    }
    void setReferencePath(String text){
        ref_path.setText(text);
    }
    void setFindDuplicates(boolean bool){
        find_dups.setSelected(bool);
    }
    void setProportionFilter(String text){
        prop_filter.setText(text);
    }
    void setCoverageFilter(String text){
        cov_filter.setText(text);
    }
    void setJobSubmitter(String text){
        job_submitter.setValue(text);
    }
    void setJobSubmitterOptions(ObservableList<String> options){
        job_submitter.setItems(options);
    }


}