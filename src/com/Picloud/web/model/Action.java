package com.Picloud.web.model;

public class Action {
	public String name = "";
	public String title = "";
	public String url = "";

	public Action(String name, String title, String url) {
		super();
		this.name = name;
		this.title = title;
		this.url = url;
	}

	public Action(String name, String title) {
		this.name = name;
		this.title = title;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getTitle(){
		return this.title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
