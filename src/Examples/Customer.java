package Examples;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

public class Customer {
	
	

	/**
	 * <p>Java class for Customer complex type.
	 * 
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType name="Customer">
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="customerID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
	 *         &lt;element name="customerAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="customerPostcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="customerPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */

	//SHOULD ADD DETAILS OF CUSTOMER TO GENIUS BAR APPOINTMENT !! <<<<<<<<<<<<

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "Customer", propOrder = {
	    "customerName",
	    "customerID",
	    "customerAddress",
	    "customerPostcode",
	    "customerPhone"
	})
	public class GeniusAppt {

	    @XmlElement(required = true)
	    protected String customerName;
	    
	    @XmlElement(required = true)
	    protected int customerID;
	    
	    @XmlElement(required = true)
	    protected String customerAddress;
	    
	    @XmlElement(required = true)
	    protected String customerPostcode;
	    
	    @XmlElement(required = true)
	    protected String customerPhone;

	    /**
	     * Gets the value of the problem property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getCustomerName() {
	        return customerName;
	    }

	    /**
	     * Sets the value of the customerName property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setCustomerName(String value) {
	        this.customerName = value;
	    }

	    /**
	     * Gets the value of the customerID property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link int }
	     *     
	     */
	    public int getCustomerID() {
	        return customerID;
	    }

	    /**
	     * Sets the value of the customerID property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link int }
	     *     
	     */
	    public void setCustomerID(int value) {
	        this.customerID = value;
	    }

	    /**
	     * Gets the value of the customerAddress property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getCustomerAddress() {
	        return customerAddress;
	    }

	    /**
	     * Sets the value of the customerAddress property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setCustomerAddress(String value) {
	        this.customerAddress = value;
	    }
	    
	    /**
	     * Gets the value of the customerPostcode property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getCustomerPostcode() {
	        return customerPostcode;
	    }

	    /**
	     * Sets the value of the customerPostcode property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setCustomerPostcode(String value) {
	        this.customerPostcode = value;
	    }
	    
	    /**
	     * Gets the value of the customerPhone property.
	     * 
	     * @return
	     *     possible object is
	     *     {@link String }
	     *     
	     */
	    public String getPhone() {
	        return customerPhone;
	    }

	    /**
	     * Sets the value of the customerPhone property.
	     * 
	     * @param value
	     *     allowed object is
	     *     {@link String }
	     *     
	     */
	    public void setPhone(String value) {
	        this.customerPhone = value;
	    }

	}


}
