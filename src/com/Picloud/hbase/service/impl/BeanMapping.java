package com.Picloud.hbase.service.impl;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.stereotype.Service;

import com.Picloud.web.model.Space;
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
		System.out.println("BeanMapping的无参构造方法");
	}
	

	/**
	 * 将数据库读出的数据映射到Picture
	 * @param rs
	 * @param rowkey
	 * @return
	 */
//	public Picture PictureMapping(Result rs, String rowkey){
//		Picture pb = new Picture();
//		if (rs.isEmpty()) {
//			// 没有检索到，说明数据库中没有该图片，返回错误信息
//			return null;
//		} else {
//			pb.setKey(rowkey);
//			for(Cell cell:rs.rawCells()){
//				String v = new String(CellUtil.cloneQualifier(cell));
//				String val = new String(CellUtil.cloneValue(cell));
//				if (v.equals("name")) {
//					pb.setName(val);
//				}
//				if (v.equals("size")) {
//					pb.setSize(val);
//				}
//				if (v.equals("type")) {
//					pb.setType(val);
//				}
//				if (v.equals("space")) {
//					pb.setSpace(val);
//				}
//				if (v.equals("usr")) {
//					pb.setUsr(val);
//				}
//				if (v.equals("createTime")) {
//					pb.setCreateTime(val);
//				}
//				if (v.equals("path")) {
//					pb.setPath(val);
//				}
//				if (v.equals("status")) {
//					pb.setStatus(val);
//				}
//				if (v.equals("updateTime")) {
//					pb.setUpdateTime(val);
//				}
//				if (v.equals("visitCount")) {
//					pb.setVisitCount(val);
//				}
//			}
//		}
//		return pb;
//	}
//
//	/**
//	 * 将数据库读出的数据映射到Space
//	 * @param rs
//	 * @param rowkey
//	 * @return
//	 */
//	public Space SpaceMapping(Result rs,String rowkey){
//		Space sb = new Space();
//		if (rs.isEmpty()) {
//			// 没有检索到，说明数据库中没有该图片，返回错误信息
//			return null;
//		} else {
//			sb.setKey(rowkey);
//			for(Cell cell:rs.rawCells()){
//				String v = new String(CellUtil.cloneQualifier(cell));
//				String val = new String(CellUtil.cloneValue(cell));
//				if (v.equals("name")) {
//					sb.setName(val);
//				}
//				if (v.equals("desc")) {
//					sb.setDesc(val);
//				}
//				if (v.equals("cover")) {
//					sb.setCover(val);
//				}
//				if (v.equals("uid")) {
//					sb.setUid(val);
//				}
//				if (v.equals("storage")) {
//					sb.setStorage(val);
//				}
//				if (v.equals("number")) {
//					sb.setNumber(val);
//				}
//			}
//		}
//		return sb;
//	}


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
//	
//	/**
//	 * 将数据库读出的数据映射到MapfileBean
//	 * @param rs
//	 * @param rowkey
//	 * @return
//	 */
//	public MapfileBean mapfileMapping(Result rs,String rowkey){
//		MapfileBean mb = new MapfileBean();
//		if (rs.isEmpty()) {
//			// 没有检索到，说明数据库中没有该图片，返回错误信息
//			return null;
//		} else {
//			mb.setKey(rowkey);
//			for(Cell cell:rs.rawCells()){
//				String v = new String(CellUtil.cloneQualifier(cell));
//				String val = new String(CellUtil.cloneValue(cell));
//				if (v.equals("name")) {
//					mb.setName(val);
//				}
//				if (v.equals("uid")) {
//					mb.setUid(val);
//				}
//				if (v.equals("flagNum")) {
//					mb.setFlagNum(val);
//				}
//				if (v.equals("picNum")) {
//					mb.setPicNum(val);
//				}
//			}
//			return mb;
//		}
//	}
//	
//	public PanoBean panoBeanMapping(Result rs, String rowkey){
//		PanoBean pb = new PanoBean();
//		if (rs.isEmpty()) {
//			// 没有检索到，说明数据库中没有该图片，返回错误信息
//			return null;
//		} else {
//			pb.setKey(rowkey);
//			for(Cell cell:rs.rawCells()){
//				String v = new String(CellUtil.cloneQualifier(cell));
//				String val = new String(CellUtil.cloneValue(cell));
//				if (v.equals("name")) {
//					pb.setName(val);
//				}
//				if (v.equals("uid")) {
//					pb.setUid(val);
//				}
//				if (v.equals("createTime")) {
//					pb.setCreateTime(val);
//				}
//				if (v.equals("size")) {
//					pb.setSize(val);
//				}
//				if(v.equals("path")){
//					pb.setPath(val);
//				}
//			}
//		}
//		return pb;
//	}
//	
//	public Pic3DBean Pic3DMapping(Result rs, String rowkey){
//		Pic3DBean pb = new Pic3DBean();
//		if (rs.isEmpty()) {
//			// 没有检索到，说明数据库中没有该图片，返回错误信息
//			return null;
//		} else {
//			pb.setKey(rowkey);
//			for(Cell cell:rs.rawCells()){
//				String v = new String(CellUtil.cloneQualifier(cell));
//				String val = new String(CellUtil.cloneValue(cell));
//				if (v.equals("name")) {
//					pb.setName(val);
//				}
//				if (v.equals("uid")) {
//					pb.setUid(val);
//				}
//				if (v.equals("createTime")) {
//					pb.setCreateTime(val);
//				}
//				if (v.equals("size")) {
//					pb.setSize(val);
//				}
//				if (v.equals("num")) {
//					pb.setNum(val);;
//				}
//			}
//		}
//		return pb;
//	}
}
