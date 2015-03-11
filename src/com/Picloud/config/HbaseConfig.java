package com.Picloud.config;

import org.apache.hadoop.conf.Configuration;

public class HbaseConfig {
	private String clientPort;
	private String quorum;
	private String master;
	private Configuration configuration = null;
	
	public String getClientPort() {
		return clientPort;
	}
	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
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
