package utils;

import com.jcraft.jsch.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * Manages basic logging via the Jsch Logger interface for SSH SFTP and EXEC actions
 *
 * @author Jean-Paul Labadie
 */
public class LogManager implements Logger{

    private static FileHandler fh;
    private static Hashtable< Integer, String > name = new Hashtable<>();
    private static LogManager instance = null;

    /**
     * Define log types
     */
    static{
        name.put( DEBUG, "DEBUG" );
        name.put( INFO, "INFO" );
        name.put( WARN, "WARNING" );
        name.put( ERROR, "SEVERE" );
    }

    protected LogManager(){

        try {
            // This block configure the logger with handler and formatter

            fh = new FileHandler("log-"+ getDate()+".txt",true);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter( formatter );

            // the following statement is used to log any messages
            this.info( null, null, "LM: Logger initialized." );

        } catch ( SecurityException | IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Check what level of logging has been enabled.
     * !!! Unimplemented !!!
     * @param level level of logging to check if enabled
     * @return true if logging at that level is enabled, false otherwise
     */
    public boolean isEnabled( int level ){
        return true;
    }

    /**
     * Logs a given message at the specified level (of those defined in the constructor).
     * @param level the log level (type) {DEBUG,INFO,WARN,ERROR,FATAL}
     * @param message the log message for the action being logged
     */
    public void log( int level, String message ){

        message = getTimestamp() + " :- " + message;

        LogRecord record = new LogRecord( Level.parse( name.get( level )),message );
        fh.publish( record );
        System.err.print( name.get( level ) + ": " ); //echo the message to System.err
        System.err.println( message );
    }

    /**
     * Wrapper for INFO logs
     * @param sourceClass the class in which the log was called
     * @param sourceMethod the method in the class in which the log was called
     * @param message log message for INFO events
     */
    public void info(String sourceClass, String sourceMethod, String message){

        String origin = " (Class: " + sourceClass + "," + " Method: " + sourceMethod +")";
        log( INFO, message + origin );
    }

    /**
     * Wrapper for WARN logs
     * @param sourceClass the class in which the log was called
     * @param sourceMethod the method in the class in which the log was called
     * @param message log message for WARN events
     */
    public void warn( String sourceClass, String sourceMethod, String message ){

        String origin= " (Class: " + sourceClass + "," + " Method: " + sourceMethod +")";
        log( WARN, message + origin );
    }

    /**
     * Wrapper for ERROR logs
     * @param sourceClass the class in which the log was called
     * @param sourceMethod the method in the class in which the log was called
     * @param message log messages for ERROR events
     */
    public void error( String sourceClass, String sourceMethod, String message ){

        String origin= " (Class: " + sourceClass + "," + " Method: " + sourceMethod +")";
        log( ERROR, message + origin );
    }

    /**
     *
     * @return the current time as a formatted string
     */
    static String getTimestamp(){

        Date date = new Date( System.currentTimeMillis() );
        DateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy-HH_mm_ss" );

        return formatter.format( date );
    }

    protected static String getDate(){

        Date date = new Date( System.currentTimeMillis() );
        DateFormat formatter = new SimpleDateFormat( "dd-MMM-YYYY" );

        return formatter.format( date );
    }

    public static LogManager getInstance() {
        if( instance == null ) {
            instance = new LogManager();
        }
        return instance;
    }
}
