package com.Picloud.web.test;

import com.Picloud.hdfs.HarfileHandler;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.hdfs.SequencefileHandler;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;

public class TestImageReader {
	private InfoDaoImpl infoDaoImpl;
	private ImageDaoImpl mImageDaoImpl;
	private HdfsHandler mHdfsHandler;
	private MapfileHandler mMapfileHandler;
	private SequencefileHandler mSequencefileHandler;
	private HarfileHandler mHarfileHandler;
	
	public TestImageReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestImageReader(InfoDaoImpl infoDaoImpl){
		this.infoDaoImpl = infoDaoImpl;
		this.mHdfsHandler = infoDaoImpl.getmHdfsHandler();
		this.mMapfileHandler = infoDaoImpl.getmMapfileHandler();
		this.mSequencefileHandler = infoDaoImpl.getmSequencefileHandler();
		this.mHarfileHandler = infoDaoImpl.getmHarfileHandler();
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
	
	/**
	 * 从HDFS读取SEQUENCEFILE图片 
	 */
	public byte[] readSequencefilePicture(String path,String fileName) throws Exception{
		byte[] content = mSequencefileHandler.getSyncPosition(path, fileName);
		return content;
	}
	
	public long  readHarfilePicture(String fileName) throws Exception{
		long length = mHarfileHandler.read(fileName);
		return length;
	}
}
