package utilities;

import org.junit.Test;

import java.io.File;

/**
 * @author Jean-Paul Labadie
 * @date 8/11/2015
 */
public class TransformCsvTest {

    @Test
    public void testSnpdistTransform() throws Exception {

        TransformCsv.snpdistTransform( new File( "big_results.csv") );
    }
}