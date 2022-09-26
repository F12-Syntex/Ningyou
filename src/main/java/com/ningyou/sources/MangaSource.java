package com.ningyou.sources;
import java.util.Map;
import java.util.concurrent.Callable;

import com.ningyou.media.Manga;
import com.ningyou.modals.ChapterManager;
import com.ningyou.modals.Pages;
import com.ningyou.queries.MangaEntity;

public abstract class MangaSource extends Source<MangaEntity>{
	
	public MangaSource(String domain) {
		super(domain);
	}
	
	public abstract ChapterManager getChapterManagerFromHTML(MangaEntity entity, String html);
	public abstract Callable<Pages> getPagesFromUrlLater(String mangaURL);
	public abstract Map<String, String> getAttributesFromHTML(MangaEntity entity, String html);
	
	public ChapterManager getChapterManagerFromMangaEntity(MangaEntity entity) {
		String html = this.readURL(entity.url);
		return this.getChapterManagerFromHTML(entity, html);
	}
	public Map<String, String> getAttributesFromMangaEntity(MangaEntity entity) {
		String html = this.readURL(entity.url);
		return this.getAttributesFromHTML(entity, html);
	}
	
	public abstract Manga getMangaFromMangaEntity(MangaEntity entity);

}