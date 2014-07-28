/* 	
 * 	Main Class - Edlio Test Project
 * 	Finds files, Parses them using SAX, Uses Youtube API, then prints findings
 * 	Timothy Myung
 * 
 * */

package com.testproject;

import java.io.File;
import java.util.List;

import com.google.common.collect.HashMultiset;

public class StandardsProcessor {
	public void runProgram() {

		System.out.println("XML Parser and Searcher");
		System.out.println("Finding Files...");
		List<File> files = new FileFinder().findFilenames();
		System.out.println("... found " + files.size());

		System.out.println("Parsing files...");
		HashMultiset<CoreStandard> hm = HashMultiset.create();
		for (File file : files) {
			System.out.println("... " + file.getName());

			hm.addAll(new SaxParser().parseXML(file));
		}

		System.out.println("Searching for YouTube links...");
		hm = new YouTubeSearcher().runSearch(hm);

		System.out.println("\nPrinting results...");
		new CodePrinter().print(hm);

		System.out.println("Completed. (" + hm.size() + " entries)");

	}

	public static void main(String[] args) {
		StandardsProcessor rp = new StandardsProcessor();

		rp.runProgram();

	}

}
