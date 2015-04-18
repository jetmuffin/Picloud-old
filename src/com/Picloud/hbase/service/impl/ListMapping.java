package com.Picloud.hbase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.springframework.stereotype.Service;

import com.Picloud.utils.StringSplit;
import com.Picloud.web.model.Dustbin;
import com.Picloud.web.model.HdImage;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.PanoImage;
import com.Picloud.web.model.PanoScene;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.ThreeDImage;

/**
 * 将数据库读出的数据映射到List
 * @author hadoop
 *
 */
@Service
public class ListMapping {
	
	/**
	 * 将数据库读出的数据映射到Image 的 List
	 * @param rs
	 * @return
	 */
	public List<Image> imageListMapping(ResultScanner rs){
		List<Image> list = new ArrayList<Image>();
		for (Result r : rs) {
			Image image = new Image();
			image.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
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
				}
				if (v.equals("updateTime")) {
					image.setUpdateTime(val);
				}
				if (v.equals("visitCount")) {
					image.setVisitCount(val);
				}
			}
				list.add(image);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到Space 的 List
	 * @param rs
	 * @return
	 */
	public List<Space> spaceListMapping(ResultScanner rs){
		List<Space> list = new ArrayList<Space>();
		for (Result r : rs) {
			Space space = new Space();
			space.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
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
			list.add(space);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到Log的 List
	 * @param rs
	 * @return
	 */
	public List<Log> logListMapping(ResultScanner rs){
		List<Log> list = new ArrayList<Log>();
		for (Result r : rs) {
			Log log = new Log();
			log.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("uid")) {
					log.setUid(val);
				}
				if (v.equals("time")) {
					log.setTime(val);
				}
				if (v.equals("operation")) {
					log.setOperation(val);
				}
			}
			list.add(log);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到HdImage的List
	 * @param rs
	 * @return
	 */
	public List<HdImage> hdImageListMapping(ResultScanner rs){
		List<HdImage> list = new ArrayList<HdImage>();
		for (Result r : rs) {
			HdImage hdImage = new HdImage();
			hdImage.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
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
			list.add(hdImage);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到ThreeDImage的List
	 * @param rs
	 * @return
	 */
	public List<ThreeDImage> ThreeDImageListMapping(ResultScanner rs){
		List<ThreeDImage> list = new ArrayList<ThreeDImage>();
		for (Result r : rs) {
			ThreeDImage threeDImage = new ThreeDImage();
			threeDImage.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
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
			list.add(threeDImage);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到PanoImage的List
	 * @param rs
	 * @return
	 */
	public List<PanoImage> panoListMapping(ResultScanner rs){
		List<PanoImage> list = new ArrayList<PanoImage>();
		for (Result r : rs) {
			PanoImage panoImage = new PanoImage();
			panoImage.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
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
			list.add(panoImage);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

	/**
	 * 将数据库读出的数据映射到Dustbin的List
	 * @param rs
	 * @return
	 */
	public List<Dustbin> dustbinListMapping(ResultScanner rs){
		List<Dustbin> list = new ArrayList<Dustbin>();
		for (Result r : rs) {
			Dustbin dustbin = new Dustbin();
			dustbin.setKey(new String(r.getRow()));
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("picName")) {
					dustbin.setPicName(val);
				}
				if (v.equals("mapfileName")) {
					dustbin.setMapfileName(val);
				}
			}
			list.add(dustbin);
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 将数据库读出的数据映射到Dustbin图片名字的List
	 * @param rs
	 * @return
	 */
	public List<String> dustbinPicNameListMapping(ResultScanner rs){
		List<String> list = new ArrayList<String>();
		for (Result r : rs) {
			for(Cell cell:r.rawCells()){
				String v = new String(CellUtil.cloneQualifier(cell));
				String val = new String(CellUtil.cloneValue(cell));
				if (v.equals("picName")) {
					list.add(val);
				}
			}
		}
		rs.close();
		if (list.size() == 0) {
			return null;
		}
		return list;
	}

}
