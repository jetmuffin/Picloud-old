package com.Picloud.web.controller;

import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.PanoImageException;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.PanoImageDao;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.User;
import com.Picloud.utils.DateUtil;

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
		List<PanoImage> panoImages = panoImageDao.load(loginUser.getUid());
		model.addAttribute("panoImages", panoImages);
		return "pano/list";
	}

	/**
	 * 上传全景图片
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
			throw new PanoImageException("上传全景图片错误，没有文件域！");
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
							+ loginUser.getUid() + "/Pano/";
					
					//TODO 修改方法
					HdfsHandler hdfsHandler = new HdfsHandler(SystemConfig.getSystemPath());
					InputStream uploadedStream = item.getInputStream();
					String filePath = hdfsPath +item.getName() ;
					flag = hdfsHandler.upLoad(uploadedStream, filePath);
//					ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
//					flag = imageWriter.uploadToHdfs(hdfsPath, item,
//							loginUser.getUid());
					
					
					String key = EncryptUtil.imageEncryptKey(item.getName(),
							loginUser.getUid());
					String createTime = DateUtil.getCurrentDateStr();
					PanoImage panoImage = new PanoImage(key, item.getName(),
							loginUser.getUid(), createTime, Long.toString(item
									.getSize()), hdfsPath);
					panoImageDao.add(panoImage);
					System.out.println("更新数据库成功！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:list";
	}

}
