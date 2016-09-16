package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

/**
 * Manages user details and default settings
 *
 * @author Jean-Paul Labadie
 */
public class UserSettingsManager {

    private static UserSettingsManager instance;
    private static LogManager log;

    private static JSONObject general_settings;
    private static JSONObject remote_settings;

    private static String general_config_dir ;
    private static String usr_config_dir;
    private static String remote_config_dir;
    private static String local_save_dir;

    private static String current_password;

    private static final String CURREMCONFIG = "curremconfig";
    private static final String CONFIGNAME = "configname";
    private static final String USERNAME = "username";
    private static final String URL = "url";
    private static final String PORT = "port";
    private static final String JOBMANAGER = "jobmgr";
    private static final String REMDIRS = "remdirs";

    private UserSettingsManager(){

        log = LogManager.getInstance();

        general_config_dir = new File(getClass().getResource("/configs/general_settings.json").getPath()).toString();
        remote_config_dir = new File(getClass().getResource("/configs/remote_settings.json").getPath()).toString();
        System.out.println(general_config_dir);

        general_settings = readSettings(general_config_dir);

        if (general_settings != null) {
            usr_config_dir = (String)general_settings.get("usrsettingsdir");
            local_save_dir = (String)general_settings.get("localsavedir");
        }

        remote_settings = readSettings(remote_config_dir);
        if( remote_settings != null){

        }
    }

    /**
     *
     * @return UserSettingsManager Singleton
     */
    public static UserSettingsManager getInstance(){
        if(instance == null)
            instance = new UserSettingsManager();
        return instance;
    }

    /**
     *
     * @return a JSONObject containing only current remote settings information
     */
    public static JSONObject getCurrentRemoteSettings(){
        if (!remote_settings.containsKey(CURREMCONFIG))
        {
            log.error(null, null, "Failed to set Current Remote: specified remote settings not found");
            return null;
        }
        log.info(null, null, "Current Remote Settings successfully returned.");
        String current_remote_id = (String) remote_settings.get(CURREMCONFIG);
        return (JSONObject) remote_settings.get(current_remote_id);
    }

    /**
     * Sets the currently chosen remote settings.
     * Changes are made to the runtime environment and to saved configurations.
     *
     * @param settings_name the remote settings name to try and use as current remote
     */
    @SuppressWarnings("unchecked")
    public static void setCurrentRemote(String settings_name){

        if(settings_name == null) {
            log.error(null, null, "Failed to set Current Remote: given remote settings name was null");
            return;
        }
        else if (!remote_settings.containsKey(settings_name))
        {
            log.error(null, null, "Failed to set Current Remote: specified remote settings not found");
            return;
        }

        general_settings.put(CURREMCONFIG,settings_name);
        writeSettings(general_config_dir +"general_settings.json",general_settings);
        log.info(null, null, "Current Remote Settings successfully modified in runtime and local media: " + settings_name);
    }

    /**
     * Adds a new remote settings JSON to the runtime and to local media.
     * These saved settings can be used to quickly change target remote hosts in the UI.
     *
     * @param settings_name the name given to the settings, to be used as a key
     * @param usrname the user name for the given remote host
     * @param url the remote address of the remote host
     * @param port the port to use when connecting to the remote host
     * @param jobmngr the name of the jobmngr system
     * @param remote_dirs an array of Strings representing remote directories to display in the UI
     */
    @SuppressWarnings("unchecked")
    public static void addRemoteSettings(String settings_name, String usrname, String url, int port,
                                         String jobmngr, String[] remote_dirs){
        JSONObject json = new JSONObject();
        json.put(CONFIGNAME, settings_name);
        json.put(USERNAME, usrname);
        json.put(URL, url);
        json.put(PORT, port);
        json.put(JOBMANAGER, jobmngr);

        JSONArray dirs = new JSONArray();

        Collections.addAll(dirs, remote_dirs);
        json.put(REMDIRS, dirs);

        remote_settings.put(settings_name,json);
        writeSettings(remote_config_dir ,remote_settings);
        log.info(null, null, "Remote Settings successfully added to runtime and local media: " + settings_name);
    }

    /**
     * Removes saved Remote Settings from the runtime and from saved JSON
     *
     * @param settings_name the name of the remote settings to be removed, if found
     */
    public static void removeRemoteSettings(String settings_name){

        if(settings_name == null){
            log.error(null, null, "Failed to remove Remote Settings: given path was null");
            return;
        }

        try{
            JSONObject json = (JSONObject) remote_settings.remove(settings_name);
            assert json != null;
        }
        catch (Exception e){
            log.error(null, null, "Failed to remove Remote Settings: given path was null");
            return;
        }

        writeSettings(remote_config_dir ,remote_settings);
        log.info(null, null, "Remote Settings successfully removed: " + settings_name);
    }

    /**
     * @param path the local path to write to
     * @param json the json to write to the local media
     */
    private static void writeSettings(String path, JSONObject json){

        if(path == null){
            log.error(null, null, "Failed to save Settings to local file: given path was null");
            return;
        }

        try (FileWriter fw = new FileWriter(path)) {
            fw.write(json.toJSONString());
            log.info(null, null, "Settings successfully saved to local file: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(null, null, "Failed to save Settings to local file: " + path + "\nReason:\n" + e.getMessage());
        }
    }

    /**
     * Loads saved settings JSONObjects from the local disk
     * @return the loaded settings as a JSONObject, or null
     */
    private static JSONObject readSettings(String path){

        if(path == null){
            log.error(null, null, "USM: Failed to load Settings from local file: given path was null");
            return null;
        }
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(path));
            log.info(null, null, "USM: Settings successfully loaded from local file: " + path);
            return (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(null, null, "USM: Failed to load Settings from local file: " + path + "\nReason:\n" + e.getMessage());
        }
        return null;
    }

    /**
     *
     * @return the local username
     */
    public static String getUsername() {

        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            return (String) current.get(USERNAME);
        }
        return "";
    }

    /**
     *
     * @param new_username change the default username
     */
    @SuppressWarnings("unchecked")
    public static void setUsername(String new_username) {
        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            current.put(USERNAME, new_username);
        }
    }

    /**
     *
     * @return the url of the current host
     */
    public static String getCurrentServerUrl() {

        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            return (String) current.get(URL);
        }
        return "";
    }

    /**
     *
     * @return the local path where files should be saved
     */
    public static String getDefaultLocalSaveDir() {

        return local_save_dir;
    }

    /**
     *
     * @param new_dir set a new local path for where files should be saved
     */
    public static void setDefaultLocalSaveDir(String new_dir) {

        local_save_dir = new_dir;
    }

    /**
     *
     * @return the port number specified by current remote server defaults
     */
    public static Integer getCurrentServerPort() {
        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            return ((Long)current.get(PORT)).intValue();
        }
        return null;

    }

    /**
     *
     * @param port change the default remote server port number
     */
    public static void setCurrentServerPort(int port) {
        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            current.put(PORT, port);
        }
    }

    /**
     *
     * @return SHOULD return a list of all remote paths to watch, but currently returns only a single path
     */
    public static String getDefaultRemoteDirs() {

        JSONObject current = getCurrentRemoteSettings();

        if (current != null) {
            JSONArray rem_dirs = (JSONArray) current.get(REMDIRS);
            return (String)rem_dirs.get(0);
        }
        return null;
    }

    public static String getCurrentPassword() {
        return current_password;
    }

    public static void setCurrentPassword(String current_password) {
        UserSettingsManager.current_password = current_password;
    }
}
