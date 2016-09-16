
package xmlbinds.nasp_xmlbinds;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for AlignmentFolderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlignmentFolderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Alignment" type="{}AlignmentType"/>
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
@XmlType(name = "AlignmentFolder", propOrder = {
    "alignment"
})
public class AlignmentFolder {

    @XmlElement(name = "Alignment", required = true)
    protected List<Alignment> alignment;
    @XmlAttribute(name = "path")
    protected String path;

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link Alignment }
     *     
     */
    public List<Alignment> getAlignment() {
        if( alignment == null)
            alignment = new ArrayList<>();
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alignment }
     *     
     */
    public void setAlignment(List<Alignment> value) {
        this.alignment = value;
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
