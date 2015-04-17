package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IHdImageDao;
import com.Picloud.web.model.HdImage;

@Repository
public class HdImageDaoImpl implements IHdImageDao {
	
	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	/**
	 * 将HdImage信息写入数据库
	 */
	@Override
	public void add(HdImage hdImage) {
		if((hdImage == null)||(hdImage.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		mHbaseOperationImpl.insertData("cloud_hd", hdImage.getKey(), "attr", "name", hdImage.getName());
		mHbaseOperationImpl.insertData("cloud_hd", hdImage.getKey(), "attr", "size", hdImage.getSize());
		mHbaseOperationImpl.insertData("cloud_hd", hdImage.getKey(), "attr", "uid", hdImage.getUid());
		mHbaseOperationImpl.insertData("cloud_hd", hdImage.getKey(), "attr", "createTime", hdImage.getCreateTime());

	}

	/**
	 * 删除HdImage
	 */
	@Override
	public void delete(String key) {
		mHbaseOperationImpl.deleteRow("cloud_hd", key);
	}
	
	/**
	 * 根据主键检索
	 */
	@Override
	public HdImage find(String key) {
		if (key == null)
			return null;
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_hd", key);
		return mBeanMapping.hdImageMapping(rs, key);
	}
	
	/**
	 * 检索用户所有HdImage
	 */
	@Override
	public List<HdImage> load(String uid) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_hd", "attr", "uid", uid);
		return mListMapping.hdImageListMapping(rs);
	}

}
