package com.ningyou.modals;

import java.util.Comparator;
import java.util.List;

public class ChapterManager extends NinjgouList<Chapter>{
	private static final long serialVersionUID = 1L;
	
	private NinjgouList<Chapter> chapters;

	public ChapterManager(List<Chapter> chapters) {
		this.chapters = new NinjgouList<>();
		this.chapters.addAll(chapters);
		
		//add chapters in order
		chapters.sort(new Comparator<Chapter>() {
			@Override
			public int compare(Chapter o1, Chapter o2) {
				if(o1.chapter > o2.chapter) {
					return 1;
				}
				if(o1.chapter < o2.chapter) {
					return -1;
				}
				return 0;
			}
		});
		
		this.addAll(chapters);
	}
	public ChapterManager() {
		this.chapters = new NinjgouList<Chapter>();
	}
	
	
	
	
	
	

}
