package com.Picloud.hbase.utils.impl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import com.Picloud.hbase.utils.IHbaseOperation;

public class HbaseOperationImpl implements IHbaseOperation{
	
	private Configuration mConfiguration;
	public HbaseOperationImpl() {
		System.out.println("HbaseOperationImpl的无参构造方法");
	}
	
	public HbaseOperationImpl(HbaseTemplate mHbaseTemplate) {
		super();
		this.mConfiguration = mHbaseTemplate.getConfiguration();
	}

	@Override
	public void insertData(String tableName, String rowkey, String rowfamily,
			String row, String data) {
		 try {
			 	
				HBaseAdmin admin = new HBaseAdmin(mConfiguration);
				if(admin.tableExists(tableName)){
					HTable table=new HTable(mConfiguration, tableName);
		    		// 一个put代表一行数据，再new一个put表示第二行数据,每行一个唯一的rowkey
					Put put = new Put(rowkey.getBytes());
		    		// 本行数据的第一列 
		    		put.add(rowfamily.getBytes(), row.getBytes(), data.getBytes());
		    		table.put(put);
//		    		table.close();
//		    		admin.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
	}

	@Override
	public void deleteRow(String tableName, String rowkey) {
	    try {
			HBaseAdmin admin = new HBaseAdmin(mConfiguration);
			if(admin.tableExists(tableName)){
				HTable table=new HTable(mConfiguration, tableName);
				Delete delete = new Delete(Bytes.toBytes(rowkey));
	    		table.delete(delete);
//	    		table.close();
//	    		admin.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Result QueryByRowKey(String tableName, String rowkey) {
		try {
			HTable table=new HTable(mConfiguration, tableName);
			Get scan = new Get(rowkey.getBytes());
			Result r = table.get(scan);
//			table.close();
			return r;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultScanner QueryByColumn(String tableName, String family,
			String column, String val) {
		 try { 
        	 HTable table=new HTable(mConfiguration, tableName);
            // 当列colunm的值为val时进行查询 
             Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family),Bytes 
                     .toBytes(column), CompareOp.EQUAL, Bytes 
                     .toBytes(val)); 
             Scan s = new Scan(); 
             s.setFilter(filter); 
             ResultScanner rs = table.getScanner(s); 
//             table.close();
             return rs;
         } catch (Exception e) { 
             e.printStackTrace(); 
             return null;
         } 
	}

	@Override
	public void dropTable(String tableName) {
		   try { 
	             HBaseAdmin admin = new HBaseAdmin(mConfiguration); 
	             admin.disableTable(tableName); 
	             admin.deleteTable(tableName); 
//	             admin.close();
	         } catch (MasterNotRunningException e) { 
	             e.printStackTrace(); 
	         } catch (ZooKeeperConnectionException e) { 
	             e.printStackTrace(); 
	         } catch (IOException e) { 
	             e.printStackTrace(); 
	         } 
	}

}
