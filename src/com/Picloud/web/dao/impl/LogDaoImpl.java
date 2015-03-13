package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.ILogDao;
import com.Picloud.web.model.Log;

@Repository
public class LogDaoImpl implements ILogDao{

	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	/**
	 * 将日志写入数据库
	 */
	@Override
	public void add(Log log) {
		if((log == null)||(log.getKey().equals(""))){
			//传值有问题，处理一下
			return ;
		}
		mHbaseOperationImpl.insertData("cloud_log", log.getKey(), "attr", "uid", log.getUid());
		mHbaseOperationImpl.insertData("cloud_log", log.getKey(), "attr", "time", log.getTime());
		mHbaseOperationImpl.insertData("cloud_log", log.getKey(), "attr", "operation", log.getOperation());
	}

	/**
	 * 检索某用户的所有日志
	 */
	@Override
	public List<Log> getByUid(String uid) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_log", "attr", "uid", uid);
		return mListMapping.logListMapping(rs);
	}

	/**
	 * 根据时间范围检索日志
	 */
	@Override
	public List<Log> getByTime(String uid, String min, String max) {
		ResultScanner rs = mHbaseOperationImpl.queryLog(uid, min, max);
		return mListMapping.logListMapping(rs);
	}

	/**
	 * 日志分页
	 */
	@Override
	public List<Log> logPage(String uid, String row, int num) {
		ResultScanner	rs = mHbaseOperationImpl.logPage(uid, row, num);
		return mListMapping.logListMapping(rs);
	}

}
