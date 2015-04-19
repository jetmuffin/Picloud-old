package com.Picloud.web.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.HdImageException;
import com.Picloud.exception.PanoImageException;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.image.HdCut;
import com.Picloud.image.HdImageCut;
import com.Picloud.image.ImageReader;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.JspUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.IHdImageDao;
import com.Picloud.web.dao.impl.HdImageDaoImpl;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.PanoImageDao;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.model.HdImage;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.ThreeDImage;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/hd")
public class HDController {
	private String module = "应用中心";
	@Autowired
	private HdImageDaoImpl hdImageDao;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	@Autowired
	private LogDaoImpl mLogDaoImpl;
	@Autowired 
	private ImageDaoImpl imageDaoImpl;
	@Autowired
	private SpaceDaoImpl spaceDaoImpl;
	
	private static final String HDFS_UPLOAD_ROOT = "/upload";
	private static final int BLOCK_SIZE = 800;
	/**
	 * 高清图片列表
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", " 高清图片");

		User loginUser = (User) session.getAttribute("LoginUser");
		List<HdImage> hdImages = hdImageDao.load(loginUser.getUid());
		model.addAttribute("hdImages",hdImages);
		
		List<Space> spaces = spaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces",spaces);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "查看高清图片");
		mLogDaoImpl.add(log);
		return "hd/list";
	}
/**
 * 删除高清图片
 * @param imageName 图片名
 * @param session
 * @return
 * @throws Exception
 */
	@RequestMapping(value="/{hdkey}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String hdkey,HttpSession session,Model model) throws Exception{
		User loginUser = (User) session.getAttribute("LoginUser");
		HdImage hdImage=hdImageDao.find(hdkey);
		hdImageDao.delete(hdkey);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "删除高清图片"+hdImage.getName());
		mLogDaoImpl.add(log);
		return "hd/list";
	}
	
	/**
	 * 上传高清图片
	 * 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpSession session, HttpServletRequest request,String imageKey) throws Exception {
		User loginUser = (User) session.getAttribute("LoginUser");
		
		Image image = imageDaoImpl.find(imageKey);
		int width = Integer.parseInt(image.getWidth());
		int height = Integer.parseInt(image.getHeight());
		String hdfsHdPath = HDFS_UPLOAD_ROOT + "/" + loginUser.getUid() + "/HdImage";
		
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] imageBuffer = imageReader.readPicture(imageKey);
		
		Long start = new Date().getTime();  
		HdCut hdCut = new HdCut();
		hdCut.HdCutx(imageBuffer, hdfsHdPath, image.getName(), BLOCK_SIZE, width, height);
		Long end = new Date().getTime();  
		
		HdImage hdImage = new HdImage();
		hdImage.setKey(EncryptUtil.hdEncryptKey(image.getName(), loginUser.getUid()));
		hdImage.setName(image.getName());
		hdImage.setSize(String.valueOf(BLOCK_SIZE));
		hdImage.setUid(loginUser.getUid());
		hdImage.setCreateTime(JspUtil.getCurrentDateStr());
		hdImageDao.add(hdImage);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "上传了高清图片"+hdImage.getName());
		mLogDaoImpl.add(log);
		
		return "redirect:list";
	}
	
	/**
	 * 查看高清图片
	 * @param imageName 图片名
	 * @param session
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value="{hdkey}",method=RequestMethod.GET)
		public String show(@PathVariable String hdkey,HttpSession session,Model model) throws Exception{
			User loginUser = (User) session.getAttribute("LoginUser");
			HdImage hdImage = hdImageDao.find(hdkey);
			model.addAttribute("hdImage", hdImage);
			return "hd/show";
		}
		
		@RequestMapping(value="/read",method=RequestMethod.GET)
		public void show(String fileName,HttpSession session,HttpServletResponse response) throws Exception{
			User loginUser = (User) session.getAttribute("LoginUser");
			
			String RealPath = HDFS_UPLOAD_ROOT + "/" + loginUser.getUid() + "/HdImage/" + fileName;
			HdfsHandler hdfsHandler = new HdfsHandler();
			
			try {
				if (RealPath.toLowerCase().endsWith(".dzi")) {
			
					byte[] fileByte =  hdfsHandler.readFile(RealPath);
					response.reset();
					OutputStream output = response.getOutputStream();// 得到输出流
					response.setContentType("text/xml;charset=utf-8");
					response.setHeader("Access-Control-Allow-Origin",
							"http://localhost:81");

					InputStream imageIn = new ByteArrayInputStream(fileByte);
					BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
					BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
					byte data[] = new byte[4096];// 缓冲字节数
					int size = 0;
					size = bis.read(data);
					while (size != -1) {
						bos.write(data, 0, size);
						size = bis.read(data);
					}
					
					bis.close();
					bos.flush();// 清空输出缓冲流
					bos.close();
					output.close();
				} else {
					byte[] fileByte =  hdfsHandler.readFile(RealPath);
					response.reset();
					OutputStream output = response.getOutputStream();// 得到输出流
					response.setContentType("image/jpeg;charset=GB2312");  

					InputStream imageIn = new ByteArrayInputStream(fileByte);
					BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
					BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
					byte data[] = new byte[4096];// 缓冲字节数
					int size = 0;
					size = bis.read(data);
					while (size != -1) {
						bos.write(data, 0, size);
						size = bis.read(data);
					}
					
					bis.close();
					bos.flush();// 清空输出缓冲流
					bos.close();
					output.close();
				}
				

			} catch (Exception e) {
				PrintWriter out = response.getWriter();
				out.println("cannot find the file!");
				out.close();
				e.printStackTrace();
			}
		}
}
