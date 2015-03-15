package com.Picloud.web.dao.impl;

import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Picloud.hbase.service.impl.BeanMapping;
import com.Picloud.hbase.service.impl.HbaseOperationImpl;
import com.Picloud.web.dao.IUserDao;
import com.Picloud.web.model.User;

@Repository
public class UserDaoImpl implements IUserDao {
	
	@Autowired
	private HbaseOperationImpl mHbaseOperationImpl;
	
	@Autowired
	private BeanMapping mBeanMapping;

	public UserDaoImpl() {
		super();
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
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "imageNum", user.getImageNum());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "imageTotalSize", user.getImageTotalSize());
		mHbaseOperationImpl.insertData("cloud_user", user.getUid(), "var", "spaceNum", user.getSpaceNum());
	}

	@Override
	public void update(User user) {
		add(user);
	}

	@Override
	public void delete(String uid) {
		mHbaseOperationImpl.deleteRow("cloud_user", uid);
	}

	@Override
	public User find(String uid) {
		Result result = mHbaseOperationImpl.queryByRowKey("cloud_user", uid);
		return mBeanMapping.UserMapping(result, uid);
	}

}
