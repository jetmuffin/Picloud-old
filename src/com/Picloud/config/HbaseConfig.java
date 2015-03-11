package com.Picloud.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HbaseConfig {
	private String clientPort;
	private String quorum;
	private String master;
	private Configuration configuration;
	
	
	
	public HbaseConfig() {
		super();
		configuration  = HBaseConfiguration.create(); 
	}
	public String getClientPort() {
		return clientPort;
	}
	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
		if(clientPort == null)
				System.out.println("null");
		configuration.set("hbase.zookeeper.property.clientPort", clientPort); 
	}
	public String getQuorum() {
		return quorum;

	}
	public void setQuorum(String quorum) {
		this.quorum = quorum;
		 configuration.set("hbase.zookeeper.quorum", quorum); 
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
        configuration.set("hbase.master", master); 
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	@Override
	public String toString() {
		return "HbaseConfig [clientPort=" + clientPort + ", quorum=" + quorum
				+ ", master=" + master + ", configuration=" + configuration
				+ "]";
	}
	
}
