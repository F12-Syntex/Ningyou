package com.ningyou.sources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Node;

import com.ningyou.exceptions.EntryNotFoundException;
import com.ningyou.media.Manga;
import com.ningyou.modals.Chapter;
import com.ningyou.modals.ChapterManager;
import com.ningyou.modals.NinjgouList;
import com.ningyou.modals.Page;
import com.ningyou.modals.Pages;
import com.ningyou.queries.MangaEntity;
import com.ningyou.utils.Extraction;
import com.ningyou.utils.TagUtils;

public class MangaStream extends MangaSource{

	public MangaStream() {
		super("http://mangastream.mobi");
	}

	@Override
	public NinjgouList<MangaEntity> submitQuery(String query) {

		   NinjgouList<MangaEntity> queries = new NinjgouList<>();
			
			try {
			
				String data = this.readURL("http://mangastream.mobi/search?q=" + query.replace(" ", "+"));
	
				List<String[]> images = TagUtils.getImageTags(data, 2, 1);
				
				for(String[] j : images) {
	
					String img = j[2].split("\"")[1].split("\"")[0];
					String alt = j[3].split("\"")[1].split("\"")[0];
					String url = j[1].split("\"")[1].split("\"")[0];
					
					MangaEntity manga = new MangaEntity(alt, img, url, this);
					queries.add(manga);
				}
				
			}catch (IndexOutOfBoundsException e) {
				new EntryNotFoundException(query, this).printStackTrace();
			}
			
			if(queries.isEmpty()) {
				new EntryNotFoundException(query, this).printStackTrace();
			}
			
			
			return queries;
	}
	

	@Override
	public ChapterManager getChapterManagerFromHTML(MangaEntity entity, String html) {

		//store chapters
		NinjgouList<Chapter> chapters = new ChapterManager();
		
		String[] mangaSection = html.split("<div class=\"col-xs-12 chapter\">");
		
		for(int i = 0; i < mangaSection.length; i++) {
			
			String section = mangaSection[i];
			
			//map to store the attributes
			Map<String, String> mangaAttributes = new HashMap<String, String>();
			
			//manga details
			String title = Extraction.of(section).after("title=\"").beforeQuotation(); 
			String url = Extraction.of(section).afterHref().beforeQuotation();

			//ignore first
			if(title.equalsIgnoreCase("Read Manga Online For Free")) continue;
			
			double chapterNumber = (mangaSection.length - i);
			
			if(title.contains("Chapter")) {
				chapterNumber = Double.parseDouble(title.split("Chapter")[1].replace(" ", ""));
			}
			
			//get pages
			Callable<Pages> pages = this.getPagesFromUrlLater(url);
			
			//add chapter
			Chapter chapter = new Chapter(title, url, chapterNumber, pages, mangaAttributes);
			chapters.add(chapter);
		}
		
		ChapterManager chapterManager = new ChapterManager(chapters);
		
		return chapterManager;
	}
	
	@Override
	public Manga getMangaFromMangaEntity(MangaEntity entity) {

		//get html
		String html = this.readURL(entity.url);
		
		//map to store the attributes
		Map<String, String> attributes = this.getAttributesFromHTML(entity, html);

		ChapterManager chapterManager = this.getChapterManagerFromHTML(entity, html);
		
		Manga manga = new Manga(entity, attributes, chapterManager);
		
		return manga;
	}
	
	public static void main(String[] args) {
		new MangaStream().submitQuery("soul").get(0).asManga().chapters.forEach(System.out::println);
		
		
		
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
		 
		//map to store the attributes
		Map<String, String> attributes = new HashMap<String, String>();
		
		//get the section that represents the attributes of this manga
		String attributesHTML = html.split("<p class=\"description-update\">")[1].split("<span id=\"bookmark\">")[0];
		
		//parse all span nodes, which normally have the attributes
		Jsoup.parse(attributesHTML).forEachNode(i -> {
			if(i.nodeName().equalsIgnoreCase("span")) {
				Node sibling = i.nextSibling();
				
				Attributes children = sibling.attributes();
				
				if(children.size() != 0) {
					//System.out.println(i.attributes() + ": " + sibling);
					
					StringBuilder results = new StringBuilder();
					
					for(Attribute o : children) {
						results.append(o.getValue() + ", ");
					}

					if(results.length() != 0) {
						results.setLength(results.length() - 2);
					}
					
					results.trimToSize();
					
					String resultsString = results.toString().trim();
					
					String nodeName = Jsoup.parse(i.toString()).text();
					
					if(resultsString.length() >= 3) {
						attributes.put(nodeName.trim().substring(0, nodeName.trim().length() - 1), resultsString);
					}
					
					
					
				}
				
			}
		});

		return attributes;
	}
	 

}
