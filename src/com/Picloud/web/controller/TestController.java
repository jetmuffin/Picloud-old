package com.Picloud.web.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Picloud.config.HdfsConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.User;

/**
 * 测试Spring-mvc框架
 * 
 * @author jeff
 */

@Controller
public class TestController {
	@Autowired
	private UserDaoImpl mUserDaoImpl;

	@Autowired
	private HdfsConfig hdfsConfig;
	
	@RequestMapping({ "/test[{width},{height}]" })
	public String hello(@PathVariable String width,
			@PathVariable String height, Model model) {
		model.addAttribute("width", width);
		model.addAttribute("height", height);
		return "test";
	}

	@RequestMapping(value = "/test")
	public String test() {
		System.out.println("Test");
		User user = new User();
		user.setUid("112314");
		user.setNickname("sunzequn");
		mUserDaoImpl.add(user);
		return "index";
	}

	@RequestMapping(value="/testhdfs")
	public String testhdfs() throws IOException{
		HdfsHandler hdfsUtil = new HdfsHandler(hdfsConfig.getFileSystemPath());
		return "test";
	}
	
}
