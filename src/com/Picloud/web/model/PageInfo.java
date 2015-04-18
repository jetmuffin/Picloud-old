package com.Picloud.web.model;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {

	private int num;
	private int page;
	private boolean ifHaveNext;
	private List<String> startKeys = new ArrayList<String>();
	public PageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageInfo(int num, int page, boolean ifHaveNext,
			List<String> startKeys) {
		super();
		this.num = num;
		this.page = page;
		this.ifHaveNext = ifHaveNext;
		this.startKeys = startKeys;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public boolean isIfHaveNext() {
		return ifHaveNext;
	}
	public void setIfHaveNext(boolean ifHaveNext) {
		this.ifHaveNext = ifHaveNext;
	}
	public List<String> getStartKeys() {
		return startKeys;
	}
	public void setStartKeys(List<String> startKeys) {
		this.startKeys = startKeys;
	}

}
