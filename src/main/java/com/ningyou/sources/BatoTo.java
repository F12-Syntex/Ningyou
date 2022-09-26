package com.ningyou.sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jsoup.Jsoup;

import com.ningyou.exceptions.EntryNotFoundException;
import com.ningyou.media.Manga;
import com.ningyou.modals.ChapterManager;
import com.ningyou.modals.NinjgouList;
import com.ningyou.modals.Page;
import com.ningyou.modals.Pages;
import com.ningyou.queries.MangaEntity;
import com.ningyou.utils.Extraction;

public class BatoTo extends MangaSource{

	public BatoTo() {
		super("https://bato.to");
	}

	@Override
	public NinjgouList<MangaEntity> submitQuery(String query) {

	    NinjgouList<MangaEntity> queries = new NinjgouList<>();
		
		try {
			
			String html = this.readURL("https://bato.to/search?word=" + query.replace(" ", "+"));
			
		   	Extraction.of(html).before("<div class=\"my-5 ms-auto d-flex justify-content-end\">").forEachAsQueriedEntity("<a class=\"item-cover\"", o -> {
		   		MangaEntity entity = new MangaEntity(o.alt, o.img, o.url, this);
		   		queries.add(entity);
		   	}, this);
		   
			return queries;
			
		}catch (Exception e) {
			new EntryNotFoundException(query, this).printStackTrace();
		}
		
		return queries;
	}
	
	
	
	public static void main(String[] args) {
		new BatoTo().submitQuery("soul").getFirst().asManga().getChapters().forEach(o -> {
			System.out.println(o.getName());
		});
	}

	
	@Override
	public ChapterManager getChapterManagerFromHTML(MangaEntity entity, String html) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Callable<Pages> getPagesFromUrlLater(String mangaURL) {
		return () -> {
			
			String chapterHTML = this.readURL(mangaURL);
			
			String pagesSection = chapterHTML.split("<p id=arraydata style=display:none>")[1].split("</p>")[0];
			String[] htmlPages = pagesSection.split("[,]");
			
			List<Page> pages = new ArrayList<>();
			
			for(int i = 0; i < htmlPages.length; i++) {
				Page page = new Page(htmlPages[i], (i+1));
				pages.add(page);
			}
			
			Pages data = new Pages(pages);
			
			return data;
		};
	}

	@Override
	public Map<String, String> getAttributesFromHTML(MangaEntity entity, String html) {
		 
		//get the section that represents the attributes of this manga
		String attributesHTML = Extraction.of(html).after("<div class=\"col-24 col-sm-16 col-md-18 mt-4 mt-sm-0 attr-main\">").before("<div class=\"gf-slot mt-4\" data-types=\"respHor\"></div>").retrieve();
		
		//map to store the attributes
		Map<String, String> attributes = new HashMap<String, String>();
		
		//variable used to break out of the Consumer node when we have obtained all our values
		AtomicBoolean running = new AtomicBoolean(true);
		
		//Helper to create map
		//AttributesMaker<String> maker = new AttributesMaker<>();
		
		//parse all span nodes, which normally have the attributes
		Jsoup.parse(attributesHTML).forEach(i -> {
			i.textNodes().forEach(o -> {
				if(o.getWholeText().trim().equals("Notices:")) {
					running.set(false);
					return;
				}
				if(!o.getWholeText().trim().isEmpty() && !o.getWholeText().trim().startsWith(",") && running.get()) {
					System.out.println("o -> {" + o.getWholeText() + "}");
					//maker.push(o.getWholeText().trim());
				}
			});
		});
		
		//maker.print();
		return attributes;
	}

	@Override
	public Manga getMangaFromMangaEntity(MangaEntity entity) {
		return null;
	}


}
