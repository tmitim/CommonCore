// 

package com.testproject;

import java.io.File;
import java.util.List;

import com.google.common.collect.HashMultiset;

public class StandardsProcessor {
	public void runProgram() {

		System.out.println("Finding Files...");
		List<File> files = new FileFinder().findFilenames();

		System.out.println("Parsing files...");
		HashMultiset<CoreStandard> hm = HashMultiset.create();
		for (File file : files) {
			System.out.println("... " + file.getName());

			hm.addAll(new SaxParser().parseXML(file));
		}

		System.out.println("Searching for YouTube links...");
		hm = new YouTubeSearcher().runSearch(hm);

		System.out.println("Printing results...");
		new CodePrinter().print(hm);

		System.out.println("Completed printing.");

	}

	public static void main(String[] args) {
		StandardsProcessor rp = new StandardsProcessor();

		rp.runProgram();

	}

}
