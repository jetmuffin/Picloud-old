package com.Picloud.web.dao.impl;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IMapfileDao;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;

@Repository
public class MapfileDaoImpl implements IMapfileDao {

	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	/**
	 * 将信息写入数据库
	 */
	@Override
	public void add(Mapfile mapfile) {
		if((mapfile == null)||(mapfile.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		mHbaseOperationImpl.insertData("cloud_mapfile",mapfile.getKey(), "attr", "name", mapfile.getName());
		mHbaseOperationImpl.insertData("cloud_mapfile",mapfile.getKey(), "attr", "uid", mapfile.getUid());
		mHbaseOperationImpl.insertData("cloud_mapfile",mapfile.getKey(), "var", "flagNum", mapfile.getFlagNum());
		mHbaseOperationImpl.insertData("cloud_mapfile",mapfile.getKey(), "var", "picNum", mapfile.getPicNum());

	}

	/**
	 * 根据主键检索信息
	 */
	@Override
	public Mapfile find(String key) {
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_mapfile", key);
		return mBeanMapping.mapfileMapping(rs, key);
	}

	@Override
	public void update(Mapfile mapfile) {
		add(mapfile);
		
	}

	/**
	 * 删除图片
	 */
	@Override
	public void delete(Mapfile mapfile) {
		mHbaseOperationImpl.deleteRow("cloud_mapfie", mapfile.getKey());
	}
}
