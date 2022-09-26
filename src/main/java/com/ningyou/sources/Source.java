package com.ningyou.sources;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.TimeValue;

import com.ningyou.modals.NinjgouList;
import com.ningyou.queries.QueryEntity;

public abstract class Source<T extends QueryEntity> {
	
	public String domain;
	
	public Source(String domain) {
		this.domain = domain;
	}
	
	protected Logger log = Logger.getLogger(this.getClass().getName());  

	public abstract NinjgouList<T> submitQuery(String query);
	
	public Callable<List<T>> retrieveQueryResultsLater(String query) {
		return () -> {
			return submitQuery(query);
		};
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
	
	public <A> A cast(Class<A> clazz) {
	    try {
	        return clazz.cast(this);
	    } catch(ClassCastException e) {
	        return null;
	    }
	}
	
	@Override
	public String toString() {
		  StringBuilder result = new StringBuilder();
		  String newLine = System.getProperty("line.separator");

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append(newLine);

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for (Field field : fields) {
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(newLine);
		  }
		  result.append("}");

		  return result.toString();
		}

}