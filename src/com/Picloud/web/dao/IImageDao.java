package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.Image;

public interface IImageDao {
	public void add(Image image);
	public void update(Image image);
	public void delete(Image image);
	public Image find(String key);
	public List<Image> load(String space);
}
