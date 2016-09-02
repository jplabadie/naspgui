package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs a finalized Job XML on NASP.
 * Jobs may be run locally or on remote environments.
 *
 * @author Jean-Paul Labadie
 */
public class JobTrackingManager {

    private static LogManager log = LogManager.getInstance();
    private static JobSaveLoadManager jslm = JobSaveLoadManager.getInstance();
    private RemoteNetUtil rnm;
    private JSONParser parser = new JSONParser();
    private ArrayList<JobRecord> jobList = new ArrayList<>();

    private JobTrackingManager(RemoteNetUtil net_mgr){

        rnm = net_mgr;
        //TODO: Remove hardcoded locations, use config file
        loadJobList( "out\\jobs\\jobrecords.json" );
    }

    public ArrayList<JobRecord> loadJobList( String path_to_jobs_json ){

        JSONArray ja= null;
        try{
            Object obj = parser.parse( new FileReader( path_to_jobs_json ));
            ja = (JSONArray) obj;
        }
        catch ( ParseException | IOException e ){
            e.printStackTrace();
        }

        if( ja != null ){

            ArrayList<JobRecord> records = new ArrayList<>();
            for( Object o : ja ) {
                JSONObject jo = (JSONObject) o;

                JobRecord jr = new JobRecord(
                        (String) jo.get("username"),
                        (String) jo.get("host"),
                        (Integer) jo.get("port"),
                        (String) jo.get("rempath"),
                        (String) jo.get("locpath"),
                        ((List<String>) jo.get("jobids"))
                );
                records.add( jr );
            }
            return records;
        }
        //TODO: Logging and error handling
        return null;
    }

    public void updateRemoteJobRecord( File job_records_json, String directory ){
        try{
            log.info( null, null, "JM: Update job records for " + rnm.getUsername() + " at "
                    + rnm.getHost() + ":" + rnm.getPort() + " with json saved to: " + directory );
            rnm.upload( job_records_json, directory );
            log.info( null, null, "JM: Update job records successful." );
        }
        catch ( Exception e ){
            log.error( getClass().toString(), "updateRemoteJobRecord" , " Update job record failed." );
        }
    }

    /**
     * TODO: Unfinished
     * Saves a record of a new Job and all its details to the local and remote file systems
     * Used to populate and guide views of previous and new jobs for presenting views that make sense
     *
     * @param jobs
     */
    @SuppressWarnings("unchecked")
    public void saveJobList( ArrayList<JobRecord> jobs ) {

        JSONArray jobrecords = new JSONArray();
        for( JobRecord record : jobs ) {

            JSONObject jsonRecord = new JSONObject();
            jsonRecord.put( "username", record.getUsername() );
            jsonRecord.put( "timestamp", record.getStartTimestamp() );
            jsonRecord.put( "host", record.getServer() );
            jsonRecord.put( "port", record.getPort() );
            jsonRecord.put( "rempath", record.getRemoteXmlPath() );
            jsonRecord.put( "locpath", record.getLocalXmlPath() );

            JSONArray jobids = new JSONArray();
            jobids.addAll( record.getJobIds() );
            jsonRecord.put( "jobids", jobids );

            jobrecords.add( jsonRecord );
        }

        // try-with-resources statement based on post comment below :)
        String path = "out\\jobs\\jobrecords.json";
        try ( FileWriter file = new FileWriter( path ) ) {
            file.write( jobrecords.toJSONString() );
            log.info( null, null, "JM: Job Dispatch Configuration logged to file: " + path );

        } catch ( IOException e ) {
            e.printStackTrace();
            log.error( null, null, "JM: Failed to log Job Dispatch Configuration to file: " + path + "\nReason:\n" + e.getMessage());
        }
    }

    public JobRecord loadJobRecord( String path){
        JobRecord jr;
        JSONObject jo = null;
        try{
            Object obj = parser.parse( new FileReader( path ));
            jo = (JSONObject) obj;
        }
        catch ( ParseException | IOException e ){
            e.printStackTrace();
        }

        if( jo != null ){
            jr = new JobRecord(
                    (String)  jo.get("username"),
                    (String)  jo.get("host"),
                    (Integer) jo.get("port"),
                    (String)  jo.get("rempath"),
                    (String)  jo.get("locpath"),
                    ((List<String>) jo.get("jobids"))
            );

            return jr;
        }
        return null;
    }
}
