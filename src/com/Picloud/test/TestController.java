package com.Picloud.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping(value = "/mapfile", method = RequestMethod.POST)
	public String mapfile(){
		return "test";
	}
	
	@RequestMapping(value = "/sequencefile", method = RequestMethod.POST)
	public String sequencefile(){
		return "test";
	}
	
	@RequestMapping(value = "/namenode", method = RequestMethod.POST)
	public String namenode(){
		return "test";
	}
	
	@RequestMapping(value = "/harfile", method = RequestMethod.POST)
	public String harfile(){
		return "test";
	}
}
