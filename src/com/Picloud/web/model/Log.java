package com.Picloud.web.model;

import com.Picloud.utils.JspUtil;

public class Log {
	//主键
	String key = "";
	
	//用户
	String uid = "";
	//时间
	String time = "";
	//操作
	String operation = "";
	
	/**
	 * 构造方法，为了进行排序，请使用该构造方法
	 * @param uid 用户id
	 * @param operation  操作
	 */
	public Log(String uid, String operation) {
		super();
		this.uid = uid;
		this.operation = operation;
		try {
			this.time = JspUtil.getCurrentDateStr();
			String max = "99999999999999999";
			double d1 =   Double.parseDouble(max);
			double d2 = Double.parseDouble(JspUtil.getCurrentDateMS());
			this.key = String.valueOf(d1-d2)+uid;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Log() {
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
