package com.Picloud.config;

import org.springframework.stereotype.Service;

@Service
public class SystemConfig {
	private String fileSystemPath = "localhost:9000";
	private String uploadPath = "hdfs://localhost:9000/upload";
	private String systemPath = "/home/hadoop/workspace";
	private String localUploadPath = "/home/hadoop/workspace/upload";
	private String imagePath = "http://localhost:8080/Picloud/server/";
	public static String getSystemPath() {
		return "/home/hadoop/workspace";
	}

	public String getFileSystemPath() {
		return fileSystemPath;
	}

	public void setFileSystemPath(String fileSystemPath) {
		this.fileSystemPath = fileSystemPath;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getLocalUploadPath() {
		return localUploadPath;
	}

	public void setLocalUploadPath(String localUploadPath) {
		this.localUploadPath = localUploadPath;
	}

	public void setSystemPath(String systemPath) {
		this.systemPath = systemPath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return imagePath;
	}

}
