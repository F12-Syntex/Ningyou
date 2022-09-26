package com.ningyou.modals;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 
 * 
 * this class stores all chapters of a manga
 * @author saif khan
 *
 */
public class Chapter{

	public String name; 
	public String url;
	public double chapter;
	public Callable<Pages> pages;
	
	public Map<String, String> attributes;

	
	public Chapter(String name, String url, double chapter, Callable<Pages> pages, Map<String, String> attributes) {
		this.name = name;
		this.url = url;
		this.chapter = chapter;
		this.pages = pages;
		this.attributes = attributes;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getChapterNumber() {
		return chapter;
	}
	public void setChapterNumber(double chapter) {
		this.chapter = chapter;
	}
	public Callable<Pages> getPages() {
		return pages;
	}
	public void setPages(Callable<Pages> pages) {
		this.pages = pages;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public Pages getPagesNow() {
		try {
			return this.pages.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pages pages = new Pages();
		return pages;
	}


	public String getChapterNumberAsString() {
		String number = String.valueOf(this.getChapterNumber());
		if(number.split("[.]")[1].equals("0")) {
			return String.valueOf((int)this.getChapterNumber());
		}
		return number;
	}
	
}
