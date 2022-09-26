package com.ningyou.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.TimeValue;
import org.json.JSONObject;

import com.ningyou.utils.Extraction;

public class AniDB {
	
	public ExecutorService threadpool = Executors.newFixedThreadPool(10); 
	
	public void crawl() {
		
		int years = 2022 - 1980;
		int months = 12;
		
		int elements = years * months;
		int elements_passed = 0;
		
		AtomicInteger animes = new AtomicInteger();

		
		JSONObject main = new JSONObject();
		
		for(int i = 1980; i <= 2022; i++) {
			
			JSONObject year = new JSONObject();
			
			for(int o = 1; o < 13; o++) {
				
				JSONObject month = new JSONObject();
				
				String html = readURL("https://anidb.net/anime/season/2022/1/?do=calendar&h=1&view=smallgrid");
				
				
				
				Extraction.of(html).forEachAsExtraction("<div class=\"thumb image\">", k -> {
					
					String name = k.clone().after("alt=\"").beforeQuotation().trim().replace(System.lineSeparator(), "");
					String img = k.clone().after("src=\"").beforeQuotation().trim().replace(System.lineSeparator(), "");
					String date = k.clone().after("<div class=\"date\">").before("</div>").retrieve().trim().replace(System.lineSeparator(), "");
					String type = k.clone().after("<div class=\"general\">").before("</div>").retrieve().trim().replace(System.lineSeparator(), "");
					
					JSONObject anime = new JSONObject();
					
					anime.put("title", name);
					anime.put("img", img);
					anime.put("date", date);
					anime.put("type", type);
					
					month.put(animes.incrementAndGet()+"", anime);
					
				});
				
				animes.set(0);
				
				elements_passed++;
				
				String error = "ERROR!";
				
				if(html.split("<div class=\"thumb image\">").length > 1) {
					error = "OK!";
				}
				
				System.out.println(elements_passed + " / " + elements + " ( " + error  + " )");
				
				String m = "";
				
				switch (o) {
					case 1:
						m = "Januray";
						break;
					case 2:
						m = "Febuary";
						break;
					case 3:
						m = "March";
						break;
					case 4:
						m = "April";
						break;
					case 5:
						m = "May";
						break;
					case 6:
						m = "June";
						break;
					case 7:
						m = "July";
						break;
					case 8:
						m = "August";
						break;
					case 9:
						m = "September";
						break;
					case 10:
						m = "October";
						break;
					case 11:
						m = "November";
						break;
					case 12:
						m = "December";
						break;
					default:
						break;
				}
				
				year.put(m, month);
				
			}
			
			main.put(i+"", year);
		}
		

		threadpool.shutdown();
        try {
			while (!threadpool.awaitTermination(100, TimeUnit.MILLISECONDS)) {}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
		      FileWriter myWriter = new FileWriter("C:\\Users\\synte\\Desktop\\Mangas\\animeDB1.json");
		      myWriter.write(main.toString(4));
		      myWriter.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		
	}
	
	public String readURL(String page) {
		
		try {
			HttpGet request = new HttpGet(page);
			CloseableHttpClient client = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36").evictIdleConnections(TimeValue.ofSeconds(10)).build();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String dat = EntityUtils.toString(entity);
			return dat;
		} catch (Exception e) {}
		
		return "";
	}
	
	

	public static void main(String[] args) {
		
		
		new AniDB().crawl();
		
	}
	
}
