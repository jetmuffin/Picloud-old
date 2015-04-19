package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IDustbinDao;
import com.Picloud.web.model.Dustbin;

@Repository
public class DustbinDaoImpl implements IDustbinDao{

	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	@Override
	public void add(Dustbin dustbin) {
		if((dustbin == null)||(dustbin.getMapfileName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		mHbaseOperationImpl.insertData("cloud_dustbin", dustbin.getKey(), "attr", "picName", dustbin.getPicName());
		mHbaseOperationImpl.insertData("cloud_dustbin", dustbin.getKey(), "attr", "mapfileName", dustbin.getMapfileName());
	}

	@Override
	public void delete(String mapfileName) {
		List<String> list = get(mapfileName);
		if(list == null)
				return;
		for(int i = 0; i < list.size(); i++){
			mHbaseOperationImpl.deleteRow("cloud_dustbin", mapfileName+list.get(i));
		}
	}

	@Override
	public List<String> get(String mapfileName) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_dustbin", "attr", "mapfile", mapfileName);
		return mListMapping.dustbinPicNameListMapping(rs);
	}

}
