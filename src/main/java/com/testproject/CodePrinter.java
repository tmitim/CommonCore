// Display the information

package com.testproject;

import com.google.common.collect.HashMultiset;

public class CodePrinter {
	
	public void print(HashMultiset<CoreStandard> hm){
		for(CoreStandard temp: hm){
			System.out.println("============================================================"
					+ "============================================================");
			System.out.println(temp.getCommonCoreCode() + " - " + temp.getCommonCoreDescription());
			System.out.println(temp.getCommonCoreReferenceUrl());
			System.out.println("------------------------------------------------------"
					+ "------------------------------------------------------------------");
			for(String tempString: temp.url){
				if(!tempString.contains(" "))
					System.out.print("https://www.youtube.com/watch?v=");
				System.out.println(tempString);
				
			}
			System.out.println("\n");
			
		}
	}

}
