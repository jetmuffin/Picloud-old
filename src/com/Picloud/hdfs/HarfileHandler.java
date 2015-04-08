package com.Picloud.hdfs;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.HarFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.springframework.stereotype.Service;

@Service
public class HarfileHandler {
	public static String hdfsUrl = "hdfs://localhost:9000";
	
	public void packageToHdfs(File[] items,String localPath,String filePath) throws IOException, URISyntaxException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		HarFileSystem hfs = new HarFileSystem(fs);  
		fs.initialize(new URI("har:///upload/test/harfiles/419.har"), conf);  
	    FileStatus[] listStatus = fs.listStatus(new Path("sub_dir"));  
	    for (FileStatus fileStatus : listStatus) {  
	        System.out.println(fileStatus.getPath().toString());  
	    }  
	}
	
	public long read(String fileName) throws IOException, URISyntaxException{
	    Configuration conf = new Configuration();  
	    conf.set("fs.default.name", "hdfs://localhost:9000");  
	          
	    	  long length = 0;
	    HarFileSystem fs = new HarFileSystem();  
	    fs.initialize(new URI("har:///upload/test/harfile/harfiles.har"), conf);  
	    FileStatus[] listStatus = fs.listStatus(new Path("namenode"));  
	    for (FileStatus fileStatus : listStatus) {  
	    				if(fileStatus.getPath().getName().equals(fileName))
	    					length = fileStatus.getLen();
	    }  
	    return length;
	}
	public static void main(String[] args) throws IOException, URISyntaxException {
		HarfileHandler h = new HarfileHandler();
		String localPath = "/home/jeff/workspace/upload/test";
		String filePath = hdfsUrl + "/upload/test/harfile";
		h.read("10.gif");
	}
}
