package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;

/**
 * Project naspgui.
 * Created by jlabadie on 8/5/16.
 *
 * @author jlabadie
 */
public class ReformatCSV {

    public File reformat( File input ) {

        Reader read_in;
        Iterable<CSVRecord> records = null;
        String output_name = "reform_" + input.getName() ;
        try {
            read_in = new FileReader( input );
            records = CSVFormat.DEFAULT.parse( read_in );
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> lines = new ArrayList<>();
        int sample_index = 2; // samples start in the 2 index (aggdist, then true samples)
        int size = 10;
        boolean firstline = true;
        CSVRecord header = null;
        while( sample_index < size ){

            for ( CSVRecord record : records ) {
                if( firstline ) {
                    header = record;
                    System.out.println( header.toString() );
                    firstline = false;
                    size = record.size();
                    continue;
                }

                String from = record.get( 0 );
                String to = record.get( 1 );
                String value = record.get( sample_index );
                String line = header.get( sample_index )+","+from + "," + to + ","  + value;
                lines.add( line );
            }

            sample_index ++ ;
            System.out.println("index: " + sample_index);
        }

        FileWriter fileWriter = null;
        CSVPrinter printer = null;

        final String[] HEADER = { "sample","start","end","value" };
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader( HEADER );

        final Appendable out = new StringBuilder();
        try {
            fileWriter = new FileWriter( output_name );
            printer = new CSVPrinter( fileWriter, csvFileFormat );
            printer.printRecord( HEADER );
            printer.printRecord( lines );

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                fileWriter.flush();
                fileWriter.close();
                printer.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return new File( output_name );
    }


}
