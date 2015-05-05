package com.Picloud.memcached;

import com.Picloud.utils.PropertiesUtil;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class Memcached {
	private MemCachedClient mcc = null;
	
	public Memcached(){
		  mcc = new MemCachedClient();
		 String server = PropertiesUtil.getValue("memcached");
		 String [] servers = {server};
		// 设置服务器权重
	        Integer[] weights = {3, 2};

	        // 创建一个Socked连接池实例
	        SockIOPool pool = SockIOPool.getInstance();

	        pool.setServers(servers);
	        pool.setWeights(weights);
	        pool.setNagle(false);
	        pool.setSocketTO(3000);
	        pool.setSocketConnectTO(0);

	       // initialize the connection pool
	        pool.initialize();
	}
	
	public void flush(){
		mcc.flushAll();
	}
	
	public static void main(String[] args) {
		Memcached mcc = new Memcached();
		mcc.flush();
	}
}
