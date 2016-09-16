package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.table.TableFilter;

import java.util.ArrayList;

/**
 * Provides a view of the Job Manager status on the remote cluster, with filtering and control options.
 * For instance, Users should be able to view all jobs, only NASP jobs, only their own jobs, as well as the ability
 * to cancel their own jobs (only).
 *
 * Future support could be included for tracking finished jobs and running vis, if those features are not
 * built into a separate view.
 *
 * project naspgui.
 * created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class JobManagerPane extends BorderPane{

    private ScrollPane scrollPane = new ScrollPane();
    private MasterDetailPane mdp = new MasterDetailPane();
    private TableView<Job> table = new TableView<>();
    private TableFilter<Job> filter;
    private ObservableList<Job> jobs ;
    private ToolBar toolBar = new ToolBar();

    public JobManagerPane( ArrayList<Job> input ){
        jobs = FXCollections.observableArrayList( input);
        initialize();
    }

    @SuppressWarnings( "unchecked" )
    void initialize(){
        this.setPrefWidth( 1000 );
        this.setCenter( scrollPane ); // set a ScrollPane as the center container for this BorderPane

        scrollPane.setContent( table ); // add a VBox to the scroll pane ( the VBox will hold our GridPanes )
        scrollPane.setPrefWidth( 800 );
        //jobs = FXCollections.observableArrayList( new ArrayList<Job>() );
        table.setItems( jobs );
        table.setPrefWidth( 800 );
        filter = new TableFilter( table );
        mdp.setPrefWidth( 1000 );
        mdp.setMasterNode( table );
        mdp.setDetailNode( new PropertySheet() );
        mdp.setDetailSide( Side.RIGHT );
        mdp.setShowDetailNode( true );

        TableColumn<Job,String> runNameCol = new TableColumn<>("Run");
        runNameCol.setCellValueFactory(new PropertyValueFactory("runName"));

        TableColumn<Job,String> jobIdCol = new TableColumn<>("Job ID");
        jobIdCol.setCellValueFactory(new PropertyValueFactory("jobId"));

        TableColumn<Job,String> jobNameCol = new TableColumn<>("Job Name");
        jobNameCol.setCellValueFactory(new PropertyValueFactory("jobName"));

        TableColumn<Job,String> userNameCol = new TableColumn<>("User Name");
        userNameCol.setCellValueFactory(new PropertyValueFactory("userName"));

        TableColumn<Job,String> queueCol = new TableColumn<>("Queue");
        queueCol.setCellValueFactory(new PropertyValueFactory("queue"));

        TableColumn<Job,String> sessionIdCol = new TableColumn<>("Session");
        sessionIdCol.setCellValueFactory(new PropertyValueFactory("sessionId"));

        TableColumn<Job,String> ndsCol = new TableColumn<>("Nodes");
        ndsCol.setCellValueFactory(new PropertyValueFactory("nds"));

        TableColumn<Job,String> reqTaskCol = new TableColumn<>("CPUs");
        reqTaskCol.setCellValueFactory(new PropertyValueFactory("reqTask"));

        TableColumn<Job,String> reqMemCol = new TableColumn<>("RAM");
        reqMemCol.setCellValueFactory(new PropertyValueFactory("reqMem"));

        TableColumn<Job,String> elapsedTimeCol = new TableColumn<>("Elapsed Time");
        elapsedTimeCol.setCellValueFactory(new PropertyValueFactory("elapsedTime"));

        TableColumn<Job,String> timeCol = new TableColumn<>("Wall Time");
        timeCol.setCellValueFactory(new PropertyValueFactory("time"));

        TableColumn<Job,String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));

        table.getColumns().setAll( runNameCol, jobIdCol, jobNameCol, userNameCol, queueCol,
                sessionIdCol, ndsCol, reqTaskCol, reqMemCol, elapsedTimeCol, timeCol,
                statusCol );
    }

    void setJobs( ArrayList<Job> jobz ){
        this.jobs = FXCollections.observableArrayList( jobz );
    }
}
