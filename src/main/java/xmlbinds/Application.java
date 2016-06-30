package xmlbinds;

/**
 * Project naspgui.
 * Created by jlabadie on 6/30/16.
 *
 * @author jlabadie
 */
public interface Application {

    JobParameters getJobParameters();
    void setJobParameters( JobParameters params );

    String getName();
    void setName( String name );

    String getPath();
    void setPath( String path );

    String getAdditionalArguments();
    void setAdditionalArguments( String args );


}
