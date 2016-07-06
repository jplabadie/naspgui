package utils;

import xmlbinds.NaspInputData;

import java.io.File;
import java.net.URL;

/**
 * @author Jean-Paul Labadie
 */
public class JobRecordSaveLoadManagerTest {


    @org.junit.BeforeClass
    public static void onlyOnce(){

    }

    @org.junit.Test
    public void testAddElement() throws Exception {


    }

    @org.junit.Test
    public void testCreateOutputXML() throws Exception {
        NaspInputData naspInputData = null;
        try {

            URL nip = getClass()
                    .getResource("/Users/jlabadie/Workspace/naspgui/src/test/java/utils/NaspInputExample.xml");
            File nfile = new File(nip.getFile());

            naspInputData = JobSaveLoadManager.jaxbXMLToObject(nfile);

            JobSaveLoadManager.jaxbObjectToXML(naspInputData, "test_out");

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        if(naspInputData != null) {
            System.out.println(naspInputData.getExternalApplications().getAligner().get(0).getName());
        }

    }
}