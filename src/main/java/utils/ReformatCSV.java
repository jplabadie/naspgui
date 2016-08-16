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
        Iterable< CSVRecord > records = null;
        String output_name = "reform_" + input.getName() ;
        try {
            read_in = new FileReader( input );
            records = CSVFormat.DEFAULT.parse( read_in );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        ArrayList<CSVRecord> recs = new ArrayList<>();
        ArrayList<String[]> lines = new ArrayList<>();

        for(CSVRecord r : records){
            recs.add( r );
        }

        int size = 10;
        boolean firstline = true;
        String[] samples = null;
        String sample = "";
        for( int sample_index = 0; sample_index < size; sample_index++ ){

            for ( int i = 0; i < recs.size(); i++ ) {
                CSVRecord record = recs.get( i );
                if( firstline ) {
                    CSVRecord header = record;
                    samples = new String[ header.size() ];
                    for( int ii = 3; ii < header.size(); ii++ ){
                        samples[ ii-3 ] = header.get( ii );
                        System.out.println( samples[ ii-3 ] );
                    }
                    firstline = false;

                    System.out.println( "s1:" + size + "r:" + record.size() + ":" + record.toString() );
                    continue;
                }
                sample = samples[ sample_index ];
                size = record.size();
                String contig = record.get( 0 );

                String from = record.get( 1 );
                String to = record.get( 2 );
                String value = record.get( sample_index );
                String[] line = { contig, sample, from , to , value };
                lines.add( line );
            }

            System.out.println( "index: " + samples[ sample_index ] );
        }

        FileWriter fileWriter = null;
        CSVPrinter printer = null;

        final String[] HEADER = { "contig", "sample", "start", "end", "value" };
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader( HEADER );

        final Appendable out = new StringBuilder();
        try {
            fileWriter = new FileWriter( output_name );
            printer = new CSVPrinter( fileWriter, csvFileFormat );
            printer.printRecords( lines );

        } catch ( IOException e ) {
            e.printStackTrace();
        }
        finally{
            try{
                fileWriter.flush();
                fileWriter.close();
                printer.close();
            } catch ( IOException | NullPointerException e ) {
                e.printStackTrace();
            }
        }
        return new File( output_name );
    }
}
