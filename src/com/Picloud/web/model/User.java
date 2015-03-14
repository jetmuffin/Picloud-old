package com.Picloud.web.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	
	/**
	 *   主键
	 */
	String uid = "";
	
	/**
	 *   attr 属性列族
	 */
	//帐号类型
	String accountType = "";
	//电子邮箱
	String email = "";
	//上次登录
	String lastLogin = "";
	//网站
	String website = "";
	
	/**
	 *   vldt 验证列族
	 */
	//昵称
	String nickname = "";
	//密码
	String password = "";
	
	/**
	 *   pic 图片类族
	 */
	//图片数量
	String imageNum = "";
	//图片总大小
	String imageTotalSize = "";
	
	/**
	 *   space 空间列族
	 */
	//空间数量
	String spaceNum = "";

	
	public User() {
		super();
		imageNum = "0";
		imageTotalSize = "0";
		spaceNum = "0";
		lastLogin = Long.toString(new Date().getTime());
	}

	
	public User(String uid, String accountType, String email, String lastLogin,
			String website, String nickname, String password, String imageNum,
			String imageTotalSize, String spaceNum) {
		super();
		this.uid = uid;
		this.accountType = accountType;
		this.email = email;
		this.lastLogin = lastLogin;
		this.website = website;
		this.nickname = nickname;
		this.password = password;
		this.imageNum = imageNum;
		this.imageTotalSize = imageTotalSize;
		this.spaceNum = spaceNum;
	}

	@NotEmpty(message="用户名不能为空")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@NotEmpty(message="昵称不能为空")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Size(min=6,message="密码长度不能小于6位")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageNum() {
		return imageNum;
	}

	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}

	public String getImageTotalSize() {
		return imageTotalSize;
	}

	public void setImageTotalSize(String imageTotalSize) {
		this.imageTotalSize = imageTotalSize;
	}

	public String getSpaceNum() {
		return spaceNum;
	}

	public void setSpaceNum(String spaceNum) {
		this.spaceNum = spaceNum;
	}
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", accountType=" + accountType + ", email="
				+ email + ", lastLogin=" + lastLogin + ", website=" + website
				+ ", nickname=" + nickname + ", password=" + password
				+ ", imageNum=" + imageNum + ", imageTotalSize="
				+ imageTotalSize + ", spaceNum=" + spaceNum + "]";
	}
	
}
