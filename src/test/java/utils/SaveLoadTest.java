package utils;

import org.junit.Test;
import xmlbinds.*;

import java.util.List;

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

        Files f = new Files();
        List<ReadFolder> reads = f.getReadFolder();
        ReadFolder rf = new ReadFolder();
        rf.setPath( "ReadFolder/Path");
        reads.add( rf );

        ReadPair rp = new ReadPair();
        rp.setSample("SAMPLENAME");
        rp.setRead1Filename("file/a");
        rp.setRead2Filename("file/b");

        List<ReadPair> pairs = rf.getReadPair();
        pairs.add( rp );

        nip.setFiles( f );

        XMLSaveLoad.jaxbObjectToXML(nip, "testout");

    }

}
