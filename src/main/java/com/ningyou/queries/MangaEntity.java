package com.ningyou.queries;

import com.ningyou.media.Manga;

public class MangaEntity extends QueryEntity{

	public com.ningyou.sources.MangaSource manga;
	
	public MangaEntity(String alt, String img, String url, com.ningyou.sources.MangaSource source) {
		super(alt, img, url, source);
		this.manga = source;
	}
	
	
	public MangaEntity(MangaEntity entity) {
		super(entity.alt, entity.img, entity.url, entity.source);
		this.manga = entity.manga;
	}
	
	public Manga asManga() {
		Manga manga = this.manga.getMangaFromMangaEntity(this);
		return manga;
	}
	
	
	
	
}
