package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.Dustbin;

public interface IDustbinDao {

	public void add(Dustbin dustbin);
	public void delete(String mapfileName);
	public List<String> get(String mapfileName);
}
