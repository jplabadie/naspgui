package utils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Jean-Paul Labadie
 */
public interface RemoteNetUtil {
    /**
     *
     * @param username
     * @param password
     * @param url
     * @param port
     */
    void initSession(String username, String password, String url, int port);

    /**
     * Start the session, and open relevant channels.
     * Should be called after initSession()
     */
    void openSession();

    /**
     * Close all channels and exit the session
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
     *
     * @param job_XML_remote_abs_path
     * @return
     */
    String runNaspJob(String job_XML_remote_abs_path);

    ArrayList<String> getUserJobs();

    ArrayList<String> getAllFiles(String remote_abs_path);

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

    boolean isInitialized();
}
