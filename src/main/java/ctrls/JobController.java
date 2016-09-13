package ctrls;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import utils.*;
import xmlbinds.NaspInputData;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Project naspgui.
 * Created by jlabadie on 6/13/16.
 *
 * @author jlabadie
 */
public class JobController implements Initializable{

    @FXML
    public AnchorPane jobAnchorPane;
    public TitledPane assemblyImporterPane;
    public TitledPane requiredApplicationsPane;
    public TitledPane dupFinderPane;

    /**
     *
     * @param location  The location used to resolve relative paths for the root object, or null.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            AbstractRemoteNetUtilFactory arnuf = RemoteNetUtilFactoryMaker.getFactory();
            RemoteNetUtil REM_NETWORK = arnuf.createRemoteNetUtil();

            Integer port = UserSettingsManager.getCurrentServerPort();

            REM_NETWORK.initSession(UserSettingsManager.getUsername(), UserSettingsManager.getCurrentPassword(),
                    UserSettingsManager.getCurrentServerUrl(), port);
            REM_NETWORK.openSession();
        }
        catch ( Exception e ){
            e.printStackTrace(); //TODO: Log failure
        }

    }

    /**
     * Initialize the view to the Default Job setting (a pre-defined xml)
     */
    public void loadDefaultJob(){
        loadJobView(null);
    }

    /**
     *  Initialize the view to a saved job (a previously saved xml)
     * @param path the path to the saved xml (local)
     */
    public void loadSavedJob(String path){
        File loadedJobXml = new File (path);
        loadJobView(loadedJobXml);
    }

    private void loadJobView(File jobXML){
        NaspInputData NASP_DATA;
        if( jobXML == null ) {
            /**
             * Create the form using a default job template
             */
            File default_job = new File(getClass().getResource("defaultJob.xml").getPath());
            NASP_DATA = XMLSaveLoad.NaspJaxbXmlToObject(default_job);
        }
        else {
            NASP_DATA = XMLSaveLoad.NaspJaxbXmlToObject(jobXML);
        }




    }



}
