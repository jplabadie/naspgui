//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.01 at 12:40:32 PM MST 
//


package xmlbinds;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for NaspInputData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NaspInputData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Options" type="{}Options"/>
 *         &lt;element name="Files" type="{}Files"/>
 *         &lt;element name="ExternalApplications" type="{}ExternalApplications"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "NaspInputData", propOrder = {
    "options",
    "files",
    "externalApplications"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class NaspInputData {

    @XmlElement(name = "Options", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Options options = new Options();
    @XmlElement(name = "Files", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Files files = new Files();
    @XmlElement(name = "ExternalApplications", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected ExternalApplications externalApplications = new ExternalApplications();

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link Options }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Options getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link Options }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOptions(Options value) {
        this.options = value;
    }

    /**
     * Gets the value of the files property.
     * 
     * @return
     *     possible object is
     *     {@link Files }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Files getFiles() {
        return files;
    }

    /**
     * Sets the value of the files property.
     * 
     * @param value
     *     allowed object is
     *     {@link Files }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setFiles(Files value) {
        this.files = value;
    }

    /**
     * Gets the value of the externalApplications property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalApplications }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public ExternalApplications getExternalApplications() {
        return externalApplications;
    }

    /**
     * Sets the value of the externalApplications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalApplications }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setExternalApplications(ExternalApplications value) {
        this.externalApplications = value;
    }

}
