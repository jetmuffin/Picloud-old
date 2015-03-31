package com.Picloud.web.model;

import java.io.File;

import org.apache.commons.fileupload.FileItem;

import com.Picloud.utils.JspUtil;

public class Image {
	
	/**
	 * 主键
	 */
	
	//图片的key，为图片名和用户名经过加密得到
	String key="";

	/**
	 * attr列族
	 */
	
	//图片名
	String name = "";
	//图片大小
	String size = "";
	//图片类型
	String type = "";
	//所属图片空间
	String space = "";
	//所属用户
	String uid = "";
	//创建时间
	String createTime = "";
	//图片宽度
	String width = "";
	//图片高度
	String height="";
	
	/**
	 * var列族
	 */
	//图片所在路径
	String path="";
	//当前状态，三种状态分别用...表示
	String status = "";
	//上次修改时间
	String updateTime = "";
	//访问次数
	String visitCount = "";
	
	
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//构造方法 FileItem
	public Image(FileItem item) throws Exception {
		this.name = item.getName();
		this.size = Double.toString((double) item.getSize() /1024 /1024);
		this.type = this.name.substring(this.name.lastIndexOf(".")+1);
		this.createTime = JspUtil.getCurrentDateStr();

	}
	
	//构造方法 File
	public Image(File file) throws Exception {
		this.name = file.getName();
		this.size = Double.toString((double)  file.length() /1024 /1024);
		this.type = this.name.substring(this.name.lastIndexOf(".")+1);
		this.createTime = JspUtil.getCurrentDateStr();
	}
	
	//全参数构造方法
	public Image(String key, String name, String size, String type,
			String space, String uid, String createTime, String width,
			String height, String path, String status, String updateTime,
			String visitCount) {
		super();
		this.key = key;
		this.name = name;
		this.size = size;
		this.type = type;
		this.space = space;
		this.uid = uid;
		this.createTime = createTime;
		this.width = width;
		this.height = height;
		this.path = path;
		this.status = status;
		this.updateTime = updateTime;
		this.visitCount = visitCount;
	}

	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}

	@Override
	public String toString() {
		return "Image [key=" + key + ", name=" + name + ", size=" + size
				+ ", type=" + type + ", space=" + space + ", uid=" + uid
				+ ", createTime=" + createTime + ", width=" + width
				+ ", height=" + height + ", path=" + path + ", status="
				+ status + ", updateTime=" + updateTime + ", visitCount="
				+ visitCount + "]";
	}
	
	
}
