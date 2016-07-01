package widgets;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import utils.JobRecord;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

/**
 * Project naspgui.
 * Created by jlabadie on 6/29/16.
 *
 * @author jlabadie
 */
public class JobTab extends Tab {

    private JobRecord job_rec;

    private OptionsPane optspane;
    private FilesPane filespane;
    private ExternalApplicationsPane xappspane;
    private NaspInputData NASP_DATA;

    private Button start_job = new Button();

    private ObjectFactory OF = new ObjectFactory();


    /**
     * Creates a job tab window from existing NaspData
     * @param input NaspInputData initialized from XML
     */
    JobTab(NaspInputData input) {
        NASP_DATA = input;
    }

    /**
     *  Creates a blank job and initializes new NASP data
     */
    JobTab() {
        NASP_DATA = OF.createNaspInputData();
        optspane = new OptionsPane(NASP_DATA.getOptions());
        filespane = new FilesPane(NASP_DATA.getFiles());
        xappspane = new ExternalApplicationsPane(NASP_DATA.getExternalApplications());

    }


}