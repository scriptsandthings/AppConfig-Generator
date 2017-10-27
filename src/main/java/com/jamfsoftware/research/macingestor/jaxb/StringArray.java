//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.17 at 09:04:29 AM CDT 
//


package com.jamfsoftware.research.macingestor.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.dd.plist.NSArray;
import com.jamfsoftware.research.macingestor.MACDataType;
import com.jamfsoftware.research.macingestor.jaxb.Options.Option;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultValue" type="{}stringArrayValueType" minOccurs="0"/>
 *         &lt;element name="constraint" type="{}stringConstraintType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="keyName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "defaultValue",
    "constraint"
})
@XmlRootElement(name = "stringArray")
public class StringArray implements MACDataType {

    protected StringArrayValueType defaultValue;
    protected StringConstraintType constraint;
    @XmlAttribute(name = "keyName", required = true)
    protected java.lang.String keyName;

    /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link StringArrayValueType }
     *     
     */
    public StringArrayValueType getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArrayValueType }
     *     
     */
    public void setDefaultValue(StringArrayValueType value) {
        this.defaultValue = value;
    }

    /**
     * Gets the value of the constraint property.
     * 
     * @return
     *     possible object is
     *     {@link StringConstraintType }
     *     
     */
    public StringConstraintType getConstraint() {
        return constraint;
    }

    /**
     * Sets the value of the constraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringConstraintType }
     *     
     */
    public void setConstraint(StringConstraintType value) {
        this.constraint = value;
    }

    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setKeyName(java.lang.String value) {
        this.keyName = value;
    }

	@Override
	public java.lang.String getValidation() {
		java.lang.String attributes = "";
    	
    	if(constraint != null && constraint.isNullable() != null && !constraint.isNullable()){
    		attributes += "data-parsley-required=\"\" ";
    	}
    	
    	if(constraint != null && constraint.getPattern() != null){
    		attributes += "pattern=\"" + constraint.getPattern()+"\" ";
    	} else {
    		if(constraint != null && constraint.min != null){
    			attributes += "data-parsley-minLength=\"" + constraint.min + "\" ";
    		}
    		
    		if(constraint != null && constraint.max != null){
    			attributes += "data-parsley-maxLength=\"" + constraint.max + "\" ";
    		}
    	}
    	return attributes;
	}

	@Override
	public List<java.lang.String> getDefaultValueList() {
		List<java.lang.String> response = new ArrayList<java.lang.String>();

		if (defaultValue != null) {
			List<Object> vals = defaultValue.getValueOrUserVariableOrDeviceVariable();
			for(Object o : vals) {
				if(o instanceof DeviceVariable) {
					response.add(((DeviceVariable) o).getJSSVariableName());
				} else if(o instanceof UserVariable) {
					response.add(((UserVariable) o).getJSSVariableName());
				} else {
					response.add(o.toString());
				}
			}
		} else {
			response.add("");
		}
		
		return response;
	}

	@Override
	public boolean isUserOrDeviceVariable() {
		try {
			if (defaultValue != null) {
				for(Object o : defaultValue.getValueOrUserVariableOrDeviceVariable()) {
					if(o.getClass().getSimpleName().equals("UserVariable") || o.getClass().getSimpleName().equals("DeviceVariable")) {
						return true;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public java.lang.String getDefaultPresentationType() {
		
		if(isUserOrDeviceVariable()) return "hidden";
		return "list";
	}

	@Override
	public Options getOptions() {
		try {
			Options options = new Options();
			options.option = new ArrayList<Option>();
			for(java.lang.String f : constraint.getValues().value){
				Option o = new Option();
				o.setValue(f.toString());
				o.setSelected(false);
				
				// set the language for display
				List<Language> lang = new ArrayList<Language>();
				Language l = new Language();
				l.setValue(f.toString());
				l.setValueAttribute("en-US");
				lang.add(l);
				o.language = lang;
				
				options.option.add(o);
			}
			return options;
			
		} catch (NullPointerException e){
			return new Options();
		}
	}

	@Override
	public Object getPlistObject(java.lang.String[] submissions) {
		NSArray array = new NSArray(submissions.length);
		for(int i = 0; i < array.count(); i++){
			array.setValue(i, submissions[i]);
		}
		return array;
	}

}
