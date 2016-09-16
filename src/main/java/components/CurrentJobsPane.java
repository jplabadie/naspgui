package components;

import javafx.scene.Scene;
import javafx.stage.Stage;
import xmlbinds.qstat_xmlbinds.QstatDataType;
import xmlbinds.qstat_xmlbinds.QstatJobType;
import utilities.LogManager;
import utilities.RemoteNetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jean-Paul Labadie
 */
public class CurrentJobsPane extends Stage {

    private RemoteNetUtil net;
    private LogManager log = LogManager.getInstance();

    public CurrentJobsPane( RemoteNetUtil net ){

        this.net = net;
        this.setTitle("Job Manager");

        QstatDataType result = net.getJobsXml( "~qstat_temp.xml" );

        ArrayList<Job> jobs  = new ArrayList<>();

        List<QstatJobType> joblist = result.getJob();

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


        this.setScene( new Scene(jmp,800,800) );
    }
}
