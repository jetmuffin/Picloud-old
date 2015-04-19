package com.Picloud.web.dao;

import java.util.List;

import org.apache.hadoop.hbase.client.ResultScanner;

import com.Picloud.web.model.Image;

public interface IImageDao {

	public void add(Image image);

	public void update(Image image);

	public void delete(Image image);

	public Image find(String key);

	public List<Image> load(String space);

	public List<Image> getByValue(String family, String column, String value);

	public List<Image> getByTime(String uid, String sTime, String eTime);

	public List<Image> imagePageByTime(String time, String uid, int num);

	public List<Image> imagePageByKey(String uid, String key, String space, int num);
	
	public List<Image> getOtherImages( String spaceId,String imageName,int num);

}
