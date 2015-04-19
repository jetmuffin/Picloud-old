package com.Picloud.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;

import com.Picloud.config.SystemConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.utils.PropertiesUtil;
import com.Picloud.web.dao.impl.DustbinDaoImpl;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.MapfileDaoImpl;
import com.Picloud.web.model.Dustbin;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;
import com.Picloud.web.model.User;
import com.Picloud.web.thread.MergeThread;
import com.Picloud.web.thread.SyncThread;

@Service
public class ImageUpdate {
	private ImageDaoImpl mImageDaoImpl;
	private InfoDaoImpl infoDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileDaoImpl mMapfileDaoImpl;
	private SystemConfig mSystemConfig;
	private DustbinDaoImpl mDustbinDaoImpl;
	private static final String LOCAL_UPLOAD_ROOT = "/upload";
	private static final String HDFS_UPLOAD_ROOT = "/upload";
	private static final String BigFile = "HdfsLargeFile";
	private static final String SmallFile = "HdfsSmallFile";
	private static final String LocalFile = "LocalFile";
	private static final String Deleted = "deleted";

	public ImageUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageUpdate(InfoDaoImpl infoDaoImpl) {
		this.infoDaoImpl = infoDaoImpl;
		this.mImageDaoImpl = infoDaoImpl.getmImageDaoImpl();
		this.mSystemConfig = infoDaoImpl.getmSystemConfig();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileDaoImpl = infoDaoImpl.getmMapfileDaoImpl();
		this.mDustbinDaoImpl=infoDaoImpl.getmDustbinDaoImpl();
	}
/**
 * 先进行删除。如果是小图片
 * 如果是小图片进行本地同步
 * 如果是大图片直接同步到mapfile
 * @param image
 * @param uid
 * @param space
 * @param imageName
 */
	public void updateImage(byte[] imagebyte, String uid, String space,String imageKey) {
		try {
			
			ImageDeleter deleter=new ImageDeleter(infoDaoImpl);
			Image image = mImageDaoImpl.find(imageKey);
			//deleteUpPicture(image);
			
			double fileLength = (double) imagebyte.length/ 1024 / 1024;
			boolean flag;
			if (fileLength > mSystemConfig.getMaxFileSize()) {

				flag=updateBigImage(imagebyte, uid, space,image);
			}
			else{
				flag=updateSmallImage(imagebyte, uid, space,image);
	
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/**
 * 更新大图片
 * @param item
 * @param uid
 * @param space
 * @return
 */
	public boolean updateBigImage(byte[] imagebyte, String uid, String space,Image image) {
		try {
			boolean flag = false;
			// HDFS文件名
			final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid
					+ "/LargeFile/" + space + '/';
			
			String filePath = hdfsPath + image.getName();		
			ByteArrayInputStream input = new ByteArrayInputStream(imagebyte);
			HdfsHandler hdfsHandler=new HdfsHandler();
			hdfsHandler.upLoad(input, hdfsPath);
			BufferedImage bufferedImage = ImageIO.read(input);
			String width = Integer.toString(bufferedImage.getWidth());
			String height = Integer.toString(bufferedImage.getHeight());
			image.setPath(filePath);
			image.setHeight(height);
			image.setWidth(width);
			mImageDaoImpl.update(image);
			
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
}

	/**
	 * 如果原图片是本地图片，直接写入更新hbase信息
	 * 如果员图片是mapfile图片，找到原来的图片信息，写入到垃圾表中，并更新hbase信息
	 * 检查本地图片是否可以打包上传
	 * @param item
	 * @param uid
	 * @param space
	 * @return
	 */
	public boolean updateSmallImage(byte[] imagebyte, String uid, String space,Image image) {
		try {
		// 本地目录为“根目录/用户名/时间戳"
		final String LocalUidPath = PropertiesUtil.getValue("systemPath")
				+ LOCAL_UPLOAD_ROOT + "/" + uid + '/';
		final String LocalPath = LocalUidPath + '/' + space + '/';
		ByteArrayInputStream input = new ByteArrayInputStream(imagebyte);
		BufferedImage bufferedImage = ImageIO.read(input);
		String width = Integer.toString(bufferedImage.getWidth());
		String height = Integer.toString(bufferedImage.getHeight());
		
		String imageName=image.getName();
		File file = new File(LocalPath, imageName);
		ImageWriter writer = new ImageWriter(infoDaoImpl);
		
		HdfsHandler hdfsHandler=new HdfsHandler();
		hdfsHandler.writeByteImage(imagebyte, LocalPath, imageName);
		if (image.getStatus().equals("LocalFile")) {
				
		} else {
			
			String path=image.getPath();
			String map_key = path.substring(path.length() - 14,path.length()) + image.getUid();
			Mapfile mapfile = mMapfileDaoImpl.find(map_key);
			//将图片名+path写入垃圾表中
			Dustbin dustbin=new Dustbin(image.getName(),mapfile.getName());
			mDustbinDaoImpl.add(dustbin);
			//建立线程检查mapfile文件是否需要重写
			MergeThread mergeThread = new MergeThread(infoDaoImpl);
			mergeThread.setProperty(mapfile, image);
			mergeThread.start();
		}
		image.setPath(LocalPath);
		image.setStatus("LocalFile");
		image.setHeight(height);
		image.setWidth(width);
		mImageDaoImpl.update(image);
		
		//检查文件夹大小
				double maxSyncSize = mSystemConfig.getMaxSyncSize();
				File LocalDir = new File(LocalPath);
				double DirSize = writer.getDirSize(LocalDir);
					if (DirSize > maxSyncSize) {
						SyncThread syncThread = new SyncThread(infoDaoImpl);
						syncThread.SetProperty(LocalPath, uid, space);
						syncThread.start();
						}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}




	/**
	 * 删除图片
	 * 
	 * @param pic
	 *            要删除的图片
	 * @return 返回成功与否的标志
	 * @throws Exception 
	 */
	private boolean deleteUpPicture(Image image) throws Exception {
		String status = image.getStatus();
		boolean flag = false;

		// 根据图片状态的不同采取不同的方式
		try {
			//大文件
			if (status.equals(BigFile)) {
				flag = mHdfsHandler.deletePath(image.getPath());
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
//				MergeThread mergeThread = new MergeThread(infoDaoImpl);
//				mergeThread.start();
				
				//本地缓存
			} else if (status.equals(LocalFile)) {
				File f = new File(image.getPath(), image.getName());
				if (f.exists()) {
					flag = f.delete();
					System.out.println("本地文件删除成功");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

}
