package widgets;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import utils.DefaultRemoteNetUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        ArrayList<String> result = nm.execCommand( "qstat -a" );
        ArrayList<Job> jobs  = new ArrayList<>();

        result.remove("tnorth-mgt.cm.cluster: ");
        result.remove("                                                                         Req'd  Req'd   Elap");
        result.remove("-------------------- ----------- -------- ---------------- ------ ----- ------ ------ ----- - -----");
        result.remove("Job ID               Username    Queue    Jobname          SessID NDS   TSK    Memory Time  S Time");
        result.remove(0);

        for(String x : result){
            System.out.println( x + " : " + result.indexOf(x));
        }

        Pattern ptrn = Pattern.compile( "(\\S+)" );
        for( String x : result ){
            ArrayList<String> out = new ArrayList<>();
            Matcher m = ptrn.matcher( x );

                System.out.println("!");
                while (m.find()) {
                    out.add( x.substring( m.start(), m.end() ));
                }
                String[] temp = new String[out.size()];
                System.out.println( out );
                for(int i = 0; i < out.size(); i ++){
                    temp[i] = out.get(i);
                    System.out.println("%" + out.get(i));
                }

                System.out.println(temp.length);
                Job j = new Job( "test", temp[0], temp[1],temp[2],temp[3],temp[4],temp[5],
                        temp[6],temp[7],temp[8],temp[10], temp[9]);

                jobs.add(j);
                out.clear();
        }

        JobManagerPane jmp = new JobManagerPane( jobs );
        Scene scene = new Scene( jmp, 800, 600 );
        //scene.getStylesheets().add( "css/darc.css" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
}