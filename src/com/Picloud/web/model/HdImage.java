package com.Picloud.web.model;

public class HdImage {
		//主键
		String key="";
		//图片名称
		String name = "";

		//所属用户
		String uid = "";
		//上传时间
		String createTime = "";
		//图片大小
		String size = "";
		
		public HdImage(String key, String name, String uid, String createTime,
				String size) {
			super();
			this.key = key;
			this.name = name;
			this.uid = uid;
			this.createTime = createTime;
			this.size = size;
		}
		
		public HdImage() {
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
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		
		
}
