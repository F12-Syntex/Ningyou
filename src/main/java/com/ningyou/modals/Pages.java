package com.ningyou.modals;

import java.util.Comparator;
import java.util.List;

public class Pages extends NinjgouList<Page>{
	private static final long serialVersionUID = 1L;
	
	private NinjgouList<Page> pages;

	public Pages(List<Page> pages) {
		this.pages = new NinjgouList<>();
		this.pages.addAll(pages);
		
		//add chapters in order
		pages.sort(new Comparator<Page>() {
			@Override
			public int compare(Page o1, Page o2) {
				if(o1.pageNumber > o2.pageNumber) {
					return 1;
				}
				if(o1.pageNumber < o2.pageNumber) {
					return -1;
				}
				return 0;
			}
		});
		
		this.addAll(pages);
	}
	
	public Pages() {
		this.pages = new NinjgouList<Page>();
	}
	

}
