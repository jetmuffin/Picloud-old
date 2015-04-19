package com.Picloud.web.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.hbase.service.impl.ListMapping;
import com.Picloud.web.dao.IImageDao;
import com.Picloud.web.model.Image;

@Repository
public class ImageDaoImpl implements IImageDao {

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
	public void add(Image image) {
		if((image == null)||(image.getName().equals(""))){
			//传值有问题，处理一下
			return ;	
		}
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "name", image.getName());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "size", image.getSize());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "type", image.getType());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "space", image.getSpace());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "createTime", image.getCreateTime());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "uid", image.getUid());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "path", image.getPath());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "width", image.getWidth());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "attr", "height", image.getHeight());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "var", "status", image.getStatus());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "var", "updateTime", image.getUpdateTime());
		mHbaseOperationImpl.insertData("cloud_image", image.getKey(), "var", "visitCount", image.getVisitCount());
	}
	
	/**
	 * 更新图片信息
	 */
	@Override
	public void update(Image image) {
		add(image);
	}

	/**
	 * 删除图片
	 */
	@Override
	public void delete(Image image) {
		mHbaseOperationImpl.deleteRow("cloud_image", image.getKey());
	}

	/**
	 * 根据主键检索图片
	 */
	@Override
	public Image find(String key) {
		if (key == null)
			return null;
		Result rs = mHbaseOperationImpl.queryByRowKey("cloud_image", key);
		return mBeanMapping.ImageMapping(rs, key);
	}
	
	/**
	 * 检索某空间下的所有图片
	 */
	@Override
	public List<Image> load(String spaceId) {
		ResultScanner rs = mHbaseOperationImpl.queryImage(spaceId);
		return mListMapping.imageListMapping(rs);
	}


	/**
	 * 根据图片的某个列值匹配检索图片
	 */
	@Override
	public List<Image> getByValue(String family, String column, String value) {
		ResultScanner rs = mHbaseOperationImpl.queryByColumn("cloud_image", family, column,value);
		return mListMapping.imageListMapping(rs);
	}

	/**
	 * 检索某用户某时间段内上传的图片
	 */
	@Override
	public List<Image> getByTime(String uid, String sTime, String eTime) {
		ResultScanner rs = mHbaseOperationImpl.queryLimitImage(uid, sTime, eTime);
		return mListMapping.imageListMapping(rs);
	}


	/**
	 * 根据时间分页
	 */
	@Override
	public List<Image> imagePageByTime(String time, String uid,
			int num) {
		ResultScanner rs = mHbaseOperationImpl.imagePageByTime(time, uid, num);
		return mListMapping.imageListMapping(rs);
	}

	/**
	 * 根据主键分页
	 */
	@Override
	public List<Image> imagePageByKey(String uid, String key, String space,
			int num) {
		ResultScanner rs = mHbaseOperationImpl.imagePageByKey(uid, key, space, num);
		return mListMapping.imageListMapping(rs);
	}

	/**
	 * 其他图片信息
	 */
	@Override
	public List<Image> getOtherImages(String spaceId, String imageName,int num) {
		ResultScanner rs = mHbaseOperationImpl.getOtherImages(spaceId, imageName,num);
		List<Image> otherImages =  mListMapping.imageListMapping(rs);
		if(otherImages==null)
				return null;
		if(otherImages.size()<=num)
				return otherImages;
		else{
			Collections.shuffle(otherImages);
			return otherImages.subList(0, num);
		}
	}
	

}
