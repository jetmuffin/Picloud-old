package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping({"/test","/"})
	public String hello( String username,Model model){
		System.out.println("test");
		System.out.println(username);
		model.addAttribute("username", username);
		return "test";
	}
}
