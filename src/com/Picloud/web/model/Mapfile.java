package com.Picloud.web.model;

public class Mapfile {
	//主键，用时间加用户表示
	
	/**
	 * Attr列族
	 */
	String key="";
	//名称
	String name = "";
	//用户
	String uid = "";
	
	/**
	 * Var列族
	 */
	//总标记数
	String  flagNum = "";
	//总图片数额
	String picNum = "";
	
	public Mapfile(String key, String name, String uid, String flagNum,
			String picNum) {
		super();
		this.key = key;
		this.name = name;
		this.uid = uid;
		this.flagNum = flagNum;
		this.picNum = picNum;
	}
	
	public Mapfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFlagNum() {
		return flagNum;
	}
	public void setFlagNum(String flagNum) {
		this.flagNum = flagNum;
	}
	public String getPicNum() {
		return picNum;
	}
	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Mapfile [key=" + key + ", name=" + name + ", uid=" + uid
				+ ", flagNum=" + flagNum + ", picNum=" + picNum + "]";
	}
	
	
}
