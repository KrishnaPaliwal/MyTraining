package com.fisglobal.host;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fisglobal.bean.Rate;
import com.fisglobal.utils.Utility;

public final class MRPC102Response extends BaseHandler {
	private String name;
	private String currentElement;
	private Date effectiveDate;
	private Rate currentRate;
	private final List<Rate> rateList;
	private Rate[] rates = new Rate[0];
	private String code;
	private String termInterval;
	boolean insideDep = false;
	private final StringBuffer characterBuffer = new StringBuffer(" ");

	private MRPC102Response() {
		rateList = new ArrayList<Rate>(15);
	}

	public static MRPC102Response getInstance(final String responseString) throws IOException, SAXException, ParserConfigurationException {
		return BaseHandler.parse(responseString, new MRPC102Response());
	}

	public static MRPC102Response getInstance(final InputStream responseStream) throws IOException, ParserConfigurationException, SAXException {
		final MRPC102Response response = new MRPC102Response();
		BaseHandler.getSAXParser().parse(responseStream, response);
		return response;
	}

	public static MRPC102Response getInstance(final Reader responseReader) throws IOException, ParserConfigurationException, SAXException {
		final MRPC102Response response = new MRPC102Response();
		BaseHandler.getSAXParser().parse(new InputSource(responseReader), response);
		return response;
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName, final Attributes attrs) throws SAXException {
		if ("DEP".equals(qName)) {
			currentRate = new Rate();
			insideDep = true;
		}
		currentElement = qName;
		characterBuffer.delete(0, characterBuffer.length());
	}

	@Override
	public void characters(final char buf[], final int offset, final int len) throws SAXException {
		final String s = new String(buf, offset, len);
		characterBuffer.append(s);
	}

	@Override
	public void endElement(final String namespaceURI, final String lName, final String qName) throws SAXException {
		final String s = characterBuffer.toString().trim();
		if (!s.equals("")) {
			if ("EFD".equals(currentElement)) {
				effectiveDate = Utility.fromProfileDate(s);
			} else if ("TERMINT".equals(currentElement)) {
				termInterval = s;
			} else if ("CODE".equals(currentElement)) {
				code = s;
			} else if (insideDep) {
				if ("RATE".equals(currentElement)) {
					currentRate.setRate(s);
				} else if ("MINBAL".equals(currentElement)) {
					currentRate.setMinimumBalance(s);
				} else if ("TERM".equals(currentElement)) {
					currentRate.setTerm(s);
				} else if ("APY".equals(currentElement)) {
					currentRate.setApy(Double.parseDouble(s) / 100);
				}
			}
		}
		if ("DEP".equals(qName)) {
			rateList.add(currentRate);
			insideDep = false;
		}
		currentElement = qName;
	}

	@Override
	public void endDocument() throws SAXException {
		name = currentElement;
		if (termInterval != null) {
			rates = new Rate[rateList.size()];
			for (int i = 0; i < rateList.size(); ++i) {
				final Rate rate = rateList.get(i);
				if (termInterval != null && Integer.parseInt(rate.getTerm()) == 0) {
					rateList.remove(i);
					--i;
				} else {
					rate.setEffectiveDate(effectiveDate);
					rate.setTermInterval(termInterval);
					rates[i] = rate;
				}
			}
		}
		rates = rateList.toArray(new Rate[rateList.size()]);
	}

	public String getName() {
		return name;
	}

	public Rate[] getRates() {
		return rates.clone();
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getDeclaration() {
		return "<?xml version=\"1.0\"?><!DOCTYPE MRPC102 SYSTEM \"mrpc102.dtd\">";
	}

	@Override
	public String getValidationPath() {
		return "/com/fisglobal/host/mrpc102.dtd";
	}
}
