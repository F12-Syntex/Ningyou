package com.ningyou.api;

import com.ningyou.domains.Domain;
import com.ningyou.modals.NinjgouList;
import com.ningyou.queries.MangaEntity;
import com.ningyou.queries.QueryEntity;
import com.ningyou.sources.MangaSource;
import com.ningyou.sources.Source;
import com.ningyou.sources.SourceType;

/**
 * This is the main api class for ningyou
 * @author saif khan
 */
public class Ningyou {

	//search some manga query
	public static NinjgouList<MangaEntity> searchManga(String query, Domain domain) {
		try {
			
			//instantiate the source
			MangaSource source = domain.source.cast(MangaSource.class);
			
			//call the query
			NinjgouList<MangaEntity> entries = source.submitQuery(query);
			
			//return entries
			return entries;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	public static NinjgouList<MangaEntity> searchManga(String query) {
		return searchManga(query, Domain.recommended(SourceType.MANGA));
	}
	
	//search some genric query
	public static NinjgouList<? extends QueryEntity> genericSearch(String query, Domain domain) {
		try {
			
			//instantiate the source
			Source<? extends QueryEntity> source = domain.source;
			
			//call the query
			NinjgouList<? extends QueryEntity> entries = source.submitQuery(query);
			
			//return entries
			return entries;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
}
