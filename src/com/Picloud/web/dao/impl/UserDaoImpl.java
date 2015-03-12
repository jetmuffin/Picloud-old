package com.Picloud.web.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import com.Picloud.hbase.utils.impl.BeanMapping;
import com.Picloud.hbase.utils.impl.HbaseOperationImpl;
import com.Picloud.web.dao.IUserDao;
import com.Picloud.web.model.User;

@Service
public class UserDaoImpl implements IUserDao {
	
	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;

	public UserDaoImpl() {
		super();
		System.out.println("UserDaoImpl的无参构造方法");
	}

	@Override
	public void add(User user) {
		if((user == null)||(user.getUid().equals(""))){
			//传值有问题，处理一下
			return ;
		}
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "attr", "accountType", user.getAccountType());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "attr", "email", user.getEmail());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "attr", "lastLogin", user.getLastLogin());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "attr", "website", user.getWebsite());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "nickname", user.getNickname());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "password", user.getPassword());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "imageNumm", user.getImageNum());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "imageTotalSize", user.getImageTotalSize());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "spaceNum", user.getSpaceNum());
	}

	@Override
	public void update(User user) {
		add(user);
	}

	@Override
	public void delete(String uid) {
		mHbaseOperationImpl.deleteRow("picloud_user", uid);
	}

	@Override
	public User find(String uid) {
		Result result = mHbaseOperationImpl.QueryByRowKey("cloud_user", uid);
		return mBeanMapping.UserMapping(result, uid);
	}

}
