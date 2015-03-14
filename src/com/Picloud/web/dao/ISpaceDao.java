package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.Space;

public interface ISpaceDao {
	public void add(Space space);
	public void delete(Space space);
	public void delete(String key);
	public Space find(String key);
	public List<Space> load(String uid);
	public void update(Space space);
}
