package com.Picloud.hbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class DeleteAllHbase {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		HbaseOperationImpl hbaseOperationImpl = (HbaseOperationImpl) ctx.getBean("hbaseOperation");
		hbaseOperationImpl.dropTable("cloud_image");
		hbaseOperationImpl.dropTable("cloud_hd");
		hbaseOperationImpl.dropTable("cloud_user");
		hbaseOperationImpl.dropTable("cloud_log");
		hbaseOperationImpl.dropTable("cloud_mapfile");
		hbaseOperationImpl.dropTable("cloud_pano");
		hbaseOperationImpl.dropTable("cloud_space");
		hbaseOperationImpl.dropTable("cloud_threeD");
	}

}
