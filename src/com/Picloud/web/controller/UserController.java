package com.Picloud.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.exception.UserException;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDaoImpl mUserDaoImpl;
	private String module = "用户中心";
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String uid,String password,HttpSession session){
		User user = mUserDaoImpl.find(uid);
		if(user==null){
			throw new UserException("用户名不存在");
		} else if(!user.getPassword().equals(password)) {
			throw new UserException("用户名或密码错误");
		}
		session.setAttribute("LoginUser", user);
		session.removeAttribute("LOGIN_MSG");
		return "redirect:../index";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:../login.jsp";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(@ModelAttribute("user") User user){
		return "register";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(@Validated User user,BindingResult br){
		if(br.hasErrors()) {
			return "register";
		}
		if(mUserDaoImpl.find(user.getUid())!=null){
			throw new UserException("用户名已被使用");
		}
		mUserDaoImpl.add(user);
		return "test";
	}
	
	
	/**
	 * 查看个人信息
	 */
	@RequestMapping(value="/show",method=RequestMethod.GET)
	public String show(){
		return "user/show";
	}
	
	/**
	 * 个人信息修改页
	 */
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(Model model,@ModelAttribute("user") User user){
		model.addAttribute("action","帐号管理");
		model.addAttribute("module",module);
		return "user/update";
	}
	
	/**
	 * 修改个人信息
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Model model,@Validated User user,BindingResult br,HttpSession session,String pwd_old){
		model.addAttribute("action","帐号管理");
		model.addAttribute("module",module);

		User LoginUser = (User) session.getAttribute("LoginUser");
		if(br.hasErrors()) {
			return "user/update";
		}
		if(!pwd_old.equals(LoginUser.getPassword())){
			throw new UserException("原密码不正确");
		}

		mUserDaoImpl.update(user);
		session.setAttribute("LoginUser", user);
		return "redirect:/user/update";			
	}
	
	/**
	 * 查看个人日志
	 */
	@RequestMapping(value="/log",method=RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("action","操作日志");
		model.addAttribute("module",module);
		return "user/log";
	}
	
	@ExceptionHandler(value=(UserException.class))
	public String handlerException(UserException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
}
