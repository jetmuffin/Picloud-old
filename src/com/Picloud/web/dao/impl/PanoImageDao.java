package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IPanoImageDao;
import com.Picloud.web.model.PanoImage;

@Repository
public class PanoImageDao implements IPanoImageDao{

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
	public void add(PanoImage panoImage) {
		if((panoImage == null)||(panoImage.getName().equals(""))){
			//传值有问题，处理一下
			return;
		}
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "name", panoImage.getName());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "number", panoImage.getNumber());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "uid", panoImage.getUid());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "createTime", panoImage.getCreateTime());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "path", panoImage.getPath());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "info", panoImage.getInfo());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "desc", panoImage.getDesc());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "sceneName", panoImage.getSceneName());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "mus_path", panoImage.getMus_path());
		mHbaseOperationImpl.insertData("cloud_pano", panoImage.getKey(), "attr", "type", panoImage.getType());
	}

	/**
	 * 根据主键检索信息
	 */
	@Override
	public PanoImage find(String key) {
		if (key == null)
			return null;
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_pano", key);
		return mBeanMapping.panoImageMapping(rs, key);
	}

	/**
	 * 删除PanoImage
	 */
	@Override
	public void delete(String key) {
		mHbaseOperationImpl.deleteRow("cloud_pano", key);
	}

	/**
	 * 检索用户的所有PanoImage
	 */
	@Override
	public List<PanoImage> load(String uid) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_pano", "attr", "uid", uid);
		return mListMapping.panoListMapping(rs);
	}

}
