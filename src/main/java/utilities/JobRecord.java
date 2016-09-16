package utilities;

import java.util.List;

/**
 * Simple object for holding details regarding a job submission
 *
 * @author Jean-Paul Labadie
 */
public class JobRecord {
    private String username;
    private String server;
    private Integer port;
    private String remote_xml_path;
    private String local_xml_path;
    private String start_timestamp;
    private String end_timestamp;
    private boolean completed;
    private List<String> jobIds;

    public JobRecord(String username, String server, Integer port, String remote_xml_path,
                     String local_xml_path, List<String> jobids) {
        this.username = username;
        this.server = server;
        this.port = port;
        this.remote_xml_path = remote_xml_path;
        this.local_xml_path = local_xml_path;
        this.jobIds = jobids;
    }

    public List getJobIds(){
        return jobIds;
    }

    public void setJobIds( List<String> jobids ){
        jobIds = jobids;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStartTimestamp() {
        return start_timestamp;
    }

    public void setStartTimestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public String getEndTimestamp() {
        return end_timestamp;
    }

    public void setEndTimestamp(String end_timestamp) {
        this.end_timestamp = end_timestamp;
    }


    public String getLocalXmlPath() {
        return local_xml_path;
    }

    public void setLocalXmlPath(String local_xml_path) {
        this.local_xml_path = local_xml_path;
    }

    public String getRemoteXmlPath() {
        return remote_xml_path;
    }

    public void setRemoteXmlPath(String remote_xml_path) {
        this.remote_xml_path = remote_xml_path;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
