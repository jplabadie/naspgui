//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.01 at 12:40:32 PM MST 
//


package xmlbinds.nasp_xmlbinds;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import java.util.List;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xmlbinds.nasp_xmlbinds package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NaspInputData_QNAME = new QName("", "NaspInputData");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xmlbinds.nasp_xmlbinds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NaspInputData }
     * 
     */
    public NaspInputData createNaspInputData() {

        NaspInputData naspInputData = new NaspInputData();

            Options options = new Options();
                Filters filters = new Filters();
                Reference reference = new Reference();
            options.setFilters( filters );
            options.setReference( reference );
        naspInputData.setOptions( options );

        Files files = new Files();
            List<AssemblyFolder> assemblyFolderList = files.getAssemblyFolder();
                AssemblyFolder assemblyFolder = new AssemblyFolder();
                    List<Assembly> assemblyList = assemblyFolder.getAssembly();
                        Assembly assembly = new Assembly();
                    assemblyList.add( assembly );
                assemblyFolderList.add( assemblyFolder );
            List<ReadFolder> readFolderList = files.getReadFolder();
                ReadFolder readFolder = new ReadFolder();
                    List<ReadPair> readPairList =  readFolder.getReadPair();
                        ReadPair readPair = new ReadPair();
                    readPairList.add( readPair );
                readFolderList.add( readFolder );
            List<VCFFolder> vcfFolderList = files.getVCFFolder();
                VCFFolder vcfFolder = new VCFFolder();
                    List<VCFFile> vcfFiles =  vcfFolder.getVCFFile();
                        VCFFile vcf = new VCFFile();
                    vcfFiles.add( vcf );
                vcfFolderList.add( vcfFolder );
        naspInputData.setFiles( files );

        ExternalApplications externalApplications = new ExternalApplications();
        naspInputData.setExternalApplications( externalApplications );

        return naspInputData;
    }

    /**
     * Create an instance of {@link Options }
     * 
     */
    public Options createOptions() {
        return new Options();
    }

    /**
     * Create an instance of {@link AssemblyImporter }
     * 
     */
    public AssemblyImporter createAssemblyImporter() {
        return new AssemblyImporter();
    }
    /**
     * Create an instance of {@link VCFFolder }
     *
     */
    public VCFFolder createVCFFolderType() {
        return new VCFFolder();
    }
    /**
     * Create an instance of {@link VCFFile }
     *
     */
    public VCFFile createVCFFileType() {
        return new VCFFile();
    }

    /**
     * Create an instance of {@link Alignment }
     *
     */
    public Alignment createAlignmentType() {
        return new Alignment();
    }

    /**
     * Create an instance of {@link AlignmentFolder }
     *
     */
    public AlignmentFolder createAlignmentFolderType() {
        return new AlignmentFolder();
    }

    /**
     * Create an instance of {@link Reference }
     * 
     */
    public Reference createReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link Samtools }
     * 
     */
    public Samtools createSamtools() {
        return new Samtools();
    }

    /**
     * Create an instance of {@link Aligner }
     * 
     */
    public Aligner createAligner() {
        return new Aligner();
    }

    /**
     * Create an instance of {@link AssemblyFolder }
     * 
     */
    public AssemblyFolder createAssemblyFolder() {
        return new AssemblyFolder();
    }

    /**
     * Create an instance of {@link Index }
     * 
     */
    public Index createIndex() {
        return new Index();
    }

    /**
     * Create an instance of {@link BamIndex }
     *
     */
    public BamIndex createBamIndexType() {
        return new BamIndex();
    }


    /**
     * Create an instance of {@link ReadFolder }
     * 
     */
    public ReadFolder createReadFolder() {
        return new ReadFolder();
    }

    /**
     * Create an instance of {@link Picard }
     * 
     */
    public Picard createPicard() {
        return new Picard();
    }

    /**
     * Create an instance of {@link ReadPair }
     * 
     */
    public ReadPair createReadPair() {
        return new ReadPair();
    }

    /**
     * Create an instance of {@link Filters }
     * 
     */
    public Filters createFilters() {
        return new Filters();
    }

    /**
     * Create an instance of {@link MatrixGenerator }
     * 
     */
    public MatrixGenerator createMatrixGenerator() {
        return new MatrixGenerator();
    }

    /**
     * Create an instance of {@link DupFinder }
     * 
     */
    public DupFinder createDupFinder() {
        return new DupFinder();
    }

    /**
     * Create an instance of {@link JobParameters }
     * 
     */
    public JobParameters createJobParameters() {
        return new JobParameters();
    }

    /**
     * Create an instance of {@link Files }
     * 
     */
    public Files createFiles() {
        return new Files();
    }

    /**
     * Create an instance of {@link SNPCaller }
     * 
     */
    public SNPCaller createSNPCaller() {
        return new SNPCaller();
    }

    /**
     * Create an instance of {@link Assembly }
     * 
     */
    public Assembly createAssembly() {
        return new Assembly();
    }

    /**
     * Create an instance of {@link ExternalApplications }
     * 
     */
    public ExternalApplications createExternalApplications() {
        return new ExternalApplications();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NaspInputData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NaspInputData")
    public JAXBElement<NaspInputData> createNaspInputData(NaspInputData value) {
        return new JAXBElement<NaspInputData>(_NaspInputData_QNAME, NaspInputData.class, null, value);
    }

}