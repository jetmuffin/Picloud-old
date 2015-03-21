package com.Picloud.web.model;

import java.text.DecimalFormat;

import org.hibernate.validator.constraints.NotEmpty;

public class Space {
	
	/**
	 * Attr列族
	 */
	//空间key TODO 规定
	String key="";
	//空间名称
	String name = "";
	//空间描述
	String desc = "";
	//空间封面
	String cover = "";
	//所属用户
	String uid = "";
	
	/**
	 * Var列族
	 */
	//已用容量
	String storage  = "";
	//图片数量
	String number = "";
	
	
	public Space() {
		super();
		number = "0";
		storage = "0";
		// TODO Auto-generated constructor stub
	}
	
	
	public Space(String key, String name, String desc, String cover,
			String uid, String storage, String number) {
		super();
		this.key = key;
		this.name = name;
		this.desc = desc;
		this.cover = cover;
		this.uid = uid;
		this.storage = storage;
		this.number = number;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@NotEmpty(message="空间名不能为空")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotEmpty(message="空间描述不能为空")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		double d = Double.parseDouble(storage);
		DecimalFormat df  = new DecimalFormat("######0.00");  
		this.storage = df.format(d);
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Space [key=" + key + ", name=" + name + ", desc=" + desc
				+ ", cover=" + cover + ", uid=" + uid + ", storage=" + storage
				+ ", number=" + number + "]";
	}
	
	
}
