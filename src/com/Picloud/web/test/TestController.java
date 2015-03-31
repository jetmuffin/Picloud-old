package com.Picloud.web.test;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.image.ImageWriter;
import com.Picloud.web.dao.impl.InfoDaoImpl;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	
	@RequestMapping(value = "/mapfile/upload", method = RequestMethod.POST)
	public String mapfileUpload(HttpServletRequest request) throws FileUploadException{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
				imageWriter.write(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	@RequestMapping(value = "/sequencefile", method = RequestMethod.GET)
	public String sequencefile(){
		return "test";
	}
	
	@RequestMapping(value = "/namenode", method = RequestMethod.POST)
	public String namenode(){
		return "test";
	}
	
	@RequestMapping(value = "/harfile", method = RequestMethod.POST)
	public String harfile(){
		return "test";
	}
}
