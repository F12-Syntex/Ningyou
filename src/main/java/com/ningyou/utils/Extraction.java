package com.ningyou.utils;

import java.util.function.Consumer;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.TimeValue;

import com.ningyou.queries.QueryEntity;
import com.ningyou.sources.MangaSource;

/*
 * helper class to retrieve data from string
 * @author syntex
 */
public class Extraction {

	//text variable
	private String text;
	
	/*
	 * create an instance of string text
	 */
	public static Extraction of(String text) {
		Extraction extraction = new Extraction();
		extraction.text(text);
		return extraction;
	}
	
	/*
	 * set the string text
	 */
	public Extraction text(String text) {
		this.text = text;
		return this;
	}
	
	/*
	 * get string after a certain filter
	 * @param takes in the string, which will be used to return a string after the filter.
	 */
	public Extraction after(String filter) {
		String[] decompile = this.text.split(filter);
		
		String compile = "";
		for(int i = 1; i < decompile.length; i++) {
			compile+=decompile[i];
			if((i+1) < decompile.length) {
				compile+=filter;
			}
		}
		
		this.text = compile;
		return this;
	}
	

	/**
	 * @returns a queried entity from html
	 */
	public QueryEntity extract(MangaSource source) {

		String alt = "", img = "", url = "";
		
		boolean check[] = {false, false, false};
		
		//alts
		if(this.text.contains("title=\"")) {
			alt = this.clone().after("title=\"").beforeQuotation();
			check[0] = true;
		}
		if(this.text.contains("title\"") && !check[0]) {
			alt = this.ignoring("<span class=\"highlight-text\">", "</span>").after("title\"").between(">", "<").toString();
			check[0] = true;
		}
		
		//imgs
		if(this.text.contains("img src=\"")) {
			img = this.clone().after("img src=\"").beforeQuotation();
			check[1] = true;
		}
		if(this.text.contains("data src=\"") && !check[1]) {
			img = this.clone().after("data src=\"").beforeQuotation();
			check[1] = true;
		}
		
		//url
		if(this.text.contains("href")){
			url = this.afterHref().beforeQuotation();
			if(url.startsWith("/")) {
				url = source.domain + url;
			}
		}
		
		QueryEntity entity = new QueryEntity(alt.trim(), img.trim(), url.trim(), source);
		return entity;
	}
	
	public Extraction ignoring(String... blacklist) {
		String data = this.text;
		for(String i : blacklist.clone()) {
			data = data.replace(i, "");
		}
		return Extraction.of(data);
	}

	
	/*
	 * returns all the text before a string.
	 */
	public Extraction after(String... filter) {
		for(String i : filter.clone()) {
			this.after(i);
		}
		return this;
	}
	
	/*
	 * returns all the text between a string.
	 */
	public Extraction between(String after, String before) {
		return this.after(after).before(before);
	}
	
	
	/*
	 * returns all the text before some strings.
	 */
	public Extraction before(String... filter) {
		for(String i : filter.clone()) {
			this.before(i);
		}
		return this;
	}
	
	/*
	 * before quotation
	 */
	public String beforeQuotation() {
		return this.before("\"").text.trim();
	}
	
	/*
	 * after img src
	 */
	public Extraction afterImgSource() {
		return this.after("<img src=\"");
	}
	
	/*
	 * after img src
	 */
	public Extraction afterHref() {
		return this.after("href=\"");
	}
	
	/*
	 * after img src
	 */
	public Extraction afterDataSrc() {
		return this.after("data-src=\"");
	}
	
	/*
	 * after img src
	 */
	public Extraction afterTitle() {
		return this.after("title=\"");
	}
	
	public Extraction before(String filter) {
		this.text = this.text.split(filter)[0];
		return this;
	}
	
	public Extraction clone() {
		Extraction extraction = Extraction.of(text);
		return extraction;
	}
	
	/*
	 * iterate through all the element of filter filter.
	 */
	public void forEach(String filter, Consumer<String> consumer) {
		if(!this.text.contains(filter)) return;
		for(int i = 1; i < this.text.split(filter).length; i++) {
			consumer.accept(this.text.split(filter)[i]);
		}
	}
	
	/*
	 * iterate through all the element of filter filter.
	 */
	public void forEachAsExtraction(String filter, Consumer<Extraction> consumer) {
		if(!this.text.contains(filter)) return;
		for(int i = 1; i < this.text.split(filter).length; i++) {
			consumer.accept(Extraction.of(this.text.split(filter)[i]));
		}
	}
	
	/*
	 * iterate through all the element of filter filter.
	 */
	public void forEachAsQueriedEntity(String filter, Consumer<QueryEntity> consumer, MangaSource source) {
		if(!this.text.contains(filter)) return;
		for(int i = 1; i < this.text.split(filter).length; i++) {
			consumer.accept(Extraction.of(this.text.split(filter)[i]).extract(source));
		}
	}
	
	/*
	 * retrieve the current string
	 */
	public String retrieve() {
		return this.text;
	}

	
	/*
	 * testing method
	 */
	public static void main(String[] args) {
		Extraction.of("asd1asd2a1a2a3a4a5asd4asd5").after("asd2").before("asd5", "asd4").forEach("a", System.out::println);			
	}
	
	@Override
	public String toString() {
		return this.retrieve();
	}
	
	/**
	 * @param url
	 * @return Extraction instance from url
	 */
	public Extraction ofURL(String url) {
		try {
			HttpGet request = new HttpGet(url);
			CloseableHttpClient client = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36").evictIdleConnections(TimeValue.ofSeconds(10)).build();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String dat = EntityUtils.toString(entity);
			return Extraction.of(dat);
		} catch (Exception e) {}
		return null;
	}
	
}
