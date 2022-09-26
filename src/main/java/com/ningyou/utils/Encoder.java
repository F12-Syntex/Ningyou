package com.ningyou.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Encoder {

	 public static String encode(String url){  
               try {  
            	    return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
               } catch (UnsupportedEncodingException e) {  
                    return "Issue while encoding" +e.getMessage();  
               }  
     }  

	 
	    public static InputStream openInputStream(String uri){
    		
	    	URL url = null;
    		try {
    			url = new URL(uri);
    		}catch (Exception e) {
    			try {
					url = new URL("https://c.tenor.com/IHdlTRsmcS4AAAAC/404.gif");
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
    		}
    		
    		try {
		        URLConnection uRLConnection = url.openConnection();
		        uRLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
		        return uRLConnection.getInputStream();
	    	}catch (Exception e) {
				e.printStackTrace();
				 System.err.println("Tried to open stream to " + url);
			}
	    	return null;
	    }
	    public static InputStream openInputStream(URL url){
	    	try {
		        URLConnection uRLConnection = url.openConnection();
		        uRLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
		        return uRLConnection.getInputStream();
	    	}catch (Exception e) {
				e.printStackTrace();
				 System.err.println("Tried to open stream to " + url);
			}
	    	return null;
	    }
	    public static InputStream openInputStream(URL url, String agent){
	    	try {
		        URLConnection uRLConnection = url.openConnection();
		        uRLConnection.setRequestProperty("User-Agent", agent);
		        return uRLConnection.getInputStream();
	    	}catch (Exception e) {
				e.printStackTrace();
				 System.err.println("Tried to open stream to " + url);
			}
	    	return null;
	    }
	 
	
}
