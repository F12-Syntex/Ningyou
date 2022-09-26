package com.ningyou.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class that allows for the quick creation of a Map, by adding keys and values in order.
 * {@code
 * AttributesMaker<String> mapMaker = new AttributesMaker<>();
 * mapMaker.push("Name");
 * mapMaker.push("Syntex");
 * mapMaker.push("Age");
 * mapMaker.push("19");
 * this will return a map of values {"Name": "syntex", "Age": "19"}
 * }
 * @author synte
 * @param <K> Data type for key and value
 */
public class AttributesMaker<K> {
	
	private K casheKey = null;
	private Map<K, K> map = new HashMap<>();
	
	/**
	 * append data to the Map, if no key exists this value will be pushed as a Key with the value of null, the next push command will replace this null value with data.
	 * @param data the value to be added
	 */
	public void push(K data) {
		if(casheKey == null) {
			casheKey = data;
			return;
		}else {
			map.put(casheKey, data);
			casheKey = null;
		}
	}
	
	public Map<K, K> get() {
		return this.map;
	}
	
	public void print() {
		this.get().entrySet().forEach(o -> {
			System.out.println(o.getKey() + " : " + o.getValue());
		});
	}

}
