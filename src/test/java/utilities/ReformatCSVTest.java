package utilities;

import org.junit.Test;

import java.io.File;

/**
 * Project naspgui.
 * Created by jlabadie on 8/5/16.
 *
 * @Author jlabadie
 */
public class ReformatCSVTest {
    @Test
    public void reformat() throws Exception {

        ReformatCSV rf = new ReformatCSV();
        File file = new File( getClass().getResource( "/web/examples/big_results.csv" ).getPath() );

        System.out.println( file.toString() );
        rf.reformat( file );


    }

}