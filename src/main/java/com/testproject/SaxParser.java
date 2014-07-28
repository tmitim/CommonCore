package com.testproject;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;

import com.google.common.collect.HashMultiset;

public class SaxParser{
	public HashMultiset <CoreStandard> parseXML(File filename) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();

			SaxHandler handler = new SaxHandler(); 
			saxParser.parse("src/main/xml/" + filename.getName(), handler);
			return handler.getHm();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("Something is Wrong with this File");
			// returns empty hashmultiset
			return HashMultiset.create();
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


}
