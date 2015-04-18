package com.Picloud.web.model;

public class DataNodeState {
	
	private String node;
	private String capacity;
	private String used;
	private String nonDFSUsed;
	private String remaining;
	private String blocks;
	private String blockPoolUsed;
	private String failedVolumes;
	private String version;
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getNonDFSUsed() {
		return nonDFSUsed;
	}
	public void setNonDFSUsed(String nonDFSUsed) {
		this.nonDFSUsed = nonDFSUsed;
	}
	public String getRemaining() {
		return remaining;
	}
	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}
	public String getBlocks() {
		return blocks;
	}
	public void setBlocks(String blocks) {
		this.blocks = blocks;
	}
	public String getBlockPoolUsed() {
		return blockPoolUsed;
	}
	public void setBlockPoolUsed(String blockPoolUsed) {
		this.blockPoolUsed = blockPoolUsed;
	}
	public String getFailedVolumes() {
		return failedVolumes;
	}
	public void setFailedVolumes(String failedVolumes) {
		this.failedVolumes = failedVolumes;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "DataNodeState [node=" + node + ", capacity=" + capacity
				+ ", used=" + used + ", nonDFSUsed=" + nonDFSUsed
				+ ", remaining=" + remaining + ", blocks=" + blocks
				+ ", blockPoolUsed=" + blockPoolUsed + ", failedVolumes="
				+ failedVolumes + ", version=" + version + "]";
	}
	
}
