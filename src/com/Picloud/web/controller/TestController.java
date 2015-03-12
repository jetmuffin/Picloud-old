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

import com.Picloud.config.HbaseConfig;
import com.Picloud.config.HdfsConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.web.dao.impl.UserDaoImpl;

/**
 * 测试Spring-mvc框架
 * 
 * @author jeff
 */

@Controller
public class TestController {
	@Autowired
	private UserDaoImpl user;
	
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
		user.find("123");
		return "test";
	}

	@RequestMapping(value = "/test1")
	public String test1() {
		// // 创建云图片表
		String name = "cloud_test";
		String[] column = { "attr", "var" };
		createTable(name, column);
		return "test";
	}

	@RequestMapping(value="/testhdfs")
	public String testhdfs() throws IOException{
		HdfsHandler hdfsUtil = new HdfsHandler(hdfsConfig.getFileSystemPath());
		return "test";
	}
	
	public void createTable(String tableName, String[] column) {
	
		Configuration mConfiguration = user.getHbaseTemplate().getConfiguration();

		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(mConfiguration);
			// 如果存在要创建的表，不做操作
			if (hBaseAdmin.tableExists(tableName)) {
				// System.out.println(tableName + " is exist....");
			} else {
				// 建列族
				HTableDescriptor tableDescriptor = new HTableDescriptor(
						TableName.valueOf(tableName));
				int num = column.length;
				for (int i = 0; i < num; i++) {
					// 添加列族
					tableDescriptor.addFamily(new HColumnDescriptor(column[i]));
				}
				hBaseAdmin.createTable(tableDescriptor);
				hBaseAdmin.close();
			}
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
