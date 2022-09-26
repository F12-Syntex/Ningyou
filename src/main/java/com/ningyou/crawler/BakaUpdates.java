package com.ningyou.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.TimeValue;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ningyou.utils.Extraction;

public class BakaUpdates {
	
	public ExecutorService threadpool = Executors.newFixedThreadPool(10); 
	
	public void crawl() {
		
		
		JSONObject main = new JSONObject();
		
		AtomicInteger processed = new AtomicInteger(0);
		
		int[] amount = {0};
		
		for(int page = 1; page < 101; page++) {
		
			String html = this.readURL("https://www.mangaupdates.com/series.html?page=" + page + "&perpage=100");
			
			Extraction.of(html).forEachAsExtraction("<div class=\"col-12 col-lg-6 p-3 text\">", i -> {
				
				String name = StringEscapeUtils.unescapeHtml4(i.clone().after("<u><b>").before("</b>").retrieve());
				String img = i.clone().after("<img src='").before("'>").retrieve();				
				JSONArray genre = new JSONArray();
				
				i.clone().after("none\" title=\"").before("\"").forEach(",", o -> {
					genre.put(o.substring(1));
				});
				
				String date = i.clone().after("<div class='text'>").after("<div class='text'>").before("</div>").before(" ").retrieve();
				if(date.isEmpty()) date = "	N/A";
				
				JSONObject manga = new JSONObject()
						;
				
				manga.put("title", name);
				manga.put("genre", genre);
				manga.put("release", date);
				manga.put("img", img);
			
				
				try {
				String desc = StringEscapeUtils.unescapeHtml4(i.clone().after("<div class='text flex-grow-1'>").before("</div>").retrieve().split("Official English Translation")[0].trim());
				manga.put("desc", desc);
				}catch (Exception e) {
					manga.put("desc", "");
				}
				
				main.put(amount + "", manga);
				amount[0]++;
					
			});
				
			System.out.println("Processed " + processed.incrementAndGet() + "/100");
		
		}
		
		threadpool.shutdown();
        try {
			while (!threadpool.awaitTermination(100, TimeUnit.MILLISECONDS)) {}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
		      FileWriter myWriter = new FileWriter("C:\\Users\\synte\\Desktop\\Mangas\\database1.json");
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
		
		
		new BakaUpdates().crawl();
		
	}
	
}
