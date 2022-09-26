package com.ningyou.modals;

public class Page{
	
	public String imgUrl;
	public int pageNumber;
	
	public Page(String imgUrl, int pageNumber) {
		this.imgUrl = imgUrl;
		this.pageNumber = pageNumber;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	@Override
	public String toString() {
		return this.imgUrl;
	}
	
}
