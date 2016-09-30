package utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jean-Paul Labadie
 */
public class TransformCsv {

    /**
     * Transforms the output csv from the SNPDist program.
     * Creates a D3-ready csv variant
     *
     * @param csv the input csv in SNPDist format
     * @return the transformed csv
     */
    static File snpdistTransform( File csv ) throws IOException {
        LogManager logger = LogManager.getInstance();
        logger.info("TransformCsv", "snpdistTransform", "Attempting to open " +
                csv.getPath() + " for CSV transformation.");
        int contig_pos = 0;
        int from_pos = 1;
        int to_pos = 2;
        int agg_dist_pos = 3;

        String pre_aligned_tag = "::pre-aligned";
        String pre_called_tag = "pre-called";
        List<CSVRecord> list;
        CSVParser parser;
        boolean sliding = false;

        try {
            parser = CSVParser.parse(csv, Charset.defaultCharset(), CSVFormat
                    .DEFAULT
                    .withRecordSeparator("\n"));
            list = parser.getRecords();
        } catch (IOException e) {
            logger.info("TransformCsv", "snpdistTransform", "Failed to parse input csv " + csv.getPath());
            throw e;
        }

        String last_item = "";
        List<String> header = new ArrayList<>();
        int header_pos =0;
        for( String item : list.get(0) ){
            if( item.equalsIgnoreCase(pre_aligned_tag) ){
                String update_last =
                        header.get(header_pos-1);
                update_last += "::"+pre_aligned_tag ;
                header.set(header_pos - 1, update_last);
            }
            else if( item.equalsIgnoreCase( pre_called_tag ) ){
                String update_last = header.get( header_pos-1 );
                update_last += "::"+pre_called_tag ;
                header.set(header_pos-1,update_last);
            }
            else {
                header.add(item);
                header_pos++;
            }
        }

        String outname = "~transformed_" + csv.getName();
        FileWriter fw = new FileWriter( outname );

        CSVPrinter out = new CSVPrinter(
                fw,
                CSVFormat.DEFAULT
                .withAllowMissingColumnNames());

        // header order =  "contig", "start", "end", "samp1_aggdist", "samp2", ..., "samp_n"
        CSVRecord line2 = list.get(1);
        CSVRecord line3 = list.get(2);
        int frompos = Integer.parseInt(line2.get(from_pos));
        int midpos = Integer.parseInt(line2.get(to_pos));
        int endpos = Integer.parseInt(line3.get(from_pos));

        //int window_size = midpos - frompos;

        if (midpos > endpos) {
            sliding = true;
        }

        //get a count of how many samples are in the data
        int num_samples = header.size()- 3; // AggregateDist is treated as a sample
        System.out.println( "Samples "+ num_samples );
        ArrayList<List<String>> lines = new ArrayList<>(); // to hold sample data

        boolean first_record_skipped = false;
        boolean skip_record = false;

        for (CSVRecord rec : list) {
            // skip the first record (its the header information)
            if( ! first_record_skipped) {
                List<String> header_line = new ArrayList<>();
                header_line.add("contig");
                header_line.add("start");
                header_line.add("end");
                for( int i = agg_dist_pos; i < header.size(); i++ ) {
                    header_line.add( header.get(i) );
                }
                lines.add( header_line );
                first_record_skipped = true;
                continue;
            }

            //skip every other data row (reduce output size and handle sliding window issue)
            else if( sliding && skip_record ) {
                skip_record = false;
                continue;
            }
            else if( sliding )
                skip_record = true;

            List<String> line = new ArrayList<>();

            String contig = rec.get( contig_pos );
            String start = rec.get( from_pos );
            String end = rec.get( to_pos );

            line.add(contig);
            line.add(start);
            line.add(end);

            for ( int i=agg_dist_pos; i< rec.size();i++ ){

                String value = rec.get( i );
                line.add( value );
            }

            lines.add(line);

        }

        for( List<String> line : lines ){
            out.printRecord( line );
        }

        out.close();
        return new File( outname );
    }
}
