package com.testproject;
import org.xml.sax.*; // simple api xml
import org.w3c.dom.*; // read to memory

import javax.xml.parsers.*;

public class ReadXmlFile {
	
	public static Element getNextElement(Element baseElement, String tagName){
		NodeList tempNodeList = baseElement.getElementsByTagName(tagName);
		Element tempElement = (Element)tempNodeList.item(0);
		return tempElement;
	}

	public static void main(String[] args) {
		Document xmlDoc = getDocument("src/main/xml/shortened.xml");
		
		System.out.println(xmlDoc.getDocumentElement());
		NodeList listOfStandards = xmlDoc.getElementsByTagName("LearningStandardItem");
		//System.out.println(listOfStandards.getLength());
		
		for(int i=0; i < listOfStandards.getLength(); i++){
			Node tempNode = listOfStandards.item(i);
			
			Element stItemElement = (Element) tempNode;
			//Statement Code
			Element statementCodesListElement = getNextElement(stItemElement, "StatementCodes");
			Element statementCodeListElement = getNextElement(statementCodesListElement, "StatementCode");
			System.out.println(statementCodeListElement.getTextContent());
			
			//Description Codes\
			Element statementsListElement =  getNextElement(stItemElement,"Statements");
			Element statementListElement =  getNextElement(statementsListElement,"Statement");
			System.out.println(statementListElement.getTextContent());
			
			//RefURI
			Element refURIElement =  getNextElement(stItemElement,"RefURI");
			System.out.println(refURIElement.getTextContent());
			
			
		}
		
	}

	private static Document getDocument(String docString) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(docString));
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

}
