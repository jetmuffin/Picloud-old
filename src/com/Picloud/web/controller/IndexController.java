package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
	private String module = "主页";
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("module",module);
		return "index";
	}
}
