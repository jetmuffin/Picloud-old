package com.Picloud.hdfs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Picloud.config.HdfsConfig;
import com.Picloud.config.SystemConfig;
import com.Picloud.exception.FileException;
import com.Picloud.exception.ImageException;
import com.Picloud.utils.PropertiesUtil;

@Service
public class HdfsHandler {
	private Configuration conf;
	private FileSystem fs; 
	/**
	 * 构造方法
	 * @throws IOException
	 */
	
	
	public HdfsHandler() throws IOException {
		super();
		conf = new Configuration();
		fs = FileSystem.newInstance(URI.create(PropertiesUtil.getValue("FileSystemPath")),conf);
	}

	/**
	 * 上传文件
	 * @param in
	 * @param hdfsPath
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean upLoad(InputStream in, String hdfsPath) throws Exception{
		Path p = new Path(hdfsPath);
		try{
			if(fs.exists(p)){
				throw new FileException("文件已存在！(HDFS LargeFile)");
			}

			Progressable progress = new Progressable(){
				public void progress() {					
					//System.out.print(".");
				}			
			};
			FSDataOutputStream out = fs.create(p,progress);
			IOUtils.copyBytes(in, out, conf);
			
			out.flush();
			out.close();
			in.close();		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 *  下载文件
	 * @param hdfsPath
	 * @param localPath
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean downLoad(String hdfsPath,String localPath ) throws IOException{
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				throw new FileException("未找到文件！");
			}
			FSDataInputStream in =  fs.open(path);
			OutputStream out = new FileOutputStream(localPath);
			byte[] buffer = new byte[400];
			int length = 0;
			while((length = in.read(buffer))>0){
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	
	/**
	 * 删除文件
	 * @param hdfsPath
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean deletePath(String hdfsPath) throws IOException{
		Path path = new Path(hdfsPath);
		try {
			if(!fs.exists(path)){
				throw new FileException("未找到文件！");
			}
			fs.delete(path, true);
		} catch (IOException e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
    /**
     * 读取文件于字节缓冲数组
     * @param hadoopFile
     * @return buffer
     */
	public byte[] readFile(String hdfsPath) throws Exception {
		Configuration conf = new Configuration();
		Path path = new Path(hdfsPath);
		if (fs.exists(path)) {
			FSDataInputStream in = fs.open(path);
			FileStatus stat = fs.getFileStatus(path);
			byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat
					.getLen()))];
			in.readFully(0, buffer);
			in.close();
			return buffer;
		} else {
			throw new Exception("the file is not found .");
		}
	}
	
    /** 
     * create a file  创建一个文件 
     * @param filePath 
     * @return 
     */  
    public  void createFile(String filePath,String content) throws Exception{    
          Configuration conf= new Configuration();  
          Path path = new Path(filePath);  
          FSDataOutputStream out = fs.create(path);  
          out.write(content.getBytes());  
          out.close();  
          fs.close();  
    }  
    
    /**
     * 读取图片为BufferedImage
     * @param hdfsPath
     * @return
     * @throws Exception
     */
    public BufferedImage readImage(String hdfsPath) throws Exception{
        Configuration conf = new Configuration();
        Path path = new Path(hdfsPath);
        if ( fs.exists(path) )
        {
            FSDataInputStream in = fs.open(path);
            FileStatus stat = fs.getFileStatus(path);       
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            in.readFully(0, buffer);
            in.close();
            fs.close();    
            ByteArrayInputStream bin = new ByteArrayInputStream(buffer); 
            BufferedImage image = ImageIO.read(bin);  
            return image;
        }
        else
        {
            throw new Exception("the file is not found .");
        }
    }
    
    public void writeByteImage(byte[]imageByte,String localPath,String imageName){
    	try {
			DataOutputStream d=new DataOutputStream(new FileOutputStream(localPath+imageName));
			d.write(imageByte);
			d.flush();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    
}
