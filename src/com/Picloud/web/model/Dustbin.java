package com.Picloud.web.model;

public class Dustbin {
	
	String key;
	String picName;
	String mapfileName;
	
	public Dustbin(String picName, String mapfileName) {
		super();
		this.picName = picName;
		this.mapfileName = mapfileName;
		this.key = mapfileName + picName;
	}
	public Dustbin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getMapfileName() {
		return mapfileName;
	}
	public void setMapfileName(String mapfileName) {
		this.mapfileName = mapfileName;
	}

}
