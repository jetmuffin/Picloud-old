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
     public Result QueryByRowKey(String tableName, String rowkey);
     
     /**
      * 根据列和值检索表
      * @param tableName 表名
      * @param family 列族
      * @param column 列
      * @param val 值
      * @return
      */
     public  ResultScanner QueryByColumn(String tableName,String family, String column, String val);
     
     /**
      * 删除表
      * @param tableName 表名
      */
     public void dropTable(String tableName) ;
     
}
	
