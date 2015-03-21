package com.Picloud.web.model;

public class ThreeDImage {
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
		//图片数量
		String number;
		
		public ThreeDImage(String key, String name, String uid,
				String createTime, String size,String number) {
			super();
			this.key = key;
			this.name = name;
			this.uid = uid;
			this.createTime = createTime;
			this.size = size;
			this.number = number;
		}
		
		public ThreeDImage() {
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

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		@Override
		public String toString() {
			return "ThreeDImage [key=" + key + ", name=" + name + ", uid="
					+ uid + ", createTime=" + createTime + ", size=" + size
					+ ", number=" + number + "]";
		}

}
