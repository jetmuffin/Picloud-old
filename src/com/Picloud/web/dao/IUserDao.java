package com.Picloud.web.dao;

import com.Picloud.web.model.User;

public interface IUserDao {
	
	public void add(User user);
	public void update(User user);
	public void delete(String uid);
	public User find(String uid);
	
	//TODO 其他获取方法
}
