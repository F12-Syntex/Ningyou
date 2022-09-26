package com.ningyou.queries;

import com.ningyou.modals.NinjgouList;
import com.ningyou.sources.Source;

public class QueryEntity {
	
	public String alt;
	public String img;
	public String url;
	
	public Source<? extends QueryEntity> source;
	
	public QueryEntity(String alt, String img, String url, Source<? extends QueryEntity> source) {
		
		this.alt = alt;
		this.img = img;
		this.url = url;
		
		this.source = source;
	}
	
	public QueryEntity(QueryEntity entity) {		
		this.alt = entity.alt;
		this.img = entity.img;
		this.url = entity.url;
		
		this.source = entity.source;
	}
	
	public NinjgouList<? extends QueryEntity> submitQuery(String query) {
		return this.source.submitQuery(query);
	}
	
	public <T> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}

}
