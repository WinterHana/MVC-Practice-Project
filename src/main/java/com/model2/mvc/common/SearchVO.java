package com.model2.mvc.common;


public class SearchVO {
	
	private int page;
	String searchCondition;
	String searchKeyword;
	String searchKeywordSub;
	int pageUnit;
	int pageSize;
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() {
		return searchCondition;
	}
	
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	
	public String getSearchKeywordSub() {
		return searchKeywordSub;
	}

	public void setSearchKeywordSub(String searchKeywordSub) {
		this.searchKeywordSub = searchKeywordSub;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}