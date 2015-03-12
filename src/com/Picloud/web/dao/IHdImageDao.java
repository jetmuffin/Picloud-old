package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.HdImage;

public interface IHdImageDao {
	public HdImage find(String key);
	public void add(HdImage hdImage);
	public void delete(String key);
	public List<HdImage> load(String uid);
}
