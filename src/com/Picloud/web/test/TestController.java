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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
				TestImageWriter testImageWriter = new TestImageWriter(infoDaoImpl,"mapfile");
				testImageWriter.write(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	@RequestMapping(value = "/sequencefile/upload", method = RequestMethod.POST)
	public String sequencefile(HttpServletRequest request) throws FileUploadException{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				TestImageWriter testImageWriter = new TestImageWriter(infoDaoImpl,"sequencefile");
				testImageWriter.write(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	@RequestMapping(value = "/namenode/upload", method = RequestMethod.POST)
	public String namenode(HttpServletRequest request) throws FileUploadException{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				TestImageWriter testImageWriter = new TestImageWriter(infoDaoImpl,"namenode");
				testImageWriter.uploadToHdfs(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	@RequestMapping(value = "/namenode", method = RequestMethod.GET)
	public String namenode(Model model,String fileName) throws Exception{
		TestImageReader testImageReader = new TestImageReader(infoDaoImpl);
		String path = "/upload/test/namenode";
		byte[] content = testImageReader.readHdfsPicture(path,fileName);
		long length = content.length;
		model.addAttribute("length", length);
		return "test";
	}
	
	@RequestMapping(value = "/sequencefile", method = RequestMethod.GET)
	public String sequencefile(Model model,String fileName,String filePath) throws Exception{
		TestImageReader testImageReader = new TestImageReader(infoDaoImpl);
		String path = "/upload/test/sequencefile/" + filePath;
		byte[] content = testImageReader.readSequencefilePicture(path,fileName);
		long length = content.length;
		model.addAttribute("length", length);
		return "test";
	}
	
	@RequestMapping(value = "/mapfile", method = RequestMethod.GET)
	public String mapfile(Model model,String fileName,String filePath) throws Exception{
		TestImageReader testImageReader = new TestImageReader(infoDaoImpl);
		String path = "/upload/test/mapfile/" + filePath;
		byte[] content = testImageReader.readMapfilePicture(path,fileName);
		long length = content.length;
		model.addAttribute("length", length);
		return "test";
	}
	
	@RequestMapping(value = "/harfile", method = RequestMethod.GET)
	public String harfile(Model model,String fileName) throws Exception{
		TestImageReader testImageReader = new TestImageReader(infoDaoImpl);
		long length = testImageReader.readHarfilePicture(fileName);
		model.addAttribute("length", length);
		return "test";
	}
}
