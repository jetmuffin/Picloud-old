package com.Picloud.web.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.MapfileDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;


public class MergeThread extends Thread{
	private Mapfile mapfile;
	private Image image;
	private InfoDaoImpl infoDaoImpl;
	private ImageDaoImpl imageDaoImpl;
	private MapfileDaoImpl mapfileDaoImpl;
	private MapfileHandler mapfileHandler;
	
	public MergeThread(InfoDaoImpl infoDaoImpl) {
		this.infoDaoImpl = infoDaoImpl;
		this.imageDaoImpl = infoDaoImpl.getmImageDaoImpl();
		this.mapfileDaoImpl = infoDaoImpl.getmMapfileDaoImpl();
		this.mapfileHandler = infoDaoImpl.getmMapfileHandler();
	}

	public void setProperty(Mapfile mapfile,Image image){
		this.image = image;
		this.mapfile = mapfile;
	}
	
	public MergeThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MergeThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public MergeThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public void run(){
		// 如果标记的数目大于等于总文件数目的一半就对mapfile进行重写
		int flagnum = Integer.parseInt(mapfile.getFlagNum());
		int picnum = Integer.parseInt(mapfile.getPicNum());
		if (flagnum >= picnum / 2) {
			// 获取在同一个mapfile下的image
			List<Image> imagesAll;
			try {
				imagesAll = imageDaoImpl.getByValue("attr", "path", image.getPath());
				List<Image> images = new ArrayList<Image>();

				for (Image image : imagesAll) {
					// 如果文件被标记删除则从数据库中删除文件信息，否则将文件加入到images中
					if (image.getStatus().equals("deleted")) {
						imageDaoImpl.delete(image);
						System.out.println("成功从数据库删除小文件！");
					} else {
						images.add(image);
					}
				}
				mapfileDaoImpl.delete(mapfile);
				System.out.println("从数据库删除mapfile文件成功！");
				// 执行重写mapfile操作
				mapfileHandler.deleteImage(image.getPath(), images);
				System.out.println("删除文件成功！");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
