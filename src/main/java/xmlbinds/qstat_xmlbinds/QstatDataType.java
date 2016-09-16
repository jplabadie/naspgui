
package xmlbinds.qstat_xmlbinds;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Job" type="{}JobType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement( name = "Data" )
@XmlType(name = "QstatDataType", propOrder = {
    "job"
})
public class QstatDataType {

    @XmlElement(name = "Job")
    protected List<QstatJobType> job;

    /**
     * Gets the value of the job property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the job property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJob().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QstatJobType }
     * 
     * 
     */
    public List<QstatJobType> getJob() {
        if (job == null) {
            job = new ArrayList<QstatJobType>();
        }
        return this.job;
    }

}
