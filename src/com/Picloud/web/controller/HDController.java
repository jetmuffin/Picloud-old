package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hd")
public class HDController {
	private String module = "应用中心";
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String  list(Model model){
		model.addAttribute("module", module);
		model.addAttribute("action", "高清图片");
		
		return "hd/list";
	}
}
