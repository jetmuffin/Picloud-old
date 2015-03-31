package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/inspect")
public class InspectController {
	private String module = "系统监测";
	@RequestMapping(value="/cpu",method=RequestMethod.GET)
	public String cpu(Model model){
		model.addAttribute("module", module);
	
		return "inspect/cpu";
	}
}
