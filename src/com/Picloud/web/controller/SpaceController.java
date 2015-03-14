package com.Picloud.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.Picloud.exception.SpaceException;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/space")
public class SpaceController {
	private String module = "图片服务器";
	@Autowired
	private SpaceDaoImpl mSpaceDaoImpl;
	@Autowired
	private UserDaoImpl mUserDaoImpl;
	
	@RequestMapping(value="/spaces",method=RequestMethod.GET)
	public String list(Model model,@ModelAttribute("space") Space space,HttpSession session){	
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		
		User LoginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(LoginUser.getUid());
		model.addAttribute("spaces",spaces);
		return "space/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Model model,@Validated Space space,BindingResult br,HttpSession session) throws Exception{
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		
		User LoginUser = (User) session.getAttribute("LoginUser");
		if(br.hasErrors()) {
			return "space/list";
		}
		
		//设定data = space.desc + "_" + uid , key = base64(data)
		String data = space.getName() + "_" + LoginUser.getUid();
		String key = EncryptUtil.encryptBASE64(data.getBytes());
		if(mSpaceDaoImpl.find(key)!=null){
			throw new SpaceException("该空间已存在！");
		}
		space.setKey(key);
		space.setUid(LoginUser.getUid());
		mSpaceDaoImpl.add(space);
		
		String spaceNum = LoginUser.getSpaceNum();
		LoginUser.setSpaceNum(Integer.toString(Integer.parseInt(spaceNum)+1));
		mUserDaoImpl.update(LoginUser);
		return "redirect:spaces";
	}
	
	@RequestMapping(value="/{spaceKey}",method=RequestMethod.GET)
	public String show(@PathVariable String spaceKey,Model model,HttpSession session) throws Exception{
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");

		Space space = mSpaceDaoImpl.find(spaceKey);
		model.addAttribute("activeSpace", space.getName());
		model.addAttribute(space);
		return "space/show";
	}
	
	@RequestMapping(value="/{spaceName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String spaceName,Model model,HttpSession session){
		model.addAttribute("module", module);
		model.addAttribute("action", "图片空间");
		model.addAttribute("activeSpace", spaceName);

		User LoginUser = (User) session.getAttribute("LoginUser");
		String key = spaceName + "_" + LoginUser.getUid();
		mSpaceDaoImpl.delete(key);
		return "redirect:/space/spaces";
	}
	
	@ExceptionHandler(value=(SpaceException.class))
	public String handlerException(SpaceException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
}
