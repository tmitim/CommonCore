// Parses XML File using sax and dom

package com.testproject;
import java.io.File;

import org.xml.sax.*; // simple api xml
import org.w3c.dom.*; // read to memory

import com.google.common.collect.HashMultiset;

import javax.xml.parsers.*;
import javax.xml.validation.SchemaFactory;

public class XmlFileParser {
	/*
	public Element getNextElement(Element baseElement, String tagName){
		NodeList tempNodeList = baseElement.getElementsByTagName(tagName);
		Element tempElement = (Element)tempNodeList.item(0);
		
		return tempElement;
	}*/
	
	
	// Find element in element using multiple leaf terms
	public Element getNextElement(Element baseElement, String[] tagNames){
		
		for(String tagName: tagNames){
			NodeList tempNodeList = baseElement.getElementsByTagName(tagName);
			baseElement = (Element)tempNodeList.item(0);
		}
		
		return baseElement;
	}
	
	public HashMultiset<CoreStandard> parseXML(File filename){
		Document xmlDoc = getDocument("src/main/xml/" + filename.getName());
		NodeList listOfStandards = xmlDoc.getElementsByTagName("LearningStandardItem");
		
		HashMultiset <CoreStandard>hm = HashMultiset.create();
		
		for(int i=0; i < listOfStandards.getLength(); i++){
			Node tempNode = listOfStandards.item(i);
			
			Element stItemElement = (Element) tempNode;
			//Statement Code
			String[] statementCodes = {"StatementCodes","StatementCode"};
			Element statementCode = getNextElement(stItemElement, statementCodes);
					
			//Description Codes
			String[] statements = {"Statements","Statement"};
			Element statement = getNextElement(stItemElement, statements);
			
			
			//RefURI
			String[] referrals = {"RefURI"};
			Element refURI = getNextElement(stItemElement, referrals);
			

			// check for xml files without the correct elements
			if((refURI != null) && (statementCode != null)&& statements!= null){
				String commonCoreCode = statementCode.getTextContent();
				String commonCoreDescription = statement.getTextContent();
				String commonCoreReferenceURL = refURI.getTextContent();
				CoreStandard temp = new CoreStandard(commonCoreCode, commonCoreDescription, commonCoreReferenceURL);
				
				hm.add(temp);
			}
			
			
		}
		
		return hm;
		//System.out.println("using count = " + hm.toString());
		
	}

	private Document getDocument(String docString) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			//factory.setValidating(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			//builder.setErrorHandler(new SimpleErrorHandler());
			return builder.parse(new InputSource(docString));
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

}
