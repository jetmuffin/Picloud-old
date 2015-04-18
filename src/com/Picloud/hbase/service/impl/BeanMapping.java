package com.Picloud.hbase.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Service;

import com.Picloud.utils.StringSplit;
import com.Picloud.web.model.Dustbin;
import com.Picloud.web.model.HdImage;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.PanoScene;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.ThreeDImage;
import com.Picloud.web.model.User;


/**
 * 将数据库读出的数据映射到Bean
 * @author hadoop
 *
 */
@Service
public class BeanMapping {
	
	public BeanMapping() {
		super();
	}
	

	/**
	 * 将数据库读出的数据映射到Image
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public Image ImageMapping(Result rs, String rowkey){
		Image image = new Image();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			image.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					image.setName(val);
				}
				if (v.equals("size")) {
					image.setSize(val);
				}
				if (v.equals("type")) {
					image.setType(val);
				}
				if (v.equals("space")) {
					image.setSpace(val);
				}
				if (v.equals("uid")) {
					image.setUid(val);
				}
				if (v.equals("createTime")) {
					image.setCreateTime(val);
				}
				if (v.equals("width")) {
					image.setWidth(val);
				}
				if (v.equals("height")) {
					image.setHeight(val);
				}
				if (v.equals("path")) {
					image.setPath(val);
				}
				if (v.equals("status")) {
					image.setStatus(val);
				}
				if (v.equals("updateTime")) {
					image.setUpdateTime(val);
				}
				if (v.equals("visitCount")) {
					image.setVisitCount(val);
				}
			}
		}
		return image;
	}

	/**
	 * 将数据库读出的数据映射到Space
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public Space SpaceMapping(Result rs,String rowkey){
		Space space = new Space();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			space.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					space.setName(val);
				}
				if (v.equals("desc")) {
					space.setDesc(val);
				}
				if (v.equals("cover")) {
					space.setCover(val);
				}
				if (v.equals("uid")) {
					space.setUid(val);
				}
				if (v.equals("storage")) {
					space.setStorage(val);
				}
				if (v.equals("number")) {
					space.setNumber(val);
				}
			}
		}
		return space;
	}


	/**
	 * 将数据库读出的数据映射到User
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public User UserMapping(Result rs,String uid){
		User user = new User();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			user.setUid(uid);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("accountType")) {
					user.setAccountType(val);
				}
				if (v.equals("email")) {
					user.setEmail(val);
				}
				if (v.equals("lastLogin")) {
					user.setLastLogin(val);
				}
				if (v.equals("website")) {
					user.setWebsite(val);
				}
				if (v.equals("nickname")) {
					user.setNickname(val);
				}
				if (v.equals("password")) {
					user.setPassword(val);
				}
				if (v.equals("imageNum")) {
					user.setImageNum(val);
				}
				if (v.equals("imageTotalSize")) {
					user.setImageTotalSize(val);
				}
				if (v.equals("spaceNum")) {
					user.setSpaceNum(val);
				}
			}
		}
		return user;
		
	}
	
	/**
	 * 将数据库读出的数据映射到Mapfile
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public Mapfile mapfileMapping(Result rs,String rowkey){
		Mapfile mapfile = new Mapfile();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			mapfile.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					mapfile.setName(val);
				}
				if (v.equals("uid")) {
					mapfile.setUid(val);
				}
				if (v.equals("flagNum")) {
					mapfile.setFlagNum(val);
				}
				if (v.equals("picNum")) {
					mapfile.setPicNum(val);
				}
			}
			return mapfile;
		}
	}
	
	/**
	 * 将数据库读出的数据映射到PanoImage
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public PanoImage panoImageMapping(Result rs, String rowkey){
		PanoImage panoImage = new PanoImage();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			panoImage.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					panoImage.setName(val);
				}
				if (v.equals("uid")) {
					panoImage.setUid(val);
				}
				if (v.equals("createTime")) {
					panoImage.setCreateTime(val);
				}
				if (v.equals("number")) {
					panoImage.setNumber(val);
				}
				if(v.equals("path")){
					panoImage.setPath(val);
				}
				if(v.equals("info")){
					panoImage.setInfo(val);
				}
				if(v.equals("desc")){
					panoImage.setDesc(val);
				}
				if(v.equals("mus_path")){
					panoImage.setMus_path(val);
				}
				if(v.equals("sceneName")){
					panoImage.setSceneName(val);
				}
				if(v.equals("type")){
					panoImage.setType(val);
				}
			}
			panoImage.init();
		}
		return panoImage;
	}
	
	/**
	 * 将数据库读出的数据映射到ThreeDImage
	 * @param rs
	 * @param rowkey
	 * @return
	 */
	public ThreeDImage threeDImageMapping(Result rs, String rowkey){
		ThreeDImage threeDImage = new ThreeDImage();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			threeDImage.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					threeDImage.setName(val);
				}
				if (v.equals("uid")) {
					threeDImage.setUid(val);
				}
				if (v.equals("createTime")) {
					threeDImage.setCreateTime(val);
				}
				if (v.equals("size")) {
					threeDImage.setSize(val);
				}
				if (v.equals("number")) {
					threeDImage.setNumber(val);;
				}
			}
		}
		return threeDImage;
	}
	
	/**
	 * 将数据库读出的数据映射到HdImage
	 * @param rs
	 * @return
	 */
	public HdImage hdImageMapping(Result rs, String rowkey){
		HdImage hdImage = new HdImage();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			hdImage.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("name")) {
					hdImage.setName(val);
				}
				if (v.equals("uid")) {
					hdImage.setUid(val);
				}
				if (v.equals("createTime")) {
					hdImage.setCreateTime(val);
				}
				if (v.equals("size")) {
					hdImage.setSize(val);
				}
			}
		}
		return hdImage;
	}
	
	/**
	 * 将数据库读出的数据映射到Dustbin
	 * @param rs
	 * @return
	 */
	public Dustbin dustbinMapping(Result rs, String rowkey){
		Dustbin dustbin = new Dustbin();
		if (rs.isEmpty()) {
			// 没有检索到，说明数据库中没有该图片，返回错误信息
			return null;
		} else {
			dustbin.setKey(rowkey);
			for(Cell cell:rs.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("picName")) {
					dustbin.setPicName(val);
				}
				if (v.equals("mapfileName")) {
					dustbin.setMapfileName(val);
				}
			}
		}
		return dustbin;
	}
}