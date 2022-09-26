package com.ningyou.tests;

import com.ningyou.api.Ningyou;
import com.ningyou.domains.Domain;
import com.ningyou.modals.Chapter;
import com.ningyou.modals.ChapterManager;
import com.ningyou.modals.NinjgouList;
import com.ningyou.modals.Pages;
import com.ningyou.queries.MangaEntity;

public class GetMangaList {

	public static void main(String[] args) {
		
		String query = "overpowered";
		Domain domain = Domain.MANGA_STREAM;
		
		System.out.println("Searching " + query + " in " + domain.name());
		
		NinjgouList<MangaEntity> results = Ningyou.searchManga(query, domain);

		results.forEach(i -> {
			System.out.println(" - " + i.alt);
		});

		
		MangaEntity result = results.getRandom();
		System.out.println("getting data from " + result.alt);
		
		ChapterManager manga = result.asManga().chapters;
		System.out.println(result.alt + " has " + manga.size() + " chapter");
		
		Chapter chapter_one = manga.getRandom();
		System.out.println("getting pages from " + chapter_one.name);
		
		try {
			Pages pages = chapter_one.getPages().call();
			pages.forEach(i -> {
				System.out.println(" - " + i.imgUrl);
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
