package com.Picloud.web.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Picloud.image.ImageWriter;
import com.Picloud.web.dao.impl.InfoDaoImpl;

@Service
public class SyncThread extends Thread {
	private String LocalPath; 
	private String uid; 
	private String space;
	private InfoDaoImpl infoDaoImpl;
	
	
	public SyncThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SyncThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public SyncThread(InfoDaoImpl  infoDaoImpl) { 
		this.infoDaoImpl = infoDaoImpl;
	} 
	
	public void SetProperty(String LocalPath,String uid,String space){
		this.LocalPath = LocalPath; 
		this.uid = uid;
		this.space = space;
	}
	
	
	@Override
	public String toString() {
		return "SyncThread [LocalPath=" + LocalPath + ", uid=" + uid
				+ ", space=" + space + ", infoDaoImpl=" + infoDaoImpl + "]";
	}

	public void run() { 
		try {
			ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
			imageWriter.localDirSync(LocalPath, uid ,space);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
