package com.Picloud.web.dao;

import java.util.List;

import com.Picloud.web.model.Log;

public interface ILogDao {
	public void add(Log log);
	public List<Log> getByUid(String uid); 
	public List<Log> getByTime(String user, String min, String max); 
	public List<Log> logPage(String uid, String row, int num);
}
