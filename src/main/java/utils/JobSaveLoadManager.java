package utils;

import xmlbinds.NaspInputData;
import xmlbinds.ObjectFactory;

import javax.xml.bind.*;
import java.io.File;


/**
 * Creates XML that represents a NASP job for saving or running.
 *
 * @author Jean-Paul Labadie
 */
public class JobSaveLoadManager {

    private static JobSaveLoadManager instance;
    private static LogManager lm = LogManager.getInstance();

    private JobSaveLoadManager(){
        lm.info("JSLM: JSLM Singleton Initialized");
    }

    /**
     *
      * @param xml_path the absolute path to the xml file we will use to create Java objects
     * @return a populated NaspInputData object with references to related classes
     */
    @SuppressWarnings(" unchecked ")
    public static NaspInputData jaxbXMLToObject(File xml_path) {
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            NaspInputData naspData = ((JAXBElement<NaspInputData>) unmarshaller.unmarshal(xml_path)).getValue();
            lm.info("JSLM: Job XML loaded and converted to objects from: "+xml_path.getPath());
            return naspData;
        } catch (JAXBException e) {
            lm.error("JSLM: Job XML failed to load from: " + xml_path.getPath() + "\nError occured:\n" + e.getMessage());
        }
        return null;
    }

    /**
     *  Writes the current XML DOM to the disk using the filename given
     *  in the default output directory. This finished XML represents
     *  the entirety of the NASP tool job request, and can be sent to
     *  a remote service running NASP to begin a new job.
     *
     * @param input_for_conversion NaspInputData object which will be converted to XML for output to NASP
     * @param output_path the absolute path desired for the output XML
     */
    public static void jaxbObjectToXML(NaspInputData input_for_conversion, String output_path) {
        try {
            JAXBContext context = JAXBContext.newInstance(NaspInputData.class);
            Marshaller m = context.createMarshaller();
            //for "pretty-print" XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Ensure correct .xml tag is added
            output_path = setFileTagsToXml(output_path);
            // Write to File
            m.marshal(input_for_conversion, new File(output_path));
            lm.info("JSLM: Job XML converted from objects and saved to XML at: "+output_path);
        } catch (JAXBException e) {
            lm.error("JSLM: Job objects failed to convert or save as XML to: " + output_path + "\nError occured:\n"
                    + e.getMessage());
        }
    }

    /**
     * Helper method to add the .xml tag to the chosen output path
     *
     * @param path the user-provided path for output
     * @return the newly edited path as a String
     */
    private static String setFileTagsToXml(String path){
        String xmltag = ".xml";
        int done = path.lastIndexOf(xmltag);
        int dot = path.lastIndexOf(".");

        if(done>=0) return path;

        else if(dot>=0){
            path = path.substring(0,dot);
            path += xmltag;
        }
        lm.info("JSLM: .xml tag missing, was automatically added to Job XML save name: "+ path);
        return path;
    }

    /**
     *
     * @return a Singleton object instance of this class
     */
    public static JobSaveLoadManager getInstance() {
        if(instance == null)
            instance = new JobSaveLoadManager();
        return instance;
    }
}
