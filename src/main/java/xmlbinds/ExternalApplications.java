
package xmlbinds;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ExternalApplicationsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExternalApplicationsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{}IndexType"/>
 *         &lt;element name="MatrixGenerator" type="{}MatrixGeneratorType"/>
 *         &lt;element name="Picard" type="{}PicardType"/>
 *         &lt;element name="Samtools" type="{}SamtoolsType"/>
 *         &lt;element name="DupFinder" type="{}DupFinderType"/>
 *         &lt;element name="AssemblyImporter" type="{}AssemblyImporterType"/>
 *         &lt;element name="Aligner" type="{}AlignerType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SNPCaller" type="{}SNPCallerType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalApplications", propOrder = {
    "index",
    "matrixGenerator",
    "picard",
    "samtools",
    "dupFinder",
    "assemblyImporter",
    "aligner",
    "snpCaller"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class ExternalApplications {

    @XmlElement(name = "Index", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Index index;
    @XmlElement(name = "MatrixGenerator", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected MatrixGenerator matrixGenerator;
    @XmlElement(name = "Picard", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Picard picard;
    @XmlElement(name = "Samtools", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Samtools samtools;
    @XmlElement(name = "DupFinder", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected DupFinder dupFinder;
    @XmlElement(name = "AssemblyImporter", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected AssemblyImporter assemblyImporter;
    @XmlElement(name = "Aligner")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Aligner> aligner;
    @XmlElement(name = "SNPCaller")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<SNPCaller> snpCaller;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link Index }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Index getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link Index }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setIndex(Index value) {
        this.index = value;
    }

    /**
     * Gets the value of the matrixGenerator property.
     * 
     * @return
     *     possible object is
     *     {@link MatrixGenerator }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public MatrixGenerator getMatrixGenerator() {
        return matrixGenerator;
    }

    /**
     * Sets the value of the matrixGenerator property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatrixGenerator }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setMatrixGenerator(MatrixGenerator value) {
        this.matrixGenerator = value;
    }

    /**
     * Gets the value of the picard property.
     * 
     * @return
     *     possible object is
     *     {@link Picard }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Picard getPicard() {
        return picard;
    }

    /**
     * Sets the value of the picard property.
     * 
     * @param value
     *     allowed object is
     *     {@link Picard }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPicard(Picard value) {
        this.picard = value;
    }

    /**
     * Gets the value of the samtools property.
     * 
     * @return
     *     possible object is
     *     {@link Samtools }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Samtools getSamtools() {
        return samtools;
    }

    /**
     * Sets the value of the samtools property.
     * 
     * @param value
     *     allowed object is
     *     {@link Samtools }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setSamtools(Samtools value) {
        this.samtools = value;
    }

    /**
     * Gets the value of the dupFinder property.
     * 
     * @return
     *     possible object is
     *     {@link DupFinder }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public DupFinder getDupFinder() {
        return dupFinder;
    }

    /**
     * Sets the value of the dupFinder property.
     * 
     * @param value
     *     allowed object is
     *     {@link DupFinder }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDupFinder(DupFinder value) {
        this.dupFinder = value;
    }

    /**
     * Gets the value of the assemblyImporter property.
     * 
     * @return
     *     possible object is
     *     {@link AssemblyImporter }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public AssemblyImporter getAssemblyImporter() {
        return assemblyImporter;
    }

    /**
     * Sets the value of the assemblyImporter property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssemblyImporter }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAssemblyImporter(AssemblyImporter value) {
        this.assemblyImporter = value;
    }

    /**
     * Gets the value of the aligner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aligner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAligner().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Aligner }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Aligner> getAligner() {
        if (aligner == null) {
            aligner = new ArrayList<Aligner>();
        }
        return this.aligner;
    }

    /**
     * Gets the value of the snpCaller property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the snpCaller property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSNPCaller().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SNPCaller }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<SNPCaller> getSNPCaller() {
        if (snpCaller == null) {
            snpCaller = new ArrayList<SNPCaller>();
        }
        return this.snpCaller;
    }

}
