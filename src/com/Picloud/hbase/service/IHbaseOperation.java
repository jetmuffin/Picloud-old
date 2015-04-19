package com.Picloud.hbase.service;


import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

public interface IHbaseOperation {

	/**
	 * 插入数据
	 * @param tableName 表名
	 * @param rowkey 主键
	 * @param rowfamily 列族
	 * @param row 列
	 * @param data 数据
	 */
    public void  insertData(String tableName, String rowkey, 
    		String rowfamily, String row,  String data);
    
    /**
     * 根据主键删除数据
     * @param tableName 表名
     * @param rowkey 主键
     */
     public void deleteRow(String tableName, String rowkey) ;
     
     /**
      * 根据主键查询信息
      * @param tableName 表名
      * @param rowkey 主键
      * @return
      */
     public Result queryByRowKey(String tableName, String rowkey);
     
     /**
      * 根据列和值检索表
      * @param tableName 表名
      * @param family 列族
      * @param column 列
      * @param val 值
      * @return
      */
     public  ResultScanner queryByColumn(String tableName,String family, String column, String val);
     
     /**
      * 删除表
      * @param tableName 表名
      */
     public void dropTable(String tableName) ;
     
     /**
      * 根据用户和时间范围检索日志
      * @param uid 用户id
      * @param min  起始时间
      * @param max 终止时间
      * @return
      */
      public  ResultScanner queryLog(String uid, String min, String max);
     /**
      * 根据space和uid检索图片
      * @param uid 用户id
      * @param space 图片空间
      * @return
      */
     public  ResultScanner queryImage( String space);
     
     /**
      * 查询某个用户某个时间段内上传的图片
      * @param uid 用户id
      * @param sTime 起始时间
      * @param eTime 终止时间
      * @return
      */
     public  ResultScanner queryLimitImage(String uid, String sTime, String eTime) ;
     
     /**
      * 根据图片名字前缀匹配检索图片
      * @param uid
      * @param subStr
      * @return
      */
     public ResultScanner imageNameMatching(String uid, String space, String subStr);
     
     /**
      * 日志分页
      * @param uid
      * @param rowkey 
      * @param num 每页数量
      * @return
      */
 	public ResultScanner logPage( String uid,String rowkey,  int num);
 	
 	/**
 	 * 根据时间图片分页
 	 * @param time
 	 * @param uid
 	 * @param num
 	 * @return
 	 */
 	public ResultScanner imagePageByTime(String time, String uid,int num);
 	
 	/**
 	 * 根据主键图片分页
 	 * @param uid
 	 * @param key
 	 * @param spaceId
 	 * @param num
 	 * @return
 	 */
 	public ResultScanner imagePageByKey( String uid, String key,String space,int num);

 	/**
 	 * 得到其他图片
 	 * @param spaceId
 	 * @param imageName
 	 * @param num 图片数量
 	 * @return
 	 */
	public ResultScanner  getOtherImages( String space,String imageName,int num);
}
