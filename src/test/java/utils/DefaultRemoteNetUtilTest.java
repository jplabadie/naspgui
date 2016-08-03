package utils;

import com.jcraft.jsch.Session;
import org.junit.*;

import java.util.ArrayList;

/**
 * @author Jean-Paul Labadie
 * @date 8/11/2015
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
        catch (Exception e){
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

        if(sess == null)
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
        ArrayList<String> result =  nm.getUserJobs("mvalentine");
        for(String x : result){
            System.out.println( "Jobs: " + x);
        }
        ArrayList<String> result2 =  nm.getUserJobs("mvalentine");
        for(String x : result2){
            System.out.println( "Jobs: " + x);
        }
    }
    @Test
    public void testGetAllFiles() throws Exception {
        ArrayList<String> result =  nm.getAllFiles("/home/jlabadie");
        for(String x : result){
            System.out.println( "Files: " + x);
        }
    }

    @Test
    public void testRunNaspJob() throws Exception {

    }


}