//EXAMPLE OF abstract METHODS, BELOW BOTH ARE ABSTRACT METHODS IN CLASS
// MRPC097Response, MRPC098Response AND MRPC102Response EXTENDS BaseHandler 
//AND WRITTING THE BODY OF abstract METHODS
//	public abstract String getDeclaration();

//	public abstract String getValidationPath();

package com.fisglobal.host;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.fisglobal.base.Logger;
import com.fisglobal.base.SanchezException;

public abstract class BaseHandler extends DefaultHandler {
	public static <T extends BaseHandler> T parse(final String responseString, final T response) throws SAXException, IOException, ParserConfigurationException {
		final SAXParser parser = getSAXParser();
		parser.parse(new InputSource(new StringReader(response.getDeclaration() + responseString)), response);
		return response;
	}

	public static SAXParser getSAXParser() throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException, SAXException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		try {
			factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
		} catch (final SAXNotRecognizedException exception) {
			Logger.warn("SAX feature not supported by SAXParserFactory from " + SAXParserFactory.class.getResource("/javax/xml/parsers/SAXParserFactory.class").getFile() + ": " + exception.getMessage());
		}
		final SAXParser parser = factory.newSAXParser();
		return parser;
	}

	@Override
	public void error(final SAXParseException exception) throws SAXException {
		Logger.error("", exception);
		throw new SanchezException(exception);
	}

	@Override
	public void fatalError(final SAXParseException exception) throws SAXException {
		Logger.fatal("", exception);
		throw new SanchezException(exception);
	}

	@Override
	public void warning(final SAXParseException exception) throws SAXException {
		Logger.warn("", exception);
		throw new SanchezException(exception);
	}

	@Override
	public InputSource resolveEntity(final String publicId, final String systemId) throws IOException, SAXException {
		return new InputSource(getClass().getResourceAsStream(getValidationPath()));
	}

	public abstract String getDeclaration();

	public abstract String getValidationPath();
}
