package com.Picloud.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.utils.DateUtil;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/")
public class IndexController {
	private String module = "主页";
	@Autowired
	private SpaceDaoImpl mSpaceDaoImpl;
	@Autowired
	private LogDaoImpl mLogDaoImpl;
	private String previousData;
	private String nowData;
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(Model model,HttpSession session){
		User user=(User) session.getAttribute("LoginUser");
		
		previousData=DateUtil.getPereviousDayMS();
		nowData=DateUtil.getCurrentDateMS();
		List<Space> space=mSpaceDaoImpl.load(user.getUid());
		List<Log> log=mLogDaoImpl.getByTime(user.getUid(),previousData ,nowData);
		//为了不影响使用，暂时将该方法注释掉，等到lastlogin方法写好以后打开
		//lastLogin(user,session);
		
		model.addAttribute("module",module);
		model.addAttribute("space",space);
		model.addAttribute("user",user);
		model.addAttribute("log",log);
		
		return "index";
	}
	
	private void lastLogin(User user,HttpSession session){
		String lastlogin=user.getLastLogin();
		String last=lastlogin.substring(6, 8);
		String now=DateUtil.getCurrentDateStr().substring(6, 8);
		int previousDay=Integer.getInteger(now)-Integer.getInteger(last);
		if(previousDay>0){
			session.setAttribute("lastlogin", previousDay+"天前");
		}
		else if(previousDay>0)
			session.setAttribute("lastlogin", "很久以前");
		else 
			session.setAttribute("lastlogin", lastlogin);
	
	}
}
