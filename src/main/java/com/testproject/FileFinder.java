package com.testproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
	
	// find all files in the folder that end with .xml
	public List<File> findFilenames(){
		File folder = new File("src/main/xml");
		File[] listOfFiles= folder.listFiles();
		

		List <File>filteredList = new ArrayList<File>();
		for (File temp: listOfFiles){
			if(temp.getName().endsWith(".xml")){
				filteredList.add(temp);
			}
			
		}
		
		return filteredList;
	}


}
