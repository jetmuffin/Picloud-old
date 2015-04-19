package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.ISpaceDao;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Repository
public class SpaceDaoImpl implements ISpaceDao {
	
	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	/**
	 * 将空间信息写入数据库
	 */
	@Override
	public void add(Space space) {
		if((space == null)||(space.getName().equals(""))){
			//传值有问题，处理一下
			return ;	
		}
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "attr", "name", space.getName());
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "attr", "desc", space.getDesc());
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "attr", "cover", space.getCover());
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "attr", "uid", space.getUid());
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "var", "storage", space.getStorage());
		mHbaseOperationImpl.insertData("cloud_space",space.getKey(), "var", "number", space.getNumber());

	}
	
	@Override
	public void update(Space space) {
		add(space);
	}

	/**
	 * 删除空间
	 */
	@Override
	public void delete(Space space) {
		mHbaseOperationImpl.deleteRow("cloud_space", space.getKey());
		}
	
	@Override
	public void delete(String key) {
		mHbaseOperationImpl.deleteRow("cloud_space", key);
	}
	/**
	 * 根据主键检索空间
	 */
	@Override
	public Space find(String key) {
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_space", key);
		return mBeanMapping.SpaceMapping(rs, key);
	}

	/**
	 * 检索某用户的所有空间
	 */
	@Override
	public List<Space> load(String uid) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_space", "attr", "uid",uid);
		return mListMapping.spaceListMapping(rs);
	}

	/**
	 * 搜索空间下的图片
	 */
	@Override
	public List<Image> search(String uid, String space, String subStr) {
		ResultScanner rs = mHbaseOperationImpl.imageNameMatching(uid, space, subStr);
		return mListMapping.imageListMapping(rs);
	}
}
