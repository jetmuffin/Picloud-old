package com.Picloud.web.dao.impl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseAccessor;

/**
 * create hbase table
 * @author hadoop
 *
 */
public class CreateHbase {
	
	@Autowired
	private UserDaoImpl mUserDaoImpl;
	private HbaseAccessor  mHbaseAccessor = mUserDaoImpl.getHbaseTemplate();
	private  Configuration mConfiguration = mHbaseAccessor.getConfiguration();
	
//	public static void main(String[] args) {
////		// 创建云图片表
//		String name1 = "cloud_picture";
//		String[] column = { "attr", "var" };
//		createTable(name1, column);
//	}
	
	public  void createTable(String tableName, String[] column) {
		try {
			HBaseAdmin hBaseAdmin = new HBaseAdmin(mConfiguration);
			// 如果存在要创建的表，不做操作
			if (hBaseAdmin.tableExists(tableName)) {
				//System.out.println(tableName + " is exist....");
			} else {
				// 建列族
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				int num = column.length;
				for (int i = 0; i < num; i++) {
					//添加列族
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
