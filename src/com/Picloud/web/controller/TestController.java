package com.Picloud.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Picloud.config.HbaseConfig;

/**
 * 测试Spring-mvc框架
 * @author jeff
 */

@Controller
public class TestController {
	@RequestMapping({"/test[{width},{height}]"})
	public String hello(@PathVariable String width,@PathVariable String height,Model model){
		model.addAttribute("width", width);
		model.addAttribute("height", height);
		return "test";
	}
	
	@RequestMapping(value="/test")
	@Resource(name="hbaseConfig")
	public String test(HbaseConfig hbaseConfig){
		System.out.println(hbaseConfig);
		System.out.println("jjj");
		return "login";
	}
}
