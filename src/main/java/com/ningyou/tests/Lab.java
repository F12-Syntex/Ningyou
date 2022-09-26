package com.ningyou.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.TimeValue;

import com.ningyou.utils.Extraction;

public class Lab {

	public static void main(String[] args) {	
		//get html
		 String html = readURL("https://readmanganato.com/manga-om991495");
		
		//String html = readURL("https://w10.mangayeh.com/manga/bug-player");
		//String html = readURL("http://mangastream.mobi/manga/adelia-flowers-bondage");
			
		
		
		//System.out.println(html);
		
		List<String> urls = new ArrayList<>();
		String[] counter = {""};
		
		Extraction.of(html).forEachAsExtraction("href=\"", o -> {
			String url = o.beforeQuotation();
			String[] splitter = url.split("/");
			
			if(url.contains("chapter") && splitter.length > 2) {
				//String[] chapterSplitter = splitter[splitter.length-1].split("-");
				//System.out.println(chapterSplitter[chapterSplitter.length - 1] + " : " + url);
				//Double chapter = Double.parseDouble(chapterSplitter[chapterSplitter.length - 1]);
				
				String id = "";
				
				/*
				if(splitter[splitter.length-1].contains("[.]")) {
					id = splitter[splitter.length-1].split(chapter+"")[0];	
				}else {
					id = splitter[splitter.length-1].split(((int)Math.round(chapter))+"")[0];
				}
				*/
				id = splitter[splitter.length - 2];
				
				//System.out.println(url + " -> " + splitter[splitter.length-2] + " c -> " + chapter);
				urls.add(url);
				//System.out.println(counter[0] + " : " + id);
				if(!counter[0].equals(id)) {
					counter[0] = id;
					urls.clear();
				}
				counter[0] = id;
			}
		});
		
		List<String> data = urls.stream().distinct().collect(Collectors.toList());
	
		System.out.println("printing");
		data.forEach(o -> {
			System.out.println("a: " + o);
		});
	
	}

	public static String readURL(String page) {
		
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
}
