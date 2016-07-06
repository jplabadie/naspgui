package utils;

import org.junit.Test;
import xmlbinds.NaspInputData;
import xmlbinds.Options;

/**
 * Project naspgui.
 * Created by jlabadie on 7/6/16.
 *
 * @author jlabadie
 */
public class SaveLoadTest {

    @Test
    public void testSaveToXML() throws Exception {
        NaspInputData nip = new NaspInputData();
        Options opts = new Options();
        opts.setRunName(" Run Name ");
        opts.setOutputFolder("Output/Folder/path");
        nip.setOptions( opts );

        JobSaveLoadManager.jaxbObjectToXML( nip, "testout");

    }

}
