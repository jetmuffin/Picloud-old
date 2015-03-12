package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.ThreeDImage;

public interface IThreeDImageDao {
	public void add(ThreeDImage threeDImage);
	public void delete(ThreeDImage threeDImage);
	public ThreeDImage find(String key);
	public List<ThreeDImage> load(String uid);
}
