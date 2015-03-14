package com.Picloud.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.SpaceException;
import com.Picloud.image.ImageWriter;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Space;
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

		String key = EncryptUtil.spaceEncryptKey( space.getName(),  LoginUser.getUid());
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
	@RequestMapping(value = "/{spaceKey}", method = RequestMethod.GET)
	public String show(@PathVariable String spaceKey, Model model,
			HttpSession session) throws Exception {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");

		Space space = mSpaceDaoImpl.find(spaceKey);
		List<Image> images = mImageDaoImpl.load(spaceKey);
		if(images!=null){
			model.addAttribute("images",images);
		}
		model.addAttribute("activeSpace", space.getName());
		model.addAttribute(space);
		return "space/show";
	}

	@RequestMapping(value = "/{spaceKey}/upload", method = RequestMethod.GET)
	public String upload(@PathVariable String spaceKey, Model model) {
		model.addAttribute("module", module);
		model.addAttribute("action", "上传图片");

		Space space = mSpaceDaoImpl.find(spaceKey);
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
	public String upload(@PathVariable String spaceKey, HttpServletRequest request , HttpSession session)
			throws FileUploadException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		@SuppressWarnings("rawtypes")
		List items = upload.parseRequest(request);
		@SuppressWarnings("rawtypes")
		Iterator iter = items.iterator();
		User loginUser = (User) session.getAttribute("LoginUser");
		try {
			boolean flag = false;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				ImageWriter imageWriter = new ImageWriter(infoDaoImpl);
				imageWriter.write(item, loginUser.getUid() , spaceKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    final String LocalPath = systemConfig.getLocalUploadPath() + "/" +  loginUser.getUid() + '/' + spaceKey + '/';
	    // 同步线程
	    SyncThread syncThread = new SyncThread(infoDaoImpl);
	    syncThread.SetProperty(LocalPath,loginUser.getUid() , spaceKey);
	    syncThread.start();
		return "test";
	}

	@RequestMapping(value = "/{spaceName}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable String spaceName, Model model,
			HttpSession session) {
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		model.addAttribute("activeSpace", spaceName);

		User LoginUser = (User) session.getAttribute("LoginUser");
		String key = spaceName + "_" + LoginUser.getUid();
		mSpaceDaoImpl.delete(key);
		return "redirect:/space/spaces";
	}

	@ExceptionHandler(value = (SpaceException.class))
	public String handlerException(SpaceException e, HttpServletRequest req) {
		req.setAttribute("e", e);
		return "error";
	}
}
