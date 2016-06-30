
package xmlbinds;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PicardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PicardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdditionalArguments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Picard", propOrder = {
    "additionalArguments"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Picard extends Application{

    @XmlElement(name = "AdditionalArguments", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String additionalArguments;
    @XmlAttribute(name = "name")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String name;
    @XmlAttribute(name = "path")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String path;

    /**
     * Gets the value of the additionalArguments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAdditionalArguments() {
        return additionalArguments;
    }

    /**
     * Sets the value of the additionalArguments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAdditionalArguments(String value) {
        this.additionalArguments = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-04-27T09:28:09-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPath(String value) {
        this.path = value;
    }

}
