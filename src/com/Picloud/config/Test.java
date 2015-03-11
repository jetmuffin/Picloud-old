package com.Picloud.config;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		
		ConfigurableApplicationContext ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/Picloud-servlet.xml");
		HbaseConfig hbaseConfig = (HbaseConfig) ctx.getBean("hbaseConfig");
		System.out.println(hbaseConfig);
		ctx.close();
	}

}
