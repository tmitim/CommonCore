package com.testproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFiles {
	
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
		for(File temp: filteredList){
			System.out.println(temp.getName());
		}
		
		
		return filteredList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFiles rmf = new ReadFiles();
		rmf.findFilenames();

	}

}
