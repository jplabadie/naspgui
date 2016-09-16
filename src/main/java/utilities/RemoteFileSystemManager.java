package utilities;

import com.pastdev.jsch.DefaultSessionFactory;
import com.pastdev.jsch.nio.file.UnixSshFileSystemProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Provides a Java FileSystem which is defined by calls through SSH to a remote service
 * Primarily intended as a source of Files/Paths for the custom file/directory tree
 *
 * @author Jean-Paul Labadie
 */
public class RemoteFileSystemManager {

    private static RemoteFileSystemManager instance ;
    private FileSystem sshfs;
    private LogManager log;

    private RemoteFileSystemManager(){
        log = LogManager.getInstance();
    }
    /**
     *
     * @param usrname a valid username on the remote service
     * @param pass the password for the username given
     * @param hosturl the url of the remote service
     * @param port the port to be used for ssh (typically = 22)
     */
    public void init(String usrname, String pass, String hosturl, int port) {
        String str = "";

        DefaultSessionFactory dsf = new DefaultSessionFactory(usrname, hosturl, port);
        dsf.setPassword(pass);

        Map<String, Object> environment = new HashMap<String, Object>();
        environment.put("defaultSessionFactory", dsf);

        //Start the session URI looking in the user's home directory
        URI uri;
        try {
            uri = new URI("ssh.unix://" + dsf.getUsername() + "@" + dsf.getHostname() + ":"
                    + dsf.getPort() + "/home/" + dsf.getUsername());
        } catch (URISyntaxException e) {
            log.error(null, null, "RFSM - Init Failed: Cannot Initialize - Bad URI: \n" + e.getMessage());
            return;
        }

        // Try to start the FileSystem with the default providers
        try {
            sshfs = FileSystems.newFileSystem(uri, environment);
        } catch (IOException e) {
            log.warn(null, null, "RFSM - Init Step Failed: Could Not Load Default Provider. \n" + e.getMessage());
            log.info(null, null, "RFSM - Init Step Recovery: Attempting to Load UNIX Provider.");

            try {
                FileSystems.getFileSystem(uri).close();
            } catch (IOException ioe) {
                log.info(null, null, "RFSM - Optional Init Recovery Step Failed: " +
                        "Cannot Close FileSystem - Never Initialized: \n" + ioe.getMessage());
            }

            // Try to start the FileSystem explicitly using the jsch-nio UnixSSH Provider
            try {
                UnixSshFileSystemProvider ussh = new UnixSshFileSystemProvider();
                sshfs = FileSystems.newFileSystem(uri, environment, ussh.getClass().getClassLoader());
                log.info(null, null, "RFSM - Init Recovery Successful.");

            } catch (IOException ioe2) {
                log.error(null, null, "RFSM - Init Recovery Failed: Loading UNIX Provider Failed or Other Failure:" +
                        " \n" + ioe2.getMessage());

            }
        }
    }
    /**
     *
     * @param requested_path the path on the remote machine to explore
     * @return an iterator of Paths found in the remote directory
     * @throws IOException
     */
    public DirectoryStream<Path> getDirectory(String requested_path) throws IOException {

        if(sshfs == null || !sshfs.isOpen()){
            throw new ClosedDirectoryStreamException();
        }

        Path path = sshfs.getPath( requested_path ); // refers to an absolute remote path
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            return ds;
        }
    }

    /**
     *
     * @return
     */
    public Path getRootAsPath() throws IOException {
        if(sshfs == null || !sshfs.isOpen()){
            throw new ClosedDirectoryStreamException();
        }

        Path root_path= null;
        for(Path path :sshfs.getRootDirectories()) // refers to an absolute remote path
        {
            root_path = path;
        }

        return root_path;
    }

    /**
     *
     * @param dir the absolute path, as a String, of the desired directory
     * @return a Path object representing the directory, or null
     * @throws IOException
     */
    public Path getDirAsPath(String dir) throws IOException{
        if(sshfs == null || !sshfs.isOpen()){
            throw new ClosedDirectoryStreamException();        }

        Path specific_path = sshfs.getPath(dir);

        if(specific_path != null)
            log.info(null, null, "RFSM: Remote path found: " + specific_path.toString());
        else
            log.error(null, null, "RFSM: Remote path root unset");

        return specific_path;
    }

    public Path getParentOfDir( String dir ){

        String parent_dir = dir.substring( 0, dir.lastIndexOf("/") );
        if( parent_dir.length() == 0 ) parent_dir = "/"; // parent is root
        Path parent = sshfs.getPath( parent_dir );
        return parent;

    }

    /**
     *
     * @return the state of the remote FileSystem, true for connected, false for disconnected
     */
    public boolean isConnected() {
        if(!sshfs.isOpen())
            return false;
        try {
            sshfs.getPath("/").getFileSystem().isOpen();
            return true;
        }
        catch(NullPointerException npe){
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static RemoteFileSystemManager getInstance(){
        if(instance == null)
            instance = new RemoteFileSystemManager();
        return instance;
    }

    /**
     *
     */
    public void close() {
        try {
            sshfs.close();
        } catch (IOException e) {
            log.info(null, null, "FileSystemManager - Closing FileSystem Failed: \n" + e.getMessage());
        }
    }
}