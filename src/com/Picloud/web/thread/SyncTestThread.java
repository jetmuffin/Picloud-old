package com.Picloud.web.thread;

import com.Picloud.image.ImageWriter;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.test.TestImageWriter;

public class SyncTestThread extends Thread{
	private InfoDaoImpl infoDaoImpl;
	private String LocalPath;
	private String Method;
	

	public SyncTestThread(InfoDaoImpl infoDaoImpl, String localPath,String Method) {
		super();
		this.infoDaoImpl = infoDaoImpl;
		LocalPath = localPath;
		this.Method = Method;
	}



	public void run() { 
		try {
			TestImageWriter testImageWriter = new TestImageWriter(infoDaoImpl,Method);
			testImageWriter.localDirSync(LocalPath,Method);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
