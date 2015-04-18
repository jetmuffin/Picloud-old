package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Picloud.jxm.SystemState;
import com.Picloud.web.model.SystemStateInfo;

@Controller
@RequestMapping("/inspect")
public class InspectController {
	private String module = "系统监测";
	
	
	@RequestMapping(value="/cpu",method=RequestMethod.GET)
	public String cpu(Model model){
		model.addAttribute("module", module);
	
		return "inspect/cpu";
	}
	
	@RequestMapping(value="/hdfs.json",method=RequestMethod.GET)
	@ResponseBody
	public SystemStateInfo hdfs(){
		SystemStateInfo systemStateInfo = SystemState.getSystemState();
		return systemStateInfo;
	}
	
	@RequestMapping(value="/hdfs",method=RequestMethod.GET)
	public String hdfs(Model model){
		model.addAttribute("module", module);
		SystemStateInfo systemStateInfo = SystemState.getSystemState();
		System.out.println(systemStateInfo);
		model.addAttribute("systemState", systemStateInfo);
		return "inspect/hdfs";
	}
}
