package com.Picloud.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.Picloud.exception.ThreeDImageException;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.DateUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.ThreeDImageDao;
import com.Picloud.web.model.Log;
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
		
		return "threeD/list";
	}
/**
 * 删除3D图片
 * @param imageName 图片名
 * @param session
 * @return
 * @throws Exception
 */
	@RequestMapping(value="/{imageName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String imageName,HttpSession session,Model model) throws Exception{
		User loginUser = (User) session.getAttribute("LoginUser");
		String threeDkey=EncryptUtil.imageEncryptKey(imageName, loginUser.getUid());
		ThreeDImage threeDImage=threeDImageDao.find(threeDkey);
		threeDImageDao.delete(threeDImage);
		
		List<ThreeDImage> threeDImages = threeDImageDao.load(loginUser.getUid());
		model.addAttribute("threeDImages", threeDImages);
		
		Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "删除3D图片"+imageName);
		mLogDaoImpl.add(log);
		
		return "threeD/list";
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
			try {
				List items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				boolean flag = false;
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					String hdfsPath = HDFS_UPLOAD_ROOT + "/"
							+ loginUser.getUid() + "/ThreeD/";
					//TODO 修改方法
					String filePath = hdfsPath +item.getName() ;

					ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
					flag = imageWriter.uploadToHdfs(filePath, item,
							loginUser.getUid());
					

					String key = EncryptUtil.imageEncryptKey(item.getName(),
							loginUser.getUid());
					String createTime = DateUtil.getCurrentDateStr();
					ThreeDImage threeDImage = new ThreeDImage(key, item.getName(),
							loginUser.getUid(), createTime, Long.toString(item
									.getSize()), hdfsPath);
					threeDImageDao.add(threeDImage);
					System.out.println("更新数据库成功！");
					
					Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "上传3D图片"+threeDImage.getName());
					mLogDaoImpl.add(log);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:list";
	}

}
