package utils;

import com.jcraft.jsch.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jean-Paul Labadie
 *
 */
public class DefaultRemoteNetUtilTest {

    private static DefaultRemoteNetUtil nm;

    @BeforeClass
    public static void setup() {
        nm = new DefaultRemoteNetUtil();
        String usr;
        String pwd;
        usr = "jlabadie";
        pwd = "C00kiemnstr!";

        nm.initSession(usr, pwd, "aspen.tgen.org", 22);
        try {
            nm.openSession();
        }
        catch ( Exception e ){
            Assert.fail();
        }
    }

    @AfterClass
    public static void teardown(){
        nm.closeSession();
    }


    @Test
    public void testGetSession() throws Exception {
        Session sess = nm.getSession();

        if( sess == null )
            Assert.fail();
    }

    @Test
    public void testUpload() throws Exception {
        //File file = new File(getClass().getClassLoader().getResource("test/NASPInputExample_Aspen.xml").toString());
        //nm.upload(file,"/home/jlabadie/test.xml");
    }

    @Test
    public void testDownload() throws Exception {
        //nm.download("/home/jlabadie/NASPInputExample.xml","testdownload.xml");
    }

    @Test
    public void testGetJobs() throws Exception {

    }
    @Test
    public void testGetAllFiles() throws Exception {
//        ArrayList<String> result =  nm.getAllFiles("/home/jlabadie");
//        for(String x : result){
//            System.out.println( "Files: " + x);
//        }
    }

    @Test
    public void testExecChan() throws Exception {


    }
}