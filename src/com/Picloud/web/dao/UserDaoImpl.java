package com.Picloud.web.dao;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import com.Picloud.web.model.User;

public class UserDaoImpl implements IUserDao {
	
	@Autowired
	private HbaseTemplate hbaseTemplate;
	
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String uid) {
		// TODO Auto-generated method stub

	}

	@Override
	public User find(String uid) {
			List<String> rows = hbaseTemplate.find("cloud_user", "vldt:nickname", new RowMapper<String>() {
			  @Override
			  public String mapRow(Result result, int rowNum) throws Exception {
			    return result.toString();
			  }
			});
		return null;
	}

}
