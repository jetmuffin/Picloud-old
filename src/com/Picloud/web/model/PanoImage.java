package com.Picloud.web.model;

import java.util.ArrayList;
import java.util.List;

import com.Picloud.utils.StringSplit;

public class PanoImage {
	//主键
	String key="";
	//图片名称
	String name = "";
	String sceneName="";
	//场景数量
	String  number="0";
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
	List<PanoScene> scene = new ArrayList<PanoScene>();
	//
	private String type="";
	
	String mus_path="";
	
	public PanoImage() {
		super();
	}
	public PanoImage(String key, String name, String number, String uid,
			String createTime, String info, String path, String desc, String mus_path, String sceneName, String type) {
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
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	}
	public String getMus_path() {
		return mus_path;
	}
	public void setMus_path(String mus_path) {
		this.mus_path = mus_path;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	
	public void append(String name, String desc){
		this.sceneName = this.sceneName + '`' + name;
		this.desc = this.desc +'`'+desc;
		this.number = String.valueOf(Integer.valueOf(this.number)+1);
	}
	
	public void init(){
		for(int i = 1; i <= Integer.parseInt(number); i++){
			PanoScene sc = new PanoScene();
			sc.setName(StringSplit.descSplit(sceneName, i));
			sc.setPath(path+"/scene/"+i+"."+type);
			sc.setThumb(path+"/thumb/"+i+"."+type);
			sc.setDesc(StringSplit.descSplit(this.desc, i));
			this.scene.add(sc);
		}
	}
	@Override
	public String toString() {
		return "PanoImage [key=" + key + ", name=" + name + ", sceneName="
				+ sceneName + ", number=" + number + ", uid=" + uid
				+ ", createTime=" + createTime + ", info=" + info + ", path="
				+ path + ", desc=" + desc + ", scene=" + scene + ", mus_path="
				+ mus_path + "]";
	}

}
