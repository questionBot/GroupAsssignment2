//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.09 at 02:55:10 PM GMT 
//


package Examples;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Services complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Services">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nameofservice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="priceRange" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Services", propOrder = {
    "nameofservice",
    "available",
    "priceRange"
})
public class Services {

    @XmlElement(required = true)
    protected String nameofservice;
    protected boolean available;
    @XmlElement(required = true)
    protected String priceRange;

    /**
     * Gets the value of the nameofservice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameofservice() {
        return nameofservice;
    }

    /**
     * Sets the value of the nameofservice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameofservice(String value) {
        this.nameofservice = value;
    }

    /**
     * Gets the value of the available property.
     * 
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     */
    public void setAvailable(boolean value) {
        this.available = value;
    }

    /**
     * Gets the value of the priceRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceRange() {
        return priceRange;
    }

    /**
     * Sets the value of the priceRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceRange(String value) {
        this.priceRange = value;
    }

}
