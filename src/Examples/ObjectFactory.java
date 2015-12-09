//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.09 at 02:55:10 PM GMT 
//


package Examples;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Examples package. 
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

    private final static QName _AppleStoresLdn_QNAME = new QName("", "appleStoresLdn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Examples
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AppleStore }
     * 
     */
    public AppleStore createAppleStore() {
        return new AppleStore();
    }

    /**
     * Create an instance of {@link Services }
     * 
     */
    public Services createServices() {
        return new Services();
    }

    /**
     * Create an instance of {@link GeniusAppt }
     * 
     */
    public GeniusAppt createGeniusAppt() {
        return new GeniusAppt();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link Discounts }
     * 
     */
    public Discounts createDiscounts() {
        return new Discounts();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppleStore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "appleStoresLdn")
    public JAXBElement<AppleStore> createAppleStoresLdn(AppleStore value) {
        return new JAXBElement<AppleStore>(_AppleStoresLdn_QNAME, AppleStore.class, null, value);
    }

}
