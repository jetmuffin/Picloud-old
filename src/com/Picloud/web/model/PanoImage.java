package com.Picloud.web.model;

import java.util.List;

import com.Picloud.utils.StringSplit;

public class PanoImage {
	//主键
	String key="";
	//图片名称
	String name = "";
	//场景数量
	String  number;
	//所属用户
	String uid = "";
	//上传时间
	String createTime = "";
	//介绍信息
	String info = "";
	//路径
	String path="";
	//场景描述
	String desc="";
	//场景
	List<PanoScene> scene ;
	
	String mus_path="";
	
	public PanoImage() {
		super();
	}
	public PanoImage(String key, String name, String number, String uid,
			String createTime, String info, String path, String desc, String mus_path) {
		super();
		this.key = key;
		this.name = name;
		this.number = number;
		this.uid = uid;
		this.createTime = createTime;
		this.info = info;
		this.path = path;
		this.desc = desc;
		this.mus_path = mus_path;
		for(int i = 0; i < Integer.valueOf(number); i++){
			PanoScene sc = new PanoScene();
			sc.setName(name+String.valueOf(i+1));
			sc.setPath(path+"/"+name+"/"+sc.getName());
			sc.setDesc(StringSplit.descSplit(desc, i));
			this.scene.add(sc);
		}
		
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<PanoScene> getScene() {
		return scene;
	}
	public void setScene(List<PanoScene> scene) {
		this.scene = scene;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
		//需要修改
		for(int i = 0; i < Integer.valueOf(number); i++){
			PanoScene sc = new PanoScene();
			sc.setName(name+String.valueOf(i+1));
			sc.setPath(path+"/"+name+"/"+sc.getName());
			sc.setDesc(StringSplit.descSplit(desc, i));
			this.scene.add(sc);
		}
	}
	public String getMus_path() {
		return mus_path;
	}
	public void setMus_path(String mus_path) {
		this.mus_path = mus_path;
	}

}
