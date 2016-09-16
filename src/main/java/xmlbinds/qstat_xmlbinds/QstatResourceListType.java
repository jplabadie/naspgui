
package xmlbinds.qstat_xmlbinds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Resource_ListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Resource_ListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mem">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="4gb"/>
 *               &lt;enumeration value="2gb"/>
 *               &lt;enumeration value="6gb"/>
 *               &lt;enumeration value="8gb"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ncpus">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="8"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="walltime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="08:00:00"/>
 *               &lt;enumeration value="04:00:00"/>
 *               &lt;enumeration value="48:00:00"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resource_ListType", propOrder = {
    "mem",
    "ncpus",
    "walltime"
})
public class QstatResourceListType {

    @XmlElement(required = true)
    protected String mem;
    @XmlElement(required = true)
    protected String ncpus;
    @XmlElement(required = true)
    protected String walltime;

    /**
     * Gets the value of the mem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMem() {
        return mem;
    }

    /**
     * Sets the value of the mem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMem(String value) {
        this.mem = value;
    }

    /**
     * Gets the value of the ncpus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNcpus() {
        return ncpus;
    }

    /**
     * Sets the value of the ncpus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNcpus(String value) {
        this.ncpus = value;
    }

    /**
     * Gets the value of the walltime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWalltime() {
        return walltime;
    }

    /**
     * Sets the value of the walltime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWalltime(String value) {
        this.walltime = value;
    }

}
