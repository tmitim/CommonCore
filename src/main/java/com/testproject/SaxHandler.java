// handler to parse through the xml files

package com.testproject;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.collect.HashMultiset;

public class SaxHandler extends DefaultHandler{
	
	HashMultiset<CoreStandard> hm = HashMultiset.create();
	String refURIString = "";
	String statementCodeString = "";
	String statementString = "";
	boolean refURI = false;
	boolean statementCode = false;
	boolean statement = false;
	
	public HashMultiset <CoreStandard> getHm(){
		return hm;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub

		if(refURI){
			refURIString = new String(ch, start, length);
			refURI = false;
		}
		if(statementCode){
			statementCodeString = new String(ch, start, length);
			statementCode = false;
		}
		if(statement){
			// if the program gets to here without uri/statementcode strings, there is a problem with the xml
			statementString =  new String(ch, start, length);
			if(!refURIString.equals("") && !statementCodeString.equals("") && !statementString.equals(""))
				hm.add(new CoreStandard(statementCodeString, statementString, refURIString));
			refURIString = "";
			statementCodeString = "";
			statementString = "";
			statement = false;
		}
	}

	@Override
	public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {
		if (qName.equalsIgnoreCase("refuri")) {
			refURI = true;
		}
		if (qName.equalsIgnoreCase("statementcode")) {
			statementCode = true;
		}
		if (qName.equalsIgnoreCase("statement")) {
			statement = true;
		}

	}

}
