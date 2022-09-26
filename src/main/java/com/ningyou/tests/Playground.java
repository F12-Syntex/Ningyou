package com.ningyou.tests;

import com.ningyou.api.Ningyou;
import com.ningyou.media.Manga;
import com.ningyou.modals.NinjgouList;
import com.ningyou.queries.MangaEntity;

public class Playground {
	
	public static void main(String[] args) {
		
		NinjgouList<MangaEntity> entries = Ningyou.searchManga("attack on titan");
		
		entries.forEach(i -> {
			
			MangaEntity entity = i.cast(MangaEntity.class);
			Manga manga = entity.asManga();
		
			System.out.println(manga.alt + " -> chapters: " + manga.getChapters().size());

		});
		
	}

}
 