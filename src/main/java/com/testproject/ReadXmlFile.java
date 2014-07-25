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
	
	public static Element getNextElement(Element baseElement, String[] tagNames){
		
		for(String tagName: tagNames){
			NodeList tempNodeList = baseElement.getElementsByTagName(tagName);
			baseElement = (Element)tempNodeList.item(0);
		}
		
		return baseElement;
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
			String[] statementCodes = {"StatementCodes","StatementCode"};
			Element statementCode = getNextElement(stItemElement, statementCodes);
			System.out.println(statementCode.getTextContent());
					
			//Description Codes
			String[] statements = {"Statements","Statement"};
			Element statement = getNextElement(stItemElement, statements);
			System.out.println(statement.getTextContent());
			
			//RefURI
			String[] referrals = {"RefURI"};
			Element refURI = getNextElement(stItemElement, referrals);
			System.out.println(refURI.getTextContent());
			
			
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
