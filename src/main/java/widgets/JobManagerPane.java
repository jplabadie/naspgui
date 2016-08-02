package widgets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.table.TableFilter;

import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class JobManagerPane extends BorderPane{

    private ScrollPane scrollPane = new ScrollPane();
    private MasterDetailPane mdp = new MasterDetailPane();
    private TableView<Job> table = new TableView<>();
    private TableFilter<Job> filter;
    ObservableList<Job> jobs ;

    public JobManagerPane(){
        initialize();
    }

    @SuppressWarnings( "unchecked" )
    void initialize(){
        this.setPrefWidth( 800 );
        this.setCenter( scrollPane ); // set a ScrollPane as the center container for this BorderPane
        scrollPane.setContent( table ); // add a VBox to the scroll pane ( the VBox will hold our GridPanes )

        jobs = FXCollections.observableArrayList( new ArrayList<Job>() );
        table.setItems( jobs );
        filter = new TableFilter( table );
        mdp.setMasterNode( table );
        mdp.setDetailNode( new PropertySheet() );
        mdp.setDetailSide( Side.RIGHT );
        mdp.setShowDetailNode( true );

        TableColumn<Job,String> runNameCol = new TableColumn<>("Run Name");
        runNameCol.setCellValueFactory(new PropertyValueFactory("runName"));

        TableColumn<Job,String> jobIdCol = new TableColumn<>("Job ID");
        jobIdCol.setCellValueFactory(new PropertyValueFactory("jobId"));

        TableColumn<Job,String> jobNameCol = new TableColumn<>("Job Name");
        jobNameCol.setCellValueFactory(new PropertyValueFactory("jobName"));

        TableColumn<Job,String> userNameCol = new TableColumn<>("User Name");
        userNameCol.setCellValueFactory(new PropertyValueFactory("userName"));

        TableColumn<Job,String> queueCol = new TableColumn<>("Queue");
        queueCol.setCellValueFactory(new PropertyValueFactory("queue"));

        TableColumn<Job,String> sessionIdCol = new TableColumn<>("Session ID");
        sessionIdCol.setCellValueFactory(new PropertyValueFactory("sessionId"));

        TableColumn<Job,String> ndsCol = new TableColumn<>("Nodes");
        ndsCol.setCellValueFactory(new PropertyValueFactory("nds"));

        TableColumn<Job,String> reqTaskCol = new TableColumn<>("Req Task");
        reqTaskCol.setCellValueFactory(new PropertyValueFactory("reqTask"));

        TableColumn<Job,String> reqMemCol = new TableColumn<>("Req Mem");
        reqMemCol.setCellValueFactory(new PropertyValueFactory("reqMem"));

        TableColumn<Job,String> elapsedTimeCol = new TableColumn<>("Elapsed Time");
        elapsedTimeCol.setCellValueFactory(new PropertyValueFactory("elapsedTime"));

        TableColumn<Job,String> timeCol = new TableColumn<>("Time");
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
