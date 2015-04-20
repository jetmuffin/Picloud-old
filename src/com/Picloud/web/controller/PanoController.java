package com.Picloud.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.FileException;
import com.Picloud.exception.ImageException;
import com.Picloud.exception.PanoImageException;
import com.Picloud.exception.ThreeDImageException;
import com.Picloud.exception.UserException;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.image.GraphicMagick;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.ByteUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.PanoImageDao;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.User;
import com.Picloud.utils.JspUtil;

@Controller
@RequestMapping(value = "/pano")
public class PanoController {
	private String module = "应用中心";
	@Autowired
	private PanoImageDao panoImageDao;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	@Autowired
	private LogDaoImpl mLogDaoImpl;
	@Autowired
	private HdfsHandler mHdfsHandler;
	
	private static final String HDFS_UPLOAD_ROOT = "/upload";

	/**
	 * 全景图片列表
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "全景图片");

		User loginUser = (User) session.getAttribute("LoginUser");
		System.out.println("user"+loginUser);
		List<PanoImage> panoImages = panoImageDao.load(loginUser.getUid());
		model.addAttribute("panoImages", panoImages);
		return "pano/list";
	}
	
	/**
	 * 上传music 
	 * @param panoKey
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{panoKey}/music", method = RequestMethod.POST)
	public String music(@PathVariable String panoKey,RedirectAttributes attr,HttpSession session,HttpServletRequest request){
		
		User  loginUser=(User) session.getAttribute("LoginUser");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			throw new PanoImageException("上传音乐错误，没有文件域！");
		} else {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				boolean flag = false;
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					String musicPath = HDFS_UPLOAD_ROOT + "/"
							+ loginUser.getUid() + "/Pano/"+panoKey+"/Music/";

					ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
					flag = imageWriter.uploadToHdfs(musicPath, item,
							loginUser.getUid());
					
					PanoImage panoImage=panoImageDao.find(panoKey);
					panoImage.setMus_path(musicPath+item.getName());
					panoImageDao.add(panoImage);
					Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "上传全景图片"+panoImage.getName()+"的背景音乐");
					mLogDaoImpl.add(log);
				}
			
			} catch (Exception e) {
				throw new PanoImageException(e.getMessage());
			}
			attr.addFlashAttribute("editMsg", "修改背景音乐成功!");
			return "redirect:edit";
	}
 }
	
	
	/**
	 *  全景图片编辑
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{panoKey}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String panoKey,Model model, HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "全景图片编辑");
		
		User loginUser = (User) session.getAttribute("LoginUser");
		PanoImage panoImage = panoImageDao.find(panoKey);
		model.addAttribute("panoImage", panoImage);
	   
	   
		return "pano/edit";
	}
/**
 * 删除全景图片
 * @param imageName 图片名
 * @param session
 * @return
 * @throws Exception
 */
	@RequestMapping(value="/{panokey}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String panokey,HttpSession session,Model model) throws Exception{
		User loginUser = (User) session.getAttribute("LoginUser");
		PanoImage panoImage = panoImageDao.find(panokey);
		panoImageDao.delete(panokey);
		
		List<PanoImage> panoImages = panoImageDao.load(loginUser.getUid());
		model.addAttribute("panoImages", panoImages);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "删除全景图片"+panoImage.getName());
		mLogDaoImpl.add(log);
		return "pano/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("module", module);
		model.addAttribute("action", "制作全景图片");
		
		return "pano/add";
	}
	/**
	 * 上传全景图片
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{panoKey}/scene", method = RequestMethod.POST)
	public String scene(@PathVariable String panoKey,HttpSession session, RedirectAttributes attr,HttpServletRequest request) {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			throw new PanoImageException("上传全景图片错误，没有文件域！");
		} else {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();

				String hdfsPath = HDFS_UPLOAD_ROOT + "/"
						+ loginUser.getUid() + "/Pano/"+panoKey+"/scene/";
				String sceneName="";
				String sceneDesc="";
				String filePath;
				String fileName;
				PanoImage panoImage=panoImageDao.find(panoKey);
				int number=Integer.parseInt(panoImage.getNumber())+1;

				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					//若为普通表单
					if (item.isFormField()) {  		
						String name = item.getFieldName();
						if(name.equals("sceneName")) {
							 sceneName = item.getString();
							 sceneName = new String(sceneName.getBytes("iso8859-1"),
										"utf-8");
						} else if(name.equals("sceneDesc")) {
							 sceneDesc = item.getString();
							 sceneDesc = new String(sceneDesc.getBytes("iso8859-1"),
										"utf-8");
						}
					//若为文件
					} else {
						String type = JspUtil.getFileType(item.getName());
						//首次上传
						if(panoImage.getType().equals("")){
							panoImage.setType(type);
							fileName=number+"."+type;
							filePath = hdfsPath+fileName;
						}
						//非首次上传图片格式限制
						else if(panoImage.getType().equals(type)){
							fileName=number+"."+type;
							filePath = hdfsPath+fileName;
						}
						else throw new PanoImageException("图片格式不正确");
						InputStream origin = item.getInputStream();
						mHdfsHandler.upLoad(origin, filePath);
//						ImageWriter imageWriter=new ImageWriter(infoDaoImpl);
//						imageWriter.uploadToHdfs(filePath, item,loginUser.getUid());
						
						//制作缩略图
						InputStream in = item.getInputStream();
						byte[] buffer = ByteUtil.inputStreamToByte(in);
						GraphicMagick gm = new GraphicMagick(buffer,type);
						byte[] bufferOut = gm.scaleImage(220);
						InputStream is = new ByteArrayInputStream(bufferOut); 
						String thumbPath = HDFS_UPLOAD_ROOT + "/"
								+ loginUser.getUid() + "/Pano/"+panoKey+"/thumb/" + number+"."+type;;
								mHdfsHandler.upLoad(is, thumbPath);
					}
				}
				panoImage.append(sceneName, sceneDesc);
				panoImageDao.add(panoImage);
				Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "上传全景图片"+panoImage.getName());
				mLogDaoImpl.add(log);
			} catch (Exception e) {
				throw new PanoImageException(e.getMessage());
			}
		}
		attr.addFlashAttribute("editMsg", "添加场景成功");
		return "redirect:edit";
	}
	
	
	/**
	 * 添加全景的信息
	 * @param panoName 全景图片的名字
	 * @param info 全景图片的信息
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpSession session, HttpServletRequest request) {
	
		String panoName=request.getParameter("panoName");
		String info=request.getParameter("panoDesc");
		User loginUser = (User) session.getAttribute("LoginUser");
		String hdfsPath = HDFS_UPLOAD_ROOT + "/"
				+ loginUser.getUid() + "/Pano/";
		String key="";
		try {
			//使用图片名加用户昵称加密
			key=EncryptUtil.panoEncryptKey(panoName, loginUser.getNickname());
			PanoImage panoImage=new PanoImage ();
			panoImage.setKey(key);
			panoImage.setInfo(info);
			panoImage.setName(panoName);
			panoImage.setUid(loginUser.getUid());
			panoImage.setPath(hdfsPath+key);
			String createTime=JspUtil.getCurrentDateStr();
			panoImage.setCreateTime(createTime);
			panoImageDao.add(panoImage);
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "创建全景图片"+panoImage.getName());
			mLogDaoImpl.add(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new PanoImageException(e.getMessage());
		}
		return "redirect:" + key + "/edit";
	}
	
	/**
	 * 修改全景的信息
	 * @param panoName 全景图片的名字
	 * @param info 全景图片的信息
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{panoKey}/info", method = RequestMethod.POST)
	public String info(@PathVariable String panoKey,RedirectAttributes attr,HttpSession session, HttpServletRequest request) {
	
		String panoName=request.getParameter("panoName");
		String info=request.getParameter("panoDesc");
		User loginUser = (User) session.getAttribute("LoginUser");
		try {
			//使用图片名加用户昵称加密
			PanoImage panoImage=panoImageDao.find(panoKey);
			panoImage.setInfo(info);
			panoImage.setName(panoName);
			panoImageDao.add(panoImage);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "修改全景图片"+panoImage.getName()+"的信息");
			mLogDaoImpl.add(log);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new PanoImageException(e.getMessage());
		}
		attr.addFlashAttribute("editMsg", "修改项目信息成功!");
		return "redirect:edit";
	}
	
	/**
	 * 查看全景图片
	 * @param imageName 图片名
	 * @param session
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value="/{panokey}",method=RequestMethod.GET)
		public String show(@PathVariable String panokey,HttpSession session,Model model) throws Exception{
			User loginUser = (User) session.getAttribute("LoginUser");

			PanoImage panoImage = panoImageDao.find(panokey);
			model.addAttribute("panoImage", panoImage);
			return "pano/show";
		}
	
		/**
		 * 查看全景图片
		 * @param imageName 图片名
		 * @param session
		 * @return
		 * @throws Exception
		 */
			@RequestMapping(value="/{panokey}.json",method=RequestMethod.GET)
			@ResponseBody
			public PanoImage show(@PathVariable String panokey,Model model) throws Exception{

				PanoImage panoImage = panoImageDao.find(panokey);
				return panoImage;
			}
			
		/**
		 * 查看全景图片
		 * @param imageName 图片名
		 * @param session
		 * @return
		 * @throws Exception
		 */
			@RequestMapping(value="/read/{panokey}",method=RequestMethod.GET)
			public void read(@PathVariable String panokey,HttpSession session,HttpServletResponse response) throws Exception{
				User loginUser = (User) session.getAttribute("LoginUser");
				PanoImage panoImage = panoImageDao.find(panokey);
				String imageName = panoImage.getName();
				
				try{
					String RealPath = HDFS_UPLOAD_ROOT + "/" + loginUser.getUid() + "/Pano/" + imageName;
					HdfsHandler hdfsHandler = new HdfsHandler();
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
				}catch(Exception e){
					throw new PanoImageException(e.getMessage());
				}
			}	
			
			@RequestMapping(value="/readFile",method=RequestMethod.GET)
			public void read(String name,String path,HttpSession session,HttpServletResponse response) throws Exception{
				User loginUser = (User) session.getAttribute("LoginUser");
				try{
					HdfsHandler hdfsHandler = new HdfsHandler();
					byte[] fileByte =  hdfsHandler.readFile(path);
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
				}catch(Exception e){
					throw new PanoImageException(e.getMessage());
				}
			}
			
			@RequestMapping(value="/readMusic",method=RequestMethod.GET)
			public void readMusic(String name,String path,HttpSession session,HttpServletResponse response) throws Exception{
				User loginUser = (User) session.getAttribute("LoginUser");
				try{
					HdfsHandler hdfsHandler = new HdfsHandler();
					byte[] fileByte =  hdfsHandler.readFile(path);
					response.reset();
					OutputStream output = response.getOutputStream();// 得到输出流
					response.setContentType("audio/mp3");  

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
				}catch(Exception e){
					throw new PanoImageException(e.getMessage());
				}
			}
			
			
		@ExceptionHandler(value=(PanoImageException.class))
		public String handlerException(Exception e,HttpServletRequest req){
			req.setAttribute("e", e);
			return "error";
		}
		
		@RequestMapping(value="/test",method=RequestMethod.GET)
		public String test() throws Exception{
			
//			PanoImage = new PanoImage(key, name, number, uid, createTime, info, path, scene)
			
			return "pano/show";
		}
		
		
	
}
