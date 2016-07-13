
package xmlbinds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VCFFolderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VCFFolderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VCFFile" type="{}VCFFileType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VCFFolder", propOrder = {
    "vcfFile"
})
public class VCFFolder {

    @XmlElement(name = "VCFFile", required = true)
    protected VCFFile vcfFile;
    @XmlAttribute(name = "path")
    protected String path;

    /**
     * Gets the value of the vcfFile property.
     * 
     * @return
     *     possible object is
     *     {@link VCFFile }
     *     
     */
    public VCFFile getVCFFile() {
        return vcfFile;
    }

    /**
     * Sets the value of the vcfFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link VCFFile }
     *     
     */
    public void setVCFFile(VCFFile value) {
        this.vcfFile = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
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
    public void setPath(String value) {
        this.path = value;
    }

}
