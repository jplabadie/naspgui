//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.01 at 12:40:32 PM MST 
//


package xmlbinds.nasp_xmlbinds;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Filters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Filters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProportionFilter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CoverageFilter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Filters", propOrder = {
    "proportionFilter",
    "coverageFilter"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Filters {

    @XmlElement(name = "ProportionFilter", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String proportionFilter;
    @XmlElement(name = "CoverageFilter", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String coverageFilter;

    /**
     * Gets the value of the proportionFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getProportionFilter() {
        return proportionFilter;
    }

    /**
     * Sets the value of the proportionFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setProportionFilter(String value) {
        this.proportionFilter = value;
    }

    /**
     * Gets the value of the coverageFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCoverageFilter() {
        return coverageFilter;
    }

    /**
     * Sets the value of the coverageFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-07-01T12:40:32-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCoverageFilter(String value) {
        this.coverageFilter = value;
    }

}