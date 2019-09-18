package com.fisglobal.host;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public final class MRPC097Response extends BaseHandler {
	private String name;
	private String currentElement;
	private String status;
	private String fullName;
	private String customerNumber;
	private String namePrefix;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nameSuffix;
	private String email;
	private String taxIdNumber;
	private StringBuilder characterBuffer = new StringBuilder(" ");

	private MRPC097Response() {
	}

	public static MRPC097Response getInstance(String responseString) throws IOException, SAXException, ParserConfigurationException {
		return BaseHandler.parse(responseString, new MRPC097Response());
	}

	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
		currentElement = qName;
		characterBuffer.delete(0, characterBuffer.length());
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		characterBuffer.append(s);
	}

	public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
		String s = characterBuffer.toString().trim();
		if (!s.equals("")) {
			if ("ACN".equals(currentElement)) {
				customerNumber = s;
			} else if ("NAM".equals(currentElement)) {
				fullName = s;
			} else if ("STATUS".equals(currentElement)) {
				status = s;
			} else if ("PREF".equals(currentElement)) {
				namePrefix = s;
			} else if ("FNAME".equals(currentElement)) {
				firstName = s;
			} else if ("MNAME".equals(currentElement)) {
				middleName = s;
			} else if ("LNM".equals(currentElement)) {
				lastName = s;
			} else if ("SUFFIX".equals(currentElement)) {
				nameSuffix = s;
			} else if ("EMAIL".equals(currentElement)) {
				email = s;
			} else if ("TAXID".equals(currentElement)) {
				taxIdNumber = s;
			}
		}
		currentElement = qName;
	}

	public void endDocument() throws SAXException {
		name = currentElement;
	}

	public String getName() {
		return name;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public String getStatus() {
		return status;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public String getTaxIdNumber() {
		return taxIdNumber;
	}
	
	public String getDeclaration() {
		return "<?xml version=\"1.0\"?><!DOCTYPE MRPC097 SYSTEM \"mrpc097.dtd\">";
	}
	
	public String getValidationPath() {
		return "/com/fisglobal/host/mrpc097.dtd";
	}
}
