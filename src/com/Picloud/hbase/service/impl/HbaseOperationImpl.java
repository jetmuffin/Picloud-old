package com.Picloud.hbase.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import com.Picloud.hbase.service.IHbaseOperation;

import org.apache.hadoop.hbase.filter.SubstringComparator;

import com.Picloud.web.model.Image;

public class HbaseOperationImpl implements IHbaseOperation{
	
	private Configuration mConfiguration;
	public HbaseOperationImpl() {
	}
	
	public HbaseOperationImpl(HbaseTemplate mHbaseTemplate) {
		super();
		this.mConfiguration = mHbaseTemplate.getConfiguration();
	}

	/**
	 * 将数据写入数据库
	 */
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

	/**
	 * 删除某行数据
	 */
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

	/**
	 * 根据主键检索数据
	 */
	@Override
	public Result queryByRowKey(String tableName, String rowkey) {
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

	/**
	 * 根据某个列值检索数据
	 */
	@Override
	public ResultScanner queryByColumn(String tableName, String family,
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

	/**
	 * 删除某表
	 */
	@Override
	public void dropTable(String tableName) {
		   try { 
	             HBaseAdmin admin = new HBaseAdmin(mConfiguration); 
	             admin.disableTable(tableName); 
	             admin.deleteTable(tableName); 
	             System.out.println("delete  " + tableName);
//	             admin.close();
	         } catch (MasterNotRunningException e) { 
	             e.printStackTrace(); 
	         } catch (ZooKeeperConnectionException e) { 
	             e.printStackTrace(); 
	         } catch (IOException e) { 
	             e.printStackTrace(); 
	         } 
	}
	/**
 	 *  根据用户和时间范围检索日志
 	 */
     public  ResultScanner queryLog(String uid, String min, String max) { 
         try { 
        	 HTable table=new HTable(mConfiguration, "cloud_log");
             List<Filter> filters = new ArrayList<Filter>(); 
  
             Filter filter1 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("time"), 
                     CompareOp.GREATER_OR_EQUAL, Bytes .toBytes(min)); 
             filters.add(filter1); 
             
             Filter filter2 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("time"), 
                     CompareOp.LESS_OR_EQUAL, Bytes .toBytes(max)); 
             filters.add(filter2); 
             
             Filter filter3 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("user"), 
                     CompareOp.EQUAL, Bytes .toBytes(uid)); 
             filters.add(filter3); 
             
             FilterList filterList = new FilterList(filters); 
             Scan scan = new Scan(); 
             scan.setFilter(filterList); 
             ResultScanner rs = table.getScanner(scan); 
//             table.close();
             return rs;
         } catch (Exception e) { 
             e.printStackTrace(); 
             return null;
         } 
     } 
     
     /**
      * 根据space检索图片
      */
     @Override
     public  ResultScanner queryImage(String space) { 
         try { 
        	 HTable table=new HTable(mConfiguration, "cloud_image");
             List<Filter> filters = new ArrayList<Filter>(); 
  
             Filter filter2 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("space"), 
                     CompareOp.EQUAL, Bytes .toBytes(space)); 
             filters.add(filter2); 
             
             Filter filter3 = new SingleColumnValueFilter(Bytes 
                     .toBytes("var"), Bytes .toBytes("status"), 
                     CompareOp.NOT_EQUAL, Bytes .toBytes("deleted")); 
             filters.add(filter3); 
             
             FilterList filterList = new FilterList(filters); 
             Scan scan = new Scan(); 
             scan.setFilter(filterList); 
             ResultScanner rs = table.getScanner(scan);
//             table.close();
             return rs;
         } catch (Exception e) { 
             e.printStackTrace(); 
             return null;
         } 
     } 
     
    /**
     * 查询某个用户某个时间段内上传的图片
     */
     @Override
     public  ResultScanner queryLimitImage(String uid, String sTime, String eTime) { 
         try { 
        	 HTable table=new HTable(mConfiguration, "cloud_image");
             List<Filter> filters = new ArrayList<Filter>(); 
  
             Filter filter1 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("createTime"), 
                     CompareOp.GREATER_OR_EQUAL, Bytes .toBytes(sTime)); 
             filters.add(filter1); 
             
             Filter filter2 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("createTime"), 
                     CompareOp.LESS_OR_EQUAL, Bytes .toBytes(eTime)); 
             filters.add(filter2); 
             
             Filter filter3 = new SingleColumnValueFilter(Bytes 
                     .toBytes("attr"), Bytes .toBytes("usr"), 
                     CompareOp.EQUAL, Bytes .toBytes(uid)); 
             filters.add(filter3); 
             
             Filter filter4 = new SingleColumnValueFilter(Bytes 
                     .toBytes("var"), Bytes .toBytes("status"), 
                     CompareOp.NOT_EQUAL, Bytes .toBytes("deleted")); 
             filters.add(filter4); 
             
             FilterList filterList = new FilterList(filters); 
             Scan scan = new Scan(); 
             scan.setFilter(filterList); 
             ResultScanner rs = table.getScanner(scan); 
//             table.close();
             return rs;
         } catch (Exception e) { 
             e.printStackTrace(); 
             return null;
         } 
     } 
     
     /**
      * 根据图片名字前缀匹配检索图片
      */
     @Override
 	public ResultScanner imageNameMatching(String uid, String subStr) {
 		try {
 	        HTable table=new HTable(mConfiguration, "cloud_image"); 
 			List<Filter> filters = new ArrayList<Filter>();
 			
 			SubstringComparator comp = new SubstringComparator(subStr);
 			Filter filter = new SingleColumnValueFilter(Bytes.toBytes("attr"),
 					Bytes.toBytes("name"), CompareOp.EQUAL, comp);
 			filters.add(filter);

 			Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("attr"),
 					Bytes.toBytes("uid"), CompareOp.EQUAL, Bytes.toBytes(uid));
 			filters.add(filter1);

 			Scan s = new Scan();
 			FilterList filterList = new FilterList(filters);
 			s.setFilter(filterList);
 			ResultScanner rs =table.getScanner(s);
// 			table.close();
 			return rs;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
 	}
     
     /**
      * 日志分页
      */
 	@Override
 	public ResultScanner logPage( String uid,String rowkey,  int num){
		 try { 
			 HTable table=new HTable(mConfiguration, "cloud_log");
	            List<Filter> filters = new ArrayList<Filter>(); 
	 
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("time"), 
	                    CompareOp.LESS, Bytes .toBytes(rowkey)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("user"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = table.getScanner(scan); 
//	            table.close();
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}

 	/**
 	 * 根据时间图片分页
 	 */
 	@Override
	public ResultScanner imagePageByTime(String time, String uid, String spaceId,int num){
		 try { 
		        HTable table=new HTable(mConfiguration, "cloud_image"); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	 
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("createTime"), 
	                    CompareOp.LESS, Bytes .toBytes(time)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("uid"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            Filter filter3 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("space"), 
	                    CompareOp.EQUAL, Bytes .toBytes(spaceId)); 
	            filters.add(filter3); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs =table.getScanner(scan); 
//	            table.close();
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}

 	/**
 	 * 根据主键图片分页
 	 */
 	@Override
	public ResultScanner imagePageByKey( String uid, String key,String spaceId,int num){
		 try { 
		        HTable table=new HTable(mConfiguration, "cloud_image"); 
	            List<Filter> filters = new ArrayList<Filter>(); 
	            
	            Filter rf = new RowFilter(CompareOp.GREATER_OR_EQUAL, new BinaryComparator(key.getBytes()));
	            filters.add(rf);
	            
	            Filter filter1 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("space"), 
	                    CompareOp.EQUAL, Bytes .toBytes(spaceId)); 
	            filters.add(filter1); 
	            
	            Filter filter2 = new SingleColumnValueFilter(Bytes 
	                    .toBytes("attr"), Bytes .toBytes("uid"), 
	                    CompareOp.EQUAL, Bytes .toBytes(uid)); 
	            filters.add(filter2); 
	            
	            PageFilter pf = new PageFilter(num);
	            filters.add(pf);
	            
	            FilterList filterList = new FilterList(filters); 
	            Scan scan = new Scan(); 
	            scan.setFilter(filterList); 
	            ResultScanner rs = table.getScanner(scan); 
//	            table.close();
	            return rs;
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return null;
	        } 
	}
}
