package com.ningyou.modals;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NinjgouList<T> extends ArrayList<T>{
	private static final long serialVersionUID = 1L;
	
	public T getFirst() {
		return this.get(0);
	}
	
	public T getLast() {
		return this.get(this.size() - 1);
	}
	
	public T getRandom() {
		return this.get(ThreadLocalRandom.current().nextInt(this.size()));
	}
	
	@Override
	public String toString() {
		
		StringBuilder values = new StringBuilder();
		
		this.forEach(i -> {
			values.append(i + ", ");
		});
		
		if(values.length() == 0) {
			return "{}";
		}
		
		return "{" + values.substring(0, values.length() - 2) + "}";
	}

}
