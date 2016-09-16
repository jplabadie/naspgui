package components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import qstat_xmlbinds.QstatDataType;
import qstat_xmlbinds.QstatJobType;
import utilities.DefaultRemoteNetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Project naspgui.
 * Created by jlabadie on 8/2/16.
 *
 * @author jlabadie
 */
public class JobManagerPaneTest extends Application {

    public static void main( String[] args ) {
        launch( args );
    }

    @Override
    public void start( Stage primaryStage ) {

        primaryStage.setTitle( "Job Manager Visual Test" );
        DefaultRemoteNetUtil nm = new DefaultRemoteNetUtil();
        String usr;
        String pwd;
        usr = "jlabadie";
        pwd = "C00kiemnstr!";

        nm.initSession( usr, pwd, "aspen.tgen.org", 22 );
        try {
            nm.openSession();
        }
        catch ( Exception e ){
            Assert.fail();
        }

        QstatDataType result = nm.getJobsXml( "~qstat_temp.xml" ); // nm.execCommand( "qstat -a" );

        ArrayList<Job> jobs  = new ArrayList<>();

        List <QstatJobType> joblist = result.getJob();

        for( QstatJobType x : joblist ){

            //if( x.getJobOwner().equalsIgnoreCase( usr )) {

                Job j = new Job();
                j.setJobId( x.getJobId());
                j.setElapsedTime( x.getEtime() );
                j.setJobName( x.getJobName() );
                j.setNds( x.getStartCount() );
                j.setQueue( x.getQueue() );
                j.setReqMem( x.getResourceList().getMem() );
                j.setReqTask( x.getResourceList().getNcpus() );
                j.setSessionId( x.getSessionId() );
                j.setStatus( x.getJobState() );
                j.setTime( x.getCtime() );
            j.setUserName( x.getJobOwner() );

                jobs.add(j);
           // }
        }

        JobManagerPane jmp = new JobManagerPane( jobs );
        Scene scene = new Scene( jmp, 800, 600 );
        //scene.getStylesheets().add( "styles/darc.styles" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
}