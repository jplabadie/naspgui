package utils;

import qstat_xmlbinds.QstatDataType;

import java.io.File;
import java.util.List;

/**
 * Interface for classes which provide for interactions with the remote cluster where NASP will run
 *
 * @author Jean-Paul Labadie
 */
public interface RemoteNetUtil {

    /**
     * Initializes required variables for the remote networking session
     *
     * @param username defines the username
     * @param password defines the password
     * @param url defines the url of the remote server
     * @param port defines the port to use on the remote server
     */
    void initSession(String username, String password, String url, int port);

    /**
     * Start the session, and open relevant channels.
     * Should be called after initSession()
     */
    void openSession();

    /**
     * Close all channels and exit the session
     * Should only be called when openSession has been successfully called
     * and the user intends to disconnect from the service
     */
    void closeSession();

    /**
     * Sends a file over SFTP to the remote service using the current Session
     *
     * @param file the File to be uploaded
     * @param abs_remote_path the absolute path to the file on the remote machine
     */
    void upload(File file, String abs_remote_path);

    /**
     * Downloads a file over SFTP from the remote directory to the local directory
     *
     * @param abs_remote_path the absolute path to the file/dir on the remote machine
     * @param abs_local_path the absoulte path to the local directory
     */
    void download(String abs_remote_path, String abs_local_path);

    /**
     * Attempts to start the NASP job on the remote machine using the xml specified at the remote location
     *
     * @param job_XML_remote_abs_path the path to the uploaded xml for NASP to execute on
     * @return true if successful, false otherwise
     */
    boolean runNaspJob(String job_XML_remote_abs_path);

    /**
     * @return a list of all jobs currently being managed by the job manager
     */
    List<String> getUserJobs();


    /**
     * @return a list of jobs currently being managed by the job manager for a specific username
     */
    List<String> getUserJobs( String username );

    QstatDataType getJobsXml( );

    /**
     * Returns a list representing all files and symbolic links found in the specified directory
     * and all sub-directories
     *
     * @param remote_abs_path the directory to begin searching for files in
     * @return
     */
    List<String> getAllFiles(String remote_abs_path);

    /**
     * Requests that the job manager kill a specific range of job ids IF those jobs belong to the current user
     * If a job id in a range does not belong to the given user, it should be ignored
     *
     * @param lowerJobId the inclusive lowest job-id in the range to be terminated
     * @param upperJobId the inclusive highest job-id in the range to be terminated
     */
    void killJob( int lowerJobId, int upperJobId );

    /**
     *  Tests to see if a specified file exists and is a file on the remote server
     *
     * @param remote_file_abs_path the absolute path to the file on the remote server
     */
    boolean isRemoteFile(String remote_file_abs_path);

    /**
     *  Tests to see if a specified directory exists and is a directory on the remote server
     *
     * @param remote_dir_abs_path the absolute path to the file on the remote server
     * @return true if the remote directory exists, false if otherwise
     */
    boolean isRemoteDir(String remote_dir_abs_path);

    /**
     *
     * @return true if required variables have been initialized with initSession
     */
    boolean isInitialized();

    String getUsername();

    String getHost();

    Integer getPort();
}
