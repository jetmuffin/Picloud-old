package com.Picloud.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;


public class Test {
	

	public String test(HbaseConfig hbaseConfig){
		System.out.println(hbaseConfig);
		return "login";
	}
	

}
