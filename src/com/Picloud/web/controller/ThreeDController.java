package com.Picloud.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.FileException;
import com.Picloud.exception.ImageException;
import com.Picloud.exception.ThreeDImageException;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.JspUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.ThreeDImageDao;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.ThreeDImage;
import com.Picloud.web.model.User;

@Controller
@RequestMapping(value = "/threeD")
public class ThreeDController {
	private String module = "应用中心";
	@Autowired
	private ThreeDImageDao threeDImageDao;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	@Autowired
	private LogDaoImpl mLogDaoImpl;
	
	private static final String HDFS_UPLOAD_ROOT = "/upload";

	/**
	 * 3D图片列表
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "3D图片");

		User loginUser = (User) session.getAttribute("LoginUser");
		List<ThreeDImage> threeDImages = threeDImageDao.load(loginUser.getUid());
		model.addAttribute("threeDImages", threeDImages);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "查看3D图片");
		mLogDaoImpl.add(log);
		
		return "threed/list";
	}
	
/**
 * 删除3D图片
 * @param imageName 图片名
 * @param session
 * @return
 * @throws Exception
 */
	@RequestMapping(value="/{threeDkey}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String threeDkey,HttpSession session,Model model) throws Exception{
		User loginUser = (User) session.getAttribute("LoginUser");
		ThreeDImage threeDImage=threeDImageDao.find(threeDkey);
		threeDImageDao.delete(threeDImage);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "删除3D图片"+threeDImage.getName());
		mLogDaoImpl.add(log);
		
		return "redirect:/threeD/list";
	}
	
	/**
	 * 上传3D图片
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpSession session, HttpServletRequest request) {

		User loginUser = (User) session.getAttribute("LoginUser");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			throw new ThreeDImageException("上传3D图片错误，没有文件域！");
		} else {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Double totalSize =  0.0;
			String hdfsPath = HDFS_UPLOAD_ROOT + "/" + loginUser.getUid() + "/ThreeD/";
			String threeDImageName = "";
			String key = "";
			try {
				List items = upload.parseRequest(request);
				System.out.println(items.size());
				Iterator iter = items.iterator();
				boolean flag = false;
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					//若为普通表单
					if (item.isFormField()) {  		
						String name = item.getFieldName();
						if(name.equals("threeDImageName")) {
							threeDImageName = item.getString();
							 key = EncryptUtil.tdEncryptKey(threeDImageName,
										loginUser.getUid());
							 //检查该图片名是否被使用过
							ThreeDImage threeDImage = threeDImageDao.find(key);
							if(threeDImage != null) {
								throw new ThreeDImageException("3D图片名已存在!");
							}
						}
					} else {
						String filePath = hdfsPath + threeDImageName + '/';
						ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
						flag = imageWriter.uploadToHdfs(filePath, item,
								loginUser.getUid());
						totalSize += item.getSize();					
					}
				}
				
				//更新Hbase
				totalSize = totalSize/1024/1024 ;
				String createTime = JspUtil.getCurrentDateStr();
				ThreeDImage threeDImage = new ThreeDImage(key, threeDImageName,
						loginUser.getUid(), createTime, Double.toString(totalSize), Integer.toString(items.size()-1));
				threeDImageDao.add(threeDImage);
				
				Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "上传3D图片"+threeDImage.getName());
				mLogDaoImpl.add(log);
			} catch (Exception e) {
				throw new ThreeDImageException(e.getMessage());
			}
		}
		return "redirect:list";
	}

	/**
	 * 查看3D图片
	 * @param imageName 图片名
	 * @param session
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value="/{threeDkey}",method=RequestMethod.GET)
		public String show(@PathVariable String threeDkey,HttpSession session,Model model) throws Exception{
			User loginUser = (User) session.getAttribute("LoginUser");

			ThreeDImage threeDImage = threeDImageDao.find(threeDkey);
			model.addAttribute("threeDImage", threeDImage);
			return "threed/show";
		}
		
		/**
		 * 读取3D图片资源
		 * @param name 3D图片文件名
		 * @param file 3D图片资源名
		 * @param session
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(value="/read",method=RequestMethod.GET)
		public void show(String name,String file,HttpSession session,HttpServletResponse response) throws Exception{
			User loginUser = (User) session.getAttribute("LoginUser");
			
			try{
				String RealPath = HDFS_UPLOAD_ROOT + "/" + loginUser.getUid() + "/ThreeD/" + name + '/' + file;
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
				throw new ThreeDImageException(e.getMessage());
			}
		}
		
		@ExceptionHandler(value=(ThreeDImageException.class))
		public String handlerException(ImageException e,HttpServletRequest req){
			req.setAttribute("e", e);
			return "error";
		}
		
	
}
