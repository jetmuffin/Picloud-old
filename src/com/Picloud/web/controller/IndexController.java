package com.Picloud.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.utils.JspUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
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
	@Autowired
	private ImageDaoImpl mImageDaoImpl;
	
	@RequestMapping(value={"/index","/"},method=RequestMethod.GET)
	public String index(Model model,HttpSession session){
		model.addAttribute("module",module);
		
		User user=(User) session.getAttribute("LoginUser");
		String yesterday = JspUtil.getPereviousDayMS();
		String today = JspUtil.getCurrentDateMS();
		
		List<Space> spaces = mSpaceDaoImpl.load(user.getUid());
		List<Log> logs = mLogDaoImpl.getByTime(user.getUid(),yesterday ,today);
		List<Image> recentImages = mImageDaoImpl.imagePageByTime(today, user.getUid(), 4);
		
		session.setAttribute("lastLogin", JspUtil.getLastTime(user.getLastLogin()));
		model.addAttribute("spaces",spaces);
		model.addAttribute("logs",logs);
		model.addAttribute("recentImages",recentImages);
		return "index";
	}
}
