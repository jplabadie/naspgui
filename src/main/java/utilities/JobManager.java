package utilities;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages/records jobs started by the user
 * Used to populate the Job Manager Pane with accurate state information
 *
 * @author Jean-Paul Labadie
 */
public class JobManager {

    private static LogManager log = LogManager.getInstance();
    private static XMLSaveLoad jslm = XMLSaveLoad.getInstance();
    private RemoteNetUtil rnm;

    private JobManager( RemoteNetUtil net_mgr ){
        rnm = net_mgr;
    }

    /**
     *
     *
     * @param nasp_xml the prepared NASP XML
     * @param usrname the username for the remote server
     * @param password the password for the remote server with given username
     * @param url the url of the remote server as a String
     * @param port the port of the remote server
     * @param remote_path the directory in which to save the uploaded xml on the remote server
     */
    public void startNewRemoteJob( File nasp_xml, String usrname, String password,
                                  String url, Integer port, String remote_path ){

        log.info(null, null, "JM: Remote Job Requested by "+ usrname +" at " +url+":"+port+" using "+remote_path);
        //JobRecord dc = new JobRecord(usrname,url, port,remote_path,remote_path );
        //saveJobRecord(dc);

        rnm.initSession(usrname,password,url,port);
        rnm.openSession();
        rnm.upload(nasp_xml,remote_path);
        rnm.runNaspJob(remote_path);
    }

    /**
     * Saves a record of an attempted NASP job request to the local system
     *
     * @param dc
     */
    @SuppressWarnings("unchecked")
    public void saveJobRecord(JobRecord dc) {
        JSONObject obj = new JSONObject();
        obj.put("User Name", dc.getUsername());
        obj.put("Timestamp", dc.getStartTimestamp());
        obj.put("Host",dc.getServer());
        obj.put("Port",dc.getPort());
        obj.put("XML Path",dc.getRemoteXmlPath());

        // try-with-resources statement based on post comment below :)
        String path = "out\\joblog\\"+ LogManager.getTimestamp()+".json";
        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());

            log.info(null, null, "JM: Job Dispatch Configuration logged to file: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(null, null, "JM: Failed to log Job Dispatch Configuration to file: " + path + "\nReason:\n" + e.getMessage());
        }
    }
}