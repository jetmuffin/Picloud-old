package com.Picloud.web.model;

import java.util.List;

public class Module {
	public String name = "";
	public String title = "";
	public String icon = "";
	public String url = "";
	public List<Action> action = null;

	public Module(String name, String title, String icon, String url) {
		this.name = name;
		this.title = title;
		this.icon = icon;
		this.url = url;
	}

	public Module(String name, String title, String icon, String url,
			List<Action> action) {
		this.name = name;
		this.title = title;
		this.icon = icon;
		this.url = url;
		this.action = action;
	}

	public String getName() {
		return this.name;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getIcon() {
		return this.icon;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public List<Action> getAction() {
		return this.action;
	}
}
