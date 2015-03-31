package com.Picloud.web.thread;

import com.Picloud.image.ImageWriter;
import com.Picloud.web.dao.impl.InfoDaoImpl;

public class SyncTestThread extends Thread{
	private InfoDaoImpl infoDaoImpl;
	private String LocalPath;
	
	

	public SyncTestThread(InfoDaoImpl infoDaoImpl, String localPath) {
		super();
		this.infoDaoImpl = infoDaoImpl;
		LocalPath = localPath;
	}



	public void run() { 
		try {
			ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
			imageWriter.localDirSync(LocalPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
