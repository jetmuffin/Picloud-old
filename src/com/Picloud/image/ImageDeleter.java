package com.Picloud.image;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.MapfileDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;
import com.Picloud.web.thread.MergeThread;

public class ImageDeleter {
	private static final String BigFile = "HdfsLargeFile";
	private static final String SmallFile = "HdfsSmallFile";
	private static final String LocalFile = "LocalFile";
	private static final String Deleted = "deleted";

	private InfoDaoImpl infoDaoImpl;
	private ImageDaoImpl mImageDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileHandler mMapfileHandler;
	private MapfileDaoImpl mMapfileDaoImpl;
	private UserDaoImpl mUserDaoImpl;
	private SpaceDaoImpl mSpaceDaoImpl;
	
	public ImageDeleter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageDeleter(InfoDaoImpl infoDaoImpl) {
		this.infoDaoImpl = infoDaoImpl;
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mImageDaoImpl = infoDaoImpl.getmImageDaoImpl();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileHandler = infoDaoImpl.getmMapfileHandler();
		this.mMapfileDaoImpl = infoDaoImpl.getmMapfileDaoImpl();
		this.mUserDaoImpl = infoDaoImpl.getmUserDaoImpl();
		this.mSpaceDaoImpl = infoDaoImpl.getmSpaceDaoImpl();
	}

	/**
	 * 删除图片
	 * 
	 * @param pic
	 *            要删除的图片
	 * @return 返回成功与否的标志
	 * @throws Exception 
	 */
	public boolean deletePicture(Image image) throws Exception {
		String status = image.getStatus();
		boolean flag = false;

		// 根据图片状态的不同采取不同的方式
		try {
			//大文件
			if (status.equals(BigFile)) {
				flag = mHdfsHandler.deletePath(image.getPath());
				mImageDaoImpl.delete(image);
				System.out.println("大文件删除成功");
				
				//小文件
			} else if (status.equals(SmallFile)) {
				// 将小文件和对应的mapfile进行标记
				image.setStatus(Deleted);
				String path = image.getPath();
				String map_key = path.substring(path.length() - 14,path.length()) + image.getUid();
				Mapfile mapfile = mMapfileDaoImpl.find(map_key);
				int flagnum = Integer.parseInt(mapfile.getFlagNum()) + 1;
				mapfile.setFlagNum(Integer.toString(flagnum));
				mMapfileDaoImpl.update(mapfile);
				mImageDaoImpl.update(image);
				flag = true;
				System.out.println("成功对小文件进行标记！");

				// 创建线程检查mapfile是否需要重写
				MergeThread mergeThread = new MergeThread(infoDaoImpl);
				mergeThread.setProperty(mapfile, image);
				mergeThread.start();
				
				//本地缓存
			} else if (status.equals(LocalFile)) {
				File f = new File(image.getPath(), image.getName());
				if (f.exists()) {
					flag = f.delete();
					mImageDaoImpl.delete(image);
					System.out.println("本地文件删除成功");
				}
			}
			// 更新用户和空间信息
			updateInfo(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * 删除图片更新用户和空间信息
	 * 
	 * @param pic
	 *            删除的图片
	 * @throws Exception 
	 */
	public void updateInfo(Image image) throws Exception {

		Space space = mSpaceDaoImpl.find(image.getSpace());
		User user = mUserDaoImpl.find(image.getUid());
		
		// 空间和用户的图片数量减1
		int number = Integer.parseInt(space.getNumber()) - 1;
		space.setNumber(Integer.toString(number));
		number = Integer.parseInt(user.getImageNum()) - 1;
		user.setImageNum(Integer.toString(number));

		// 空间和用户的容量减少
		double size = Double.parseDouble(space.getStorage())
				- Double.parseDouble(image.getSize());
		space.setStorage(Double.toString(size));
		size = Double.parseDouble(user.getImageTotalSize())
				- Double.parseDouble(image.getSize());
		user.setImageTotalSize(Double.toString(size));

		// 将修改后的spaceh和user更新到数据库
		mSpaceDaoImpl.update(space);
		mUserDaoImpl.update(user);
		System.out.println("空间和用户信息同步成功！");
	}
}
