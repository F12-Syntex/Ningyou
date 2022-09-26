package com.ningyou.exceptions;

import java.util.logging.Logger;

import com.ningyou.sources.MangaSource;

/**
 * 
 * This exception isa called whenever an query is not found.
 * @author saif khan
 * 
 */
public class EntryNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private String query;
	private MangaSource source;
	
	private Logger log = Logger.getLogger(EntryNotFoundException.class.getName());  

	public EntryNotFoundException(String query, MangaSource source) {
		this.query = query;
		this.source = source;
	}
	
	@Override
	public void printStackTrace() {
		log.warning("Sorry but " + query + " was not found in source " + this.source.getClass().getName());
	}
	
}
