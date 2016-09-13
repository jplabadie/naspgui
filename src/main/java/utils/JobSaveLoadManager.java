package utils;

import qstat_xmlbinds.QstatDataType;
import xmlbinds.NaspInputData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;


/**
 * Creates XML that represents a NASP job for saving or running.
 *
 * @author Jean-Paul Labadie
 */
public class JobSaveLoadManager {

    private static JobSaveLoadManager instance;
    private static LogManager lm = LogManager.getInstance();

    private JobSaveLoadManager(){
        lm.info( null, null, "JSLM: JSLM Singleton Initialized" );
    }

    /**
     *
     * @param xml_path the absolute path to the xml file we will use to create Java objects
     * @return a populated NaspInputData object with references to related classes
     */
    @SuppressWarnings(" unchecked ")
    public static NaspInputData NaspJaxbXmlToObject(File xml_path ) {

        try {
            JAXBContext context = JAXBContext.newInstance( NaspInputData.class );
            Unmarshaller unmarshaller = context.createUnmarshaller();
            NaspInputData naspData = ( (NaspInputData) unmarshaller.unmarshal( xml_path ) );
            lm.info( null, null, "JSLM: Job XML loaded and converted to objects from: "+ xml_path.getPath() );
            return naspData;

        } catch ( JAXBException e ) {
            lm.error( null, null, "JSLM: Job XML failed to load from: " + xml_path.getPath() +
                    "\nError occured:\n" + e.getMessage() );
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param xml the xml as a string, used to create Java objects via bindings
     * @return a populated QstatDataType object with references to related classes
     */
    @SuppressWarnings(" unchecked ")
    public static QstatDataType QstatJaxbXmlToObject( String xml ) {

        try {
            JAXBContext context = JAXBContext.newInstance( QstatDataType.class );
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader st = new StringReader( xml );
            QstatDataType qstatData = ( (QstatDataType) unmarshaller.unmarshal( st ) );
            lm.info( null, null, "JSLM: QStat XML loaded and converted to objects from XML string." );
            return qstatData;

        } catch ( JAXBException e ) {
            lm.error( null, null, "JSLM: QStat XML failed to load from XML string" +
                    "\nError occured:\n" + e.getMessage() );
            e.printStackTrace();
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
    public static File jaxbObjectToXML( NaspInputData input_for_conversion, String output_path ) {

        try {
            JAXBContext context = JAXBContext.newInstance( input_for_conversion.getClass() );
            Marshaller m = context.createMarshaller();
            //for "pretty-print" XML in JAXB
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

            //Ensure correct .xml tag is added
            output_path = setFileTagsToXml( output_path );
            File outfile = new File( output_path);
            // Write to File
            m.marshal(input_for_conversion, outfile);
            lm.info( null, null, "JSLM: Job XML converted from objects and saved to XML at: " + output_path );
            return outfile;
        } catch ( JAXBException e ) {
            String temp ="";
            for( StackTraceElement x : e.getStackTrace() ){
                temp+= "\t"+ x.toString() + "\n";
            }
            lm.error(null, null, "JSLM: Job objects failed to convert or save as XML to: " + output_path +
                    "\nError occured:\n" + temp);
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Writes the current XML DOM to the disk using the filename given
     *  in the default output directory. This finished XML represents
     *  the entirety of the QstatDataType.
     *
     * @param input_for_conversion QstatDataType object which will be converted to XML
     * @param output_path the absolute path desired for the output XML
     */
    public static File jaxbQstatDataTypeToXML( QstatDataType input_for_conversion, String output_path ) {

        try {
            JAXBContext context = JAXBContext.newInstance( input_for_conversion.getClass() );
            Marshaller m = context.createMarshaller();
            //for "pretty-print" XML in JAXB
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

            //Ensure correct .xml tag is added
            output_path = setFileTagsToXml( output_path );
            File outfile = new File( output_path);
            // Write to File
            m.marshal(input_for_conversion, outfile);
            lm.info( null, null, "JSLM: Job XML converted from objects and saved to XML at: " + output_path );
            return outfile;
        } catch ( JAXBException e ) {
            String temp ="";
            for( StackTraceElement x : e.getStackTrace() ){
                temp+= "\t"+ x.toString() + "\n";
            }
            lm.error(null, null, "JSLM: Job objects failed to convert or save as XML to: " + output_path +
                    "\nError occured:\n" + temp);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Helper method to add the .xml tag to the chosen output path
     *
     * @param path the user-provided path for output
     * @return the newly edited path as a String
     */
    private static String setFileTagsToXml( String path ){
        String xmltag = ".xml";
        int done = path.lastIndexOf( xmltag );

        if( done>0 )
            return path;
        else {
            path += xmltag;
        }
        lm.info(null, null, "JSLM: .xml tag missing, was automatically added to Job XML save name: "+ path);
        return path;
    }

    /**
     *
     * @return a Singleton object instance of this class
     */
    public static JobSaveLoadManager getInstance() {
        if( instance == null )
            instance = new JobSaveLoadManager();
        return instance;
    }
}