package com.Picloud.web.dao;

import com.Picloud.web.model.Mapfile;

public interface IMapfileDao {
	public void add(Mapfile mapfile);
	public Mapfile find(String key);
	public void update(Mapfile mapfile);
	void delete(Mapfile mapfile);
}
