package utils;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Vector;

/**
 * @author Jean-Paul Labadie
 */
public class DefaultRemoteNetUtil implements RemoteNetUtil {

    private Session session;
    private ChannelSftp sftp_channel;
    private ChannelExec exec_channel;
    private BufferedReader sftp_in;
    private OutputStream sftp_out;
    private BufferedReader exec_in;
    private OutputStream exec_out;


    private static DefaultRemoteNetUtil instance = null;
    private LogManager log = LogManager.getInstance();
    private JSch jsch = new JSch();

    /**
     * Initialize the logger and create a new Jsch object
     */
    public DefaultRemoteNetUtil() {
        JSch.setLogger(log);
    }

    /**
     *
     * @param username the username for the remote server
     * @param password the password used for the remote server with given username
     * @param url the url of the remote server
     * @param port the port of the remote server (typically 22 for ssh)
     */
    public void initSession(String username, String password, String url, int port) {
        try {
            session = jsch.getSession(username, url, port);
            session.setUserInfo(new UserInfo() {
                public String getPassphrase() {
                    return password;
                }

                public String getPassword() {
                    return password;
                }

                public boolean promptPassword(String s) {
                    return true;
                }

                public boolean promptPassphrase(String s) {
                    return true;
                }

                public boolean promptYesNo(String s) {
                    return true;
                }

                public void showMessage(String s) {

                }
            });

        } catch (JSchException e1) {
            log.error("Unable to initialize a new session: \n" + e1.getMessage());
        }
    }

    /**
     * Start the session, and open relevant channels.
     * Should be called after initSession()
     *
     */
    public void openSession(){
        log.info("RNU: Attempting to Open Remote Session and related connections.");

        try {
            session.connect();
            log.info("RNU: Open Session - Session connected successfully.");
        } catch (JSchException e) {
            log.error("NM - Unable to Open the Session: \n" + e.getMessage());
            return;
        }

        try {

            Channel sftpchannel=session.openChannel("sftp");
            sftpchannel.connect();
            sftp_channel=(ChannelSftp)sftpchannel;

            Channel execchannel=session.openChannel("exec");
            execchannel.connect();
            exec_channel=(ChannelExec)execchannel;

            log.info("RNU: Open Session - SFTP/EXEC Channels connected successfully.");
        } catch (JSchException e) {
            log.error("NM - Unable to Open and Connect to SFTP/EXEC Channels: \n" + e.getMessage());
            session.disconnect();
            log.warn("NM - Session failed : Closing Session.");
            return;
        }

        try {
            (exec_channel).setErrStream(System.err);

            exec_in = new BufferedReader(new InputStreamReader(exec_channel.getInputStream()));
            log.info("RNU: Open Session - I/O Streams connected successfully.");
            log.info("RNU: Open Session - SFTP channel at directory: "+sftp_channel.pwd());
        } catch (IOException e) {
            log.error("NM - Unable to Get SFTP/EXEC I/O Streams: \n" + e.getMessage());
            session.disconnect();
            log.warn("NM - Session failed : Closing Session.");
        } catch (SftpException e) {
            log.error("RNU: Unable to Get SFTP PWD \n" + e.getMessage());
        }
    }

    /**
     * Close all channels and exit the session
     */
    public void closeSession(){

        try{
            session.isConnected();
        }
        catch (NullPointerException e){
            log.warn("NM - Could Not Close Session: the Session instance was null.");
            return;
        }
        if(session.isConnected()) {

            sftp_channel.disconnect();
            exec_channel.disconnect();
            log.info("NM - Session Channels Disconnected." );

            session.disconnect();
            log.info("NM - Session Disconnected." );
        }
    }

    /**
     *
     * @return the current session object
     */
    public Session getSession(){
        return session;
    }

    /**
     * Sends a file over SFTP to the remote service using the current Session
     *
     * @param file the File to be uploaded
     * @param abs_remote_path the absolute path to the file on the remote machine
     */
    public void upload(File file, String abs_remote_path){
        log.info("RNU: Attempting upload: "+ file.getName()+ " at "+ abs_remote_path);

        if (sftp_channel == null){
            log.error("NM - Upload Step Fail: SFTP Channel is null. Cannot upload.");
            return;
        }
        else if(!file.exists()){
            log.error("NM - Upload Step Fail: File for SFTP Upload is null. Cannot upload.");
            return;
        }

        if(!isRemoteDir(abs_remote_path)){
            log.error("NM - Upload Step Fail: Connection failed or remote path " +
                    "is not a valid path for SFTP Upload. Cannot upload.");
            return;
        }

        try {
            log.info("RNU: Upload - Attempting: cd " + abs_remote_path);
                sftp_channel.cd(abs_remote_path); //cd to the absolute directory


        }
        catch (SftpException e){
            //the directory cannot be visited
            log.error("RNU: Upload Step Fail: cd " + abs_remote_path +": Failed. Insufficient Permissions? \n"
                    + e.getMessage());

        }

        try {
            try {
                sftp_channel.put(new FileInputStream(file), file.getName());
                log.info("RNU: Upload Step Successful: file uploaded to " + abs_remote_path );
            } catch (FileNotFoundException e) {
                log.error(e.getMessage());
                log.error("RNU: Upload Step Fail: File Not Found. " +
                        "Insufficient Permissions? \n" + e.getMessage());
            }

        } catch (SftpException e) {
            log.error("NM - Upload Step Fail: SFTP Failed. " +
                    "Insufficient Permissions? \n" + e.getMessage());
        }
    }

    /**
     * Downloads a file over SFTP from the remote directory to the local directory
     *
     * @param abs_remote_path the absolute path to the file/dir on the remote machine
     * @param abs_local_path the absoulte path to the local directory
     */
    public void download(String abs_remote_path, String abs_local_path) {
        try {
            int fileCount = 0;
            sftp_channel.lcd(abs_local_path);
            log.info("lcd " + sftp_channel.lpwd());

            // Get a listing of the remote directory
            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> list = sftp_channel.ls(".");
            log.info("ls .");

            // iterate through objects in list, identifying specific file names
            for (ChannelSftp.LsEntry oListItem : list) {
                // output each item from directory listing for logs
                log.info(oListItem.toString());

                // If it is a file (not a directory)
                if (!oListItem.getAttrs().isDir()) {

                    // Grab the remote file ([remote filename], [local path/filename to write file to])
                    log.info("get " + oListItem.getFilename());
                    sftp_channel.get(oListItem.getFilename(), oListItem.getFilename());  // while testing, disable this or all of your test files will be grabbed

                    fileCount++;

                    // Delete remote file
                    //c.rm(oListItem.getFilename());  // Deleting remote files is not required in every situation.
                }
            }

            // Report files grabbed to log
            if (fileCount == 0) {
                log.info("Found no new files to grab.");
            } else {
                log.info("Retrieved " + fileCount + " new files.");
            }
        } catch(SftpException e) {
            log.warn(e.toString());
        } finally {
            // disconnect session.  If this is not done, the job will hang and leave log files locked
            session.disconnect();
            log.info("Session Closed");
        }
    }

    /**
     *
     * @param job_XML_abs_path the absolute path to the XML job file on the remote server
     */
    public String runNaspJob(String job_XML_abs_path) {
        String runpath = job_XML_abs_path.substring(0,job_XML_abs_path.lastIndexOf('/'));
        try {
            assert exec_channel != null;
            exec_channel.setCommand("cd "+ runpath);
            exec_channel.setCommand("module load nasp"); //main nasp tool
            log.info("RNU: Run command - module load nasp");
            exec_channel.setCommand("module load tnorth"); //main the tnorth tool [what does this do??]
            log.info("RNU: Run command - module load tnorth");
            exec_channel.setCommand("nasp --config " + job_XML_abs_path); //run nasp with the xml
            log.info("RNU: Run command - nasp --config " + job_XML_abs_path);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("RNU: Run NASP failed: " + e.getMessage());
        }

        //dreams
        return "The ID of the started Job";
    }

    /**
     *
     * @return
     */
    @Override
    public String getUserJobs() {
        exec_channel.setCommand("qstat -au" + getUsername() );
        String output = "";
        try {
            while(exec_in.ready()) {
                output += exec_in.readLine() + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


    /**
     *  Tests to see if a specified file exists and is a file on the remote server
     *
     * @param remote_file_abs_path the absolute path to the file on the remote server
     * @return true if the file exists on the remote system, false otherwise
     */
    public boolean isRemoteFile(String remote_file_abs_path){

        InputStream exec_in;
        int exec_status =-1;
        assert exec_channel != null;
        try {
            exec_in = exec_channel.getInputStream();
            exec_status = exec_in.read();
        } catch (IOException e) {
            log.error("RN: Could not determine if remote file exists. Failed due to:\n" +e.getMessage());
            return false;
        }
        exec_channel.setCommand("test -f " + remote_file_abs_path);

        return exec_status != -1; //returns false only if the remote system returned -1
    }

    /**
     *  Tests to see if a specified directory exists and is a directory on the remote server
     *
     * @param remote_dir_abs_path the absolute path to the file on the remote server
     * @return true if the directory exists on the remote system, false otherwise
     */
    public boolean isRemoteDir(String remote_dir_abs_path) {
        int exec_status;
        log.info("RNU: Checking for remote directory - " + remote_dir_abs_path);
        if (exec_channel == null)
            log.error("RNU: Cannot check remote dir - Exec channel is null.");
        log.info("RNU: Checking for remote directory - Running test -d (remote path)...");
        exec_channel.setCommand("test -d " + remote_dir_abs_path);
        exec_status = exec_channel.getExitStatus();
        log.info("RNU: Checking for remote directory - Exec returned: "+exec_status);
        return exec_status != -1;
    }

    public String getUsername(){
        if(isInitialized())
            return session.getUserName();
        log.warn("RNU: Cannot get username, session is not active.");
        return null;
    }

    public int getPort(){
        if(isInitialized())
            return session.getPort();
        log.warn("RN: Cannot get port, session is not active.");
        return -1;
    }

    public String getHost(){
        if(isInitialized())
            return session.getHost();
        log.warn("RN: Cannot get hostname, session is not active.");
        return null;
    }

    public boolean isInitialized() {
        return session != null ;

    }

    public boolean isConnected() {
        return session.isConnected();
    }
}



