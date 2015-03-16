package com.Picloud.config;

import org.springframework.stereotype.Service;

@Service
public class HdfsConfig {
	private String fileSystemPath;
	private String uploadPath;
	
	public HdfsConfig(String fileSystemPath, String uploadPath){
		this.fileSystemPath = fileSystemPath;
		this.uploadPath = uploadPath;
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
	
	
}
