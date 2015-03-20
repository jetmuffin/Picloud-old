package com.Picloud.image;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;


public class ImageReader {
	private InfoDaoImpl infoDaoImpl;
	private ImageDaoImpl mImageDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileHandler mMapfileHandler;
	
	
	public ImageReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageReader(InfoDaoImpl infoDaoImpl){
		this.infoDaoImpl = infoDaoImpl;
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mImageDaoImpl = infoDaoImpl.getmImageDaoImpl();
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileHandler = infoDaoImpl.getmMapfileHandler();
	}
	
	/**
	 * 根据图片key读取图片
	 * @param imageName
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] readPicture(String imageKey) throws Exception {
		Image image = mImageDaoImpl.find(imageKey);
		if(image != null) {
			byte[] buffer = null;
			buffer = readPicture(image);
			return buffer;			
		} else {
			return null;
		}
	}
	
	/**
	 * 根据PictureBean读取图片
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public byte[] readPicture(Image image) throws Exception {
		byte[] buffer = null;
		if (image.getStatus().equals("LocalFile")) {
			buffer =readLocalPicture(image.getPath(),
					image.getName());
		} else if (image.getStatus().equals("HdfsSmallFile")) {
			buffer = readMapfilePicture(image.getPath(),
					image.getName());
		} else {
			buffer = readHdfsPicture(image.getPath(),
					image.getName());
		}
		return buffer;
	}
	
	/**
	 * 从本地读取图片
	 * @param path
	 * @param fileName
	 * @return
	 */
	public byte[] readLocalPicture(String path, String fileName) {
		File file = new File(path, fileName);
		byte[] content = null;
		try {
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(in);
			byte[] bytes = new byte[1024];
			int len;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while ((len = bis.read(bytes)) > 0) {
				out.write(bytes, 0, len);
			}
			bis.close();
			content = out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 从HDFS读取大图片
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public byte[] readHdfsPicture(String path, String fileName) throws Exception {
		  String RealPath = path + '/' + fileName;
		  byte[] content = mHdfsHandler.readFile(RealPath);
		  return content;
	}
	
	/**
	 * 从HDFS读取MAPFILE图片
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public byte[] readMapfilePicture(String path,String fileName) throws Exception {
		byte[] content = mMapfileHandler.readFromHdfs(path,fileName);
		return content;
	}
}
