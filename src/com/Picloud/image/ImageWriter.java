package com.Picloud.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Picloud.config.SystemConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.utils.DateUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Service
public class ImageWriter {
	private static final double MAX_SYNC_SIZE = 1.0;
	private static final double MAX_FILE_SIZE = 2.0;     
	private static final String LOCAL_UPLOAD_ROOT  =  "/upload";
	private static final String HDFS_UPLOAD_ROOT = "/upload";
	//TODO 全局变量设置
	
	private InfoDaoImpl infoDaoImpl;
	private ImageDaoImpl mImageDaoImpl;
	private LogDaoImpl mLogDaoImpl;
	private UserDaoImpl mUserDaoImpl;
	private SpaceDaoImpl mSpaceDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileHandler mMapfileHandler;
	
	
	public ImageWriter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageWriter(InfoDaoImpl infoDaoImpl){
		this.infoDaoImpl = infoDaoImpl;
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mImageDaoImpl = infoDaoImpl.getmImageDaoImpl();
		this.mUserDaoImpl = infoDaoImpl.getmUserDaoImpl();
		this.mLogDaoImpl = infoDaoImpl.getmLogDaoImpl();
		this.mSpaceDaoImpl = infoDaoImpl.getmSpaceDaoImpl();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileHandler = infoDaoImpl.getmMapfileHandler();
	}
	
	public boolean write(FileItem item,  String uid ,String space) throws Exception {
		Image image = searchFile(item,uid);
		boolean flag;
		if (image != null) {
			System.out.println("文件已存在(hbase)");
			return false;
		}
		double fileLength = (double) item.getSize() / 1024 / 1024;
		// 文件大小判断
		if (fileLength > MAX_FILE_SIZE) {
			flag = uploadToHdfs(item, uid , space);
		} else {
			flag = uploadToLocal(item, uid,space);
		}
		return flag;
	}
	
	public  Image searchFile(FileItem item,String uid) throws Exception {
		String key = EncryptUtil.imageEncryptKey( item.getName(), uid);
		Image image = mImageDaoImpl.find(key);
		if(image != null){
			return image;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取文件集合大小
	 * @author Jet-Muffin
	 * @param List<FileItem> 文件集合
	 * @return
	 */
	public static  long fileListLength(List<FileItem> items){
		long TotalLength = 0;
		for(FileItem item:items){
			TotalLength += item.getSize();
		}
		return TotalLength;
	}
	
	/**
	 * 直接将文件集合传到HDFS
	 * 
	 * @author Jet-Muffin
	 * @param List
	 * @param items 文件集合
	 * @param fileName
	 *            HDFS中文件夹名
	 * @return
	 */
	public  boolean uploadToHdfs(FileItem item, String uid , String space) {
		try {
			boolean flag = false;
			//HDFS文件名
			final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/LargeFile/" + space + '/';
			
			String filePath = hdfsPath + item.getName();
			InputStream uploadedStream = item.getInputStream();
			flag = mHdfsHandler.upLoad(uploadedStream, filePath);
			//更新数据库
			updateHbase(item, "HdfsLargeFile", hdfsPath, uid, space);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *  重写方法直接将文件集合传到HDFS
	 * 
	 * @author Jet-Muffin
	 * @param hdfsPath 文件路径
	 * @param List
	 * @param items 文件集合
	 * @param fileName
	 *            HDFS中文件夹名
	 * @return
	 */
	public  boolean uploadToHdfs(String hdfsPath,FileItem item, String uid) {
		try {
			boolean flag;
			
			//HDFS文件名
			String filePath = hdfsPath +item.getName() ;
			InputStream uploadedStream = item.getInputStream();
			flag = mHdfsHandler.upLoad(uploadedStream, filePath);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将byte型文件写入hdfs中, 代码未测试
	 * @author mpj
	 * @param buffer byte数组文件
	 * @param uid 
	 * @param space
	 * @param name 文件名
	 * @return
	 */
	public boolean uploadToHdfs(byte buffer [],String uid,String space,String name){
		try {
		boolean flag;
		final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + uid + "/LargeFile/" + space + '/';
		String filePath = hdfsPath +name;
		ByteArrayInputStream in = new ByteArrayInputStream(buffer); 
		flag = mHdfsHandler.upLoad(in, filePath);
		System.out.println("成功将byte数组写入hdfs中");
		// 更新图片相关信息
		updateImage(uid,name,hdfsPath);
		return flag;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
	/**
	 *  将图片缓存至本地
	 * @param FileItem item 文件集合
	 * @param File 本地文件对象
	 * @return
	 */
	public  boolean uploadToLocal(FileItem  item,  String uid , String space) {
		try {		
			
			//本地目录为“根目录/用户名/时间戳"
			final String LocalUidPath = SystemConfig.getSystemPath()
					+ LOCAL_UPLOAD_ROOT + "/" + uid + '/';
			final String LocalPath = LocalUidPath + '/' + space + '/' ;
			
			//文件是否存在
		    File LocalUidDir = new File(LocalUidPath);
            if(!LocalUidDir.exists()){
            	LocalUidDir.mkdir();
            }	 
				
				//文件是否存在
			    File LocalDir = new File(LocalPath);
	            if(!LocalDir.exists()){
	            	LocalDir.mkdir();
	            }	 
	            
				String fileName = item.getName();
				
				File file = new File(LocalPath, fileName);
				if (file.exists()) {
					System.out.println("Local file exists!");
					return false;
				} else {
					item.write(file);
				}
				updateHbase(item, "LocalFile", LocalPath, uid, space);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 本地与云端同步操作（线程）
	 * @author Jet-Muffin
	 * @param LocalPath
	 * @throws Exception 
	 */
	public  void  localDirSync(String LocalPath, String uid , String spaceKey) throws Exception {
		File LocalDir = new File(LocalPath);
		double DirSize = getDirSize(LocalDir);
		String filePath =  HDFS_UPLOAD_ROOT + "/" + uid + "/SmallFile/" + DateUtil.getCurrentDateStr();
		
		if(DirSize > MAX_SYNC_SIZE) {
            File[] items = LocalDir.listFiles();    
            //文件按文件名排序
            Arrays.sort(items,new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            
            //用Mapfile处理
            mMapfileHandler.packageToHdfs(items, filePath, uid, spaceKey);
            deleteFile(LocalDir);
            System.out.println("同步成功！");
		}
	}
	
	
	/**
	 * 递归清空文件夹
	 * @author Jet-Muffin   
	 * @param file
	 */
	private  void deleteFile(File file) {
		if (file.exists()) {		// 判断文件是否存在
			if (file.isFile()) {	// 判断是否是文件
				file.delete();		// 删除文件
			} else if (file.isDirectory()) {	
				File[] files = file.listFiles();		
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
		} else {
			System.out.println("所删除的文件不存在");
		}
	}
	   
	/**
	 * 获取文件夹大小
	 * @author Jet-Muffin
	 * @param file
	 * @return double 文件夹大小
	 */
	public  double getDirSize(File file) {     
        if (file.exists()) {     
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += getDirSize(f);     
                return size;     
            } else {				//如果是文件则直接返回其大小,以“兆”为单位   
                double size = (double) file.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");     
            return 0.0;     
        }     
    }     
	

	
	/**
	 * 写入图片信息，更新空间和用户的信息
	 * @param item
	 * @param status
	 * @param path
	 * @param usr
	 * @param space
	 * @throws Exception 
	 */
	public void updateHbase(FileItem item,String status, String path, String uid, String spaceKey ) throws Exception{
		Image image = new Image(item);
		image.setKey(EncryptUtil.imageEncryptKey(image.getName(), uid));
		image.setStatus(status);
		image.setPath(path);
		image.setUid(uid);
		image.setSpace(spaceKey);
		mImageDaoImpl.add(image);
		
		String imageName = item.getName();
		String operation = "上传了图片" + imageName;
		Log log = new Log(uid,operation);
		mLogDaoImpl.add(log);
		
		User user = mUserDaoImpl.find(uid);
		Space space = mSpaceDaoImpl.find(spaceKey);
		
		//空间图片数量增加1
		int spaceNum = Integer.parseInt(space.getNumber());
		space.setNumber(String.valueOf(spaceNum + 1));
		
		// 用户的图片数量加1
		int imageNum=Integer.parseInt(user.getImageNum());
		user.setImageNum(Integer.toString(imageNum + 1));
		
		//空间容量增加
		double d1 = Double.parseDouble(space.getStorage());
		double d2 = Double.parseDouble(image.getSize());
		space.setStorage(String.valueOf(d1+d2));
		
		//用户的容量增加
		d1=Double.parseDouble(user.getImageTotalSize());
	    user.setImageTotalSize(Double.toString(d1+d2));
		
	    //更新空间和用户信息
	    mUserDaoImpl.update(user);
	    mSpaceDaoImpl.update(space);
		
	}
	
	/**
	 * 图片修改后在hbase对图片信息进行修改，现在只是更改图片的路径信息
	 * @param user 用户
	 * @param name 图片名字 
	 * @param hdfsPath 图片的路径
	 * @return
	 * @throws Exception 
	 */
	public boolean updateImage(String uid,String imageName,String hdfsPath) throws Exception{
		Image image = mImageDaoImpl.find(EncryptUtil.imageEncryptKey(imageName, uid));
		//对图片的路径和状态进行修改，其他保持不变
		image.setPath(hdfsPath);
		image.setStatus("HdfsLargeFile");
		mImageDaoImpl.update(image);
		System.out.println("更新图片信息成功！");
		return true;
	}
}
