package com.Picloud.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.web.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session){
		//TODO 用户信息验证
		//session.setAttribute("user", u);
		return "redirect:index";
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
		System.out.println(user);
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
	public String update(){
		return "user/update";
	}
	
	/**
	 * 修改个人信息
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@Validated User user,BindingResult br){
		if(br.hasErrors()) {
			return "user/update";
		}
		return "redirect:/user/show";			
	}
	
	/**
	 * 查看个人日志
	 */
	@RequestMapping(value="/log",method=RequestMethod.GET)
	public String list(){
		return "user/log";
	}
	
	//TODO 异常处理
//	@ExceptionHandler(value=(UserException.class))
//	public String handlerException(UserException e,HttpServletRequest req){
//		req.setAttribute("e", e);
//		return "error";
//	}
}
