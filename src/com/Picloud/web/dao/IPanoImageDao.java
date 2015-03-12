package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.PanoImage;

public interface IPanoImageDao {
	public void add(PanoImage panoImage);
	public PanoImage find(String key);
	public void delete(String key);
	public List<PanoImage> load(String uid);
}
