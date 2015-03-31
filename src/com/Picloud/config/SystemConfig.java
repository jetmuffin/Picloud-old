package com.Picloud.config;

import org.springframework.stereotype.Service;

@Service
public class SystemConfig {
	private String fileSystemPath = "localhost:9000";
	private String uploadPath = "hdfs://localhost:9000/upload";
	private String systemPath = "/home/jeff/workspace";
	private String localUploadPath = "/home/jeff/workspace/upload";
	private String imagePath = "http://localhost:8080/Picloud/server/";
	private double maxSyncSize = 32.0;
	private double maxFileSize = 2.0;     
	private double syncSizeStep = 32.0;
	
	public void addSyncSize(){
		this.maxSyncSize = this.maxSyncSize + this.syncSizeStep;
	}
	
	public void subSyncSize(){
		this.maxSyncSize = this.maxSyncSize - this.syncSizeStep;
	}
	public double getSyncSizeStep() {
		return syncSizeStep;
	}


	public void setSyncSizeStep(double syncSizeStep) {
		this.syncSizeStep = syncSizeStep;
	}


	public static String getSystemPath() {
		return "/home/jeff/workspace";
	}


	public double getMaxSyncSize() {
		return maxSyncSize;
	}


	public void setMaxSyncSize(double maxSyncSize) {
		this.maxSyncSize = maxSyncSize;
	}


	public double getMaxFileSize() {
		return maxFileSize;
	}


	public void setMaxFileSize(double maxFileSize) {
		this.maxFileSize = maxFileSize;
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
