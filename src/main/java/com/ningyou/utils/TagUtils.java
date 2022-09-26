package com.ningyou.utils;

import java.util.ArrayList;
import java.util.List;

public class TagUtils {

	/**
	 * This is a class that takes HTML input and returns all lines with the element img-src, as well as lines top and bottom.
	 *
	 * @param HTML input
	 * @param lines to the top of result
	 * @param lines to the bottom of result
	 * @return list of results
	 */
	public static List<String[]> getImageTags(String data, int bottom, int top) {
		
		String[] lines = data.split(System.lineSeparator());
		
		List<String[]> results = new ArrayList<>();
		
		List<String> lineData = new ArrayList<>();
		 
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("img src")) {
				
				for(int o = 0; o < bottom; o++) {
					lineData.add(lines[i-(o+1)].trim());
				}
				
				lineData.add(lines[i].trim());

				for(int o = 0; o < top; o++) {
					lineData.add(lines[i+(o+1)].trim());
				}
				
				String[] resultData = new String[top+bottom+1];
				
				results.add(lineData.toArray(resultData));
				lineData.clear();
			}
		}
		
		return results;
	}
	
	/**
	 * This is a class that takes HTML input and returns all lines with the element "element", as well as lines top and bottom.
	 *
	 * @param HTML input
	 * @param lines to the top of result
	 * @param lines to the bottom of result
	 * @return list of results
	 */
	public static List<String[]> getImageTags(String element, String data, int bottom, int top) {
		
		String[] lines = data.split(System.lineSeparator());
		
		List<String[]> results = new ArrayList<>();
		
		List<String> lineData = new ArrayList<>();
		 
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains(element)) {
				
				for(int o = 0; o < bottom; o++) {
					lineData.add(lines[i-(o+1)].trim());
				}
				
				lineData.add(lines[i].trim());

				for(int o = 0; o < top; o++) {
					lineData.add(lines[i+(o+1)].trim());
				}
				
				String[] resultData = new String[top+bottom+1];
				
				results.add(lineData.toArray(resultData));
				lineData.clear();
			}
		}
		
		return results;
	}
	
	public static String getContainedValue() {
		return "";
	}
	
}
