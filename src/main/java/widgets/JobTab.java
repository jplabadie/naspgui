package widgets;

import javafx.scene.control.Tab;
import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

/**
 * Project naspgui.
 * Created by jlabadie on 6/29/16.
 *
 * @author jlabadie
 */
public class JobTab extends Tab {

    private OptionsPane optspane;
    private FilesPane filespane;
    private ExternalApplicationsPane xappspane;
    private NaspInputData naspInputData;

    private ObjectFactory OF = new ObjectFactory();

    JobPane( NaspInputData input ){
    }

    JobPane(){
        naspInputData = OF.createNaspInputDataType();


    }
}
