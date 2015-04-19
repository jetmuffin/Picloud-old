package com.Picloud.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.core.ApplicationContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.SpaceException;
import com.Picloud.exception.ThreeDImageException;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.JspUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.utils.PropertiesUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.PageInfo;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.ThreeDImage;
import com.Picloud.web.model.User;
import com.Picloud.web.thread.SyncThread;

@Controller
@RequestMapping("/space")
public class SpaceController {
	private String module = "图片服务器";
	@Autowired
	private SpaceDaoImpl mSpaceDaoImpl;
	@Autowired
	private UserDaoImpl mUserDaoImpl;
	@Autowired
	private ImageDaoImpl mImageDaoImpl;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private InfoDaoImpl infoDaoImpl;

	private static int pageNum = 6+1;

	/**
	 * 查看所有空间
	 * 
	 * @param model
	 * @param space
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/spaces", method = RequestMethod.GET)
	public String list(Model model, @ModelAttribute("space") Space space,
			HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");

		User LoginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(LoginUser.getUid());
		model.addAttribute("spaces", spaces);
		return "space/list";
	}

	/**
	 * 创建空间
	 * 
	 * @param model
	 * @param space
	 * @param br
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, @Validated Space space, BindingResult br,
			HttpSession session) throws Exception {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");

		User LoginUser = (User) session.getAttribute("LoginUser");
		if (br.hasErrors()) {
			return "space/list";
		}

		String key = EncryptUtil.spaceEncryptKey(space.getName(),
				LoginUser.getUid());
		if (mSpaceDaoImpl.find(key) != null) {
			throw new SpaceException("该空间已存在！");
		}
		space.setKey(key);
		space.setUid(LoginUser.getUid());
		mSpaceDaoImpl.add(space);

		String spaceNum = LoginUser.getSpaceNum();
		LoginUser.setSpaceNum(Integer.toString(Integer.parseInt(spaceNum) + 1));
		mUserDaoImpl.update(LoginUser);
		return "redirect:spaces";
	}

	/**
	 * 查看某个空间下的所有图片
	 * 
	 * @param spaceKey
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{spaceKey}/{page} ", method = RequestMethod.GET)
	public String show(@PathVariable String spaceKey, @PathVariable int page, Model model,
			HttpSession session) throws Exception {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");

		User loginUser = (User) session.getAttribute("LoginUser");
		Space space = mSpaceDaoImpl.find(spaceKey);
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		PageInfo pi = (PageInfo) session.getAttribute("imagePagePnfo");
		 if(pi == null){
			 pi = new PageInfo();
			 pi.setNum(1);
			 pi.setPage(0);
			 pi.getStartKeys().add(" ");
		 }
		 pi.setPage(page);
		 List<Image> images = mImageDaoImpl.imagePageByKey(loginUser.getUid(),
		 pi.getStartKeys().get(pi.getPage()), spaceKey, pageNum);
		 if(images == null ||images.size() < pageNum){
			 pi.setIfHaveNext(false);
		 }else{
			 pi.setIfHaveNext(true);
			 pi.setNum(pi.getNum()+1);
			 pi.getStartKeys().add(images.get(pageNum-1).getKey());
			 images.remove(pageNum-1);
		 }
		session.setAttribute("imagePagePnfo", pi);
		model.addAttribute("images", images);
		model.addAttribute("activeSpace", space);
		model.addAttribute(space);
		model.addAttribute("spaces", spaces);
		return "space/show";
	}

	/**
	 * 快速上传页面
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model, HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "上传图片");

		User loginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces", spaces);
		return "space/upload";
	}

	/**
	 * 快速上传处理
	 * 
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpSession session, HttpServletRequest request)
			throws Exception {
		String spaceName = null;
		String spaceKey = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		User loginUser = (User) session.getAttribute("LoginUser");
		final String LocalPath = PropertiesUtil.getValue("localUploadPath") + "/"
				+ loginUser.getUid() + '/' + spaceKey + '/';
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) { // 若为普通表单
					String name = item.getFieldName();
					if (name.equals("space")) {

						spaceName = item.getString();
						spaceName = new String(spaceName.getBytes("iso8859-1"),
								"utf-8");
						spaceKey = EncryptUtil.spaceEncryptKey(spaceName,
								loginUser.getUid());
					}
				} else {
					ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
					imageWriter.write(item, loginUser.getUid(), spaceKey,
							LocalPath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}

	/**
	 * 上传图片页面
	 * 
	 * @param spaceKey
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{spaceKey}/upload", method = RequestMethod.GET)
	public String upload(@PathVariable String spaceKey, Model model) {
		model.addAttribute("module", module);
		model.addAttribute("action", "上传图片");

		Space space = mSpaceDaoImpl.find(spaceKey);
		model.addAttribute("activeSpace", space);
		model.addAttribute(space);
		return "space/upload";
	}

	/**
	 * 上传图片
	 * 
	 * @param space
	 *            图片所在空间
	 * @param attachs
	 *            图片附件数组
	 * @throws FileUploadException
	 */
	@RequestMapping(value = "/{spaceKey}/upload", method = RequestMethod.POST)
	public String upload(@PathVariable String spaceKey,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		User loginUser = (User) session.getAttribute("LoginUser");
		final String LocalPath = PropertiesUtil.getValue("localUploadPath")+ "/"
				+ loginUser.getUid() + '/' + spaceKey + '/';
		boolean flag = false;
		try {

			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					String temp = item.getString();
					System.out.println(temp);
				} else {
					ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
					flag = imageWriter.write(item, loginUser.getUid(),
							spaceKey, LocalPath);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag) {
			response.setContentType("text/html;charset=gb2312");
			response.setStatus(200);
		} else {
			response.setContentType("text/html;charset=gb2312");
			response.setStatus(302);
		}
		return "test";
	}

	@RequestMapping(value = "/{spaceName}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable String spaceName, Model model,
			HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		model.addAttribute("activeSpace", spaceName);

		User LoginUser = (User) session.getAttribute("LoginUser");
//		String key = spaceName + "_" + LoginUser.getUid();
		String key;
		try {
			key = EncryptUtil.spaceEncryptKey(spaceName, LoginUser.getUid());
			mSpaceDaoImpl.delete(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/space/spaces";
	}

	@ExceptionHandler(value = (SpaceException.class))
	public String handlerException(SpaceException e, HttpServletRequest req) {
		req.setAttribute("e", e);
		return "error";
	}

	/**
	 * 读取空间信息
	 * 
	 * @param spaceKey
	 * @return Space信息JSON
	 */
	@RequestMapping(value = "/{spaceKey}.json", method = RequestMethod.GET)
	@ResponseBody
	public Space show(@PathVariable String spaceKey) {
		Space space = new Space();
		space = mSpaceDaoImpl.find(spaceKey);
		return space;
	}

	/**
	 * 读取空间内所有图片信息
	 * 
	 * @param spaceKey
	 * @return Space所有图片信息JSON
	 */
	@RequestMapping(value = "/{spaceKey}/images.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Image> getAllImages(@PathVariable String spaceKey) {
		List<Image> images = mImageDaoImpl.load(spaceKey);
		return images;
	}

	/**
	 * 读取用户 所有空间
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/spaces.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Space> getAllSpace(HttpSession session) {
		User LoginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(LoginUser.getUid());
		return spaces;
	}

	@RequestMapping(value = "/{spaceKey}/test", method = RequestMethod.POST)
	public String uploadTest(@PathVariable String spaceKey,
			HttpServletRequest request, HttpSession session)
			throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		@SuppressWarnings("rawtypes")
		List items = upload.parseRequest(request);
		@SuppressWarnings("rawtypes")
		Iterator iter = items.iterator();
		User loginUser = new User("test", "1", "", "", "", "test", "123456",
				"0", "0", "0");
		final String LocalPath = PropertiesUtil.getValue("localUploadPath")+ "/"
				+ loginUser.getUid() + '/' + spaceKey + '/';
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
				imageWriter
						.write(item, loginUser.getUid(), spaceKey, LocalPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 同步线程
		return "test";
	}

	@RequestMapping(value = "/{spaceKey}/test2", method = RequestMethod.POST)
	public String test(@PathVariable String spaceKey, HttpSession session,
			HttpServletRequest request) throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// User loginUser = (User) session.getAttribute("LoginUser");
		User loginUser = new User("test", "", "", "", "", "test", "123456",
				"0", "0", "0");
		String hdfsPath = "/upload" + "/" + loginUser.getUid();
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String filePath = hdfsPath + "/BigFile/";
				ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
				flag = imageWriter.uploadToHdfs(filePath, item,
						loginUser.getUid());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	/**
	 * 搜索空间下的图片
	 * @param spaceKey
	 * @param subStr
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{spaceKey}/search", method = RequestMethod.GET)
	public String search(@PathVariable String spaceKey, String key, Model model,
			HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		User LoginUser = (User) session.getAttribute("LoginUser");
		List<Image> images = mSpaceDaoImpl.search(LoginUser.getUid(), spaceKey, key);
		Space space = mSpaceDaoImpl.find(spaceKey);
		model.addAttribute("images", images);
		model.addAttribute("space", space);
		return "space/show";
	}
}
