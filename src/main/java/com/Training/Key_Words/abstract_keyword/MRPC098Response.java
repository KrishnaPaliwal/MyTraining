package com.fisglobal.host;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public final class MRPC098Response extends BaseHandler {
	private String name;
	private String currentElement;
	private String status;
	private String secretQuestion;
	private String customerNumber;
	private StringBuffer characterBuffer = new StringBuffer(" ");

	private MRPC098Response() {
	}

	public static MRPC098Response getInstance(String responseString) throws IOException, SAXException, ParserConfigurationException {
		return BaseHandler.parse(responseString, new MRPC098Response());
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
			if ("STATUS".equals(currentElement)) {
				status = s;
			} else if ("QUESTION".equals(currentElement)) {
				secretQuestion = s;
			} else if ("ACN".equals(currentElement)) {
				customerNumber = s;
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

	public String getStatus() {
		return status;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}
	
	public String getDeclaration() {
		return "<?xml version=\"1.0\"?><!DOCTYPE MRPC098 SYSTEM \"mrpc098.dtd\">";
	}
	
	public String getValidationPath() {
		return "/com/fisglobal/host/mrpc098.dtd";
	}
}
