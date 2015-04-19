package com.Picloud.web.test;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.fileupload.FileItem;

import com.Picloud.config.SystemConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.hdfs.SequencefileHandler;
import com.Picloud.utils.JspUtil;
import com.Picloud.utils.PropertiesUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.thread.SyncTestThread;

public class TestImageWriter {
	private static final String LOCAL_UPLOAD_ROOT = "/upload";
	private static final String HDFS_UPLOAD_ROOT = "/upload";
	// TODO 全局变量设置
	private String Method;
	private SystemConfig mSystemConfig;
	private InfoDaoImpl infoDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileHandler mMapfileHandler;
	private SequencefileHandler mSequencefileHandler;
	
	public TestImageWriter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestImageWriter(InfoDaoImpl infoDaoImpl,String Method) {
		this.infoDaoImpl = infoDaoImpl;
		this.Method = Method;
		this.mSystemConfig = infoDaoImpl.getmSystemConfig();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileHandler = infoDaoImpl.getmMapfileHandler();
		this.mSequencefileHandler = infoDaoImpl.getmSequencefileHandler();
	}
	
	/**
	 * 供测试用的写入接口
	 * @param item
	 * @return
	 */
	public boolean write(FileItem item){
		boolean flag;
		final String LocalPath = PropertiesUtil.getValue("systemPath")
				+ LOCAL_UPLOAD_ROOT + "/test/";
		double fileLength = (double) item.getSize() / 1024 / 1024;
		// 文件大小判断
		if (fileLength > mSystemConfig.getMaxFileSize()) {
			flag = uploadToHdfs(item);
		} else {
			flag = uploadToLocal(item);
		}
		//检查文件夹大小
		File LocalDir = new File(LocalPath);
		double DirSize = getDirSize(LocalDir);
			if (DirSize > mSystemConfig.getMaxSyncSize()) {
				SyncTestThread syncTestThread = new SyncTestThread(infoDaoImpl,LocalPath,Method);
				syncTestThread.start();
				}
		return flag;
	}
	
	/**
	 * 供测试用的接口 同步到HDFS
	 * @param item
	 * @param uid
	 * @param space
	 * @return
	 */
	public boolean uploadToHdfs(FileItem item) {
		try {
			boolean flag = false;
			// HDFS文件名
			final String hdfsPath = HDFS_UPLOAD_ROOT + "/" + "test"
					+ "/namenode/";

			String filePath = hdfsPath + item.getName();
			InputStream uploadedStream = item.getInputStream();
			flag = mHdfsHandler.upLoad(uploadedStream, filePath);
			// 更新数据库
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 供测试用的接口 上传到本地
	 * @param item
	 * @return
	 */
	public boolean uploadToLocal(FileItem item) {
		try {

			// 本地目录为“根目录/用户名/时间戳"
			final String LocalPath = PropertiesUtil.getValue("systemPath")
					+ LOCAL_UPLOAD_ROOT + "/test/";
			// 文件是否存在
			File LocalUidDir = new File(LocalPath);
			if (!LocalUidDir.exists()) {
				LocalUidDir.mkdir();
			}
			
			String fileName = item.getName();
			File file = new File(LocalPath, fileName);
			if (file.exists()) {
				System.out.println("Local file exists!");
				return false;
			} else {
				item.write(file);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 同步函数 供测试用
	 * @param LocalPath
	 * @param uid
	 * @param spaceKey
	 * @throws Exception
	 */
	public void localDirSync(String LocalPath,String Method)throws Exception {
		File LocalDir = new File(LocalPath);
		double DirSize = getDirSize(LocalDir);
		
			mSystemConfig.addSyncSize();
			File[] items = LocalDir.listFiles();
			// 文件按文件名排序
			Arrays.sort(items, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			
			// 用Mapfile处理
			if(Method.equals("mapfile")){
				String filePath = HDFS_UPLOAD_ROOT + "/test/mapfile/"
						+ JspUtil.getCurrentDateStr();
				mMapfileHandler.packageToHdfs(items, filePath);
			}		else if(Method.equals("sequencefile")){
				String filePath = HDFS_UPLOAD_ROOT + "/test/sequencefile/"
						+ JspUtil.getCurrentDateStr();
				mSequencefileHandler.packageToHdfs(items, filePath);
			}
	//		deleteAllFile(LocalDir);
			mSystemConfig.subSyncSize();
	}
	
	/**
	 * 获取文件夹大小
	 * 
	 * @author Jet-Muffin
	 * @param file
	 * @return double 文件夹大小
	 */
	public double getDirSize(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else { // 如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024 / 1024;
				return size;
			}
		} else {
//			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}
	
	private void deleteAllFile(File FileDir){
		if (FileDir.exists()) { // 判断文件是否存在
			if (FileDir.isDirectory()) {
				File[] files = FileDir.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}else{
				System.out.println("不是文件夹");
			}
		} else {
			System.out.println("所删除的文件不存在");
		}
	}
	/**
	 * 递归清空文件夹
	 * 
	 * @author Jet-Muffin
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // 删除文件
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
}
