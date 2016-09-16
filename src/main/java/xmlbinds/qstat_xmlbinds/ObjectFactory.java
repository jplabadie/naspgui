
package xmlbinds.qstat_xmlbinds;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xmlbinds.qstat_xmlbinds package.
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

    private final static QName _Data_QNAME = new QName("", "Data");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xmlbinds.qstat_xmlbinds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QstatDataType }
     * 
     */
    public QstatDataType createDataType() {
        return new QstatDataType();
    }

    /**
     * Create an instance of {@link QstatJobType }
     * 
     */
    public QstatJobType createJobType() {
        return new QstatJobType();
    }

    /**
     * Create an instance of {@link QstatResourceListType }
     * 
     */
    public QstatResourceListType createResourceListType() {
        return new QstatResourceListType();
    }

    /**
     * Create an instance of {@link QstatResourcesUsedType }
     * 
     */
    public QstatResourcesUsedType createResourcesUsedType() {
        return new QstatResourcesUsedType();
    }

    /**
     * Create an instance of {@link QstatWalltimeType }
     * 
     */
    public QstatWalltimeType createWalltimeType() {
        return new QstatWalltimeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QstatDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Data")
    public JAXBElement<QstatDataType> createData(QstatDataType value) {
        return new JAXBElement<QstatDataType>(_Data_QNAME, QstatDataType.class, null, value);
    }

}
