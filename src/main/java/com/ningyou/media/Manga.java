package com.ningyou.media;

import java.util.HashMap;
import java.util.Map;

import com.ningyou.modals.ChapterManager;
import com.ningyou.queries.MangaEntity;

/**
 * represents a manga.
 * @author saif
 * 
 */
public class Manga extends Media{

	public MangaEntity queryEntry;
	
	public Map<String, String> attributes = new HashMap<>();	
	
	public ChapterManager chapters;
	
	public Manga(MangaEntity queryEntry, Map<String, String> attributes, ChapterManager chapters) {
		super(queryEntry);
		
		this.queryEntry = queryEntry;
		this.attributes = attributes;
		this.chapters = chapters;
	}
	
	public MangaEntity getQueryEntry() {
		return queryEntry;
	}
	public void setQueryEntry(MangaEntity queryEntry) {
		this.queryEntry = queryEntry;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public ChapterManager getChapters() {
		return chapters;
	}
	public void setChapters(ChapterManager chapters) {
		this.chapters = chapters;
	}
	
	
	
	
	
	
}
