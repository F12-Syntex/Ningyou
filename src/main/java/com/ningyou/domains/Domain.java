package com.ningyou.domains;

import com.ningyou.queries.QueryEntity;
import com.ningyou.sources.BatoTo;
import com.ningyou.sources.MangaStream;
import com.ningyou.sources.Source;
import com.ningyou.sources.SourceType;

/**
 * represents types of sources
 * @author saif khan
 */
public enum Domain {
	
	MANGA_STREAM(false, SourceType.MANGA, new MangaStream()),
	BATO_TO(false, SourceType.MANGA, new BatoTo());
	
	public final boolean nsfw;
	public final SourceType type;
	public final Source<? extends QueryEntity> source;

	private Domain(boolean nsfw, SourceType type, Source<? extends QueryEntity> source) {
		this.nsfw = nsfw;
		this.type = type;
		this.source = source;
	}
	
	public static Domain getDomain(Source<? extends QueryEntity> source) {
		for(Domain i : Domain.values()) {
			if(i.source.domain.equals(source.domain)) {
				return i;
			}
		}
		return null;
	}
	
	public static Domain recommended(SourceType type) {
		switch (type) {
		case MANGA:
			return Domain.MANGA_STREAM;
		default:
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "{name: " + this.name() + ", nsfw: " + this.nsfw + ", domain: " + this.source.domain + "}";
	}
	
	
	
	

}
