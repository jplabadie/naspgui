
package qstat_xmlbinds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resources_usedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resources_usedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vmem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="walltime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resources_usedType", propOrder = {
    "cput",
    "mem",
    "vmem",
    "walltime"
})
public class QstatResourcesUsedType {

    @XmlElement(required = true)
    protected String cput;
    @XmlElement(required = true)
    protected String mem;
    @XmlElement(required = true)
    protected String vmem;
    @XmlElement(required = true)
    protected String walltime;

    /**
     * Gets the value of the cput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCput() {
        return cput;
    }

    /**
     * Sets the value of the cput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCput(String value) {
        this.cput = value;
    }

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
     * Gets the value of the vmem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVmem() {
        return vmem;
    }

    /**
     * Sets the value of the vmem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVmem(String value) {
        this.vmem = value;
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
