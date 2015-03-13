package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IThreeDImageDao;
import com.Picloud.web.model.ThreeDImage;

@Repository
public class ThreeDImageDao implements IThreeDImageDao {

	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;
	
	@Autowired
	private ListMapping mListMapping;
	
	/**
	 * 将图片信息写入数据库
	 */
	@Override
	public void add(ThreeDImage threeDImage) {
		if((threeDImage == null)||(threeDImage.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		mHbaseOperationImpl.insertData("cloud_threeD", threeDImage.getKey(), "attr", "name", threeDImage.getName());
		mHbaseOperationImpl.insertData("cloud_threeD", threeDImage.getKey(), "attr", "size", threeDImage.getSize());
		mHbaseOperationImpl.insertData("cloud_threeD", threeDImage.getKey(), "attr", "uid", threeDImage.getUid());
		mHbaseOperationImpl.insertData("cloud_threeD", threeDImage.getKey(), "attr", "createTime", threeDImage.getCreateTime());
		mHbaseOperationImpl.insertData("cloud_threeD", threeDImage.getKey(), "attr", "number", threeDImage.getNumber());

	}

	/**
	 * 删除图片
	 */
	@Override
	public void delete(ThreeDImage threeDImage) {
		mHbaseOperationImpl.deleteRow("cloud_threeD", threeDImage.getKey());

	}

	/**
	 * 根据主键检索图片
	 */
	@Override
	public ThreeDImage find(String key) {
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_threeD", key);
		return mBeanMapping.threeDImageMapping(rs, key);
	}

	@Override
	public List<ThreeDImage> load(String uid) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_threeD", "attr", "uid", uid);
		return mListMapping.ThreeDImageListMapping(rs);
	}

}
